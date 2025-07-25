--------------------------------------------------------------------------------
-- 테이블 삭제 (제약조건 순서에 유의)
--------------------------------------------------------------------------------
DROP TABLE schedule;
DROP TABLE workspace_blocks;
DROP TABLE commentlist;
DROP TABLE noteAccess;
DROP TABLE likes;
DROP TABLE bookmark;
DROP TABLE notification;
DROP TABLE note;
DROP TABLE follows;
DROP TABLE todolist;
DROP TABLE message;
DROP TABLE setting;
DROP TABLE userPage;
DROP TABLE contents;
DROP TABLE genre;
DROP TABLE wa_sync;
DROP TABLE wa_comment;
DROP TABLE watchParty;
DROP TABLE userAccount;
DROP TABLE category;
DROP TABLE passwordResetTokens;


--------------------------------------------------------------------------------
-- 1. category (카테고리)
--------------------------------------------------------------------------------
CREATE TABLE category (
    category_idx NUMBER PRIMARY KEY,
    category_name VARCHAR2(100) NOT NULL,
    img varchar(255) NOT NULL
);

-------------------------------------------------------------------------------
-- 1-1. 사용자 생성 카테고리
-------------------------------------------------------------------------------
CREATE TABLE userCategory (
    user_category_idx NUMBER PRIMARY KEY,
    parent_category_idx NUMBER NOT NULL,
    category_name VARCHAR2(100) NOT NULL,
    owner_ac_idx NUMBER NOT NULL,
    img varchar(255)
);
CREATE SEQUENCE userCategory_seq START WITH 1 INCREMENT BY 1;

--------------------------------------------------------------------------------
-- 2. userAccount (계정)
--------------------------------------------------------------------------------
CREATE TABLE userAccount (
    ac_idx NUMBER PRIMARY KEY,
    email VARCHAR2(255) NOT NULL UNIQUE,
    pw VARCHAR2(255) NOT NULL,
    nickname VARCHAR2(50) NOT NULL,
    img VARCHAR2(255),
    name VARCHAR2(100) NOT NULL,
    role VARCHAR2(50) DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT SYSDATE,
    category_idx NUMBER NOT NULL,
    kakao_auth_id NUMBER NULL UNIQUE,
    google_id NUMBER NULL UNIQUE,
    CONSTRAINT fk_userAccount_TO_category FOREIGN KEY (category_idx) REFERENCES category(category_idx) ON DELETE CASCADE
);
CREATE SEQUENCE userAccount_seq START WITH 1 INCREMENT BY 1;

--------------------------------------------------------------------------------
-- 5. userPage (페이지)
--------------------------------------------------------------------------------
CREATE TABLE userPage (
    userPg_idx NUMBER PRIMARY KEY,
    subject VARCHAR2(100),
    thumbnail VARCHAR2(255),
    created_at TIMESTAMP DEFAULT SYSDATE,
    ac_idx NUMBER NOT NULL,
    re_userPg_idx NUMBER,
    CONSTRAINT FK_userPage_TO_userAccount FOREIGN KEY (ac_idx) REFERENCES userAccount(ac_idx) ON DELETE CASCADE,
    CONSTRAINT FK_userPage_TO_userPage FOREIGN KEY (re_userPg_idx) REFERENCES userPage(userPg_idx) ON DELETE CASCADE
);

--------------------------------------------------------------------------------
-- 6. setting (설정)
--------------------------------------------------------------------------------
CREATE TABLE setting (
    setting_idx NUMBER PRIMARY KEY,
    font VARCHAR2(100) NOT NULL,
    theme VARCHAR2(50) NOT NULL,
    noti VARCHAR2(50) NOT NULL,
    ac_idx NUMBER NOT NULL,
    CONSTRAINT FK_setting_TO_userAccount FOREIGN KEY (ac_idx) REFERENCES userAccount(ac_idx) ON DELETE CASCADE
);
CREATE SEQUENCE setting_seq START WITH 1 INCREMENT BY 1;

--------------------------------------------------------------------------------
-- 8. message (메시지)
--------------------------------------------------------------------------------
CREATE TABLE message (
    msg_idx NUMBER PRIMARY KEY,
    text CLOB not null,
    time TIMESTAMP NOT NULL,
    img VARCHAR2(255),
    chk NUMBER(1) NOT NULL,
    ac_receiver NUMBER NOT NULL,
    ac_sender NUMBER NOT NULL,
    CONSTRAINT FK_message_TO_userAccount_rcvr FOREIGN KEY (ac_receiver) REFERENCES userAccount(ac_idx) ON DELETE CASCADE,
    CONSTRAINT FK_message_TO_userAccount_sndr FOREIGN KEY (ac_sender) REFERENCES userAccount(ac_idx) ON DELETE CASCADE
);
CREATE SEQUENCE message_seq START WITH 1 INCREMENT BY 1;

