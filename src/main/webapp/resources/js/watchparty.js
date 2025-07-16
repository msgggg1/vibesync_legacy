// 파일: /WebContent/js/watchparty.js

// 전역 변수: Host 전용 Sync WebSocket (호스트가 재생/일시정지 시 이 WS로 메시지 전송)
let wsSyncHost = null;
// 여러 개의 YT.Player 인스턴스를 관리하기 위해 Map을 사용
const hostPlayers = new Map(); // key: watchPartyIdx, value: YT.Player 인스턴스
let hostSyncIntervalIds = new Map(); // key: watchPartyIdx, value: intervalId
// [신규] 호스트 탭의 각 영상별 채팅 WebSocket을 관리하는 Map
const commentSockets = new Map();

// 페이지 로드 시 실행
window.addEventListener('DOMContentLoaded', () => {
    console.log("Loading watchparty.js");
    
    connectSyncWebSocketForHost();
    
    document.getElementById('btn-list').classList.add('active');
    loadWatchPartyList();

    document.getElementById('btn-list').addEventListener('click', () => {
        switchTab('list');
    });
    document.getElementById('btn-host').addEventListener('click', () => {
        switchTab('host');
    });

    // 추가 시작: "+ 버튼"과 "모달" 관련 요소 가져오기
    const btnAdd = document.getElementById('btn-add-video');
    const addModal = document.getElementById('add-modal');
    const addCancel = document.getElementById('add-cancel');
    // 추가 끝

    document.getElementById('host-container').addEventListener('click', function(e) {
        if (e.target.classList.contains('host-chat-send-btn')) {
            const watchPartyIdx = e.target.dataset.wpIdx;
            const input = document.getElementById(`chat-input-${watchPartyIdx}`);
            const text = input.value.trim();

            if (text === "") return;

            const ws = commentSockets.get(parseInt(watchPartyIdx));
            if (ws && ws.readyState === WebSocket.OPEN) {
                // [수정] 해당 영상의 플레이어 객체에서 현재 재생 시간을 가져옴
                const player = hostPlayers.get(parseInt(watchPartyIdx));
                const currentTime = player ? player.getCurrentTime() : 0.0;

                const chatMsg = JSON.stringify({
                    type: "comment",
                    watchPartyIdx: watchPartyIdx,
                    nickname: "host", 
                    chatting: text,
                    timeline: currentTime
                });
                ws.send(chatMsg);
                input.value = "";
            } else {
                console.error(`WebSocket for watchPartyIdx=${watchPartyIdx} is not open.`);
            }
        }
    });
    
    document.getElementById('host-container').addEventListener('keydown', function(e) {
    if (e.target.id.startsWith('chat-input-') && e.key === 'Enter') {
        e.preventDefault();
        // 입력창 id에서 watchPartyIdx를 추출
        const watchPartyIdx = e.target.id.split('-')[2];
        const sendButton = document.querySelector(`.host-chat-send-btn[data-wp-idx='${watchPartyIdx}']`);
        if (sendButton) {
            sendButton.click();
        }
    }
});


});

// 탭 전환 함수
function switchTab(tab) {
    const listBtn = document.getElementById('btn-list');
    const hostBtn = document.getElementById('btn-host');
    const listContainer = document.getElementById('list-container');
    const hostContainer = document.getElementById('host-container');
    const btnAdd = document.getElementById('btn-add-video');

    if (tab === 'list') {
        listBtn.classList.add('active');
        hostBtn.classList.remove('active');
        listContainer.style.display = 'block';
        hostContainer.style.display = 'none';
        btnAdd.style.display = 'none';
        loadWatchPartyList();
    } else {
        hostBtn.classList.add('active');
        listBtn.classList.remove('active');
        listContainer.style.display = 'none';
        hostContainer.style.display = 'block';
        btnAdd.style.display = 'block';
        loadHostWatchPartyList();
    }
}

// 전체 WatchParty 목록을 AJAX로 가져와서 #list-container에 표시
function loadWatchPartyList() {
    fetch(`${CONTEXT_PATH}/WatchPartyListServlet`)
        .then(response => {
            if (!response.ok) throw new Error('서버 응답 실패: ' + response.status);
            return response.json();
        })
        .then(data => {
            renderList(data, 'list-container');
        })
        .catch(err => {
            console.error('loadWatchPartyList 에러:', err);
            document.getElementById('list-container').innerHTML = '<p>목록을 불러오지 못했습니다.</p>';
        });
}

