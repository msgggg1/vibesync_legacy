package com.vibesync.common.domain;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {

	private int pageNum; // 현재 페이지 번호
	private int amount; // 한 페이지에 출력할 게시글 수
	
	private String type; // 검색 조건 (T, C, W, TC, TW, TCW)
	private String keyword; // 검색어

	// 기본 생성자: 기본값을 1페이지, 10개로 설정
	public Criteria() {
		this(1, 10);
	}
	
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}

	public String getListLink() {
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
										.queryParam("pageNum", this.pageNum)
										.queryParam("amount", this.amount)
										.queryParam("type", this.type)
										.queryParam("keyword", this.keyword);
		return builder.toUriString();
	}
	
	// 검색 조건을 배열로 반환
	// Mybatis → WHERE 조건절 반복해서 추가
	public String[] getTypeArr() {
		return this.type == null ? new String[] {} : this.type.split("");
	}
	
}
