<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<!-- content -->
<div id="content_wrapper">
	<section id="content">
		<div id="workspace_wrapper">
			<div id="todolist">
				<div id="calendar">
					<button id="add-event-button"
						style="display: none; position: absolute;">+</button>
				</div>
				<div id="date-picker-popover" style="display: none;">
					<div class="date-picker-body">
						<select id="year-select"></select> <select id="month-select"></select>
						<button id="goto-date-btn">이동</button>
					</div>
				</div>
				<%-- 우측 영역: .calendar_contents --%>
				<div class="calendar_contents">
					<%-- 탭 버튼 영역 --%>
					<div class="tab-buttons">
						<button class="tab-btn active" data-tab="tab_schedule">일정</button>
						<button class="tab-btn" data-tab="tab_todo">할 일</button>
					</div>
					<div class="add-btn-container">
						<button id="add-schedule-btn" class="add-btn">+ 새 일정</button>
						<button id="add-todo-btn" class="add-btn" style="display: none;">+
							새 할 일</button>
					</div>
					<%-- 탭 컨텐츠 영역 --%>
					<div class="tab-content-wrapper">
						<div id="tab_schedule" class="tab-content active">
							<%-- 날짜 제목이 표시될 영역 --%>
							<h4 class="schedule-date-title"></h4>
							<%-- 실제 일정 목록이 들어갈 컨테이너 --%>
							<div id="daily-schedule-list-container">
								<p>캘린더에서 날짜를 선택해주세요.</p>
							</div>
						</div>
						<div id="tab_todo" class="tab-content">
							<p>로딩 중...</p>
						</div>
					</div>
				</div>
			</div>


			<div class="line"></div>

			<!-- 블록 위치 변경을 위한 수정, 저장, 취소 버튼 -->
			<div class="workspace-controls">
				<h3>
					<i class="fa-solid fa-shapes"></i>&nbsp;&nbsp;My Workspace
				</h3>
				<div class="workspace-buttons">
					<button id="edit-order-btn" class="control-btn">
						<i class="fa-solid fa-pen-to-square"
							style="filter: invert(0) !important;"></i> 편집
					</button>
					<button id="save-order-btn" class="control-btn"
						style="display: none;">
						<i class="fa-solid fa-save" style="filter: invert(0) !important;"></i>
						저장
					</button>
					<button id="cancel-order-btn" class="control-btn"
						style="display: none;">
						<i class="fa-solid fa-times" style="filter: invert(0) !important;"></i>
						취소
					</button>
				</div>
			</div>

			<div id="contents_grid">
				<!-- 내가 작성한 전체 글 목록 (인기글 순) -->
				<div class="contents_item" id="my-posts">
					<div class="widget-header">
						<h4>
							<i class="fa-solid fa-pen-nib"></i>&nbsp;&nbsp;내가 작성한 글
						</h4>
						<button class="more-btn" data-type="my-posts">더보기</button>
					</div>
					<ul>
						<c:choose>
							<c:when test="${not empty initialData.myPosts}">
								<c:forEach var="post" items="${initialData.myPosts}">
									<li><a href="postView.do?nidx=${post.note_idx}"
										title="${post.title}"> <span>${post.title}</span> <span
											class="block-meta"> <i class="fa-regular fa-eye"></i>
												${post.view_count}&nbsp;&nbsp; <i
												class="fa-regular fa-thumbs-up"></i>${post.like_count}
										</span>
									</a></li>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<li class="no-items">작성한 글이 없습니다.</li>
							</c:otherwise>
						</c:choose>
					</ul>
				</div>
				<!-- 좋아요한 포스트 목록 -->
				<div class="contents_item" id="liked-posts">
					<div class="widget-header">
						<h4>
							<i class="fa-solid fa-heart"></i>&nbsp;&nbsp;좋아요한 글
						</h4>
						<button class="more-btn" data-type="liked-posts">더보기</button>
					</div>
					<ul>
						<c:choose>
							<c:when test="${not empty initialData.likedPosts}">
								<c:forEach var="post" items="${initialData.likedPosts}">
									<li><a href="postView.do?nidx=${post.note_idx}"
										title="${post.title}"> <span>${post.title}</span> <span
											class="block-meta">by ${post.author_name}</span>
									</a></li>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<li class="no-items">좋아요한 글이 없습니다.</li>
							</c:otherwise>
						</c:choose>
					</ul>
				</div>

				<!-- 안읽은 메시지 목록 -->
				<div id="unread_messages" class="contents_item"
					style="background-color: var(--sidebar-color); border-radius: 20px; padding: 20px; box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);">
					<h3
						style="font-size: 18px; margin-bottom: 16px; color: var(--font-color); border-bottom: 1px solid var(--border-color); padding-bottom: 8px;">
						<i class="fa-solid fa-bell"></i> 안읽은 메시지
					</h3>
					<div class="message_card_list">
						<c:choose>
							<c:when test="${not empty initialData.unreadMessages}">
								<c:forEach var="msg" items="${initialData.unreadMessages}">
									<div class="message_card message_item"
										data-sender-idx="${msg.ac_sender}"
										data-nickname="${msg.latestMessage.sender_nickname}">
										<%-- <div class="msg_profile">
                                        <img src="${pageContext.request.contextPath}/vibesync/sources/profile/${msg.latestMessage.sender_img}" alt="profile">
                                        </div> --%>
										<div class="msg_text_area">
											<div class="msg_sender_row">
												<div class="msg_sender">${msg.latestMessage.sender_nickname}</div>
												<span class="unread-badge">${msg.numOfUnreadMessages}</span>
											</div>
											<div class="msg_preview">${msg.latestMessage.text}</div>
											<div class="msg_time">${msg.latestMessage.relativeTime}</div>
										</div>
									</div>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<div class="no_message">새로운 메시지가 없습니다.</div>
							</c:otherwise>
						</c:choose>
					</div>
				</div>

				<!-- 동적으로 추가된 블록들 -->
				<c:forEach var="block" items="${workspaceData.blocks}">
					<div class="contents_item generated_block"
						id="block-${block.blockId}">
						<div class="block-header">
							<h4>
								<c:choose>
									<c:when test="${block.blockType == 'CategoryPosts'}">
										<i class="fa-solid fa-layer-group"></i>&nbsp;${block.categoryName} ${block.sortType == 'popular' ? '인기' : '최신'}글</c:when>
									<c:when test="${block.blockType == 'WatchParties'}">
										<i class="fa-solid fa-tv"></i>&nbsp;진행중인 워치파티</c:when>
									<c:when test="${block.blockType == 'UserStats'}">
										<i class="fa-solid fa-chart-simple"></i>&nbsp;${block.title}</c:when>
								</c:choose>
							</h4>
							<div class="block-actions">
								<button class="refresh-block-btn"
									data-block-id="${block.blockId}" title="새로고침">
									<i class="fa-solid fa-arrows-rotate"></i>
								</button>
								<button class="delete-block-btn"
									data-block-id="${block.blockId}" title="삭제">
									<i class="fa-solid fa-trash-can"></i>
								</button>
							</div>
						</div>
						<div class="block-content">
							<%-- 각 블록 타입에 맞는 JSP 프래그먼트를 include --%>
							<c:set var="block" value="${block}" scope="request" />
							<jsp:include
								page="/WEB-INF/views/page/workspace/fragments/_${block.blockType}Content.jsp" />
						</div>
					</div>
				</c:forEach>

				<!-- 블록 추가 버튼(블록) -->
				<div id="content_plus" class="contents_item">+</div>

			</div>
		</div>
	</section>
