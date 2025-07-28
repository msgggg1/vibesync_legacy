-- VIBESYNC_SPRINGLEGACY

--------------------------------------------------------------------------------
-- 더미데이터 삽입
--------------------------------------------------------------------------------

-- ▶ 독립 테이블 : 다른 테이블 참조 X
--------------------------------------------------------------------------------
-- 1. category (카테고리)
--------------------------------------------------------------------------------
-- 관리자 전용 카테고리 추가
INSERT INTO category (category_idx, category_name, img) 
VALUES (0, '관리자', 'images/system/admin_icon.png');

-- 메인 카테고리 추가
INSERT INTO category (category_idx, category_name, img) VALUES (1, '영화', 'images/category/movie_icon.jpg');
INSERT INTO category (category_idx, category_name, img) VALUES (2, '드라마', 'images/category/drama_icon.jpg');
INSERT INTO category (category_idx, category_name, img) VALUES (3, '음악', 'images/category/music_icon.jpg');
INSERT INTO category (category_idx, category_name, img) VALUES (4, '애니메이션', 'images/category/anime_icon.jpg');
INSERT INTO category (category_idx, category_name, img) VALUES (5, '일상', 'images/category/daily_icon.jpg');
COMMIT;

SELECT * FROM category;


--------------------------------------------------------------------------------
-- 2. passwordResetTokens (비밀번호 찾기)
--------------------------------------------------------------------------------

-- ▶ 1차 종속 테이블 : 독립 테이블을 참조
--------------------------------------------------------------------------------
-- 3. userAccount (계정) : category 참조
--------------------------------------------------------------------------------
-- 관리자 계정 가입
-- nickname = 'admin' ** 필수 **
-- email = 'admin@admin.com'
-- 평문 비밀번호 : qwe123!!!
UPDATE userAccount
SET role = 'ROLE_ADMIN', category_idx = 0
WHERE nickname = 'admin';
COMMIT;

-- 관리자 계정 외 계정으로 회원가입/로그인 진행 후 페이지 테스트
-- nickname = 'test'
-- email = 'test@test.com'
-- 평문 비밀번호 : qwe123!!!



-------------------
-- 전체 테이블 조회
-------------------
SELECT * 
FROM USER_TABLES;
-------------------



































