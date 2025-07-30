-- 이메일 기억하기를 위한 테이블
CREATE TABLE remembered_emails (
    id NUMBER PRIMARY KEY,
    email VARCHAR2(100) NOT NULL,
    created_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    expires_at TIMESTAMP NOT NULL
);

-- 시퀀스 생성
CREATE SEQUENCE remembered_emails_seq
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE; 