--------------------------------------------------------------------------------
-- 9. todolist (투두리스트)
--------------------------------------------------------------------------------
CREATE TABLE todolist (
    todo_idx NUMBER PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    text CLOB NOT NULL,
    todo_group VARCHAR2(100),
    color VARCHAR2(100) NOT NULL,
    ac_idx NUMBER NOT NULL,
    status NUMBER(1) DEFAULT 0 NOT NULL,
    CONSTRAINT FK_todolist_TO_userAccount FOREIGN KEY (ac_idx) REFERENCES userAccount(ac_idx) ON DELETE CASCADE
);
CREATE SEQUENCE todolist_seq START WITH 1 INCREMENT BY 1;

--------------------------------------------------------------------------------
-- 10. follows (팔로우목록)
--------------------------------------------------------------------------------
CREATE TABLE follows (
    follows_idx NUMBER PRIMARY KEY,
    ac_follow NUMBER NOT NULL,
    ac_following NUMBER NOT NULL,
    CONSTRAINT FK_follows_TO_userAccountFw FOREIGN KEY (ac_follow) REFERENCES userAccount(ac_idx) ON DELETE CASCADE,
    CONSTRAINT FK_follows_TO_userAccountFwing FOREIGN KEY (ac_following) REFERENCES userAccount(ac_idx) ON DELETE CASCADE
);
CREATE SEQUENCE follows_seq START WITH 1 INCREMENT BY 1;

--------------------------------------------------------------------------------
-- 11. note (글)
--------------------------------------------------------------------------------
CREATE TABLE note (
    note_idx NUMBER PRIMARY KEY,
    parent_note_idx NUMBER,
    ac_idx NUMBER NOT NULL,
    title VARCHAR2(2000) NOT NULL,
    text CLOB NOT NULL,
    display_order NUMBER NOT NULL,
    share_status VARCHAR2(20) DEFAULT 'PRIVATE',
    create_at TIMESTAMP DEFAULT SYSDATE,
    edit_at TIMESTAMP DEFAULT SYSDATE,
    view_count NUMBER DEFAULT 0,
    titleimg clob,
    category_idx NUMBER NOT NULL,
    CONSTRAINT FK_note_TO_category FOREIGN KEY (category_idx) REFERENCES category(category_idx) ON DELETE CASCADE,
);
CREATE SEQUENCE note_seq START WITH 1 INCREMENT BY 1;

--------------------------------------------------------------------------------
-- 11-1. note_share (공유 권한)
--------------------------------------------------------------------------------
CREATE TABLE NOTE_SHARE (
    share_idx NUMBER PRIMARY KEY,
    note_idx NUMBER NOT NULL,
    ac_idx NUMBER NOT NULL,
    permission VARCHAR2(10) NOT NULL,
    CONSTRAINT FK_share_TO_note FOREIGN KEY (note_idx) REFERENCES NOTE(note_idx),
    CONSTRAINT FK_share_TO_userAccount FOREIGN KEY (ac_idx) REFERENCES USER_ACCOUNT(ac_idx)
);
CREATE SEQUENCE note_share_seq START WITH 1 INCREMENT BY 1;

--------------------------------------------------------------------------------
-- 12. notification (알람)
--------------------------------------------------------------------------------
CREATE TABLE notification (
    notifi_idx NUMBER PRIMARY KEY,
    time TIMESTAMP NOT NULL,
    text CLOB NOT NULL,
    chk NUMBER(1) NOT NULL,
    ac_idx NUMBER NOT NULL,
    setting_idx INT,
    CONSTRAINT FK_notification_TO_userAccount FOREIGN KEY (ac_idx) REFERENCES userAccount(ac_idx) ON DELETE CASCADE,
    CONSTRAINT FK_notification_TO_setting FOREIGN KEY (setting_idx) REFERENCES setting(setting_idx) ON DELETE CASCADE
);
CREATE SEQUENCE notification_seq START WITH 1 INCREMENT BY 1;

--------------------------------------------------------------------------------
-- 13. likes (좋아요)
--------------------------------------------------------------------------------
CREATE TABLE likes (
    likes_idx NUMBER PRIMARY KEY,
    created_at TIMESTAMP default sysdate,
    note_idx NUMBER NOT NULL,
    ac_idx NUMBER NOT NULL,
    CONSTRAINT FK_likes_TO_note FOREIGN KEY (note_idx) REFERENCES note(note_idx) ON DELETE CASCADE,
    CONSTRAINT FK_likes_TO_userAccount FOREIGN KEY (ac_idx) REFERENCES userAccount(ac_idx) ON DELETE CASCADE
);
CREATE SEQUENCE likes_seq START WITH 1 INCREMENT BY 1;

