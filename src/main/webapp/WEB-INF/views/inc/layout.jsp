<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page import="java.net.URLEncoder" %>
<c:set var="path" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko" color-theme="${userProfile != null ? userProfile.theme : 'light'}">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title><tiles:getAsString name="title" /></title>
  <link rel="icon" href="${pageContext.request.contextPath}/favicon.ico" />
  
  <!-- 특정 페이지에서만 사용되는 Framework/Library -->
  <tiles:insertAttribute name="pageHead" ignore="true"/>
  
  <!-- css -->
  <link rel="stylesheet" href="${path}/resources/css/style.css">
  <link rel="stylesheet" href="${path}/resources/css/sidebar.css">
  <!-- 특정 페이지에서만 사용되는 css -->
  <c:if test="${not empty pageCss}">
      <link rel="stylesheet" href="${path}${pageCss}">
  </c:if>
  
  <!-- js -->
  <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

</head>
<body>
  <div id="notion-app">
    <input type="hidden" id="mode" value="main">
    <div class="notion-app-inner">
    	<tiles:insertAttribute name="sidebar" flush="false"/>

	    <!-- content -->
	    <div id="content_wrapper">
	      <section id="content">
	  	  	<tiles:insertAttribute name="body" flush="false"/>
	      </section>
	    </div>

    </div>
  </div>
<tiles:insertAttribute name="footer" flush="false"/>

<!-- js -->
<script>
	const ctx = "${path}";
	const isLoggedIn = '<sec:authorize access="isAuthenticated()">true</sec:authorize><sec:authorize access="isAnonymous()">false</sec:authorize>' === 'true';
	const loggedInUserAcIdx = isLoggedIn ? ${userProfile.acIdx} : -1;
</script>
<script src="${path}/resources/js/script.js"></script>
<script defer src="${path}/resources/js/theme.js"></script>
<!-- 특정 페이지에서만 사용되는 js -->
<c:if test="${not empty pageJs}">
    <script defer src="${path}${pageJs}"></script>
</c:if>
</body>

</html>