<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<ul>
    <c:choose>
        <c:when test="${not empty blockData.posts}">
            <c:forEach var="post" items="${blockData.posts}">
                <li>
                    <a href="postView.do?nidx=${post.noteIdx}" title="${post.title}">
                        <span>${post.title}</span>
                        <span class="block-meta">by ${post.authorName}</span>
                    </a>
                </li>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <li class="no-items">좋아요한 글이 없습니다.</li>
        </c:otherwise>
    </c:choose>
</ul> 