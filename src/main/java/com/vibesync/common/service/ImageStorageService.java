package com.vibesync.common.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageService {
	
    /**
     * MultipartFile을 서버에 저장하고, 웹 접근 경로를 반환
     * @param multipartFile 저장할 이미지 파일
     * @param subPath 저장할 하위 경로 (예: "notes", "profiles")
     * @param noteIdx 하위 경로에 사용할 노트 번호
     * @return 저장된 파일의 웹 접근 경로 (예: /upload/note/(noteIdx)/uuid_filename.jpg)
     */
    String store(MultipartFile multipartFile, String subPath, int noteIdx);
    
}