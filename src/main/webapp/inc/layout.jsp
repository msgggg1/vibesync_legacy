<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder" %>
<!DOCTYPE html>
<tiles:insertAttribute name="header" />
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title><tiles:insertAttribute name="title" /></title>
  <link rel="icon" href="${pageContext.request.contextPath}/sources/favicon.ico" />
  <!-- swiper -->
  <script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
  <link
    rel="stylesheet"
    href="https://unpkg.com/swiper/swiper-bundle.min.css"
  />
  <!-- css,js -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/sidebar.css">
  <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
<tiles:insertAttribute name="footer" />
</body>

</html>