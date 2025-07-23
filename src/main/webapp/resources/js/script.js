	// =========== 함수 ==============

	// 로그인 여부 확인
	function requireLogin() {
	   if (confirm('로그인이 필요한 기능입니다. 로그인 페이지로 이동하시겠습니까?')) {
	       // 1. 현재 페이지의 전체 주소 (로그인 후 돌아올 주소)
	       const currentUrl = window.location.href;
	       // 2. 로그인 페이지 주소에 'returnUrl' 파라미터를 추가하여 보냄
	       // encodeURIComponent: URL에 포함될 수 있는 특수문자(?, &, =)를 안전하게 인코딩
	       window.location.href = ctx + '/member/login';
	       // ?returnUrl=' + encodeURIComponent(currentUrl)
	   }
	}
	
	// follow
	function followToggle(targetUserAcIdx) {
	    $.ajax({
	    	url: ctx + '/api/follow/followToggle',
	        type: 'POST',
			data: {
			  targetUserAcIdx: targetUserAcIdx
			},
			dataType: 'json',
	        success: function(isFollowing) {
	        	$('#followBtn').text(isFollowing ? 'Unfollow' : 'Follow');
	   	 		updateFollowingCount();
	        },
	        error: function(xhr, status, error) {
	       	  	console.error('[AJAX-FOLLOW] 에러 발생:', error);
	       		// 팔로우/언팔로우 실패 알림창/모달창 띄우기?.. 처리 어떻게 할지 미정
	       	 	const currentStatus = $('#followBtn').text();
	            const actionText = (currentStatus === 'Unfollow') ? '팔로우 취소' : '팔로우';
	            alert(actionText + '에 실패했습니다.');
	        }
	    });
	}

document.addEventListener('DOMContentLoaded', () => {

  const back_btn = document.querySelector('.back_icon');
  if (back_btn != null) {
    back_btn.addEventListener('click', () => {
      window.history.back();
    })
  }

  const sidebar = document.getElementById('sidebar');
  if (sidebar != null) {
    const btn = document.getElementById('toggle-btn');
    sidebar.classList.add('collapsed');
    btn.textContent = '✖';

    function checkWidth() {
      if (window.innerWidth <= 1000) {
        btn.style.display = 'block';
        btn.style.position = 'fixed';
        // collapsed 상태이면 content margin 0
        if (sidebar.classList.contains('collapsed')) {
          btn.textContent = '☰';
        }
      } else {
        btn.style.display = 'none';
        sidebar.classList.remove('collapsed');
        btn.textContent = '✖';
      }
    }

    btn.addEventListener('click', () => {
      sidebar.classList.toggle('collapsed');
      // toggle에 따라 content margin 동기화
      const content = document.getElementById('content');
      if (sidebar.classList.contains('collapsed')) {
        btn.textContent = '☰';
      } else {
        btn.textContent = '✖';
      }
    });

    window.addEventListener('resize', checkWidth);
    checkWidth();
  }
  
});