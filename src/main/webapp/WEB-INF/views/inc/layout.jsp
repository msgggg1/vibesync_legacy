<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.net.URLEncoder" %>
<!DOCTYPE html>
<html lang="ko" color-theme="${sessionScope.theme != null ? sessionScope.theme : 'light'}">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title><tiles:getAsString name="title" /></title>
  <link rel="icon" href="${pageContext.request.contextPath}/sources/favicon.ico" />
  
  <!-- 특정 페이지에서만 사용되는 Framework/Library -->
  <tiles:insertAttribute name="pageHead" ignore="true"/>
  
  <!-- css -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/sidebar.css">
  <!-- 특정 페이지에서만 사용되는 css -->
  <c:if test="${not empty pageCss}">
      <link rel="stylesheet" href="${pageContext.request.contextPath}${pageCss}">
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
	const ctx = "${pageContext.request.contextPath}";
</script>
<script src="${pageContext.request.contextPath}/resources/js/script.js"></script>
<script defer src="${pageContext.request.contextPath}/resources/js/theme.js"></script>
<!-- 특정 페이지에서만 사용되는 js -->
<c:if test="${not empty pageJs}">
    <script defer src="${pageContext.request.contextPath}${pageJs}"></script>
</c:if>
</body>

</html>