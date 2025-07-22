package com.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.vibesync.member.domain.MemberVO;
import com.vibesync.member.mapper.MemberMapper;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/security-context.xml", "file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@Log4j
@Transactional
public class UserMapperTest {

    @Autowired
    private MemberMapper userMapper;

    @Test
    public void testUserMapper() {
        log.info("testUserMapper 실행");
        MemberVO user = userMapper.findVOByEmail("test@test.com");
     
        log.info(user.toString());
    }
}