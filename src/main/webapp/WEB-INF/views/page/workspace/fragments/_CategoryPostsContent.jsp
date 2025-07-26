<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<ul>
    <c:forEach var="post" items="${block.posts}">
        <li>
        	<a href="${pageContext.request.contextPath}/vibesync/postView.do?nidx=${post.noteIdx}">
        	<span>${post.title}</span>
			<span class="block-meta">
				<i class="fa-regular fa-eye"></i> ${post.viewCount}&nbsp;&nbsp;
				<i class="fa-regular fa-thumbs-up"></i>${post.likeCount}
			</span>
        	</a>
        </li>
    </c:forEach>
    <c:if test="${empty block.posts}"><li>표시할 게시글이 없습니다.</li></c:if>
</ul>