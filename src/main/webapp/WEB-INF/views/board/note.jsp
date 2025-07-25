<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<div class="back_icon">
	<a onclick="location.href = history.go(-1)"><img src="./sources/icons/arrow_back.svg" alt="arrow_back"></a>
</div>

<div id="postview_Wrapper">
	<div class="title">
		<p>Edit</p>
	</div>

	<div class="line"></div>
	<div class="text_content">
		<form id="postForm" method="post" action="noteedit.do" style="margin-bottom: 4rem;">
			<input class="title" type="text" name="title" placeholder="title..." required value="${noteDetail.note.title}">
			<textarea id="summernote" name="content">${noteDetail.note.text}</textarea>
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
					<div class="genre">
						<label for="genre">genre</label> <select id="genre" name="genreIdx">
							<c:forEach items="${ genreList }" var="genre">
								<option value="${ genre.genreIdx }"
									<c:if test="${ genre.genreIdx == noteDetail.note.genreIdx }">selected</c:if>>${ genre.genName }</option>
							</c:forEach>
						</select>
					</div>
					<div class="contents">
						<label for="contents">content</label> <select id="contents"
							name="contentIdx">
							<c:forEach items="${ contentList }" var="content">
								<option value="${ content.contentIdx }"
									<c:if test="${ content.contentIdx == noteDetail.note.contentIdx }">selected</c:if>>${ content.title }</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<input type="hidden" id="images" name="images"> <input
					type="hidden" id="pageidx" name="pageidx" value="${pageidx}">
				<input type="hidden" id="noteIdx" name="noteIdx" value="${noteDetail.note.noteIdx}">
				<div id="save_btn">
					<button type="button" id="saveBtn" class="btn btn-primary mt-3">SAVE</button>
				</div>
			</div>
		</form>
	</div>
</div>

<script>
  $(function() {
	    $('#summernote').summernote({
	        height: 300,
            toolbar: [
	            ['style', ['style']],
	            ['font', ['bold', 'italic', 'underline', 'strikethrough', 'clear']],
	            ['fontname', ['fontname']],
	            ['fontsize', ['fontsize']],
	            ['color', ['color']],
	            ['para', ['ul', 'ol', 'paragraph']],
	            ['height', ['height']],
	            ['insert'],
	        ],
	        callbacks: {
	            onImageUpload: function(files) {
	                for (let i = 0; i < files.length; i++) {
	                    sendFile(files[i], this);
	                }
	            }
	        }
	    });

	    function sendFile(file, editor) {
	        const reader = new FileReader();
	        reader.onloadend = function() {
	            const base64Data = reader.result;
	            $('#summernote').summernote('insertImage', base64Data);
	        }
	        reader.readAsDataURL(file);
	    }
	    
	    $('#saveBtn').click(function() {
	        var markup = $('#summernote').summernote('code');
	        var tempDiv = $('<div>').html(markup);
	        var imgElements = tempDiv.find('img');
	        var base64SrcArray = [];

	        imgElements.each(function(i) {
	            var src = $(this).attr('src');
	            // 새로 추가된 Base64 인코딩된 이미지만 수집
	            if (src.startsWith('data:image/')) {
	                base64SrcArray.push(src);
	            }
	        });

	        $('#images').val(base64SrcArray.join('|')); 
	        $('textarea[name=content]').val(markup); // [수정] 원본 markup을 전송
	        $('#postForm').submit();
	    });
	});
  </script>