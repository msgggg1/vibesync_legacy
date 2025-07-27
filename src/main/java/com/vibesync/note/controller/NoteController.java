package com.vibesync.note.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vibesync.common.domain.Criteria;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/note")
@RequiredArgsConstructor
public class NoteController {
	
	// 새 노트 페이지 (/vibesync/note/new)
	@GetMapping(value="/new")
	public String getNewNote(Criteria criteria, Model model) {
		log.info("노트 페이지 요청...GET");
        
        // '목록으로' 버튼이 사용할 criteria
        model.addAttribute("criteria", criteria);
		
		return "note/note";
	}
	
	// 노트 페이지 (/vibesync/note/30)
	@GetMapping(value="/{noteIdx}")
	public String getNote(@PathVariable("noteIdx") int noteIdx, Criteria criteria, Model model) {
		log.info("노트 페이지 요청...GET");
		
		// JSP가 AJAX 요청을 보낼 때 사용할 noteIdx
		model.addAttribute("noteIdx", noteIdx);
		
		// '목록으로' 버튼이 사용할 criteria
		model.addAttribute("criteria", criteria);
		
		return "note/note";
	}
	
}
