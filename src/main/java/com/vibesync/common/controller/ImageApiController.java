package com.vibesync.common.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vibesync.common.service.ImageStorageService;

@RestController
@RequestMapping("/api/images")
public class ImageApiController {

	@Autowired
	ImageStorageService imageStorageService;
	
	@PostMapping("/upload/note/{noteIdx}")
    public ResponseEntity<Map<String, Object>> uploadNoteImage(
            @RequestParam("image") MultipartFile imageFile,
            @PathVariable("noteIdx") int noteIdx) {
        
        // 1. 파일을 서버에 저장 (e.g., /upload/note/...)
		String savedUrl = imageStorageService.store(imageFile, "note", noteIdx);
		
        // 2. Editor.js 이미지 플러그인이 요구하는 JSON 형식으로 응답
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> fileData = new HashMap<>();
        
        response.put("success", 1);
        fileData.put("url", savedUrl); // 저장된 이미지의 웹 경로
        response.put("file", fileData);
        
        return ResponseEntity.ok(response);
        
    }
	
}
