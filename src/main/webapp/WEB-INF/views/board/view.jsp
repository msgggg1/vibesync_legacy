<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<div class="back_icon">
	<a href="javascript:void(0);" onclick="goBackSmartly()"><img src="${pageContext.request.contextPath}/sources/icons/arrow_back.svg" alt="arrow_back"></a>
</div>
<div id="postview_Wrapper">
	<div class="title">
		<p>${dto.noteDetail.note.title}</p>
		<c:if test="${dto.userAcIdx == dto.noteDetail.upacIdx}">
			<div>
				<button class="postview_ed_btn" onclick="location.href='edit?noteIdx=${dto.noteDetail.note.noteIdx}'">
					edit
				</button>
				<button class="postview_de_btn" onclick="confirm('정말로 삭제하시겠습니까?'); location.href='edit?noteIdx=${dto.noteDetail.note.noteIdx}'">
					delete
				</button>
			</div>
		</c:if>
	</div>
	<div class="writer_info">
		<div class="writer">
			<c:url var="profileImgUrl" value="${pageContext.request.contextPath}/sources/default/default_user.jpg" />
		    <c:if test="${not empty dto.noteDetail.member.img}">
		        <c:url var="profileImgUrl" value="${pageContext.request.contextPath}/sources/member/profileImg/${noteDetail.member.img}" />
		    </c:if>
			<img src="${profileImgUrl}" alt="writer_profile">
			<a href="userPage?acIdx=${dto.noteDetail.member.acIdx}">${dto.noteDetail.member.nickname}</a>
			<c:if test="${dto.userAcIdx != dto.noteDetail.upacIdx}">
				<form id="followForm" style="display: inline; margin: 0; padding: 0;">
					<button id="followBtn" type="button"
							style="background: #99bc85; border-radius: 5px; border: none; cursor: pointer; padding: 5px 10px;">
						${dto.following ? "Unfollow" : "Follow"}
					</button>
				</form>
			</c:if>
		</div>
		<div class="like_share">
			<div>
				<p>
					<span>view : </span><span>${dto.noteDetail.note.viewCount}</span>
				</p>
			</div>
			<form id="likeForm" style="display: inline; margin: 0; padding: 0;">
				<button id="likeBtn" type="submit" data-user-idx="${dto.userAcIdx}"
					data-note-idx="${dto.noteDetail.note.noteIdx}"
					style="border: none; background: none; cursor: pointer; filter: var(- -icon-filter);">
					<img id="likeImg"
						src="${dto.liking ? '${pageContext.request.contextPath}/sources/icons/fill_heart.png' : '${pageContext.request.contextPath}/sources/icons/heart.svg'}"
						alt="heart"
						style="vertical-align: middle; width: 2rem; height: 2rem;"><span
						id="likeCount" style="vertical-align: middle;">${dto.noteDetail.likeNum}</span>
				</button>
			</form>
		</div>
	</div>
	<div class="line"></div>
	<div class="text_content">
		<c:out value="${dto.noteDetail.note.text}" escapeXml="false" />
	</div>
	<div class="line" style="margin-top: 2rem; margin-bottom: 0px"></div>
	<div id="comment-section">
		<h4>Comments</h4>
		<c:choose>
			<%-- 1. 로그인한 경우: 기존의 댓글 입력창을 보여줌 --%>
			<c:when test="${dto.userAcIdx > 0}">
				<form id="comment-form" style="margin-bottom: 1.864rem;">
					<input type="hidden" name="noteIdx" value="${dto.noteDetail.note.noteIdx}">
					<div class="textarea-div"
						style="display: flex; align-items: center;">
						<textarea name="text" rows="3" placeholder="댓글을 입력하세요..." required
							style="width: 100%; resize: none; padding: 8px; border: solid 2px var(- -border-color); border-radius: 4px 0 0 4px; outline: none; background-color: var(- -background-color); color: var(- -font-color);"></textarea>
						<button type="submit"
							style="margin: 0px; padding: 5px 10px; height: 65px; border: solid 2px var(- -border-color); border-radius: 0 4px 4px 0; border-left: none; background-color: var(- -card-head); color: var(- -card-back); font-weight: bold;">작성</button>
					</div>
				</form>
			</c:when>
			<%-- 2. 로그인하지 않은 경우: 로그인 유도 메시지를 보여줌 --%>
			<c:otherwise>
				<div class="comment-login-prompt"
					style="margin-bottom: 1.864rem; padding: 20px; border: 2px solid var(- -border-color); border-radius: 4px; text-align: center; cursor: pointer;">
					<a href="javascript:void(0);" onclick="requireLogin()"
						style="text-decoration: none; color: #888; font-weight: bold;">
						로그인 후 댓글을 작성할 수 있습니다. </a>
				</div>
			</c:otherwise>
		</c:choose>
		<div id="comment-list" style="clear: both;"></div>
	</div>
	<div id="edit-comment-modal"
		style="display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0, 0, 0, 0.5); z-index: 1000;">
		<div
			style="position: relative; top: 20%; margin: auto; width: 500px; background: white; padding: 20px; border-radius: 5px;">
			<h5>댓글 수정</h5>
			<form id="edit-comment-form">
				<input type="hidden" id="edit-comment-id" name="commentIdx">
				<textarea id="edit-comment-text" name="text" rows="4"
					style="width: 100%; resize: none; padding: 8px;"></textarea>
				<div style="text-align: right; margin-top: 10px;">
					<button type="button" id="cancel-edit-btn">취소</button>
					<button type="submit">저장</button>
				</div>
			</form>
		</div>
	</div>
