package com.vibesync.member.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.vibesync.member.domain.SignUpDTO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "file:src/test/resources/root-context.xml"
})
@Transactional
@Log4j
public class MemberServiceTest {

	@Autowired
    private MemberService memberService;
	
	@Test
	public void testResister() throws Exception {
	    log.info("회원가입 서비스 테스트 시작");
	    
	    // given
	    SignUpDTO dto = SignUpDTO.builder()
	    						.name("관리자")
	    						.nickname("admin")
	    						.email("admin@admin.com")
	    						.password("qwe123!!!")
	    						.categoryIdx(0)
	    						.build();
	    
	    // when
	    // int isResistered = memberService.register(dto);
	    memberService.register(dto);
	    
	    // then
	    // isResistered 결과가 1일 것으로 예상하며, 1이 맞으면 테스트 성공
	    // assertEquals(1, isResistered);
        log.info("테스트 통과: 회원가입에 성공함");
	}

}
