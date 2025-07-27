<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath}" />
<div id="user_wrapper">
	<div id="userInfo">
		<div class="user_profile_img"></div>
		<div class="userInfo_detail">
			<div class="name_function">
				<div id="left-wrapper">
					<p></p>
					<button type="button" id="profileFollowBtn" class="btn_follow_1"></button>
					<button id="up_msg_btn">
						<i class="fa-solid fa-paper-plane"></i> Message
					</button>
					<button class="wp_btn" onclick="location.href='waList.jsp'">
						<img src="${path}/resources/images/icons/watch.svg">Watch Party
					</button>
				</div>
				<button id="settingBtn">
					<img src="${path}/resources/images/icons/settings_gear.svg" alt="setting">
				</button>
			</div>
			<div class="user_count">
				<p>
					POST <span id="profilePostCount"></span>
				</p>
				<p>
					FOLLOWER <span id="profileFollowerCount"></span>
				</p>
				<p>
					FOLLOW <span id="profileFollowingCount"></span>
				</p>
			</div>
		</div>
	</div>
	<div class="line"></div>
	<div id="con_wrapper"></div>
	<div id="loadingIndicator" style="display: none; text-align: center; padding: 20px;">
		로딩 중...
	</div>
</div>

<button id="pageCreateBtn">＋</button>

<div id="pageModalOverlay" class="modal-overlay">
	<div id="modalWrapper">
		<div class="modal-content" id="pageModalContent"
			style="border: solid 2px var(- -border-color);">
			<button class="modal-close" id="pageModalClose">&times;</button>
		</div>
	</div>
</div>

<div id="msgListModal" class="modal-msglist">
	<div class="modal-msg" style="min-width: 350px; max-width: 430px;">
		<span class="close-modal" onclick="closeMsgListModal()">
			&times; </span>
		<h3 id="msgRoomTitle"></h3>
		<div id="msgList"></div>
	</div>
</div>

<div id="chatModal" class="chat-modal-super-container">
	<div class="chat-modal-content">
		<span class="close-modal" onclick="closeChatModal()"> &times; </span>
		<h4 id="chatTitle"></h4>
		<div id="chatHistory"></div>
		<div class="chat-input-row">
			<input type="text" id="chatInput" placeholder="메시지를 입력하세요..."
				autocomplete="off" />
			<button type="button" id="sendMessageBtn" title="전송"
				onclick="sendChatMessage();">
				<i class="fa-solid fa-paper-plane"
					style="color: white; font-size: 16px; filter: invert(0) !important;"></i>
			</button>
		</div>
	</div>
</div>

<div id="modal-setting-container" class="modal-setting-container">
	<div class="setting-modal-content">
		<button class="setting-modal-close">&times;</button>
		<c:if test="${not empty sessionScope.linkError}">
			<div class="setting-error-msg"
				style="display: block; color: red; margin-bottom: 15px; border: 1px solid red; padding: 10px; border-radius: 5px;">
				${sessionScope.linkError}</div>
			<%-- 메시지를 한 번만 보여주기 위해 세션에서 제거 --%>
			<c:remove var="linkError" scope="session" />
		</c:if>
		<div id="settingContent"></div>
	</div>
</div>

<form id="logoutForm" action="user.do" method="post">
	<input type="hidden" name="accessType" value="logout">
</form>

