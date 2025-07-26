<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="pageData" value="${boardEditDTO ne null ? boardEditDTO : formData.noteDetail}" />

<div class="back_icon">
	<img src="${pageContext.request.contextPath}/sources/icons/arrow_back.svg" alt="arrow_back">
</div>

<div id="postview_Wrapper">
	<div class="title">
		<p>Edit</p>
	</div>

	<div class="line"></div>
	<div class="text_content">
		<form id="postForm" method="post" action="${pageContext.request.contextPath}/board/edit" style="margin-bottom: 4rem;">
			<input class="title" type="text" name="note.title" placeholder="title..." required value="${pageData.note.title}">
			<textarea id="summernote" name="note.text">
				<c:out value="${pageData.note.text}" escapeXml="false" />
			</textarea>
			<div class="note_op">
				<div id="select_wrapper">
					<div class="category">
						<label for="category">category</label>
						<select id="category" name="note.categoryIdx">
							<c:forEach items="${ categoryList }" var="category">
								<option value="${ category.categoryIdx }"
									<c:if test="${ category.categoryIdx == pageData.note.categoryIdx }">selected</c:if>>
									${ category.categoryName }
								</option>
							</c:forEach>
						</select>
					</div>
					<div class="genre">
						<label for="genre">genre</label>
						<select id="genre" name="note.genreIdx">
							<c:forEach items="${ formData.genreList }" var="genre">
								<option value="${ genre.genreIdx }"
									<c:if test="${ genre.genreIdx == pageData.note.genreIdx }">selected</c:if>>
									${ genre.genName }
								</option>
							</c:forEach>
						</select>
					</div>
					<div class="contents">
						<label for="contents">content</label>
						<select id="contents" name="note.contentIdx">
							<c:forEach items="${ formData.contentList }" var="content">
								<option value="${ content.contentIdx }"
									<c:if test="${ content.contentIdx == pageData.note.contentIdx }">selected</c:if>>
									${ content.title }
								</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<input type="hidden" id="images" name="newImages">
				<input type="hidden" name="existingImages">
				<input type="hidden" name="originalImages" value="${formData.noteDetail.note.img}">
				<input type="hidden" id="noteIdx" name="note.noteIdx" value="${formData.noteDetail.note.noteIdx}">
				<div id="save_btn">
					<button type="button" id="saveBtn" class="btn btn-primary mt-3">SAVE</button>
				</div>
			</div>
		</form>
	</div>
</div>

<script>
  $(function() {
      $(document).ready(function() {
        	$('.text_content img').each(function() {
  	        const src = $(this).attr('src');
  	        $(this).attr('src', '${pageContext.request.contextPath}' + src);
  	    });
	  
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
	        
	        var newbase64SrcArray = [];
	        var existingImageFileNames = [];

	        imgElements.each(function(i) {
	            var src = $(this).attr('src');
	            if (src) {
	                if (src.startsWith('data:image/')) {
	                    // 새로 추가된 Base64 이미지
	                    newBase64Images.push(src);
	                } else if (src.includes('/sources/upload/board/noteImg')) {
	                    // 기존에 저장된 이미지 중 살아남은 파일명 수집 (삭제된 이미지들은 서버에서 삭제 처리하기 위함)
	                    // src에서 파일명만 추출 (예: "a1b2c3d4.jpg")
	                    var fileName = src.substring(src.lastIndexOf('/') + 1);
	                    existingImageFileNames.push(fileName);
	                }
	            }
	        });

	        $('#images').val(newBase64Images.join('|'));
	        $('#existingImages').val(existingImageFileNames.join('|'));
	        $('textarea[name="note.text"]').val(markup);
	        $('#postForm').submit();
	    });
	});
  </script>