// 로그인한 유저의 Host 목록을 AJAX로 가져와서 #host-container에 표시
function loadHostWatchPartyList() {
    fetch(`${CONTEXT_PATH}/HostWatchPartyListServlet`)
        .then(response => {
            if (!response.ok) throw new Error('서버 응답 실패: ' + response.status);
            return response.json();
        })
        .then(data => {
            renderHostTable(data);
        })
        .catch(err => {
            console.error('loadHostWatchPartyList 에러:', err);
            document.getElementById('host-container').innerHTML = '<p>목록을 불러오지 못했습니다.</p>';
        });
}

// 받은 JSON 데이터를 li 태그로 렌더링
function renderList(data, containerId) {
    const container = document.getElementById(containerId);
    container.innerHTML = '';

    if (!data || data.length === 0) {
        container.innerHTML = '<p>표시할 영상이 없습니다.</p>';
        return;
    }

    const ul = document.createElement('ul');
    data.forEach(item => {
		const hostThumb = item.hostImg == null ? "sources/default/default_user.jpg" : item.hostImg;
        const li = document.createElement('li');
        console.log(item);
        li.innerHTML = `
          ${item.title}
          <div>
            <img src="${hostThumb}" alt="profile" />
            <span>${item.hostNickname}</span>
          </div>
        `;
        li.dataset.watchPartyIdx = item.watchParty_idx || item.watchPartyIdx;
        li.addEventListener('click', () => {
            window.location.href = `${CONTEXT_PATH}/vibesync/watch.jsp?watchPartyIdx=${item.watchParty_idx || item.watchPartyIdx}`;
        });
        ul.appendChild(li);
    });
    container.appendChild(ul);
}

// [수정] 호스트 테이블 렌더링 함수에 채팅 컬럼 추가
function renderHostTable(data) {
    const container = document.getElementById('host-container');
    container.innerHTML = '';

    if (!data || data.length === 0) {
        container.innerHTML = '<p>표시할 영상이 없습니다.</p>';
        return;
    }

    const table = document.createElement('table');
    table.style.width = '100%';
    table.style.borderCollapse = 'collapse';

    const thead = document.createElement('thead');
    const headerRow = document.createElement('tr');
    
    const thId = document.createElement('th');
    thId.textContent = 'ID';
    const thTitle = document.createElement('th');
    thTitle.textContent = '제목';
    const thVideo = document.createElement('th');
    thVideo.textContent = '영상';
    // [신규] 채팅 관리 헤더 추가
    const thChat = document.createElement('th');
    thChat.textContent = '채팅 관리';

    [thId, thTitle, thVideo, thChat].forEach(th => { // thChat 추가
        th.style.border = '1px solid #ccc';
        th.style.padding = '0.5rem';
        headerRow.appendChild(th);
    });
    thead.appendChild(headerRow);
    table.appendChild(thead);

    const tbody = document.createElement('tbody');
    data.forEach(item => {
        const tr = document.createElement('tr');
        const watchPartyIdx = item.watchParty_idx || item.watchPartyIdx;

        const tdId = document.createElement('td');
        tdId.textContent = watchPartyIdx;
        const tdTitle = document.createElement('td');
        tdTitle.textContent = item.title;
        const tdVideo = document.createElement('td');
        tdVideo.style.textAlign = 'center';

        const iframe = document.createElement('iframe');
        iframe.id = `host-player-${watchPartyIdx}`;
        iframe.width = '500';
        iframe.height = '281';
        iframe.src = `https://www.youtube.com/embed/${item.video_id || item.videoId}?enablejsapi=1`;
        iframe.frameBorder = '0';
        iframe.allow = 'accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture';
        tdVideo.appendChild(iframe);

        // [신규] 채팅 관리 셀 생성
        const tdChat = document.createElement('td');
        tdChat.style.verticalAlign = 'top';

        const chatwrapper = document.createElement("div");
        chatwrapper.id = "chat-wrapper"

        const chatLog = document.createElement('div');
        chatLog.id = `chat-log-${watchPartyIdx}`;
        chatLog.className = 'host-chat-log';

        const chatInputWrapper = document.createElement('div');
        chatInputWrapper.className = 'chat-input-wrapper';
        chatInputWrapper.innerHTML = `
        <div class="chat-input-submit">
            <input type="text" id="chat-input-${watchPartyIdx}" placeholder="메시지 전송...">
            <button class="host-chat-send-btn" data-wp-idx="${watchPartyIdx}">전송</button>
        </div>
        `;
        chatwrapper.appendChild(chatLog);
        chatwrapper.appendChild(chatInputWrapper);
        tdChat.appendChild(chatwrapper);

        // 각 셀에 스타일 적용 및 행에 추가
        [tdId, tdTitle, tdVideo].forEach(td => {
            td.style.border = '1px solid #ccc';
            td.style.padding = '0.5rem';
            tr.appendChild(td);
        });
        tdChat.style.border = '1px solid #ccc';
        tr.appendChild(tdChat);
        tbody.appendChild(tr);

        // YT 플레이어 인스턴스 생성
        setTimeout(() => {
            const player = new YT.Player(iframe.id, {
                events: {
                    onStateChange: (event) => handleHostPlayerStateChange(event, watchPartyIdx)
                }
            });
            hostPlayers.set(watchPartyIdx, player);
        }, 500);

        // [신규] 각 영상별 채팅 WebSocket 연결
        connectCommentWebSocket(watchPartyIdx);
    });

    table.appendChild(tbody);
    container.appendChild(table);
}

