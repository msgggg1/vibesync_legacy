package com.vibesync.workspace.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vibesync.security.domain.CustomUser;
import com.vibesync.workspace.domain.BlockDTO;
import com.vibesync.workspace.domain.NoteSummaryDTO;
import com.vibesync.workspace.domain.UserStatsBlockDTO;
import com.vibesync.workspace.service.BlockService;
import com.vibesync.workspace.service.WorkspaceNoteService;

@RestController
@RequestMapping("/api/block")
public class BlockApiController {

    @Autowired
    private BlockService blockService;
    
    @Autowired
    private WorkspaceNoteService workspaceNoteService;
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    // GET 요청 처리 : 블록 새로고침
    @GetMapping("/{blockId}")
    public ResponseEntity<Map<String, Object>> getBlock(
            @PathVariable int blockId,
            @RequestParam String period,
            @AuthenticationPrincipal CustomUser user,
            HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        int acIdx = user.getAcIdx();

        // 1. 서비스 호출하여 블록 데이터 조회
        BlockDTO blockData = blockService.getBlockContentAsDto(acIdx, blockId, period);

        if (blockData == null) {
            return ResponseEntity.notFound().build();
        }

        // 2. JSP 프래그먼트를 HTML 문자열로 렌더링
        request.setAttribute("block", blockData);
        
        // UserStatsBlock인 경우, chartDataJson을 request에 담음
        if (blockData instanceof UserStatsBlockDTO) {
            String chartDataJsonString = objectMapper.writeValueAsString(((UserStatsBlockDTO) blockData).getChartData());
            request.setAttribute("chartDataJson", chartDataJsonString);
        }
        
        String forwardPath = "/WEB-INF/views/page/workspace/fragments/_" + blockData.getBlockType() + "Content.jsp";
        String htmlContent = renderJspToString(request, response, forwardPath);

        // 3. 클라이언트에 보낼 JSON 데이터 구성
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put("html", htmlContent);
        jsonResponse.put("blockType", blockData.getBlockType());

        // 4. 차트 블록인 경우, 차트 데이터도 JSON에 추가
        if (blockData instanceof UserStatsBlockDTO) {
            jsonResponse.put("chart_data", ((UserStatsBlockDTO) blockData).getChartData());
            jsonResponse.put("title", ((UserStatsBlockDTO) blockData).getTitle());
        }

        return ResponseEntity.ok(jsonResponse);
    }

    // 고정 블록 "더보기" 기능
    @GetMapping("/fixed/{blockType}/more")
    public ResponseEntity<List<NoteSummaryDTO>> getFixedBlockMoreData(
            @PathVariable String blockType,
            @AuthenticationPrincipal CustomUser user) {
        
        int acIdx = user.getAcIdx();
        List<NoteSummaryDTO> posts;
        
        if ("my-posts".equals(blockType)) {
            posts = workspaceNoteService.getAllMyPosts(acIdx);
        } else if ("liked-posts".equals(blockType)) {
            posts = workspaceNoteService.getAllLikedPosts(acIdx);
        } else {
            return ResponseEntity.badRequest().build();
        }
        
        return ResponseEntity.ok(posts);
    }

