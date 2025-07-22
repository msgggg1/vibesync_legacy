package com.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
// 오직 security-context.xml 파일 하나만 로드합니다.
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/security-context.xml"})
public class PasswordEncoderTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testPasswordEncoderIsLoaded() {
        // 이 테스트는 passwordEncoder가 null이 아닌지만 확인합니다.
        // @Autowired 주입에 성공하면 테스트는 녹색불로 통과합니다.
        System.out.println("PasswordEncoder 빈: " + passwordEncoder);
        assertNotNull("PasswordEncoder 빈이 주입되지 않았습니다.", passwordEncoder);
    }
}