</div>

<script>
      
    $(document).ready(function() {
      // Follow/Unfollow 버튼 처리
      $('#followBtn').on('click', function(e) {
        if (!isLoggedIn) {
            requireLogin();
            return;
        }
        
        followToggle(${dto.noteDetail.member.acIdx});
      });
      
      // Like 버튼 처리
      $('#likeForm').on('submit', function(e) {
        e.preventDefault();
        
        if (!isLoggedIn) {
            requireLogin();
            return;
        }
        
        const currentCount = parseInt($('#likeCount').text(), 10);
        $.ajax({
          url: ajaxUrl,
          type: 'POST',
          data: {
        	  action: 'toggleLike',
        	  userIdx: $('#likeBtn').data('userIdx'),
        	  noteIdx: $('#likeBtn').data('noteIdx')
          },
          dataType: 'json',
          success: function(data) {
            if (data.liked) {
            	$('#likeImg').attr('src', './sources/icons/fill_heart.png');
            	$('#likeCount').text(currentCount + 1);
            } else {
            	$('#likeImg').attr('src', './sources/icons/heart.svg');
            	$('#likeCount').text(currentCount - 1);
            }
          },
          error: function(xhr, status, error) {
        	  console.error('[AJAX-LIKE] 에러 발생:', error);
          }
        });
      });
      
      // =================== 대댓글 기능이 포함된 스크립트 ===================
      const commentUrl = ctx + '/comment';
      const loggedInUserIdx = ${not empty memberInfo ? dto.userAcIdx : -1};
      const noteIdxForComment = ${dto.noteDetail.note.noteIdx};

      function loadComments() {
        $.ajax({
          url: commentUrl,
          type: 'POST',
          data: {
        	  action: 'list',
        	  noteIdx: noteIdxForComment
          },
          dataType: 'json',
          success: function(comments) {
            const $commentList = $('#comment-list').empty();
            if (comments && comments.length > 0) {
            	comments.forEach(function(comment) {
            		console.log('Comment ID:', comment.commentlist_idx, 'Received Depth:', comment.depth);
					const indentStyle = `style="margin-left: \${(comment.depth - 1) * 20}px;"`;
                	const editDeleteButtons = (comment.acIdx === loggedInUserIdx) ?
                  	` <button class="edit-btn" data-id="\${comment.commentlist_idx}" data-text="\${encodeURIComponent(comment.text)}">수정</button>
                    <button class="delete-btn" data-id="\${comment.commentlist_idx}">삭제</button>` : '';
                	const commentHtml = `
                  	<div class="comment-item" \${indentStyle} data-comment-id="\${comment.commentlist_idx}">
                    	<div class="comment-content-wrapper" style="margin-top: 6px; padding: 10px 0; cursor: pointer; border-bottom: solid 2px var(--border-color);">
                     	   <strong>\${comment.nickname}</strong>
                     	   <span style="color:#888; font-size:0.8em; margin-left:10px;">\${new Date(comment.create_at).toLocaleString()}</span>
                     	   <div style="float:right;">\${editDeleteButtons}</div>
                     	   <p style="margin-top:5px; word-wrap:break-word;">\${comment.text.replace(/\n/g, '<br>')}</p>
                   		</div>
                  	</div>`;
                	$commentList.append(commentHtml);
              	});
            } else {
              $commentList.append('<p>No Comment</p>');
            }
          },
          error: function() { $('#comment-list').html('<p>댓글을 불러오는 중 오류가 발생했습니다.</p>'); }
        });
      }

      loadComments();

      $('#comment-form').on('submit', function(e) {
        e.preventDefault();
        if (!isLoggedIn) {
            requireLogin();
            return;
        }
        const text = $(this).find('textarea[name="text"]').val();
        if(!text.trim()) { alert('댓글 내용을 입력하세요.'); return; }
        $.ajax({
          url: commentUrl,
          type: 'POST',
          data: {
        	  action: 'add',
        	  noteIdx: noteIdxForComment, text: text
          },
          dataType: 'json',
          success: function() {
        	  $('#comment-form').find('textarea[name="text"]').val('');
        	  loadComments();
          }
        });
      });

      $('#comment-list').on('click', '.comment-content-wrapper', function() {
          const $parentComment = $(this).closest('.comment-item');
          if ($parentComment.next().hasClass('reply-form-wrapper')) {
              $parentComment.next('.reply-form-wrapper').remove();
              return;
          }
          $('.reply-form-wrapper').remove();

          const replyFormHtml = `
            <div class="reply-form-wrapper" style="margin-left: \${parseInt($parentComment.css('margin-left')) + 20}px; margin-top: 10px; padding-bottom: 10px; margin-bottom: 14px;">
                <form class="reply-form">
                    <input type="hidden" name="reCommentIdx" value="\${$parentComment.data('comment-id')}">
                    <div class="reco-div" style="display:flex; align-items: center; ">
                    <textarea name="text" rows="2" placeholder="답글을 입력하세요..." required style="width:100%; resize:none; padding: 8px; border: solid 2px var(--border-color); border-radius: 4px 0 0 4px; outline: none; background-color: var(--background-color); color: var(--font-color);"></textarea>
                    <button type="submit" style="padding: 5px 10px; height: 50px; border: solid 2px var(--border-color); border-left: none; border-radius: 0 4px 4px 0; font-weight: bold; background-color: var(--card-head); color: var(--card-back);">작성</button>
                    </div>
                </form>
            </div>
          `;
          $parentComment.after(replyFormHtml);
      });

      $('#comment-list').on('submit', '.reply-form', function(e) {
          e.preventDefault();
          const text = $(this).find('textarea[name="text"]').val();
          const reCommentIdx = $(this).find('input[name="reCommentIdx"]').val();
          if(!text.trim()) { alert('답글 내용을 입력하세요.'); return; }

          $.ajax({
              url: commentUrl,
              type: 'POST',
              data: {
            	  action: 'add',
            	  noteIdx: noteIdxForComment,
            	  text: text,
            	  reCommentIdx: reCommentIdx
              },
              dataType: 'json',
              success: function() {
            	  loadComments();
              }
          });
      });
      
      $('#comment-list').on('click', '.delete-btn', function(e) { e.stopPropagation(); if (confirm('정말로 삭제하시겠습니까?')) { $.ajax({ url: commentUrl, type: 'POST', data: { action: 'delete', commentIdx: $(this).data('id') }, dataType: 'json', success: function() { loadComments(); } }); } });
      $('#comment-list').on('click', '.edit-btn', function(e) { e.stopPropagation(); $('#edit-comment-id').val($(this).data('id')); $('#edit-comment-text').val(decodeURIComponent($(this).data('text'))); $('#edit-comment-modal').show(); });
      $('#cancel-edit-btn').on('click', function() { $('#edit-comment-modal').hide(); });
      $('#edit-comment-form').on('submit', function(e) { e.preventDefault(); const text = $('#edit-comment-text').val(); if(!text.trim()) { alert('수정할 내용을 입력하세요.'); return; } $.ajax({ url: commentUrl, type: 'POST', data: { action: 'update', commentIdx: $('#edit-comment-id').val(), text: text }, dataType: 'json', success: function() { $('#edit-comment-modal').hide(); loadComments(); } }); });
    });
    
  </script>
  
<script> // 함수

	// 조건부 뒤로가기
	function goBackSmartly() {
	   // 이전 페이지의 URL을 확인합니다.
	   const referrer = document.referrer;
	
	   // 이전 페이지 URL에 'edit'가 포함되어 있으면 두 페이지 뒤로 이동합니다.
	   if (referrer && referrer.includes('edit')) {
	       history.go(-2);
	   } else {
	       // 그 외의 경우에는 한 페이지만 뒤로 갑니다.
	   	location.href = referrer;
	   }
	}
	
</script>