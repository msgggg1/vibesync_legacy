package com.vibesync.follow.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "file:src/test/resources/root-context.xml",
    "file:src/main/webapp/WEB-INF/spring/security-context.xml"
})
@Transactional
@Log4j
public class FollowServiceTest {

	@Autowired
    private FollowService followService;
	
	@Test
	public void testIsFollowing() {
	    log.info("팔로우 상태 확인 서비스 테스트 시작");
	    
	    // given - 유저 아이디 1, 2
	    int user1 = 1;
	    int user2 = 2;
	    
	    // when
	    boolean isFollowing = followService.isFollowing(user1, user2);
	    
	    // then
	    log.info("팔로우 여부: " + (isFollowing ? "팔로우 중" : "팔로우 중 아님"));
	}

}
