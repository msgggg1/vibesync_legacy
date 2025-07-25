<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="user_wrapper">
	<div id="userInfo">
		<div class="user_profile_img">
			<c:choose>
				<c:when test="${not empty userPageData.userProfile.img}">
					<img id="mainProfileImage"
						src="${pageContext.request.contextPath}/${userPageData.userProfile.img}"
						alt="프로필">
				</c:when>
				<c:otherwise>
					<img id="mainProfileImage"
						src="${pageContext.request.contextPath}/sources/default/default_user.jpg"
						alt="기본 프로필">
				</c:otherwise>
			</c:choose>
		</div>
		<div class="userInfo_detail">
			<div class="name_function">
				<div id="left-wrapper">
					<p>${userPageData.userProfile.nickname}</p>
					<c:if
						test="${sessionScope.userInfo != null && sessionScope.userInfo.ac_idx != userPageData.userProfile.ac_idx}">
						<button type="button" id="profileFollowBtn" class="btn_follow_1"
							data-author-id="${userPageData.userProfile.ac_idx}"
							data-following="${userPageData.userProfile.followedByCurrentUser ? 'true' : 'false'}">
							${userPageData.userProfile.followedByCurrentUser ? 'UNFOLLOW' : 'FOLLOW'}
						</button>
					</c:if>
					<button id="up_msg_btn">
						<i class="fa-solid fa-paper-plane"></i> Message
					</button>
					<c:if
						test="${sessionScope.userInfo != null && sessionScope.userInfo.ac_idx == userPageData.userProfile.ac_idx}">
						<button class="wp_btn" onclick="location.href='waList.jsp'">
							<img src="${pageContext.request.contextPath}/sources/icons/watch.svg">Watch Party
						</button>
					</c:if>
				</div>
				<c:if
					test="${sessionScope.userInfo != null && sessionScope.userInfo.ac_idx == userPageData.userProfile.ac_idx}">
					<button id="settingBtn">
						<img src="${pageContext.request.contextPath}/sources/icons/settings_gear.svg" alt="setting">
					</button>
				</c:if>
			</div>
			<div class="user_count">
				<p>
					POST <span>${userPageData.userProfile.postCount}</span>
				</p>
				<p>
					FOLLOWER <span id="profileFollowerCount">${userPageData.userProfile.followerCount}</span>
				</p>
				<p>
					FOLLOW <span>${userPageData.userProfile.followingCount}</span>
				</p>
			</div>
		</div>
	</div>
	<div class="line"></div>
	<div id="con_wrapper">
		<c:forEach var="post" items="${userPageData.posts}">
			<a href="${pageContext.request.contextPath}/board/view?noteIdx=${post.note_idx}">
				<div class="con_item">
					<c:choose>
						<c:when test="${not empty post.thumbnail_img}">
							<img
								src="${pageContext.request.contextPath}/${post.thumbnail_img}"
								alt="${post.title} 썸네일">
						</c:when>
						<c:otherwise>
							<img
								src="${pageContext.request.contextPath}/sources/images/default_thumbnail.png"
								alt="기본 썸네일">
						</c:otherwise>
					</c:choose>
				</div>
			</a>
		</c:forEach>
	</div>
	<div id="loadingIndicator"
		style="display: none; text-align: center; padding: 20px;">로딩
		중...</div>
</div>

<c:if
	test="${sessionScope.userInfo != null && sessionScope.userInfo.ac_idx == userPageData.userProfile.ac_idx}">
	<button id="pageCreateBtn">＋</button>
</c:if>

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

<script>
	const profileUserAcIdx = ${userPageData.userProfile.acIdx}; // ${acIdx}
</script>

<script>
    $(document).ready(function() {
        // Controller가 Model에 담아준 acIdx 값을 JavaScript 변수로 받음
        const userAcIdx = ${acIdx};
        
        // 이 acIdx를 사용하여 API 서버에 데이터 요청
        $.ajax({
            url: '${pageContext.request.contextPath}/api/page/user',
            type: 'GET',
            data: { acIdx: userAcIdx },
            dataType: 'json',
            success: function(userPageData) {
            	const nickname = response.userProfile.nickname;
                const posts = response.posts;
            	
                // 받아온 데이터로 화면을 동적으로 그리는 로직
                // 예: $('#user-profile-content').html(...);
            },
            error: function() {
                // ...
            }
        });
    });
</script>