<script> /* 함수 */
	const profileUserAcIdx = ${acIdx};
	
	let pageNum = 1; // 현재 페이지 번호
	let isLoading = false; // 로딩 중 중복 요청 방지 플래그
	let hasMorePosts = true; // 더 불러올 게시물이 있는지 여부
	
	// 게시물 목록을 화면에 추가
	function renderPosts(posts) {
		if (!posts || posts.length === 0) {
            hasMorePosts = false;
            return;
        }
		
		let postHtml = '';
	    posts.forEach(post => {
	        const thumbnailUrl = post.titleimg
	            ? `${path}/${post.titleimg}` 
	            : `${path}/resources/images/system/default_thumbnail.png`;
	
	        postHtml += `
	            <a href="${path}/note/${post.noteIdx}">
	                <div class="con_item">
	                    <img src="${thumbnailUrl}" alt="${post.title} 썸네일">
	                </div>
	            </a>
	        `;
	    });
	    $('#con_wrapper').append(postHtml);
	}
	
	// 다음 페이지 게시물을 불러오는 함수
	function loadMorePosts() {
	    if (isLoading || !hasMorePosts) return; // 로딩 중이거나 더 이상 게시물이 없으면 실행 안함
	
	    isLoading = true;
	    $('#loadingIndicator').show();
	    pageNum++; // 다음 페이지 요청
	
	    $.ajax({
	        url: `${path}/api/userpage/${profileUserAcIdx}/posts`,
	        type: 'GET',
	        data: { pageNum: pageNum, amount: 9 },
	        dataType: 'json',
	        success: function(morePosts) {
	        	renderPosts(morePosts);
	        },
	        error: function() {
	            console.error("게시물을 추가로 불러오는데 실패했습니다.");
	        },
	        complete: function() {
	            isLoading = false;
	            $('#loadingIndicator').hide();
	        }
	    });
	}
</script>

<script>
$(document).ready(function() {
    $('#pageCreateBtn').on('click', function() {
		location.href = '${path}/note/new';
	});
	
	// 초기 데이터 로딩 (프로필 + 첫 페이지 게시물)
    $.ajax({
        url: '${path}/api/userpage/${acIdx}',
        type: 'GET',
        dataType: 'json',
        success: function(userPageData) {
        	const profile = userPageData.userProfile;
        	
        	// 1. 프로필 정보 채우기
            const profileImgUrl = profile.img ? `${path}/upload/${profile.img}` : `${path}/resources/images/system/default_avatar.png`;
            $('.user_profile_img').html(`<img id="mainProfileImage" src="\${profileImgUrl}" alt="프로필">`);
        	
            $('#left-wrapper p').text(profile.nickname);
            $('#profilePostCount').text(profile.postCount);
            $('#profileFollowingCount').text(profile.followingCount);
            $('#profileFollowerCount').text(profile.followerCount);         
            
            let profileFollowBtn = $('#profileFollowBtn');
            if (isLoggedIn && loggedInUserAcIdx == profile.acIdx) {
                // 내 페이지일 경우
                $('#profileFollowBtn').hide();
                $('#up_msg_btn').hide();
                $('.wp_btn').show();
                $('#settingBtn').show();
                $('#pageCreateBtn').show();
            	
                profileFollowBtn.attr('data-author-id', ${userPageData.userProfile.acIdx});
                profileFollowBtn.attr('data-following', ${userPageData.userProfile.followedByCurrentUser ? 'true' : 'false'});
                profileFollowBtn.text(${userPageData.userProfile.followedByCurrentUser ? 'UNFOLLOW' : 'FOLLOW'});
			} else if (isLoggedIn) {
                // 다른 사람 페이지에 로그인해서 접속한 경우
                $('#profileFollowBtn').attr('data-author-id', profile.acIdx);
                $('#profileFollowBtn').attr('data-following', profile.followedByCurrentUser);
                $('#profileFollowBtn').text(profile.followedByCurrentUser ? 'UNFOLLOW' : 'FOLLOW');
                $('#profileFollowBtn').show();
                $('#up_msg_btn').show();
                $('.wp_btn').hide();
                $('#settingBtn').hide();
                $('#pageCreateBtn').hide();
			} else {
                // 비로그인 상태일 경우
                $('#profileFollowBtn').hide();
                $('#up_msg_btn').hide();
                $('.wp_btn').hide();
                $('#settingBtn').hide();
                $('#pageCreateBtn').hide();
			}
            
            // 2. 첫 페이지 게시물 표시
            renderPosts(userPageData.posts);
        }
    });

    // 스크롤 이벤트 리스너
    $(window).on('scroll', function() {
        // (현재 스크롤 위치 + 브라우저 창 높이)가 (전체 문서 높이 - 100px)보다 클 때
        if ($(window).scrollTop() + $(window).height() > $(document).height() - 100) {
            loadMorePosts();
        }
    });
});
</script>
