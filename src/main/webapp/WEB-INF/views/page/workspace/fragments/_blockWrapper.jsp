<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="contents_item generated_block" id="block-${block.blockId}">
    <div class="block-header">
        <h4>
            <c:choose>
                <c:when test="${block.blockType == 'CategoryPosts'}"><i class="fa-solid fa-layer-group"></i>&nbsp;${block.categoryName} ${block.sortType == 'popular' ? '인기' : '최신'}글</c:when>
                <c:when test="${block.blockType == 'WatchParties'}"><i class="fa-solid fa-tv"></i>&nbsp;진행중인 워치파티</c:when>
                <c:when test="${block.blockType == 'UserStats'}"><i class="fa-solid fa-chart-simple"></i>&nbsp;${block.title}</c:when>
            </c:choose>
        </h4>
        <div class="block-actions">
            <button class="refresh-block-btn" data-block-id="${block.blockId}" title="새로고침">
                <i class="fa-solid fa-arrows-rotate"></i>
            </button>
            <button class="delete-block-btn" data-block-id="${block.blockId}" title="삭제">
                <i class="fa-solid fa-trash-can"></i>
            </button>
        </div>
    </div>
    <div class="block-content" 
         <c:if test="${block.blockType == 'UserStats' and not empty chartDataJson}">data-chart-data='${chartDataJson}'</c:if>>
        <c:set var="block" value="${block}" scope="request" />
        <jsp:include page="/WEB-INF/views/page/workspace/fragments/_${block.blockType}Content.jsp" />
    </div>
</div>