// [신규] 특정 Watch Party의 채팅 WebSocket 연결 함수
function connectCommentWebSocket(watchPartyIdx) {
    const ws = new WebSocket("ws://" + location.host + CONTEXT_PATH + "/waCommentEndpoint");
    commentSockets.set(watchPartyIdx, ws);

    ws.onopen = () => {
        console.log(`Comment WS for ${watchPartyIdx} connected.`);
        // 연결 성공 시, 초기 채팅 목록 요청
        const initMsg = JSON.stringify({ type: "initComment", watchPartyIdx: watchPartyIdx });
        ws.send(initMsg);
    };

    ws.onmessage = (event) => {
        const msg = JSON.parse(event.data);
        const chatLog = document.getElementById(`chat-log-${watchPartyIdx}`);
        if (!chatLog) return;

        if (msg.type === "comment") {
            appendHostChat(chatLog, msg.nickname, msg.chatting);
        } else if (msg.type === "initCommentList") {
            msg.comments.forEach(c => {
                appendHostChat(chatLog, c.nickname, c.chatting);
            });
        }
    };

    ws.onclose = () => console.log(`Comment WS for ${watchPartyIdx} disconnected.`);
    ws.onerror = (err) => console.error(`Comment WS for ${watchPartyIdx} error:`, err);
}


function appendHostChat(chatLogElement, nickname, text) {
    const p = document.createElement('p');
    p.textContent = `${nickname}: ${text}`;
    // 호스트가 보낸 메시지인 경우 'host-chat' 클래스 추가
    if (nickname === "host") {
        p.classList.add('host-chat');
    }
    chatLogElement.appendChild(p);
    chatLogElement.scrollTop = chatLogElement.scrollHeight; // 항상 최신 메시지가 보이도록 스크롤
}

function handleHostPlayerStateChange(event, watchPartyIdx) {
  const player = hostPlayers.get(watchPartyIdx);
  if (!player) return;

  const currentTime = player.getCurrentTime();
  // YT.PlayerState.PLAYING = 1, PAUSED = 2, ENDED = 0
  if (event.data === YT.PlayerState.PLAYING) {
    // 1) play=true로 Sync 메시지 전송
    const syncMsg = JSON.stringify({
      type: "sync",
      watchPartyIdx: watchPartyIdx,
      timeline: currentTime,
      play: "PLAY"
    });
    wsSyncHost.send(syncMsg);

    // 2) 2초마다 timeline 업데이트
    if (!hostSyncIntervalIds.has(watchPartyIdx)) {
      const intervalId = setInterval(() => {
        const nowTime = player.getCurrentTime();
        const periodicMsg = JSON.stringify({
          type: "sync",
          watchPartyIdx: watchPartyIdx,
          timeline: nowTime,
          play: "PLAY"
        });
        wsSyncHost.send(periodicMsg);
      }, 2000);
      hostSyncIntervalIds.set(watchPartyIdx, intervalId);
    }
  }
  else if (event.data === YT.PlayerState.PAUSED || event.data === YT.PlayerState.ENDED) {
    // 1) play=false 로 Sync 메시지 전송 (timeline은 현재 시간)
    const syncMsg = JSON.stringify({
      type: "sync",
      watchPartyIdx: watchPartyIdx,
      timeline: currentTime,
      play: "PAUSE"
    });
    wsSyncHost.send(syncMsg);

    // 2) 주기적 업데이트 중단
    if (hostSyncIntervalIds.has(watchPartyIdx)) {
      clearInterval(hostSyncIntervalIds.get(watchPartyIdx));
      hostSyncIntervalIds.delete(watchPartyIdx);
    }
  }
}

