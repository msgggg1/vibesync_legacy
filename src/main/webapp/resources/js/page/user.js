// 변수 선언
let currentChatSenderIdx = 0;
let currentUserData = null; // 인증 성공 시 사용자 정보를 저장할 전역 변수

//============= 함수 ===============
function openMessageModal() {
    window.location.hash = "msg";
    $.ajax({
        url: ctx + '/message/list',
        type: 'GET',
        dataType: 'json',
        success: function(msgList) {
            $('#msgListModal').css('display', 'flex');
            $('#msgRoomTitle').html('<i class="fa-solid fa-comment-dots"></i> 메시지 목록');
            $('#msgList').empty();
            if (!msgList || !Array.isArray(msgList) || msgList.length === 0) {
                const placeholderHtml = `
                	<div class="no-message-placeholder">
                		<i class="fa-regular fa-comments"></i>
                		<p>메시지 내역이 없습니다.</p>
                	</div>`;
                $('#msgList').html(placeholderHtml);
                return;
            }
            const msgContainer = $('<div class="msg-container"></div>');
    		const defaultImgSrc = ctx + '/sources/default/default_user.jpg';
    		const currentImgSrcPath = ctx + '/sources/upload/member/profileImg';
            msgList.forEach(msg => {
                let profileImgHtml = msg.other.profile_img ? `<img src="${currentImgSrcPath}/${msg.other.profile_img}" alt="profile">` : `<img src="${defaultImgSrc}" alt="기본 프로필">`;
                let unreadBadgeHtml = '';
                if (msg.numOfUnreadMessages > 0) {
                    unreadBadgeHtml = `<span class="unread-badge">${msg.numOfUnreadMessages}</span>`;
                }
                const messageHtml = `
                	<div class="msg_item" data-sender-idx="${msg.other.ac_idx}" data-nickname="${msg.other.nickname}">
                		<div class="msg_profile">${profileImgHtml}</div>
                		<div class="msg_text_area">
                			<div class="msg_sender_row">
                				<div class="msg_sender">${msg.other.nickname}</div>
                				${unreadBadgeHtml}
                			</div>
                			<div class="msg_preview">${msg.latestMessage.text}</div>
                			<div class="msg_time">${msg.latestMessage.relativeTime}</div>
                		</div>
                	</div>`;
                msgContainer.append(messageHtml);
            });
            $('#msgList').append(msgContainer);
        },
        error: function() { alert('메시지 내역 불러오기 실패'); }
    });
}

function closeMsgListModal() {
    window.location.hash = "";
    $('#msgListModal').hide();
}

function openChatWithUser(userIdx, nickname) {
    currentChatSenderIdx = userIdx;
    $('#chatTitle').text(nickname + '님과의 대화');
    $('#chatHistory').html('<p style="text-align:center; padding: 20px;">대화 내역을 불러오는 중...</p>');
    closeMsgListModal();
    $.ajax({
        url: ctx + '/message/chat',
        type: 'GET',
        data: { sender_idx: userIdx },
        dataType: 'json',
        success: function (chatList) {
            $('#chatHistory').empty();
            if (!chatList || !Array.isArray(chatList) || chatList.length === 0) {
                $('#chatHistory').html('<p style="text-align:center; color:grey; padding: 20px;">아직 대화 내역이 없습니다.</p>');
            } else {
                const chatContainer = $('<div class="chat-container"></div>');
                let lastDate = null;
                chatList.forEach(msg => {
                    if (msg.date !== lastDate) {
                        lastDate = msg.date;
                        const dateLabel = $('<div class="chat-date-separator"></div>').text(lastDate);
                        chatContainer.append(dateLabel);
                    }
                    const who = msg.isMine ? 'bubble-me' : 'bubble-other';
                    const formattedText = msg.text.replace(/\n/g, '<br>');
                    const messageHtml = `
                    	<div class="chat-bubble ${who}">
                    		<div class="bubble-text">${formattedText}</div>
                    		<div class="bubble-time">${msg.relativeTime}</div>
                    	</div>`;
                    chatContainer.append(messageHtml);
                });
                $('#chatHistory').append(chatContainer);
            }
            $('#chatModal').css('display', 'flex');
            $('#chatHistory').scrollTop($('#chatHistory')[0].scrollHeight);
        },
        error: function () { alert('채팅 내역을 불러오는 데 실패했습니다.'); }
    });
}

