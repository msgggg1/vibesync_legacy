<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<ul>
    <c:forEach var="party" items="${block.watchParties}">
        <li>
           <a href="${pageContext.request.contextPath}/vibesync/watch.jsp?watchPartyIdx=${party.watchparty.watchPartyIdx}">${party.watchparty.title} (호스트: ${party.host.nickname})</a>
        </li>
    </c:forEach>
    <c:if test="${empty block.watchParties}"><li>진행 중인 워치파티가 없습니다.</li></c:if>
</ul>