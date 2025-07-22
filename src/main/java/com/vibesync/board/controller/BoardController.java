package com.vibesync.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vibesync.board.domain.NoteDetailDTO;
import com.vibesync.board.domain.NoteListDTO;
import com.vibesync.board.service.NoteService;
import com.vibesync.common.annotation.AuthenticatedUserPages;
import com.vibesync.common.domain.ContentVO;
import com.vibesync.common.domain.Criteria;
import com.vibesync.common.domain.GenreVO;
import com.vibesync.common.domain.PageDTO;
import com.vibesync.common.service.ContentService;
import com.vibesync.common.service.GenreService;
import com.vibesync.security.domain.CustomUser;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/board/*")
@AuthenticatedUserPages
public class BoardController {
	
	@Autowired
	NoteService noteService;
	
	@Autowired
	GenreService genreService;
	
	@Autowired
	ContentService contentService;
	
	// 게시글 목록 (/vibesync/board/list)
	@GetMapping(value="/list")
	public String list(Model model, Criteria criteria) {
		log.info("게시글 목록 페이지 요청");
		
		// 게시글 목록
		List<NoteListDTO> noteList = this.noteService.findAllNotesWithPaging(criteria);
		model.addAttribute("noteList", noteList);
		
		// 페이징 블럭
		int endPage = (int) (Math.ceil(this.noteService.getTotalCount(criteria) / (criteria.getAmount() * 1.0))) * criteria.getAmount();
		if (criteria.getPageNum() > endPage) {
			criteria.setPageNum(endPage == 0 ? 1 : endPage);
		}
		model.addAttribute("pageMaker", new PageDTO(criteria, this.noteService.getTotalCount(criteria)));
		
		return "board/list";
	}

	// 게시글 상세보기 (/vibesync/board/view)
	@GetMapping(value="/view")
	public String view(Model model, Criteria criteria, @RequestParam("noteIdx") int noteIdx, @AuthenticationPrincipal CustomUser user) {
		log.info("게시글 상세보기 페이지 요청");
		
		// 게시글 상세보기
		NoteDetailDTO noteDetail = this.noteService.findNoteByNoteIdx(noteIdx);
		model.addAttribute("noteDetail", noteDetail);
		
		return "board/view";
	}
	
	
	// 게시글 작성 (/vibesync/board/write)
	@GetMapping(value="/write")
	public String write(Model model) {
		log.info("게시글 작성 페이지 요청");
		
		// 장르 목록
		List<GenreVO> genreList = this.genreService.findAll();
		model.addAttribute("genreList", genreList);

		// 컨텐츠 목록
		List<ContentVO> contentList = this.contentService.findAll();
		model.addAttribute("contentList", contentList);
		
		return "board/write";
	}
	
}
