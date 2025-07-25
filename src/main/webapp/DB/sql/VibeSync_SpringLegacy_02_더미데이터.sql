--------------------------------------------------------------------------------
-- 더미데이터 삽입
--------------------------------------------------------------------------------

--------------------------------------------------------------------------------
-- 1. category (카테고리)
--------------------------------------------------------------------------------
-- 관리자 전용 카테고리 추가
INSERT INTO category (category_idx, category_name, img) 
VALUES (0, '관리자', 'images/system/admin_icon.png');

-- 메인 카테고리 추가
INSERT INTO category (category_idx, category_name, img) VALUES (1, '영화', 'images/category/movie.jpg');
INSERT INTO category (category_idx, category_name, img) VALUES (2, '드라마', 'images/category/drama.jpg');
INSERT INTO category (category_idx, category_name, img) VALUES (3, '음악', 'images/category/music.jpg');
INSERT INTO category (category_idx, category_name, img) VALUES (4, '애니메이션', 'images/category/anime.jpg');
INSERT INTO category (category_idx, category_name, img) VALUES (5, '일상', 'images/category/daily.jpg');