</div>


<%-- ======================================================== --%>
<%--                통합 추가/수정 모달 창                     --%>
<%-- ======================================================== --%>
<div class="modal-overlay" id="unified-modal" style="display: none;">
	<div class="modal-content">
		<h2 id="modal-title"></h2>
		<%-- 제목은 JS가 채워줍니다 --%>

		<form id="schedule-form" style="display: none;">
			<input type="hidden" id="schedule-id" name="scheduleIdx">
			<div class="form-group">
				<label for="schedule-title">제목</label> <input type="text"
					id="schedule-title" name="title" required>
			</div>
			<div class="form-group">
				<label for="schedule-description">설명</label>
				<textarea id="schedule-description" name="description" rows="3"></textarea>
			</div>
			<div class="form-group-row">
				<div class="form-group">
					<label for="schedule-start">시작 시간</label> <input
						type="datetime-local" id="schedule-start" name="startTime"
						required>
				</div>
				<div class="form-group">
					<label for="schedule-end">종료 시간</label> <input
						type="datetime-local" id="schedule-end" name="endTime" required>
				</div>
			</div>
			<div class="form-group">
				<label for="schedule-color">색상</label>
				<div class="color-picker-wrapper">
					<input type="color" id="schedule-color" name="color"
						value="#3788d8">
					<div id="schedule-recent-colors" class="recent-colors-container"></div>
				</div>
			</div>
			<div class="modal-buttons">
				<button type="button" class="modal-close-btn">취소</button>
				<button type="submit" class="modal-save-btn">저장</button>
			</div>
		</form>

		<form id="todo-form" style="display: none;">
			<input type="hidden" id="todo-id" name="todoIdx">
			<div class="form-group">
				<label for="todo-text">내용</label>
				<textarea id="todo-text" name="text" rows="4" required></textarea>
			</div>
			<!--      <div class="form-group">
                <label for="todo-group">그룹</label>
                <input type="text" id="todo-group" name="todo_group">
            </div> -->
			<div class="form-group">
				<label for="todo-color">색상</label>
				<div class="color-picker-wrapper">
					<input type="color" id="todo-color" name="color" value="#3788d8">
					<div id="todo-recent-colors" class="recent-colors-container"></div>
				</div>
			</div>

			<div class="modal-buttons">
				<button type="button" class="modal-close-btn">취소</button>
				<button type="submit" class="modal-save-btn">저장</button>
			</div>
		</form>
	</div>
