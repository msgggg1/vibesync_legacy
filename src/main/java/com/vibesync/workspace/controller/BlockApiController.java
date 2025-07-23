package com.vibesync.workspace.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vibesync.security.domain.CustomUser;
import com.vibesync.workspace.domain.BlockDTO;
import com.vibesync.workspace.domain.UserStatsBlockDTO;
import com.vibesync.workspace.service.BlockService;

@RestController
@RequestMapping("/api/block")
public class BlockApiController {
	
	@Autowired
	private BlockService blockService;

	// 블록 새로고침 (GET)
	@GetMapping("/{blockId}")
	public ResponseEntity<Map<String, Object>> getBlock(
			@PathVariable int blockId,
			@RequestParam String period,
			@AuthenticationPrincipal CustomUser user) {
		
		int acIdx = user.getAcIdx();
		
		BlockDTO blockData = blockService.getBlockContentAsDto(acIdx, blockId, period);
		
		if (blockData == null) {
			return ResponseEntity.notFound().build();
		}
		
		// HTML 렌더링 및 JSON 응답 구성
		Map<String, Object> response = blockService.renderBlockToJson(blockData, period);
		
		return ResponseEntity.ok(response);
	}

	// 블록 추가 (POST)
	@PostMapping
	public ResponseEntity<Map<String, Object>> addBlock(
			@RequestParam String blockType,
			@RequestParam String period,
			@RequestBody Map<String, Object> config,
			@AuthenticationPrincipal CustomUser user) {
		
		int acIdx = user.getAcIdx();
		
		// 블록 추가
		int newBlockId = blockService.addBlock(acIdx, blockType, config);
		
		if (newBlockId > 0) {
			// 새로 생성된 블록 데이터 조회
			BlockDTO newBlockData = blockService.getBlockContentAsDto(acIdx, newBlockId, period);
			
			// HTML 렌더링 및 JSON 응답 구성
			Map<String, Object> response = blockService.renderBlockToJson(newBlockData, period);
			response.put("block_id", newBlockId);
			
			return ResponseEntity.status(201).body(response);
		} else {
			return ResponseEntity.internalServerError().build();
		}
	}

	// 블록 삭제 (DELETE)
	@DeleteMapping("/{blockId}")
	public ResponseEntity<Map<String, Object>> deleteBlock(
			@PathVariable int blockId,
			@AuthenticationPrincipal CustomUser user) {
		
		int acIdx = user.getAcIdx();
		
		boolean isSuccess = blockService.removeBlock(acIdx, blockId);
		
		if (isSuccess) {
			return ResponseEntity.ok(Map.of("success", true, "message", "블록이 삭제되었습니다."));
		} else {
			return ResponseEntity.internalServerError()
					.body(Map.of("success", false, "message", "블록 삭제에 실패했거나 권한이 없습니다."));
		}
	}

	// 블록 순서 편집 (PUT)
	@PutMapping("/order")
	public ResponseEntity<Map<String, Object>> updateBlockOrder(
			@RequestBody List<Map<String, Object>> orders,
			@AuthenticationPrincipal CustomUser user) {
		
		int acIdx = user.getAcIdx();
		
		boolean isSuccess = blockService.changeBlockOrder(acIdx, orders);
		
		if (isSuccess) {
			return ResponseEntity.ok(Map.of("success", true));
		} else {
			return ResponseEntity.internalServerError()
					.body(Map.of("success", false, "message", "블록 순서 업데이트에 실패했습니다."));
		}
	}
	
	// 내 노트 조회 (GET)
	@GetMapping("/my-note")
	public ResponseEntity<Map<String, Object>> getMyNote(
			@AuthenticationPrincipal CustomUser user) {
		
		int acIdx = user.getAcIdx();
		
		// 내 노트 데이터 조회 로직
		Map<String, Object> noteData = blockService.getMyNoteData(acIdx);
		
		return ResponseEntity.ok(noteData);
	}
}
