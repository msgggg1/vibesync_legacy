<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<c:set var="path" value="${pageContext.request.contextPath}" />

<div class="back_icon">
	<img src="${path}/resources/images/icons/arrow_back.svg" alt="arrow_back">
</div>

<div id="postview_Wrapper">
	<div class="title">
		<p>Note</p>
	</div>

	<div class="line"></div>
	<div class="text_content">
		<form id="postForm" method="post" action="noteedit.do" style="margin-bottom: 4rem;">
			<input type="text" id="note-title" placeholder="제목을 입력하세요">
			<div id="editorjs"></div>
			<div class="note_op">
				<div id="select_wrapper">
					<div class="category">
						<label for="category">category</label> <select id="category"
							name="categoryIdx">
							<c:forEach items="${ categoryList }" var="category">
								<option value="${ category.categoryIdx }"
									<c:if test="${ category.categoryIdx == noteDetail.note.categoryIdx }">selected</c:if>>${ category.categoryName }</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div id="save_btn">
					<button type="button" id="saveBtn" class="btn btn-primary mt-3">SAVE</button>
				</div>
				<button id="list-btn">LIST</button>
			</div>
		</form>
	</div>
</div>

<script>
    const noteIdx = '<c:out value="${noteIdx}" default="0"/>';
    const listLink = '<c:out value="${path}/board/list${criteria.listLink}"/>';
</script>






























