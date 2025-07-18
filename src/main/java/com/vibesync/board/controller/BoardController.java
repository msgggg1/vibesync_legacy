package com.vibesync.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vibesync.board.domain.NoteVO;
import com.vibesync.board.service.NoteService;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/board/*")
public class BoardController {
	
	@Autowired
	NoteService noteService;
	
	@GetMapping(value="/list.htm")
	public String getNoteList(Model model) {
		List<NoteVO> noteList = this.noteService.getNoteList();
		model.addAttribute("noteList", noteList);
		return "board/list";
	}
	
}
