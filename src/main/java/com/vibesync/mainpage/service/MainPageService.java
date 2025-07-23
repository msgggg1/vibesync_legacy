package com.vibesync.mainpage.service;

import com.vibesync.mainpage.domain.MainPageDTO;

public interface MainPageService {
  
	public MainPageDTO loadMainPage(int category_idx);
	
}
