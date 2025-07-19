<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<div class="back_icon">
	<a onclick="history.back()"><img src="${pageContext.request.contextPath}/sources/icons/arrow_back.svg" alt="arrow_back"></a>
</div>

<div id="postview_Wrapper">
	<div class="title">
		<p>Create</p>
	</div>

	<div class="line"></div>
	
	<div class="text_content">
		<form id="postForm" method="post" action="${pageContext.request.contextPath}/board/write" style="margin-bottom: 4rem;">
			<div id="title_info">
				<label for="title"></label>
				<input class="title" id="title" type="text" name="title" placeholder="title..." required>
			</div>

			<textarea id="summernote" name="content"></textarea>
			<div class="note_op">
				<div style="margin-top: 1rem;">
					<label for="thumbnail_input" style="font-weight: bold;">
						Thumbnail Image (JPG, JPEG only)
					</label>
					<input type="file" id="thumbnail_input" accept=".jpg, .jpeg" required>
				</div>
				<div id="select_wrapper">
					<div class="category sel">
						<label for="category">category</label>
						<select id="category" name="categoryIdx">
							<c:forEach items="${ categoryList }" var="category">
								<option value="${ category.categoryIdx }">${ category.categoryName }</option>
							</c:forEach>
						</select>
					</div>
					<div class="genre sel">
						<label for="genre">genre</label>
						<select id="genre" name="genreIdx">
							<c:forEach items="${ genreList }" var="genre">
								<option value="${ genre.genreIdx }">${ genre.genName }</option>
							</c:forEach>
						</select>
					</div>
					<div class="contents sel">
						<label for="contents">content</label>
						<select id="contents" name="contentIdx">
							<c:forEach items="${ contentList }" var="content">
								<option value="${ content.contentIdx }">${ content.title }</option>
							</c:forEach>
						</select>
					</div>
				</div>

				<input type="hidden" id="images" name="images">
				<input type="hidden" name="thumbnail_base64">
				<input type="hidden" name="thumbnail_ext">
				<%-- <input type="hidden" id="pageidx" name="pageidx" value="<%=pageidx%>"> --%>

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
        placeholder: '여기에 내용을 입력하세요...',
        toolbar: [
            ['style', ['style']],
            ['font', ['bold', 'italic', 'underline', 'strikethrough', 'clear']],
            ['fontname', ['fontname']],
            ['fontsize', ['fontsize']],
            ['color', ['color']],
            ['para', ['ul', 'ol', 'paragraph']],
            ['height', ['height']],
            ['insert', ['picture']], // 링크, 비디오 등 제외
        ],
        callbacks: {
            onImageUpload: function(files) {
                for (let i = 0; i < files.length; i++) {
                    sendFile(files[i]);
                }
            }
        }
    });

    function sendFile(file) {
        const reader = new FileReader();
        reader.onloadend = function() {
            const base64Data = reader.result;
            $('#summernote').summernote('insertImage', base64Data);
        }
        reader.readAsDataURL(file);
    }
    
    // [추가] 썸네일 파일 선택 시 유효성 검사 및 Base64 변환
    $('#thumbnail_input').on('change', function() {
    	const file = this.files[0];
    	if (!file) return;

    	const fileName = file.name;
    	const fileExt = fileName.slice(fileName.lastIndexOf(".") + 1).toLowerCase();

    	if(fileExt !== "jpg" && fileExt !== "jpeg"){
    	  alert("JPG, JPEG 파일만 업로드 가능합니다.");
    	  $(this).val(""); // 파일 선택 초기화
    	  $('input[name="thumbnail_base64"]').val("");
    	  $('input[name="thumbnail_ext"]').val("");
    	  return;
    	}
    	
    	// 유효한 파일이면 Base64로 변환하여 hidden input에 저장
    	const reader = new FileReader();
        reader.onloadend = function() {
            $('input[name="thumbnail_base64"]').val(reader.result);
            $('input[name="thumbnail_ext"]').val(fileExt);
        }
        reader.readAsDataURL(file);
    });
    
    $('#saveBtn').click(function() {
        // [추가] 썸네일이 선택되었는지 최종 확인
        if (!$('input[name="thumbnail_base64"]').val()) {
        	alert('대표 이미지를 선택해주세요.');
        	$('#thumbnail_input').focus();
        	return;
        }
    
        var markup = $('#summernote').summernote('code');
        var tempDiv = $('<div>').html(markup);
        var imgElements = tempDiv.find('img');
        var base64SrcArray = [];

	    imgElements.each(function(i) {
	        var src = $(this).attr('src');
	        if (src.startsWith('data:image/')) {
	            base64SrcArray.push(src);
	        }
	    });
        
	    $('#images').val(base64SrcArray.join('|'));
	    $('textarea[name=content]').val(tempDiv.html());
	    $('#postForm').submit();
	});
});
</script>