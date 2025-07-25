package com.vibesync.board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vibesync.board.domain.BoardEditFormDTO;
import com.vibesync.board.domain.BoardEditRequestDTO;
import com.vibesync.board.domain.BoardViewDTO;
import com.vibesync.board.domain.BoardWriteFormDTO;
import com.vibesync.board.domain.BoardWriteRequestDTO;
import com.vibesync.board.domain.NoteDetailDTO;
import com.vibesync.board.domain.NoteListDTO;
import com.vibesync.board.service.NoteService;
import com.vibesync.common.annotation.AuthenticatedUserPages;
import com.vibesync.common.domain.Criteria;
import com.vibesync.common.domain.PageDTO;
import com.vibesync.follow.service.FollowService;
import com.vibesync.security.domain.CustomUser;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/board/*")
@AuthenticatedUserPages
@RequiredArgsConstructor
public class BoardController {
	
	@Autowired
	NoteService noteService;
	@Autowired
	FollowService followService;
	
	// 게시글 목록 (/vibesync/board/list)
	@GetMapping(value="/list")
	public String list(Model model, Criteria criteria) {
		log.info("게시글 목록 페이지 요청...GET");
		
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
	public String view(Model model, Criteria criteria, @RequestParam("noteIdx") int noteIdx,
						@AuthenticationPrincipal CustomUser user) {
		log.info("게시글 상세보기 페이지 요청...GET");
		
		// 게시글 상세보기
		NoteDetailDTO noteDetail = this.noteService.findNoteByNoteIdx(noteIdx);
		int userAcIdx = 0;
		boolean isFollowing = false;
		boolean isLiking = false;
		if (user != null) {
			userAcIdx = user.getAcIdx();
			isFollowing = this.followService.isFollowing(userAcIdx, noteDetail.getMember().getAcIdx());
			// isLiking = this.likeService.isLiking();
		}
		BoardViewDTO dto = BoardViewDTO.builder()
										.noteDetail(noteDetail)
										.userAcIdx(userAcIdx)
										.following(isFollowing)
										.liking(isLiking)
										.build();
		
		log.info("게시글 상세보기 페이지 DTO : " + dto);
		
		model.addAttribute("boardViewDTO", dto);
		
		return "board/view";
	}
	
	// 게시글 작성 (/vibesync/board/write)
	@GetMapping(value="/write")
	public String write(Model model, @AuthenticationPrincipal CustomUser user) {
		log.info("게시글 작성 페이지 요청...GET");
		
		BoardWriteFormDTO dto = BoardWriteFormDTO.builder()
											.acIdx(user.getAcIdx())
											.build();
		
		model.addAttribute("formData", dto);
		
		return "board/write";
	}
	@PostMapping(value="/write")
	public String write(BoardWriteRequestDTO dto, HttpServletRequest request, RedirectAttributes rttr) {
		log.info("게시글 작성 페이지 요청...POST");
		
		int result = this.noteService.save(dto, request);
		
		if (result > 0) {
			rttr.addFlashAttribute("result", "success");
			rttr.addFlashAttribute("acIdx", dto.getNote().getAcIdx());
			return "redirect:/page/user";
		} else {
			rttr.addFlashAttribute("boardWriteDTO", dto);
			rttr.addFlashAttribute("result", "fail");
			return "redirect:/board/wirte";
		}
	}
	
	// 게시글 작성 (/vibesync/board/edit)
	@GetMapping(value="/edit")
	public String edit(Model model, @RequestParam("noteIdx") int noteIdx) {
		log.info("게시글 수정 페이지 요청...GET");
		
		NoteDetailDTO noteDetail = this.noteService.findNoteByNoteIdx(noteIdx);
		
		BoardEditFormDTO dto = BoardEditFormDTO.builder()
				.noteDetail(noteDetail)
				.build();
		
		model.addAttribute("formData", dto);
		
		return "board/edit";
	}
	@PostMapping(value="/edit")
	public String edit(BoardEditRequestDTO dto, HttpServletRequest request, RedirectAttributes rttr) {
		log.info("게시글 수정 페이지 요청...POST");
		
		int result = this.noteService.edit(dto, request);
		
		if (result > 0) {
			rttr.addFlashAttribute("result", "success");
			return "redirect:/board/view";
		} else {
			rttr.addFlashAttribute("boardEditDTO", dto);
			rttr.addFlashAttribute("result", "fail");
			return "redirect:/board/edit";
		}
	}
	
	
	
}
