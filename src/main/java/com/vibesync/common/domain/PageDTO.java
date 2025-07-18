package com.vibesync.common.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO { // 페이지 블록

    private int startPage; // 페이지 블록의 시작 번호
    private int endPage; // 페이지 블록의 끝 번호
    private boolean prev, next; // 이전, 다음 버튼 활성화 여부
    private int total; // 전체 데이터 개수
    
    private Criteria criteria; // 현재 페이지, 페이지당 개수 정보
    
    public PageDTO(Criteria criteria, int total) {
        this.criteria = criteria;
        this.total = total;

        // 페이징 계산
        this.endPage = (int) (Math.ceil(criteria.getPageNum() / (criteria.getAmount() * 1.0))) * criteria.getAmount();
        this.startPage = this.endPage  - criteria.getAmount() + 1;

        int realEnd = (int) (Math.ceil((total * 1.0) / criteria.getAmount()));

        if (realEnd < this.endPage) {
            this.endPage = realEnd;
        }

        this.prev = this.startPage > 1;
        this.next = this.endPage < realEnd;
    }
}