// 쿠키에서 원하는 이름의 값을 가져오는 헬퍼 함수
function getCookie(name) {
  const cookies = document.cookie.split(';').map(c => c.trim());
  for (let c of cookies) {
    if (c.startsWith(name + '=')) {
      return c.substring((name + '=').length);
    }
  }
  return null;
}

// "+ 버튼" 클릭 → 모달 열기
document.getElementById('btn-add-video').addEventListener('click', () => {
  document.getElementById('add-modal').style.display = 'block';
});

// 모달 바깥 영역 클릭 → 모달 닫기
document.getElementById('add-modal').addEventListener('click', (e) => {
  if (e.target === document.getElementById('add-modal')) {
    document.getElementById('add-modal').style.display = 'none';
  }
});

// "취소" 버튼 클릭 → 모달 닫기
document.getElementById('add-cancel').addEventListener('click', () => {
  document.getElementById('add-modal').style.display = 'none';
});

// 폼 제출 시 WatchParty 추가
document.getElementById('add-form').addEventListener('submit', function(e) {
  e.preventDefault();

  // 1) 제목, URL 입력값 가져오기
  const titleInput = document.getElementById('wp-title').value.trim();
  const urlInput   = document.getElementById('wp-url').value.trim();

  // 2) URL에서 video_id 추출
  const regex = /(?:youtu\.be\/|youtube\.com\/(?:watch\?(?:.*&)?v=|embed\/|v\/|live\/))([\w-]{11})/;
  const match = urlInput.match(regex);
  if (!match) {
    alert('유효한 YouTube URL을 입력해주세요.');
    return;
  }
  const videoId = match[1];

  // 3) 쿠키에서 로그인된 유저 idx 가져오기
  const hostIdx = getCookie('login_user_idx');
  if (!hostIdx) {
    alert('로그인 정보가 없습니다. 다시 로그인하세요.');
    return;
  }

  // 4) AJAX 요청으로 서버(AddWatchPartyServlet)에 전송
  fetch(`${CONTEXT_PATH}/AddWatchPartyServlet`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json; charset=UTF-8' },
    body: JSON.stringify({
      title: titleInput,
      video_id: videoId,
      host: hostIdx
    })
  })
  .then(response => {
    if (!response.ok) throw new Error('추가에 실패했습니다: ' + response.status);
    return response.json();
  })
  .then(result => {
    if (result.success) {
      // 성공 시 → 모달 닫기, 입력 초기화, 호스트 목록 재조회
      document.getElementById('add-modal').style.display = 'none';
      document.getElementById('wp-title').value = '';
      document.getElementById('wp-url').value = '';
      if (document.getElementById('btn-host').classList.contains('active')) {
        loadHostWatchPartyList();
      }
    } else {
      alert('영상 추가에 실패했습니다: ' + result.error);
    }
  })
  .catch(err => {
    console.error('AddWatchParty 에러:', err);
    alert('서버 통신 중 오류가 발생했습니다.');
  });
});

function connectSyncWebSocketForHost() {
  wsSyncHost = new WebSocket("ws://" + location.host + CONTEXT_PATH + "/waSyncEndpoint");
  wsSyncHost.onopen = () => {
    console.log("Host Sync WebSocket 연결됨");
  };
  wsSyncHost.onmessage = (event) => {
    // Host 쪽에서는 다른 클라이언트의 sync 메시지를 받아서 동작할 필요가 없으므로 무시하거나 로그만 찍어 둡니다.
    console.log("[Host WS 수신]", event.data);
  };
  wsSyncHost.onclose = () => {
    console.log("Host Sync WebSocket 연결 종료");
  };
  wsSyncHost.onerror = (err) => {
    console.error("Host Sync WebSocket 에러", err);
  };
}