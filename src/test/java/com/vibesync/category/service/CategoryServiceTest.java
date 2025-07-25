package com.vibesync.category.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.vibesync.category.domain.CategoryVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "file:src/test/resources/root-context.xml"
})
@Transactional
@Log4j
public class CategoryServiceTest {

	@Autowired
    private CategoryService categoryService;
	
	@Test
	public void testFindAllWithCache() {
		log.info("카테고리 전체 조회 서비스 테스트 시작");
		List<CategoryVO> categoryList = categoryService.findAll();
		log.info("조회된 카테고리 목록: " + categoryList);
	}

}
