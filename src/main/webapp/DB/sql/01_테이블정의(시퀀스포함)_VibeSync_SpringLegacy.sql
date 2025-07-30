-- VIBESYNC_SPRINGLEGACY

--------------------------------------------------------------------------------
-- 테이블 삭제 (제약조건 순서에 유의)
--------------------------------------------------------------------------------
-- 자식 테이블
DROP TABLE notification_settings;
DROP TABLE schedule;
DROP TABLE workspace_blocks;
DROP TABLE notification;
DROP TABLE commentlist;
DROP TABLE note_share;
DROP TABLE likes;
DROP TABLE note;
DROP TABLE follows;
DROP TABLE todolist;
DROP TABLE message;
DROP TABLE setting;
DROP TABLE wa_sync;
DROP TABLE wa_comment;
DROP TABLE watchParty;
DROP TABLE custom_category;

-- 부모 테이블
DROP TABLE userAccount;
DROP TABLE category;

-- 다른 테이블과 관계 없는 테이블
DROP TABLE passwordResetTokens;


--------------------------------------------------------------------------------
-- 시퀀스 삭제
--------------------------------------------------------------------------------
DROP SEQUENCE userAccount_seq;
DROP SEQUENCE custom_category_seq;
DROP SEQUENCE setting_seq;
DROP SEQUENCE message_seq;
DROP SEQUENCE todolist_seq;
DROP SEQUENCE follows_seq;
DROP SEQUENCE note_seq;
DROP SEQUENCE note_share_seq;
DROP SEQUENCE notification_seq;
DROP SEQUENCE likes_seq;
DROP SEQUENCE commentlist_seq;
DROP SEQUENCE seq_watchparty;
DROP SEQUENCE seq_wa_sync;
DROP SEQUENCE seq_wa_comment;
DROP SEQUENCE schedule_seq;
DROP SEQUENCE workspace_blocks_seq;


--------------------------------------------------------------------------------
-- 테이블 생성 (독립 테이블 → 1차 종속 테이블 → 2차 종속 테이블 → 3차 종속 테이블)
--------------------------------------------------------------------------------


-- ▶ 독립 테이블 : 다른 테이블 참조 X
--------------------------------------------------------------------------------
-- 1. category (카테고리)
--------------------------------------------------------------------------------
CREATE TABLE category (
    category_idx NUMBER PRIMARY KEY,
    category_name VARCHAR2(100) NOT NULL,
    img varchar(255) NOT NULL
);

--------------------------------------------------------------------------------
-- 2. passwordResetTokens (비밀번호 찾기)
--------------------------------------------------------------------------------
CREATE TABLE passwordResetTokens (
    token VARCHAR(255) PRIMARY KEY, -- 고유한 토큰 값
    userEmail VARCHAR(255) NOT NULL, -- 요청한 사용자의 이메일
    expiryDate TIMESTAMP NOT NULL -- 토큰 만료 시간
);


-- ▶ 1차 종속 테이블 : 독립 테이블을 참조
--------------------------------------------------------------------------------
-- 3. userAccount (계정) : category 참조
--------------------------------------------------------------------------------
CREATE TABLE userAccount (
    -- PK
    ac_idx NUMBER PRIMARY KEY,

    -- 주요 식별 정보
    email VARCHAR2(255) NOT NULL UNIQUE,
    pw VARCHAR2(255) NOT NULL,
    nickname VARCHAR2(50) NOT NULL UNIQUE,
    name VARCHAR2(100) NOT NULL,
    role VARCHAR2(50) DEFAULT 'ROLE_USER',

    -- FK
    category_idx NUMBER NOT NULL,
    
    -- 기타 속성
    img VARCHAR2(255),
    kakao_auth_id NUMBER NULL UNIQUE,
    google_id NUMBER NULL UNIQUE,

    -- 카운트 및 날짜 (부가 정보)
    following_count NUMBER DEFAULT 0,
    follower_count NUMBER DEFAULT 0,
    created_at TIMESTAMP DEFAULT SYSDATE,
    
    CONSTRAINT fk_userAccount_TO_category FOREIGN KEY (category_idx) REFERENCES category(category_idx) ON DELETE CASCADE
);
CREATE SEQUENCE userAccount_seq START WITH 1 INCREMENT BY 1;


