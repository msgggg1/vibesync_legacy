package com.vibesync.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vibesync.board.domain.NoteVO;
import com.vibesync.board.service.NoteService;
import com.vibesync.common.domain.Criteria;
import com.vibesync.common.domain.PageDTO;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/board/*")
public class BoardController {
	
	@Autowired
	NoteService noteService;
	
	@GetMapping(value="/list")
	public String list(Model model, Criteria criteria) {
		log.info("게시글 목록 페이지 요청");
		// 게시글 목록
		List<NoteVO> noteList = this.noteService.findAllNotesWithPaging(criteria);
		model.addAttribute("noteList", noteList);
		
		// 페이징 블럭
		int endPage = (int) (Math.ceil(criteria.getPageNum() / (criteria.getAmount() * 1.0))) * criteria.getAmount();
		if (criteria.getPageNum() > endPage) {
			criteria.setPageNum(endPage == 0 ? 1 : endPage);
		}
		model.addAttribute("pageMaker", new PageDTO(criteria, this.noteService.getTotalCount(criteria)));
		return "board/list";
	}
}
