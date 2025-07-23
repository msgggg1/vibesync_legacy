package com.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.vibesync.mainpage.domain.MainPageDTO;
import com.vibesync.mainpage.service.MainPageService;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/security-context.xml"})
@WebAppConfiguration
@Log4j
@Transactional // 테스트 후 DB 롤백을 위해 추가
public class MainPageServiceTest {
	
    // 테스트 대상인 MainPageService를 주입받습니다.
	@Autowired
	private MainPageService mainPageService;
	
    /**
     * MainPageService의 loadMainPage 메소드가 정상적으로 데이터를 로드하는지 테스트합니다.
     */
	@Test
	public void testLoadMainPage() {
		// --- Given (테스트 준비) ---
        // root-context.xml에 MainPageService가 빈으로 등록되어 있어야 합니다.
        // 테스트할 사용자의 선호 카테고리 ID를 임의로 지정합니다.
		int categoryIdx = 1;

		log.info("=======================================");
		log.info(">>> MainPageService 테스트 시작...");
		log.info("주입된 Service 객체: " + mainPageService);
		
        // --- When (실행) ---
        // 서비스 메소드를 호출하여 데이터를 가져옵니다.
		MainPageDTO mainPageDTO = mainPageService.loadMainPage(categoryIdx);
		
		
        // --- Then (검증) ---
        // 1. Service 객체가 null이 아닌지 확인 (의존성 주입 성공 여부)
		assertNotNull("MainPageService가 주입되지 않았습니다.", mainPageService);
		
        // 2. 반환된 MainPageDTO 객체가 null이 아닌지 확인
		assertNotNull("MainPageDTO가 null입니다.", mainPageDTO);
		
        // 3. MainPageDTO 내부의 리스트들이 null이 아닌지 확인
		assertNotNull("최신 글 목록(latestNotes)이 null입니다.", mainPageDTO.getLatestNotes());
		assertNotNull("인기 글 목록(popularNotes)이 null입니다.", mainPageDTO.getPopularNotes());
		assertNotNull("인기 유저 목록(popularUsers)이 null입니다.", mainPageDTO.getPopularUsers());
		assertNotNull("비선호 카테고리 글 목록(popularNotesNotByMyCategory)이 null입니다.", mainPageDTO.getPopularNotesNotByMyCategory());

		log.info("반환된 DTO: " + mainPageDTO);
		log.info("최신 글 개수: " + mainPageDTO.getLatestNotes().size());
		log.info("인기 글 개수: " + mainPageDTO.getPopularNotes().size());
		log.info("인기 유저 수: " + mainPageDTO.getPopularUsers().size());
        log.info("비선호 카테고리 그룹 수: " + mainPageDTO.getPopularNotesNotByMyCategory().size());
		log.info(">>> 테스트 성공!");
		log.info("=======================================");
	}
}