--------------------------------------------------------------------------------
-- 14. commentlist (댓글)
--------------------------------------------------------------------------------
CREATE TABLE commentlist (
    commentlist_idx NUMBER PRIMARY KEY,
    text CLOB NOT NULL,
    like_count INT,
    create_at TIMESTAMP DEFAULT SYSDATE,
    re_commentlist_idx INT,
    note_idx NUMBER NOT NULL,
    ac_idx NUMBER NOT NULL,
    depth NUMBER DEFAULT 1 NOT NULL, -- [추가] 댓글 깊이 컬럼
    CONSTRAINT FK_comment_TO_comment FOREIGN KEY (re_commentlist_idx) REFERENCES commentlist(commentlist_idx) ON DELETE CASCADE,
    CONSTRAINT FK_comment_TO_note FOREIGN KEY (note_idx) REFERENCES note(note_idx) ON DELETE CASCADE,
    CONSTRAINT FK_comment_TO_userAccount FOREIGN KEY (ac_idx) REFERENCES userAccount(ac_idx) ON DELETE CASCADE
);
CREATE SEQUENCE commentlist_seq START WITH 1 INCREMENT BY 1;

--------------------------------------------------------------------------------
-- 15. watchParty (워치파티)
--------------------------------------------------------------------------------
CREATE TABLE watchParty (
    watchParty_idx NUMBER PRIMARY KEY,
    title varchar2(1000),
    video_id VARCHAR2(255) NOT NULL,
    created_at TIMESTAMP DEFAULT SYSDATE,
    host NUMBER NOT NULL,
    CONSTRAINT FK_wu FOREIGN KEY (host) REFERENCES userAccount(ac_idx) ON DELETE CASCADE
);
CREATE SEQUENCE seq_watchparty START WITH 1 INCREMENT BY 1;

CREATE TABLE wa_sync (
    sync_idx NUMBER PRIMARY KEY,
    timeline number(10, 3),
    play VARCHAR2(10),
    watchParty_idx NUMBER NOT NULL,
    CONSTRAINT FK_sw FOREIGN KEY (watchParty_idx) REFERENCES watchParty(watchParty_idx) ON DELETE CASCADE
);
CREATE SEQUENCE seq_wa_sync START WITH 1 INCREMENT BY 1;

CREATE TABLE wa_comment (
    wac_idx NUMBER PRIMARY KEY,
    nickname varchar(100),
    chatting CLOB,
    timeline number(10, 3),
    create_at TIMESTAMP default sysdate,
    watchParty_idx NUMBER NOT NULL,
    CONSTRAINT FK_cw FOREIGN KEY (watchParty_idx) REFERENCES watchParty(watchParty_idx) ON DELETE CASCADE
);
CREATE SEQUENCE seq_wa_comment START WITH 1 INCREMENT BY 1;

--------------------------------------------------------------------------------
-- 16. schedule (일정 관리) 테이블
--------------------------------------------------------------------------------
CREATE TABLE schedule (
    schedule_idx    NUMBER          PRIMARY KEY,
    title           VARCHAR2(255)   NOT NULL,         -- 일정 제목
    description     CLOB,                             -- 상세 내용
    start_time      TIMESTAMP       NOT NULL,         -- 시작 시간
    end_time        TIMESTAMP       NOT NULL,         -- 종료 시간
    color           VARCHAR2(100),                    -- 캘린더에 표시될 색상 (선택 사항)
    ac_idx          NUMBER          NOT NULL,         -- 일정을 등록한 사용자 ID
    CONSTRAINT FK_schedule_TO_userAccount FOREIGN KEY (ac_idx) REFERENCES userAccount(ac_idx) ON DELETE CASCADE
);
CREATE SEQUENCE schedule_seq START WITH 1 INCREMENT BY 1;

--------------------------------------------------------------------------------
-- 17. workspace_blocks (워크스페이스 추가블록)
--------------------------------------------------------------------------------
CREATE TABLE workspace_blocks (
    block_id      NUMBER(10)      CONSTRAINT pk_workspace_blocks PRIMARY KEY,
    ac_idx        NUMBER(10)      NOT NULL,
    block_type    VARCHAR2(50)    NOT NULL,
    block_order   NUMBER(3)       NOT NULL,
    config        VARCHAR2(4000)
);
CREATE SEQUENCE workspace_blocks_seq START WITH 1 INCREMENT BY 1;

-- 비밀번호 찾기 
CREATE TABLE passwordResetTokens (
    token VARCHAR(255) PRIMARY KEY, -- 고유한 토큰 값
    userEmail VARCHAR(255) NOT NULL, -- 요청한 사용자의 이메일
    expiryDate TIMESTAMP NOT NULL -- 토큰 만료 시간
);

SELECT COUNT(*)
FROM USER_TABLES;