-- ▶ 2차 종속 테이블 : 다른 테이블들을 참조하는 대부분의 테이블들
--------------------------------------------------------------------------------
-- 4. custom_category (사용자 생성 커스텀 카테고리) : userAccount 참조
--------------------------------------------------------------------------------
CREATE TABLE custom_category (
    custom_category_idx NUMBER PRIMARY KEY,
    parent_category_idx NUMBER NOT NULL,
    category_name VARCHAR2(100) NOT NULL,
    manager_ac_idx NUMBER NOT NULL,
    img varchar(255),
    CONSTRAINT fk_custom_TO_userAccount FOREIGN KEY (manager_ac_idx) REFERENCES userAccount(ac_idx) ON DELETE CASCADE
);
CREATE SEQUENCE custom_category_seq START WITH 1 INCREMENT BY 1;

--------------------------------------------------------------------------------
-- 5. setting (설정) : userAccount 참조
--------------------------------------------------------------------------------
CREATE TABLE setting (
    setting_idx NUMBER PRIMARY KEY,
    ac_idx NUMBER NOT NULL,
    font VARCHAR2(100) DEFAULT 'Pretendard' NOT NULL,
    theme VARCHAR2(50) DEFAULT 'light' NOT NULL,
    CONSTRAINT FK_setting_TO_userAccount FOREIGN KEY (ac_idx) REFERENCES userAccount(ac_idx) ON DELETE CASCADE
);
CREATE SEQUENCE setting_seq START WITH 1 INCREMENT BY 1;

--------------------------------------------------------------------------------
-- 6. message (메시지) : userAccount 참조
--------------------------------------------------------------------------------
CREATE TABLE message (
    -- PK
    msg_idx NUMBER PRIMARY KEY,

    -- FK
    ac_receiver NUMBER NOT NULL,
    ac_sender NUMBER NOT NULL,

    -- 주요 데이터
    text CLOB NOT NULL,
    img VARCHAR2(255),

    -- 기타 속성 및 날짜
    chk NUMBER(1) NOT NULL,
    time TIMESTAMP NOT NULL,
    
    CONSTRAINT FK_message_TO_userAccount_rcvr FOREIGN KEY (ac_receiver) REFERENCES userAccount(ac_idx) ON DELETE CASCADE,
    CONSTRAINT FK_message_TO_userAccount_sndr FOREIGN KEY (ac_sender) REFERENCES userAccount(ac_idx) ON DELETE CASCADE
);
CREATE SEQUENCE message_seq START WITH 1 INCREMENT BY 1;

--------------------------------------------------------------------------------
-- 7. todolist (투두리스트) : userAccount 참조
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
-- 8. follows (팔로우 목록) : userAccount 참조
--------------------------------------------------------------------------------
CREATE TABLE follows (
    follows_idx NUMBER PRIMARY KEY,
    follower_ac_idx NUMBER NOT NULL,
    followed_ac_idx NUMBER NOT NULL,
    CONSTRAINT FK_follows_TO_userAccountFw FOREIGN KEY (follower_ac_idx) REFERENCES userAccount(ac_idx) ON DELETE CASCADE,
    CONSTRAINT FK_follows_TO_userAccountFwing FOREIGN KEY (followed_ac_idx) REFERENCES userAccount(ac_idx) ON DELETE CASCADE
);
CREATE SEQUENCE follows_seq START WITH 1 INCREMENT BY 1;

--------------------------------------------------------------------------------
-- 9. note (글) : userAccount, category, custom_category, note 자신 참조
--------------------------------------------------------------------------------
CREATE TABLE note (
    -- PK
    note_idx NUMBER PRIMARY KEY,

    -- FK
    ac_idx NUMBER NOT NULL,
    category_idx NUMBER NOT NULL,
    parent_note_idx NUMBER,
    custom_category_idx NUMBER,
    
    -- 주요 데이터
    title VARCHAR2(2000) NOT NULL,
    text CLOB,
    titleimg VARCHAR2(255),

    -- 기타 속성
    display_order NUMBER NOT NULL,
    share_status VARCHAR2(20) DEFAULT 'PRIVATE',

    -- 카운트 및 날짜 (부가 정보)
    view_count NUMBER DEFAULT 0,
    like_count NUMBER DEFAULT 0,
    create_at TIMESTAMP DEFAULT SYSDATE,
    edit_at TIMESTAMP DEFAULT SYSDATE,
    
    CONSTRAINT FK_note_TO_category FOREIGN KEY (category_idx) REFERENCES category(category_idx) ON DELETE CASCADE,
    CONSTRAINT FK_note_TO_custom_category FOREIGN KEY (custom_category_idx) REFERENCES custom_category(custom_category_idx) ON DELETE CASCADE,
    CONSTRAINT fk_note_parent FOREIGN KEY (parent_note_idx) REFERENCES note(note_idx) ON DELETE CASCADE,
    CONSTRAINT fk_note_TO_user FOREIGN KEY (ac_idx) REFERENCES userAccount(ac_idx) ON DELETE CASCADE
);
CREATE SEQUENCE note_seq START WITH 1 INCREMENT BY 1;

