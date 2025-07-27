package com.vibesync.note.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vibesync.common.annotation.AuthenticatedUserPages;
import com.vibesync.common.domain.Criteria;
import com.vibesync.follow.service.FollowService;
import com.vibesync.note.domain.NoteDetailDTO;
import com.vibesync.note.domain.NoteSaveRequestDTO;
import com.vibesync.note.domain.NoteViewDTO;
import com.vibesync.note.service.NoteService;
import com.vibesync.security.domain.CustomUser;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@RestController
@Log4j
@RequestMapping("/api/notes")
@AuthenticatedUserPages
@RequiredArgsConstructor
public class NoteApiController {
	
	@Autowired
	NoteService noteService;
	@Autowired
	FollowService followService;
	
	// 노트 페이지 (/vibesync/api/notes/30)
	@GetMapping(value="/{noteIdx}")
	public String getNote(@PathVariable("noteIdx") int noteIdx, Criteria criteria,
						  @AuthenticationPrincipal CustomUser user, Model model) {
		log.info("노트 페이지 요청...GET");
		
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
		NoteViewDTO dto = NoteViewDTO.builder()
										.noteDetail(noteDetail)
										.userAcIdx(userAcIdx)
										.following(isFollowing)
										.liking(isLiking)
										.build();
		
		log.info("게시글 상세보기 페이지 DTO : " + dto);
		
		model.addAttribute("noteViewDTO", dto);
		
		return "note/note";
	}
	
	// 새 노트 작성/저장 (/vibesync/api/notes)
	@PostMapping(value="")
	public ResponseEntity<Integer> saveNote(@RequestBody NoteSaveRequestDTO saveDTO,
            			   @AuthenticationPrincipal CustomUser user) {
		log.info("게시글 작성 페이지 요청...POST");
		
		// NoteService를 호출하여 노트를 저장하고, 생성된 noteIdx를 반환받음
        int newNoteIdx = noteService.saveNewNote(saveDTO, user.getAcIdx());
		
        return ResponseEntity.ok(newNoteIdx);
	}
	
}
