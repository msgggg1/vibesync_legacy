package com.vibesync.common.controller; 

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.vibesync.common.annotation.AuthenticatedUserPages;

/**
 * 단순 페이지 뷰로의 이동을 처리하는 컨트롤러
 */
@Controller 
@AuthenticatedUserPages
public class PageController {
	// 워크스페이스는 별도 WorkspaceController에서 처리
}