--------------------------------------------------------------------------------
-- 10. watchParty (워치파티) : userAccount 참조
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

--------------------------------------------------------------------------------
-- 11. schedule (일정 관리) 테이블 : userAccount 참조
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
-- 12. workspace_blocks (워크스페이스 추가블록) : userAccount 참조
--------------------------------------------------------------------------------
CREATE TABLE workspace_blocks (
    block_id      NUMBER(10)      CONSTRAINT pk_workspace_blocks PRIMARY KEY,
    ac_idx        NUMBER(10)      NOT NULL,
    block_type    VARCHAR2(50)    NOT NULL,
    block_order   NUMBER(3)       NOT NULL,
    config        VARCHAR2(4000)
);
CREATE SEQUENCE workspace_blocks_seq START WITH 1 INCREMENT BY 1;


-- ▶ 3차 종속 테이블 : 1차, 2차 종속 테이블들을 다시 참조하는 테이블
--------------------------------------------------------------------------------
-- 13. note_share (공유 권한) : note, userAccount 참조
--------------------------------------------------------------------------------
CREATE TABLE note_share (
    share_idx NUMBER PRIMARY KEY,
    note_idx NUMBER NOT NULL,
    ac_idx NUMBER NOT NULL,
    permission VARCHAR2(10) NOT NULL,
    CONSTRAINT FK_share_TO_note FOREIGN KEY (note_idx) REFERENCES NOTE(note_idx) ON DELETE CASCADE,
    CONSTRAINT FK_share_TO_userAccount FOREIGN KEY (ac_idx) REFERENCES userAccount(ac_idx) ON DELETE CASCADE
);
CREATE SEQUENCE note_share_seq START WITH 1 INCREMENT BY 1;

--------------------------------------------------------------------------------
-- 14. notification_settings (알림 설정) : setting 참조
--------------------------------------------------------------------------------
CREATE TABLE notification_settings (
    setting_idx NUMBER NOT NULL,
    notification_type VARCHAR2(50) NOT NULL, -- 'LIKE', 'COMMENT', 'FOLLOW' 등
    is_enabled NUMBER(1) DEFAULT 1 NOT NULL, -- 1: ON, 0: OFF
    
    PRIMARY KEY (setting_idx, notification_type),
    CONSTRAINT FK_notif_settings_TO_setting FOREIGN KEY (setting_idx) REFERENCES setting(setting_idx) ON DELETE CASCADE,
    CONSTRAINT chk_notif_settings_enabled CHECK (is_enabled IN (0, 1))
);

--------------------------------------------------------------------------------
-- 15. likes (좋아요) : note, userAccount 참조
--------------------------------------------------------------------------------
CREATE TABLE likes (
    likes_idx NUMBER PRIMARY KEY,
    created_at TIMESTAMP DEFAULT sysdate,
    note_idx NUMBER NOT NULL,
    ac_idx NUMBER NOT NULL,
    CONSTRAINT FK_likes_TO_note FOREIGN KEY (note_idx) REFERENCES note(note_idx) ON DELETE CASCADE,
    CONSTRAINT FK_likes_TO_userAccount FOREIGN KEY (ac_idx) REFERENCES userAccount(ac_idx) ON DELETE CASCADE
);
CREATE SEQUENCE likes_seq START WITH 1 INCREMENT BY 1;