</div>
<%-- 게시글 전체 목록 표시용 모달 창 --%>
<div class="modal-overlay" id="list-modal" style="display: none;">
	<div class="modal-content">
		<h2 id="list-modal-title"></h2>
		<div class="list-modal-content"></div>
		<div class="modal-buttons">
			<button type="button" class="modal-close-btn">닫기</button>
		</div>
	</div>
</div>

<!-- 모달 -->
<!-- 메시지 모달 -->
<div id="chatModal" class="modal">
	<div class="modal-content" style="min-width: 350px; max-width: 430px;">
		<span class="close-modal" onclick="closeChatModal()"> &times; </span>
		<h4 id="chatTitle" style="text-align: center;">채팅</h4>
		<div id="chatHistory"></div>
		<div class="chat-input-row">
			<input type="text" id="chatInput" placeholder="메시지를 입력하세요..."
				autocomplete="off" />
			<button type="button" id="sendMessageBtn" title="전송">
				<svg width="22" height="22" viewBox="0 0 22 22" fill="none">
		      <path d="M3 19L20 11L3 3V10L15 11L3 12V19Z" fill="#fff" />
		    </svg>
			</button>
		</div>
	</div>
</div>

<!-- 블록 추가 모달 -->
<div id="addBlockModal" class="modal">
	<div class="modal-content" style="text-align: center;">
		<h4>추가할 블록 선택</h4>
		<hr style="width: 100%; border: solid 1px var(--border-color);">
		<br> <select id="blockTypeSelector">
			<option value="CategoryPosts">카테고리별 글</option>
			<option value="WatchParties">구독 워치파티</option>
			<option value="UserStats">내 활동 통계</option>
		</select>

		<div id="category" style="display: none;">
			<select id="categorySelector" name="category">
				<c:forEach items="${ categoryVOList }" var="categoryVO">
					<option value="${ categoryVO.category_idx }">${ categoryVO.c_name }</option>
				</c:forEach>
			</select> <br> <select id="sortTypeSelector">
				<option value="popular">인기순</option>
				<option value="latest">최신순</option>
			</select>
		</div>
		<button id="confirmAddBlock" style="display: block;">추가</button>
	</div>
</div>
<!-- 모달 끝 -->

<!-- 초기 차트 데이터 설정 -->
<c:forEach var="block" items="${workspaceData.blocks}">
	<c:if test="${block.blockType == 'UserStats'}">
		<script>
            // 서버에서 받은 차트 데이터를 data 속성으로 설정
            $(document).ready(function() {
                $('#block-${block.blockId}').find('.block-content').attr('data-chart-data', '${block.chartDataJson}');
            });
        </script>
	</c:if>
</c:forEach>

<script>
        const contextPath = "${pageContext.request.contextPath}";
</script>
</body>

</html>