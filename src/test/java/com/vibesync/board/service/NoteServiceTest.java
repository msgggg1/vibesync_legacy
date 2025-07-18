package com.vibesync.board.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.vibesync.board.domain.NoteVO;
import com.vibesync.common.domain.Criteria;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "file:src/main/webapp/WEB-INF/spring/root-context.xml",
    "file:src/main/webapp/WEB-INF/spring/security-context.xml"
})
@Transactional
@Log4j
public class NoteServiceTest {

	@Autowired
    private NoteService noteService;
	
	@Test
	public void testGetNoteList() {
		log.info("게시글 목록 조회 서비스 테스트 시작");
		List<NoteVO> noteList = noteService.findAllNotes();
		log.info("조회된 게시글 목록: " + noteList);
	}
	
	@Test
	public void testFindAllNotesWithPaging() {
	    log.info("페이징 처리된 게시글 목록 조회 서비스 테스트 시작");
	    
	    // given - 2페이지, 10개씩 보는 조건
	    Criteria criteria = new Criteria(2, 10);
	    
	    // when
	    List<NoteVO> noteList = noteService.findAllNotesWithPaging(criteria);
	    
	    // then
	    log.info("조회된 페이징 목록: " + noteList);
	    assertNotNull("리스트는 null이 아니어야 합니다.", noteList);
	    assertTrue("조회된 게시글의 수는 10개 이하여야 합니다.", noteList.size() <= 10);
	}

}