--------------------------------------------------------------------------------
-- 16. commentlist (댓글) : note, userAccount, commentlist 자신 참조
--------------------------------------------------------------------------------
CREATE TABLE commentlist (
    -- PK
    commentlist_idx NUMBER PRIMARY KEY,

    -- FK
    note_idx NUMBER NOT NULL,
    ac_idx NUMBER NOT NULL,
    re_commentlist_idx NUMBER,

    -- 주요 데이터
    text CLOB NOT NULL,

    -- 기타 속성
    depth NUMBER DEFAULT 1 NOT NULL,

    -- 카운트 및 날짜 (부가 정보)
    like_count NUMBER DEFAULT 0,
    create_at TIMESTAMP DEFAULT SYSDATE,
    
    CONSTRAINT FK_comment_TO_comment FOREIGN KEY (re_commentlist_idx) REFERENCES commentlist(commentlist_idx) ON DELETE CASCADE,
    CONSTRAINT FK_comment_TO_note FOREIGN KEY (note_idx) REFERENCES note(note_idx) ON DELETE CASCADE,
    CONSTRAINT FK_comment_TO_userAccount FOREIGN KEY (ac_idx) REFERENCES userAccount(ac_idx) ON DELETE CASCADE
);
CREATE SEQUENCE commentlist_seq START WITH 1 INCREMENT BY 1;

--------------------------------------------------------------------------------
-- 17. notification (알림) : userAccount 참조
--------------------------------------------------------------------------------
CREATE TABLE notification (
    notifi_idx NUMBER PRIMARY KEY,
    ac_idx NUMBER NOT NULL, -- 알림을 받는 사람

    -- 알림의 원인 (Nullable Foreign Keys)
    source_ac_idx NUMBER,       -- 알림을 유발한 사람 (예: 나를 팔로우한 사람)
    note_idx NUMBER,          -- 관련된 노트
    commentlist_idx NUMBER,   -- 관련된 댓글
    
    notification_type VARCHAR2(20) NOT NULL, -- 'NEW_LIKE', 'NEW_COMMENT', 'NEW_FOLLOWER'
    text CLOB NOT NULL,
    is_read NUMBER(1) DEFAULT 0 NOT NULL, -- chk -> is_read로 이름 변경
    created_at TIMESTAMP DEFAULT SYSDATE, -- time -> created_at로 이름 변경

    CONSTRAINT FK_notification_TO_user FOREIGN KEY (ac_idx) REFERENCES userAccount(ac_idx) ON DELETE CASCADE,
    CONSTRAINT FK_notification_source_user FOREIGN KEY (source_ac_idx) REFERENCES userAccount(ac_idx) ON DELETE SET NULL,
    CONSTRAINT FK_notification_TO_note FOREIGN KEY (note_idx) REFERENCES note(note_idx) ON DELETE SET NULL,
    CONSTRAINT FK_notification_TO_comment FOREIGN KEY (commentlist_idx) REFERENCES commentlist(commentlist_idx) ON DELETE SET NULL
);
CREATE SEQUENCE notification_seq START WITH 1 INCREMENT BY 1;

--------------------------------------------------------------------------------
-- 18. wa_sync : watchParty 참조
--------------------------------------------------------------------------------
CREATE TABLE wa_sync (
    sync_idx NUMBER PRIMARY KEY,
    timeline number(10, 3),
    play VARCHAR2(10),
    watchParty_idx NUMBER NOT NULL,
    CONSTRAINT FK_sw FOREIGN KEY (watchParty_idx) REFERENCES watchParty(watchParty_idx) ON DELETE CASCADE
);
CREATE SEQUENCE seq_wa_sync START WITH 1 INCREMENT BY 1;

--------------------------------------------------------------------------------
-- 19. wa_comment : watchParty 참조
--------------------------------------------------------------------------------
CREATE TABLE wa_comment (
    wac_idx NUMBER PRIMARY KEY,
    nickname VARCHAR(100),
    chatting CLOB,
    timeline NUMBER(10, 3),
    create_at TIMESTAMP default sysdate,
    watchParty_idx NUMBER NOT NULL,
    CONSTRAINT FK_cw FOREIGN KEY (watchParty_idx) REFERENCES watchParty(watchParty_idx) ON DELETE CASCADE
);
CREATE SEQUENCE seq_wa_comment START WITH 1 INCREMENT BY 1;

--------------------------------------------------------------------------------
-- 20. 스프링 시큐리티 자동로그인 테이블
--------------------------------------------------------------------------------
CREATE TABLE persistent_logins (
    username VARCHAR(64) NOT NULL,
    series VARCHAR(64) PRIMARY KEY,
    token VARCHAR(64) NOT NULL,
    last_used TIMESTAMP NOT NULL
);




-------------------
-- 생성된 테이블 조회
-------------------
SELECT COUNT(*) -- 19
FROM USER_TABLES;
-------------------
SELECT * 
FROM USER_TABLES;
-------------------
