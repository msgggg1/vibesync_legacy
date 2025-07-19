package com.vibesync.member.util;

import java.io.InputStream;
import java.util.Properties;

public class Config {
    
    private static Properties prop = new Properties();

    static {
        try {
            String resource = "/config/google.properties"; 
            InputStream inputStream = Config.class.getResourceAsStream(resource);
            
            if (inputStream == null) {
                System.err.println("설정 파일을 찾을 수 없습니다: " + resource);
            } else {
                prop.load(inputStream);
                System.out.println("Google 설정 파일 로드 완료.");
                // [추가] 실제로 로드된 값을 콘솔에 출력해서 확인
                System.out.println("[Config 확인용 로그] 로드된 클라이언트 ID:" + prop.getProperty("google.client.id"));
                inputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getGoogleClientId() {
        return prop.getProperty("google.client.id");
    }
}