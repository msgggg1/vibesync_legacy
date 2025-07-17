package com.vibesync.watchparty.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vibesync.watchparty.domain.WaSyncVO;
import com.vibesync.watchparty.mapper.WaSyncMapper;



@Component
public class WaSyncHandler extends TextWebSocketHandler {

    private final ObjectMapper mapper;
    private final WaSyncMapper syncMapper;

    /**
     * watchPartyIdx 별로 연결된 세션들을 관리
     */
    private static final Map<Integer, Set<WebSocketSession>> partySessions =
            Collections.synchronizedMap(new HashMap<>());

    @Autowired
    public WaSyncHandler(ObjectMapper mapper,
                         WaSyncMapper syncMapper) {
        this.mapper = mapper;
        this.syncMapper = syncMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // initSync 메시지를 받을 때까지는 별도 처리 없음
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        JsonNode json = mapper.readTree(message.getPayload());
        String type = json.get("type").asText();
        int wpIdx = json.get("watchPartyIdx").asInt();

        if ("initSync".equals(type)) {
            // MyBatis mapper로부터 마지막 동기화 상태 조회
            WaSyncVO lastSync = syncMapper.selectLatestByWatchParty(wpIdx);

            // 조회된 상태를 클라이언트에 전달
            if (lastSync != null) {
                Map<String, Object> resp = new HashMap<>();
                resp.put("type", "sync");
                resp.put("timeline", lastSync.getTimeline());
                resp.put("play", lastSync.getPlay());
                session.sendMessage(new TextMessage(mapper.writeValueAsString(resp)));
            }

            // 세션을 해당 파티에 등록
            addSession(wpIdx, session);
        }
        else if ("sync".equals(type)) {
            double timeline = json.get("timeline").asDouble();
            String playState = json.get("play").asText();

            // MyBatis mapper로 현재 상태 upsert
            WaSyncVO newSync = new WaSyncVO();
            newSync.setWatchPartyIdx(wpIdx);
            newSync.setTimeline(timeline);
            newSync.setPlay(playState);
            syncMapper.upsertByWatchParty(newSync);

            // 모든 클라이언트에 브로드캐스트
            Map<String, Object> broadcastMsg = new HashMap<>();
            broadcastMsg.put("type", "sync");
            broadcastMsg.put("watchPartyIdx", wpIdx);
            broadcastMsg.put("timeline", timeline);
            broadcastMsg.put("play", playState);

            String jsonStr = mapper.writeValueAsString(broadcastMsg);
            broadcastToParty(wpIdx, new TextMessage(jsonStr));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        removeSession(session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        exception.printStackTrace();
        removeSession(session);
    }

    /** 세션을 해당 watchPartyIdx 그룹에 추가 */
    private void addSession(int wpIdx, WebSocketSession session) {
        partySessions
            .computeIfAbsent(wpIdx, k -> Collections.synchronizedSet(new HashSet<>()))
            .add(session);
        session.getAttributes().put("watchPartyIdx", wpIdx);
    }

    /** 세션을 그룹에서 제거 */
    private void removeSession(WebSocketSession session) {
        Object attr = session.getAttributes().get("watchPartyIdx");
        if (attr instanceof Integer) {
            int wpIdx = (Integer) attr;
            Set<WebSocketSession> set = partySessions.get(wpIdx);
            if (set != null) {
                set.remove(session);
                if (set.isEmpty()) {
                    partySessions.remove(wpIdx);
                }
            }
        }
    }

    /** 특정 파티에 속한 모든 세션에 메시지 전송 */
    private void broadcastToParty(int wpIdx, TextMessage message) {
        Set<WebSocketSession> sessions = partySessions.get(wpIdx);
        if (sessions != null) {
            // ConcurrentModificationException 방지를 위해 복사본으로 순회
            for (WebSocketSession s : new HashSet<>(sessions)) {
                try {
                    if (s.isOpen()) {
                        s.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