    // POST 요청 처리 : 블록 추가
    @PostMapping
    public ResponseEntity<Map<String, Object>> addBlock(
            @RequestParam String blockType,
            @RequestParam String period,
            @RequestBody Map<String, Object> config,
            @AuthenticationPrincipal CustomUser user,
            HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        int acIdx = user.getAcIdx();

        // config를 JSON 문자열로 변환
        String configJson = objectMapper.writeValueAsString(config);

        // DB에 블록 추가 후, 새로 생성된 block_id 획득
        int newBlockId = blockService.addBlock(acIdx, blockType, configJson);

        if (newBlockId > 0) {
            BlockDTO newBlockData = blockService.getBlockContentAsDto(acIdx, newBlockId, period);

            // JSP 프래그먼트를 HTML 문자열로 렌더링하기 위해 request에 데이터 설정
            request.setAttribute("block", newBlockData);
            if (newBlockData instanceof UserStatsBlockDTO) {
                String chartDataJsonString = objectMapper.writeValueAsString(((UserStatsBlockDTO) newBlockData).getChartData());
                request.setAttribute("chartDataJson", chartDataJsonString);
            }
            
            String forwardPath = "/WEB-INF/views/page/workspace/fragments/_blockWrapper.jsp";
            String htmlContent = renderJspToString(request, response, forwardPath);

            // 클라이언트에 보낼 JSON 데이터 구성
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("html", htmlContent);
            jsonResponse.put("blockType", newBlockData.getBlockType());
            jsonResponse.put("blockId", newBlockData.getBlockId());

            // 차트 블록인 경우, 차트 데이터도 JSON에 추가
            if (newBlockData instanceof UserStatsBlockDTO) {
                jsonResponse.put("chart_data", ((UserStatsBlockDTO) newBlockData).getChartData());
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(jsonResponse);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // DELETE 요청 : 블록 삭제
    @DeleteMapping("/{blockId}")
    public ResponseEntity<Map<String, Object>> deleteBlock(
            @PathVariable int blockId,
            @AuthenticationPrincipal CustomUser user) {

        int acIdx = user.getAcIdx();

        // 블록 삭제
        boolean isSuccess = blockService.removeBlock(acIdx, blockId);

        // JSON 결과 반환
        if (isSuccess) {
            return ResponseEntity.ok(Map.of("success", true, "message", "블록이 삭제되었습니다."));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "블록 삭제에 실패했거나 권한이 없습니다."));
        }
    }

    // 블록 순서 편집
    @PostMapping("/order")
    public ResponseEntity<Map<String, Object>> editBlockOrder(
            @RequestBody List<Map<String, Object>> orders,
            @AuthenticationPrincipal CustomUser user) {

        int acIdx = user.getAcIdx();

        if (orders == null || orders.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "순서 데이터가 없습니다."));
        }

        try {
            boolean isSuccess = blockService.changeBlockOrder(acIdx, orders);

            if (isSuccess) {
                return ResponseEntity.ok(Map.of("success", true));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("success", false, "message", "블록 순서 업데이트에 실패했습니다."));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "서버 오류가 발생했습니다."));
        }
    }


    // 고정 블록 데이터 조회
    @GetMapping("/fixed/{blockType}")
    public ResponseEntity<Map<String, Object>> getFixedBlockData(
            @PathVariable String blockType,
            @AuthenticationPrincipal CustomUser user,
            HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        int acIdx = user.getAcIdx();

        // 고정 블록 데이터 조회
        Map<String, Object> blockData = blockService.getFixedBlockData(acIdx, blockType);

        if (blockData == null) {
            return ResponseEntity.notFound().build();
        }

        // JSP 프래그먼트를 HTML 문자열로 렌더링
        request.setAttribute("blockData", blockData);
        
        String forwardPath = "/WEB-INF/views/page/workspace/fragments/_fixed" + blockType + "Content.jsp";
        String htmlContent = renderJspToString(request, response, forwardPath);

        // 클라이언트에 보낼 JSON 데이터 구성
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put("html", htmlContent);
        jsonResponse.put("blockType", blockType);

        return ResponseEntity.ok(jsonResponse);
    }

    // JSP 파일을 실행하여 그 결과를 HTML 문자열로 반환하는 헬퍼 메소드
    private String renderJspToString(HttpServletRequest request, HttpServletResponse response, String jspPath) 
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(jspPath);
        
        // 응답을 가로채기 위한 StringWriter와 Wrapper 생성
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        
        HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response) {
            @Override
            public PrintWriter getWriter() {
                return printWriter;
            }
        };

        // forward 대신 include를 사용하여 JSP의 실행 결과를 Wrapper에 담음
        dispatcher.include(request, responseWrapper);
        printWriter.flush();
        
        return stringWriter.toString();
    }
}
