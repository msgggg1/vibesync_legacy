package com.vibesync.board.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.vibesync.board.domain.NoteVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "file:src/main/webapp/WEB-INF/spring/root-context.xml"
})
@Transactional
@Log4j
public class NoteServiceTest {

	@Autowired
    private NoteService noteService;
	
	@Test
	public void testGetNoteList() {
		log.info("게시글 목록 조회 서비스 테스트 시작");
		List<NoteVO> noteList = noteService.getNoteList();
		log.info("조회된 게시글 목록: " + noteList);
	}

}
