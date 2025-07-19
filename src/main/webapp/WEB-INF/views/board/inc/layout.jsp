<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html lang="ko" color-theme="${sessionScope.theme != null ? sessionScope.theme : 'light'}">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title><tiles:getAsString name="title" /></title>
		<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
		<link href="${pageContext.request.contextPath}/resources/css/style.css" type="text/css" rel="stylesheet" />
		<link href="${pageContext.request.contextPath}/resources/css/sidebar.css" type="text/css" rel="stylesheet" />
		<link href="${pageContext.request.contextPath}/resources/css/board/<tiles:getAsString name="css" />" type="text/css" rel="stylesheet" />
		<link rel="icon" href="${pageContext.request.contextPath}/sources/favicon.ico" />
		
		<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" rel="stylesheet">
		<link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote.min.css" rel="stylesheet">
		<script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote.min.js"></script>
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.bundle.min.js"></script>
	</head>

<body>
<div id="notion-app">
    <div class="notion-app-inner">
	<tiles:insertAttribute name="sidebar" />
        <div id="content_wrapper">
            <section id="content">
            <tiles:insertAttribute name="content" />
            </section>
        </div>
	</div>
</div>
<script src="${pageContext.request.contextPath}/resources/js/theme.js"></script>
</body>
</html>