package com.vibesync.watchparty.controller;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vibesync.watchparty.domain.WaCommentVO;
import com.vibesync.watchparty.mapper.WaCommentMapper;



@Component
public class WaCommentHandler extends TextWebSocketHandler {

    private final ObjectMapper mapper;
    private final WaCommentMapper commentMapper;

    /**
     * watchPartyIdx 별로 연결된 세션들을 관리
     */
    private static final Map<Integer, Set<WebSocketSession>> partySessions =
            Collections.synchronizedMap(new HashMap<>());

    @Autowired
    public WaCommentHandler(ObjectMapper mapper,
                            WaCommentMapper commentMapper) {
        this.mapper = mapper;
        this.commentMapper = commentMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // initComment 메시지를 받을 때까지 별도 작업 없음
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        JsonNode json = mapper.readTree(message.getPayload());
        String type = json.get("type").asText();
        int wpIdx = json.get("watchPartyIdx").asInt();

        if ("initComment".equals(type)) {
            // 1) 세션 등록
            addSession(wpIdx, session);

            // 2) 과거 댓글 조회
            List<WaCommentVO> oldComments = commentMapper.selectByWatchParty(wpIdx);

            // 3) initCommentList 메시지 생성
            ArrayNode arr = mapper.createArrayNode();
            for (WaCommentVO c : oldComments) {
                JsonNode obj = mapper.createObjectNode()
                    .put("nickname", c.getNickname())
                    .put("chatting", c.getChatting())
                    .put("timeline", c.getTimeline());
                if (c.getCreatedAt() != null) {
                    ((ObjectNode)obj).put("createdAt", c.getCreatedAt().toString());
                }
                arr.add(obj);
            }
            JsonNode resp = mapper.createObjectNode()
                .put("type", "initCommentList")
                .set("comments", arr);
            session.sendMessage(new TextMessage(mapper.writeValueAsString(resp)));
        }
        else if ("comment".equals(type)) {
            // 1) 새 댓글 저장
            String nickname = json.get("nickname").asText();
            String chatText = json.get("chatting").asText();
            double timeline = json.get("timeline").asDouble();

            WaCommentVO wc = new WaCommentVO();
            wc.setWatchPartyIdx(wpIdx);
            wc.setNickname(nickname);
            wc.setChatting(chatText);
            wc.setTimeline(timeline);
            commentMapper.insert(wc);

            // 2) 브로드캐스트 메시지 생성
            JsonNode broadcastMsg = mapper.createObjectNode()
                .put("type", "comment")
                .put("nickname", nickname)
                .put("chatting", chatText)
                .put("timeline", timeline)
                .put("timestamp", new Date().toString());

            broadcastToParty(wpIdx, new TextMessage(mapper.writeValueAsString(broadcastMsg)));
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
            // ConcurrentModificationException 방지를 위해 복사하여 순회
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
