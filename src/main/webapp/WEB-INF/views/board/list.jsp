<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<div id="board_all">

    <div class="board_info">
        <form id="filterForm" action="${pageContext.request.contextPath}/board/list" method="get">
            <select name="category_idx" class="filter_select" onchange="$('#filterForm').submit();">
                <%-- <option value="0" ${currentCategory == 0 ? 'selected' : ''}>전체</option> --%>
                <c:forEach var="category" items="${categoryList}">
                    <option value="${category.categoryIdx}">
                        <c:out value="${category.categoryName}" />
                    </option>
                </c:forEach>
            </select>
        </form>
    </div>

    <div class="line"></div>

    <div id="list-container">
        <div id="note-list">
            <c:if test="${not empty noteList}">
                <c:forEach var="note" items="${noteList}">
                    <div class="full-post">
                        <div class="post-index">${note.noteIdx}</div>
                        <div class="post-title"><a class="move" href="${note.noteIdx}"><c:out value="${note.title}"/></a></div>
                        <div class="post-author"><c:out value="${note.author}"/></div>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${empty noteList}">
                <p style="text-align:center; padding: 20px;">게시물이 없습니다.</p>
            </c:if>
        </div>
        
        <div id="search_bar">
            <form id="searchForm" action="${pageContext.request.contextPath}/board/list" method="get">
                 <%-- <input type="hidden" name="category_idx" value="${currentCategory}"> --%>
                 <select name="type" class="searchInput">
       				 <option value="T">제목</option>
       				 <option value="C">내용</option>
       				 <option value="W">작성자</option>
       				 <option value="TC">제목+내용</option>
       				 <option value="TW">제목+작성자</option>
       				 <option value="TCW">제목+내용+작성자</option>
                 </select>
                 <input type="text" class="searchInput" name="keyword" value="<c:out value='${keyword}'/>" placeholder="검색어 입력"/>
                 <button type="button" class="searchBtn">검색</button>
            </form>
        </div>

        <div class="pagination" id="pagination-container">
             <c:if test="${pageMaker.prev}">
                <a href="${pageMaker.startPage - 1}">&laquo;</a>
            </c:if>
            <c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" step="1" var="num">
                <a href="${num}" class="${num eq pageMaker.criteria.pageNum ? 'active' : ''}">${num}</a>
            </c:forEach>
            <c:if test="${pageMaker.next}">
                <a href="${pageMaker.endPage+1}">&raquo;</a>
            </c:if>
        </div>
        
    </div>
    
</div>

<form id="actionForm" action="${pageContext.request.contextPath}/board/list" method="get">
	<input type="hidden" name="pageNum" value="${pageMaker.criteria.pageNum}">
	<input type="hidden" name="amount" value="${pageMaker.criteria.amount}">
	<input type="hidden" name="type" value="${pageMaker.criteria.type}">
	<input type="hidden" name="keyword" value="${pageMaker.criteria.keyword}">
</form>

<script>

	$(function() {
		// 페이징 블럭에서 번호를 클릭할 때 이동
		var actionForm = $("#actionForm");
			
		$(".pagination a").on("click", function() {
			event.preventDefault();
			let pageNum = $(this).attr("href");
			actionForm
					  .find(":hidden[name=pageNum]")
					  								.val(pageNum)
					  								.end()
					  .submit();
		}); // $(".pagination a").on("click", function() {
		
		// 게시글을 클릭할 때 이동
		$("a.move").on("click", function() {
			event.preventDefault();
			let noteIdx = $(this).attr("href");
			actionForm
			  .attr("action", "/board/note")
			  .append(`<input type="hidden" name="noteIdx" value="\${noteIdx}">`)
			  .submit();
		}); // $("a.move").on("click", function() {
		
	});

</script>

</body>
</html>