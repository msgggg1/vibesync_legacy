package com.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.vibesync.login.domain.SignUpDTO;
import com.vibesync.login.mapper.UserMapper;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/security-context.xml", "file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@Log4j
@Transactional
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testUserMapper() {
        log.info("testUserMapper 실행");
        SignUpDTO dto = new SignUpDTO();
        dto.setEmail("test@test.com");
        dto.setPassword("1234");
        dto.setNickname("test");
        dto.setName("test");
        dto.setCategory_idx(1);
        int rowcount = userMapper.insertUser(dto);
        assertEquals(1, rowcount); 
        log.info(dto.toString());
    }
}