<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.net.URLEncoder" %>
<!DOCTYPE html>
<html lang="ko" color-theme="${sessionScope.theme != null ? sessionScope.theme : 'light'}">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title><tiles:insertAttribute name="title" /></title>
  <link rel="icon" href="${pageContext.request.contextPath}/sources/favicon.ico" />
  
  <!-- swiper (메인 페이지 등 이미지 슬라이드가 필요한 페이지에서만 사용되는 내용) -->
  <script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
  <link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css" />
  
  <!-- 게시판에서만 사용되는 내용 -->
  <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote.min.css" rel="stylesheet">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote.min.js"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.bundle.min.js"></script>
  
  <!-- 특정 페이지에서만 사용되는 내용 -->
  <link href="${pageContext.request.contextPath}/<tiles:getAsString name="css" />" type="text/css" rel="stylesheet" />
  
  <!-- css,js -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/sidebar.css">
  <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
  <script defer src="${pageContext.request.contextPath}/resources/js/script.js"></script>
</head>
<body>
  <div id="notion-app">
    <input type="hidden" id="mode" value="main">
    <div class="notion-app-inner">
   <tiles:insertAttribute name="sidebar" />

      <!-- content -->
      <div id="content_wrapper">
        <section id="content">
  			<tiles:insertAttribute name="body" />
        </section>
      </div>

    </div>
  </div>
<script src="${pageContext.request.contextPath}/resources/js/theme.js"></script>
</body>

</html>