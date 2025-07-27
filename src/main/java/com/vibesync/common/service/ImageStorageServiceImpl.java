// 위치: com.vibesync.common.service/ImageStorageServiceImpl.java
package com.vibesync.common.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageStorageServiceImpl implements ImageStorageService {

    // root-context.xml 이나 properties 파일에 설정된 업로드 루트 경로를 주입받음
    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public String store(MultipartFile multipartFile, String subPath) {
        if (multipartFile.isEmpty()) {
            return null;
        }

        try {
            // 1. 고유한 파일 이름 생성
            String originalFilename = multipartFile.getOriginalFilename();
            String storeFilename = createStoreFileName(originalFilename);

            // 2. 저장할 전체 경로 설정 (루트 경로 + 하위 경로 + 파일 이름)
            File uploadDir = new File(uploadPath + File.separator + subPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs(); // 하위 폴더가 없으면 생성
            }
            File dest = new File(uploadDir, storeFilename);
            
            // 3. 파일 저장
            multipartFile.transferTo(dest);
            
            // 4. 웹에서 접근 가능한 상대 경로 반환
            // (주의: File.separator는 OS마다 다르므로 URL 경로인 '/'로 교체)
            return ("/upload/" + subPath + "/" + storeFilename).replace(File.separator, "/");

        } catch (IOException e) {
            // 파일 저장 중 에러 처리
            throw new RuntimeException("파일을 저장하는 데 실패했습니다.", e);
        }
    }

    // 고유한 파일 이름을 만들기 위한 UUID 생성
    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    // 확장자 추출
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
    
}