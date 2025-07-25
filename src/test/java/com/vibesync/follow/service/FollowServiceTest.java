package com.vibesync.follow.service;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "file:src/test/resources/root-context.xml"
})
@Transactional
@Log4j
public class FollowServiceTest {

	@Autowired
    private FollowService followService;
	
	@Test
	public void testIsFollowing() {
	    log.info("팔로우 상태 확인 서비스 테스트 시작");
	    
	    // given - 유저 아이디 0, 1
	    int user1 = 0;
	    int user2 = 1;
	    
	    // when
	    boolean isFollowing = followService.isFollowing(user1, user2);
	    
	    // then
	    // isFollowing 결과가 false일 것으로 예상하며, false가 맞으면 테스트 성공
        assertFalse(isFollowing); 
        log.info("팔로우 여부: " + (isFollowing ? "팔로우 중" : "팔로우 중 아님"));
        log.info("테스트 통과: 0번 유저는 1번 유저를 팔로우하지 않음");
	}

}