function closeChatModal() {
    $('#chatModal').hide();
    currentChatSenderIdx = null;
    if (loggedInUserAcIdx == profileUserAcIdx) {
        openMessageModal();
    }
}

function sendChatMessage() {
    const message = $("#chatInput").val().trim();
    if (!message || !currentChatSenderIdx) return;
    $.ajax({
        url: ctx + '/message/chat',
        type: 'POST',
        data: JSON.stringify({ receiver_idx: currentChatSenderIdx, text: message }),
        contentType: "application/json; charset=utf-8",
        dataType: 'json',
        success: function(res) {
            if(res.success) {
                $("#chatInput").val("");
                reloadChatHistory();
            } else {
                alert('메시지 전송에 실패했습니다.');
            }
        },
        error: function() { alert('메시지 전송 중 오류 발생!'); }
    });
}

function reloadChatHistory() {
   if (currentChatSenderIdx) {
       const currentNickname = $('#chatTitle').text().replace('님과의 대화', '');
       openChatWithUser(currentChatSenderIdx, currentNickname);
    }
}

function showPasswordCheckView() {
    const passwordCheckHtml = `
    	<h4>비밀번호 확인</h4>
    	<p style="font-size:0.9em; color:#555; margin-top:-10px; margin-bottom:20px;">
    		계정 설정을 위해 현재 비밀번호를 입력해주세요.
    	</p>
    	<form id="passwordCheckForm">
    		<div class="setting-error-msg"></div>
    		<input type="password" name="password" placeholder="비밀번호" required autocomplete="current-password">
    		<button type="submit">확인</button>
    	</form>`;
    $('#settingContent').html(passwordCheckHtml);
}

function showCombinedSettingsView() {
    const defaultImgSrc = ctx + '/sources/default/default_user.jpg';
    const currentImgSrc = currentUserData && currentUserData.img ? ctx + '/sources/upload/member/profileImg/' + currentUserData.img : defaultImgSrc;

    const combinedHtml = `
        <h4>계정 설정</h4>
        
        <h5>프로필 사진 변경</h5>
        <form id="profileImageForm">
            <img id="profileImagePreview" src="${currentImgSrc}" alt="프로필 미리보기">
            	<input type="file" name="profileImage" id="profileImageInput" accept="image/*" required>
            <button type="submit">프로필 사진 저장</button>
        </form>

        <h5>비밀번호 변경</h5>
        <form id="changePasswordForm">
            <div class="setting-error-msg"></div>
            <input type="password" name="newPassword" placeholder="새 비밀번호" required autocomplete="new-password">
            <input type="password" name="confirmPassword" placeholder="새 비밀번호 확인" required autocomplete="new-password">
            <button type="submit">비밀번호 변경</button>
        </form>
        
        <h5>카카오 계정 연동</h5>
        <div id="social-login-container">
		    <div id="kakaoLinkContainer">
            </div>
		</div>

        <button id="btnDeleteAccount">회원 탈퇴</button>
    `;
    $('#settingContent').html(combinedHtml);
    
    const isKakaoLinked = currentUserData && currentUserData.kakao_auth_id && currentUserData.kakao_auth_id > 0;

    if (isKakaoLinked) {
        $('#kakaoLinkContainer').html('<button id="btnKakaoUnlink" class="modal-btn-kakao-unlink"><img id="kakao_img" src="${ctx}/sources/icons/KakaoTalk_logo.svg" alt="카카오 로그인"><span>카카오 연결 해제</span></button>');
    } else {
        $('#kakaoLinkContainer').html('<a style="font-size:12px;" href="${ctx}/auth/kakao/link" class="modal-btn-kakao-link"><img id="kakao_img" src="${ctx}/sources/icons/KakaoTalk_logo.svg" alt="카카오 로그인"><span>카카오 계정 연결</span></a>');
    }
}

