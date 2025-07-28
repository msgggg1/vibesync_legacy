package com.vibesync.note.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vibesync.common.annotation.AuthenticatedUserPages;
import com.vibesync.follow.service.FollowService;
import com.vibesync.note.domain.NoteSaveRequestDTO;
import com.vibesync.note.domain.NoteViewDTO;
import com.vibesync.note.service.NoteService;
import com.vibesync.security.domain.CustomUser;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@RestController
@Log4j
@RequestMapping("/api/note")
@AuthenticatedUserPages
@RequiredArgsConstructor
public class NoteApiController {
	
	private final NoteService noteService;
    private final FollowService followService;
	
	// 글 조회 (/vibesync/api/note/30)
	@GetMapping(value="/{noteIdx}")
	public ResponseEntity<NoteViewDTO> getNoteData(@PathVariable int noteIdx,
												   @AuthenticationPrincipal CustomUser user) {
        NoteViewDTO noteViewData = noteService.getNoteViewData(noteIdx, user);
        System.out.println("NoteViewDTO : " + noteViewData);
        return ResponseEntity.ok(noteViewData);
    }
	
	// 새 글 저장 (/vibesync/api/note)
	@PostMapping
	public ResponseEntity<Map<String, Object>> saveNote(@RequestBody NoteSaveRequestDTO saveDTO,
											@AuthenticationPrincipal CustomUser user) {
        
		Map<String, Object> response = noteService.saveNewNote(saveDTO, user.getAcIdx());
        return ResponseEntity.ok(response);
    }
	
	// 기존 글 수정 (/vibesync/api/note/30)
    @PutMapping("/{noteIdx}")
    public ResponseEntity<Map<String, Boolean>> updateNote(@PathVariable int noteIdx, @RequestBody NoteSaveRequestDTO saveDTO) {
        noteService.updateNote(noteIdx, saveDTO);
        return ResponseEntity.ok(Map.of("success", true));
    }
    
    // 글 삭제
    @DeleteMapping("/{noteIdx}")
    public ResponseEntity<Void> deleteNote(@PathVariable int noteIdx) {
        noteService.deleteNote(noteIdx);
        return ResponseEntity.ok().build();
    }
    
    // 하위 페이지 순서 변경
    @PutMapping("/reorder")
    public ResponseEntity<Void> reorderNotes(@RequestBody List<Integer> orderedNoteIds) {
        noteService.updateDisplayOrder(orderedNoteIds);
        return ResponseEntity.ok().build();
    }
	
}
