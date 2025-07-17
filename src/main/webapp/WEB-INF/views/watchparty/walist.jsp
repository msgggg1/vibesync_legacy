<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 수정: HttpSession, UserVO import 및 스크립틀릿 제거 -->
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>User Page - Watch Party</title>
  <!-- 수정: contextPath EL로 변경 -->
  <script>
      const CONTEXT_PATH = '${pageContext.request.contextPath}';
      const NICKNAME     = '${sessionScope.userInfo != null ? sessionScope.userInfo.nickname : "익명"}';  <!-- 수정: 닉네임 EL로 변경 -->
  </script>
  <link rel="icon" href="${pageContext.request.contextPath}/sources/favicon.ico" />  <!-- 수정: favicon 경로 EL로 변경 -->
  <script src="https://www.youtube.com/iframe_api"></script>
  <!-- 수정: JS 경로 EL로 변경 -->
  <script defer src="${pageContext.request.contextPath}/watchparty.js"></script>
<style>
/* 기존 스타일 그대로 유지 */
@import url('https://fonts.googleapis.com/css2?family=Cal+Sans&family=National+Park:wght@200..800&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Kulim+Park:ital,wght@0,200;0,300;0,400;0,600;0,700;1,200;1,300;1,400;1,600;1,700&display=swap');
body { font-family: "National Park", sans-serif; background: #000; color: #fff; width: 100%; margin: 0; }
#content-wrapper {padding: 40px; max-width: 1200px; margin: 0 auto;}
.waList-header {display: flex; justify-content: space-between; align-items: center;}
.tab-buttons { display: flex; gap: 20px; }
.tab-buttons button {padding: 8px 16px;border: 2px solid #fff;cursor: pointer;border-radius: 9px;color: #fff;background: #000;}
.tab-buttons button.active { background-color: #5087ffe8; }
.tab-buttons button:hover { background-color: #82a5f1e8; }
#list-container{ height: 300px; }
#list-container, #host-container {border: 1px solid #ccc;padding: 1rem;max-height: 80vh;overflow-y: auto;}
ul { list-style: none; padding: 0; }
li { display: flex; justify-content: space-between; align-items: center;padding: 0.5rem; border-bottom: 1px solid #ddd; cursor: pointer; }
li:hover { background: linear-gradient(90deg, rgb(63 98 133) 0%, rgb(96 21 159 / 64%) 50%, rgb(52 12 65 / 56%) 100%); }
li img {width: 30px; height: 30px; object-fit: cover; border: 4px solid transparent; border-radius: 50%;background-image: linear-gradient(#000, #000), linear-gradient(90deg, rgba(138, 196, 255, 1) 0%, rgba(227, 176, 255, 1) 50%, rgba(165, 250, 120, 1) 100%);background-origin: border-box;background-clip: content-box, border-box;}
li div { display: flex; align-items: center; gap: 20px; }
#btn-add-video { position: absolute; top: 1rem; right: 1rem; font-size: 1.2rem; background: #28a745; color: white; border: none; border-radius: 4px;width: 32px;  height: 32px; line-height: 32px; text-align: center; cursor: pointer; display: none;}
/* [추가] 호스트 채팅 관련 스타일 */
#host-container thead { background: black; }
#chat-wrapper {width: 400px; margin: 0 auto;}
.host-chat-log { height: 66vh; width: 97%;  overflow-y: auto; border: 1px solid #eee;  margin-bottom: 5px; padding: 5px; font-size: 14px; word-break:break-all; }
.host-chat-log p { margin: 2px 0; }
.host-chat { font-weight: bold; color: #ed3030; }
.chat-input-wrapper input { width: calc(100% - 60px); }
.chat-input-wrapper button { width: 50px; }
.chat-input-submit { display: flex; }
#wp_delete { position: fixed; width: 40px; height: 40px; bottom: 20px; right: 20px; background: #ed3030; border-radius: 50%; border-color: #fff; }
#wp_delete a img {width: 80%; height: 80%; margin: 0 auto;}
</style>
</head>
<body>
  <section id="content-wrapper">
    <div class="waList-header">
      <h1>Watch Party</h1>
      <div class="tab-buttons">
          <button id="btn-list">전체 영상 목록</button>
          <button id="btn-host">내가 올린 영상</button>
      </div>
    </div>
    <div id="list-container"></div>
    <button id="btn-add-video">＋</button>
    <div id="host-container" style="display:none;"></div>
    <div id="add-modal" style="display:none; position:fixed; top:0; left:0; width:100%; height:100%; background:rgba(0,0,0,0.5); z-index:1000;">
        <!-- 모달 내용 동일 -->
    </div>
    <button id="wp_delete">
      <!-- 수정: 삭제 URL contextPath EL로 변경 -->
      <a href="${pageContext.request.contextPath}/watchparty/watchpartyDelete.do?hostIdx=${sessionScope.userInfo.ac_idx}"
         onclick="return confirm('내가 올린 모든 파티를 삭제하시겠습니까?');">
        <img src="${pageContext.request.contextPath}/sources/icons/off.svg" alt="off">
      </a>
    </button>
  </section>
</body>
</html>