//========= js ===========

    /*무한스크롤 함수*/
    var isLoading = false; // 중복 요청 방지 플래그
    
    $.ajaxSetup({
        cache: false
      });
    
    function loadMorePosts(){
        var hasMore = ($('#hasMorePosts').val() === 'true');
        if (!hasMore || isLoading) return;
        isLoading = true;
        $('#loadingIndicator').show();
        var nextPage = parseInt($('#currentPageNumber').val()) + 1;
        var profileUserId = $('#profileUserAcIdx').val();
        
        $.ajax({
            url: ctx + '/loadMorePosts', 
            type: 'GET', 
            data: { userId: profileUserId, page: nextPage },
            dataType: 'json',
            success: function(response) {
                if (response.posts && response.posts.length > 0) {
                    var postsHtml = '';
                    var contextPath = ctx;
                    $.each(response.posts, function(index, post) {
                        var thumbnailUrl = post.thumbnail_img ? contextPath + post.thumbnail_img : contextPath + 'vibesync/sources/default/default_thumbnail.png';
                        postsHtml += '<a href="' + contextPath + '/vibesync/postView.do?nidx=' + post.note_idx + '">';
                        postsHtml += '    <div class="con_item">';
                        postsHtml += '        <img src="' + thumbnailUrl + '" alt="' + post.title + ' 썸네일">';
                        postsHtml += '    </div>';
                        postsHtml += '</a>';
                    });
                    $('#con_wrapper').append(postsHtml);
                    $('#currentPageNumber').val(nextPage);
                }
                if (!response.hasMore) {
                    $('#hasMorePosts').val('false');
                    $('#loadingIndicator').text('더 이상 게시물이 없습니다.');
                } else {
                    $('#loadingIndicator').hide();
                    setTimeout(function() { checkAndLoadIfNeeded(); }, 100);
                }
                isLoading = false;
            },
            error: function() {
                alert('게시물을 추가로 불러오는 중 오류가 발생했습니다.');
                $('#loadingIndicator').hide();
                isLoading = false;
            }
        });
    }
    
   function checkAndLoadIfNeeded() {
        if ($(document).height() <= $(window).height() && ($('#hasMorePosts').val() === 'true')) {
            console.log("콘텐츠가 부족하여 새 게시물을 자동으로 로드합니다.");
            loadMorePosts();
        }
   }

   $(document).ready(function() {
        $('#profileFollowBtn').on('click', function() {
            var $button = $(this);
            var authorId = $button.data('author-id');
            
            if (!isLoggedIn) {
                requireLogin();
                return;
            }
            
            followToggle(authorId);
        });

        $(window).scroll(function() {
            if ($(window).scrollTop() + $(window).height() >= $(document).height() - 200) {
                loadMorePosts();
            }
        });
        
        $(window).on('resize', function() {
            checkAndLoadIfNeeded();
        });
        
        $('#pageCreateBtn').on('click', function() {
            var acIdx = $('#profileUserAcIdx').val();
            $.get(ctx + '/page/modalList.do', { ac_idx: acIdx }, function(html) {
                $('#pageModalContent').children(':not(.modal-close)').remove();
                $('#pageModalContent').append(html);
                $('#pageModalOverlay').fadeIn();
            });
        });

        $('#pageModalOverlay').on('click', '#pageModalClose, .modal-overlay', function(e) {
            e.stopPropagation();
            $('#pageModalOverlay').fadeOut(function() {
                $('#pageModalContent').children(':not(.modal-close)').remove();
            });
        });

        $('#pageModalOverlay').on('click', '#newPageBtn', function() {
            $('#pageModalContent').children(':not(.modal-close)').remove();
            var formHtml = ''
              + '<h3>새 페이지 생성</h3>'
              + '<form id="pageCreateForm" enctype="multipart/form-data">'
              + '  <label>Subject&nbsp<input type="text" id="subject" name="subject" required/></label><br/>'
              + '  <button type="submit" class="btn_deco">Create</button>'
              + '</form>';
            $('#pageModalContent').append(formHtml);
        });
        
        $('#pageModalOverlay').on('click', '#newNoteBtn', function() {
             var selectedIdx = $('#pageSelect').val();
             $('#newNoteLink').attr('href', 'notecreate.do?pageidx=' + selectedIdx);
             window.location.href = $('#newNoteLink').attr('href');
        });

        $('#pageModalOverlay').on('submit', '#pageCreateForm', function(e) {
            e.preventDefault();
            var formData = new FormData(this);
            let subject = document.getElementById("subject").value;
            $.ajax({
                url: ctx + '/page/create?subject=' + subject,
                type: 'GET',
                data: formData,
                processData: false,
                contentType: false,
                dataType: 'json',
                success: function(res) {
                    if (res.success) {
                        $('#pageModalOverlay').fadeOut();
                    } else {
                        alert('페이지 생성 실패: ' + (res.message || ''));
                    }
                },
                error: function() {
                    alert('페이지 생성 중 오류가 발생했습니다.');
                }
            });
        });
                
        const isOwnProfile = (loggedInUserAcIdx == profileUserAcIdx);
        
        $('#up_msg_btn').on('click', function () {
            if (!isLoggedIn) {
                requireLogin();
                return;
            }
            if (isOwnProfile) {
                openMessageModal();
            } else {
                const profileUserId = $('#profileUserAcIdx').val();
                const profileUserNickname = $('#profileUserNickname').val();
                openChatWithUser(profileUserId, profileUserNickname);
            }
       });
        
        $('#msgList').on('click', '.msg_item', function() {
            const senderIdx = $(this).data('sender-idx');
            const nickname = $(this).data('nickname');
            openChatWithUser(senderIdx, nickname);
        });
    
        $("#chatInput").on("keydown", function(e) {
            if (e.key === "Enter" && !e.shiftKey) {
                e.preventDefault();
                sendChatMessage();
            }
        });
       
        $('#settingBtn').on('click', function() {
            showPasswordCheckView();
            $('#modal-setting-container').css('display', 'flex').hide().fadeIn(200);
        });

        $('#modal-setting-container').on('click', '.setting-modal-close, .modal-setting-container', function(e) {
            if (e.target === this) {
                $('#modal-setting-container').fadeOut(200);
            }
        }).on('click', '.setting-modal-content', function(e) {
            e.stopPropagation();
        });

        $('#modal-setting-container').on('submit', '#passwordCheckForm', function(e) {
            e.preventDefault();
            const password = $(this).find('input[name="password"]').val();
            $.ajax({
                url: ctx + '/api/setting/checkPassword',
                type: 'POST',
                data: { password: password },
                dataType: 'json',
                success: function(response) {
                    if (response.success) {
                        currentUserData = response.userData;
                        console.log("currentUserData : " + currentUserData)
                        showCombinedSettingsView();
                    } else {
                        $('#passwordCheckForm .setting-error-msg').text(response.message || '인증에 실패했습니다.');
                    }
                },
                error: function(xhr) {
                    const errorMsg = xhr.responseJSON ? xhr.responseJSON.message : '서버 통신 중 오류가 발생했습니다.';
                    $('#passwordCheckForm .setting-error-msg').text(errorMsg);
                }
            });
        });
        
        $('#modal-setting-container').on('change', '#profileImageInput', function() {
            if (this.files && this.files[0]) {
                const reader = new FileReader();
                
                reader.onload = function(e) {
                    $('#profileImagePreview').attr('src', e.target.result);
                }
                reader.readAsDataURL(this.files[0]);
            }
        });

     // 프로필 이미지 변경 폼 제출 ( + 이미지 미리 로드 기능)
        $('#modal-setting-container').on('submit', '#profileImageForm', function(e) {
            e.preventDefault();
            const fileInput = document.getElementById('profileImageInput');
            if (!fileInput.files || fileInput.files.length === 0) {
                alert('이미지를 선택해주세요.');
                return;
            }
            const file = fileInput.files[0];
            const reader = new FileReader();
            
            reader.onload = function(event) {
                const base64Image = event.target.result;
                
                $.ajax({
                    url: ctx + '/api/setting/setProfile',
                    type: 'POST',
                    data: {
                        profileImageBase64: base64Image
                    },
                    dataType: 'json',
                    cache: false,
                    success: function(response) {
                        if (response.success) {
                            // 1. 새 이미지 경로에 캐시 무효화를 위한 타임스탬프 추가
                            const newImgSrc = ctx + '/vibesync/' + response.newImagePath+ '?t=' + new Date().getTime();

                            // 4. 화면에 보이는 이미지들의 src를 교체 (이제 즉시 반영됨)
                            $('#mainProfileImage').attr('src', newImgSrc);
                            $('#profileImagePreview').attr('src', newImgSrc);
                            $('#profile-display img').attr('src', newImgSrc);
                            if(currentUserData) currentUserData.img = response.newImagePath;

                            // 5. 모든 시각적 업데이트가 끝난 후 사용자에게 알림
                            alert('프로필 이미지가 변경되었습니다.');
                            
                            // 이미지 로딩 실패 시 에러 처리
                            tempImg.onerror = function() {
                                alert('새 프로필 이미지를 불러오는 데 실패했습니다.');
                            };
                            
                        } else {
                            alert('오류: ' + response.message);
                        }
                    },
                    error: function() {
                        alert('프로필 이미지 변경 중 서버 오류가 발생했습니다.');
                    }
                });
            };
            reader.readAsDataURL(file);
        });
        
        $('#modal-setting-container').on('submit', '#changePasswordForm', function(e) {
            e.preventDefault();
            const newPassword = $(this).find('input[name="newPassword"]').val();
            const confirmPassword = $(this).find('input[name="confirmPassword"]').val();
            
            $('.setting-error-msg').text('');
            
            if (newPassword !== confirmPassword) {
                $(this).find('.setting-error-msg').text('비밀번호가 일치하지 않습니다.');
                return;
            }
            if (newPassword.length < 8) {
                $(this).find('.setting-error-msg').text('비밀번호는 8자 이상이어야 합니다.');
                return;
            }
            
            $.ajax({
                url: ctx + '/setting/alterPassword',
                type: 'POST',
                data: { newPassword: newPassword },
                dataType: 'json',
                success: function(response) {
                    if (response.success) {
                        alert('비밀번호가 성공적으로 변경되었습니다. 다시 로그인해주세요.');
                        $("#logoutForm").submit();
                    } else {
                        $(this).find('.setting-error-msg').text(response.message || '비밀번호 변경에 실패했습니다.');
                    }
                },
                error: function() {
                    alert('서버 오류로 비밀번호 변경에 실패했습니다.');
                }
            });
        });
        
        $('#modal-setting-container').on('click', '#btnDeleteAccount', function() {
            if (confirm('정말로 탈퇴하시겠습니까? 모든 정보는 복구되지 않습니다.')) {
                location.href = ctx + '/setting/deleteAccount.do';
            }
        });
     
       checkAndLoadIfNeeded();
    });
    
    $(window).on('load', function() {
        if (window.location.hash === "#msg") {
            openMessageModal();
        }
    });
    
	$('#modal-setting-container').on('click', '#btnKakaoUnlink', function() {
	    if (confirm('카카오 계정 연동을 해제하시겠습니까? \n카카오를 통한 로그인이 불가능해집니다.')) {
	        $.post(ctx + '/vibesync/auth/kakao/unlink', function(response) {
	            if(response.success){
	                alert('카카오 연동이 해제되었습니다.');
	                currentUserData.kakao_auth_id = null; // 전역 변수 업데이트
	                showCombinedSettingsView(); // 설정 화면 다시 그리기
	            } else {
	                alert('연동 해제에 실패했습니다: ' + response.message);
	            }
	        });
	    }
	});