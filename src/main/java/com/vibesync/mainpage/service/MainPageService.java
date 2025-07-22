package com.vibesync.mainpage.service;

import org.springframework.stereotype.Service;

import com.vibesync.mainpage.domain.MainPageDTO;

public interface MainPageService {

	public MainPageDTO loadMainPage(int category_idx);
	
}
