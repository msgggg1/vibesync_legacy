

Insert INTO userpage (subject, thumbnail, ac_idx, re_userPg_idx) VALUES('애니메이션', 'img.jpg', 26, null);

select *
from userpage
where ac_idx=26;


-- 1. 기술 관련 글
INSERT INTO note (note_idx, title, text, img, create_at, edit_at, view_count, content_idx, genre_idx, category_idx, userPg_idx)
VALUES (101, '자바스크립트 비동기 처리 마스터하기', 'Promise와 async/await에 대한 심층 분석 및 실전 예제입니다.', NULL, SYSDATE - 10, NULL, 152, 1, 1, 1, 28);

-- 2. 여행 관련 글 (이미지 포함, 수정 이력 있음)
INSERT INTO note (note_idx, title, text, img, create_at, edit_at, view_count, content_idx, genre_idx, category_idx, userPg_idx)
VALUES (102, '여름 휴가, 제주도 동쪽 해안 여행 코스', '세화부터 성산까지, 놓치면 안 될 카페와 맛집 리스트입니다.', '/images/notes/jeju_trip.jpg', SYSDATE - 8, SYSDATE - 7, 340, 2, 2, 2, 28);

-- 3. 일상/에세이 글
INSERT INTO note (note_idx, title, text, img, create_at, edit_at, view_count, content_idx, genre_idx, category_idx, userPg_idx)
VALUES (103, '오늘의 다이어리: 비 오는 날의 생각', '창밖을 바라보며 마시는 따뜻한 커피 한 잔의 여유.', NULL, SYSDATE - 5, NULL, 78, 3, 3, 3, 28);

-- 4. 영화 리뷰 글
INSERT INTO note (note_idx, title, text, img, create_at, edit_at, view_count, content_idx, genre_idx, category_idx, userPg_idx)
VALUES (104, '영화 "인셉션" 10년 만의 재관람 후기', '다시 봐도 놀라운 크리스토퍼 놀란의 상상력. 꿈과 현실의 경계에 대하여.', '/images/notes/inception_poster.jpg', SYSDATE - 3, NULL, 521, 2, 4, 2, 28);

-- 5. 요리/레시피 글
INSERT INTO note (note_idx, title, text, img, create_at, edit_at, view_count, content_idx, genre_idx, category_idx, userPg_idx)
VALUES (105, '10분 완성! 초간단 간장계란파스타 레시피', '자취생 필수 레시피, 실패 없는 황금 비율을 공개합니다.', NULL, SYSDATE - 2, SYSDATE - 1, 210, 3, 5, 3, 28);

-- 6. 개발 관련 글 2
INSERT INTO note (note_idx, title, text, img, create_at, edit_at, view_count, content_idx, genre_idx, category_idx, userPg_idx)
VALUES (106, 'Oracle DB 성능 튜닝 기본 원칙', '인덱스, 실행 계획, SQL 최적화에 대한 기본적인 내용을 다룹니다.', NULL, SYSDATE - 1, NULL, 45, 1, 1, 1, 28);

-- 7. 반려동물 관련 글
INSERT INTO note (note_idx, title, text, img, create_at, edit_at, view_count, content_idx, genre_idx, category_idx, userPg_idx)
VALUES (107, '우리집 강아지 자랑', '새로운 가족이 된 댕댕이를 소개합니다. 이름은 코코에요!', '/images/notes/puppy.jpg', SYSDATE, NULL, 998, 3, 6, 3, 28);
-- 1. 기술 관련 글
INSERT INTO note (note_idx, title, text, img, create_at, edit_at, view_count, content_idx, genre_idx, category_idx, userPg_idx)
VALUES (101, '자바스크립트 비동기 처리 마스터하기', 'Promise와 async/await에 대한 심층 분석 및 실전 예제입니다.', NULL, SYSDATE - 10, NULL, 152, 1, 1, 1, 28);

-- 2. 여행 관련 글 (이미지 포함, 수정 이력 있음)
INSERT INTO note (note_idx, title, text, img, create_at, edit_at, view_count, content_idx, genre_idx, category_idx, userPg_idx)
VALUES (102, '여름 휴가, 제주도 동쪽 해안 여행 코스', '세화부터 성산까지, 놓치면 안 될 카페와 맛집 리스트입니다.', '/images/notes/jeju_trip.jpg', SYSDATE - 8, SYSDATE - 7, 340, 2, 2, 2, 28);

-- 3. 일상/에세이 글
INSERT INTO note (note_idx, title, text, img, create_at, edit_at, view_count, content_idx, genre_idx, category_idx, userPg_idx)
VALUES (103, '오늘의 다이어리: 비 오는 날의 생각', '창밖을 바라보며 마시는 따뜻한 커피 한 잔의 여유.', NULL, SYSDATE - 5, NULL, 78, 3, 3, 3, 28);

-- 4. 영화 리뷰 글
INSERT INTO note (note_idx, title, text, img, create_at, edit_at, view_count, content_idx, genre_idx, category_idx, userPg_idx)
VALUES (104, '영화 "인셉션" 10년 만의 재관람 후기', '다시 봐도 놀라운 크리스토퍼 놀란의 상상력. 꿈과 현실의 경계에 대하여.', '/images/notes/inception_poster.jpg', SYSDATE - 3, NULL, 521, 2, 4, 2, 28);

-- 5. 요리/레시피 글
INSERT INTO note (note_idx, title, text, img, create_at, edit_at, view_count, content_idx, genre_idx, category_idx, userPg_idx)
VALUES (105, '10분 완성! 초간단 간장계란파스타 레시피', '자취생 필수 레시피, 실패 없는 황금 비율을 공개합니다.', NULL, SYSDATE - 2, SYSDATE - 1, 210, 3, 5, 3, 28);

-- 6. 개발 관련 글 2
INSERT INTO note (note_idx, title, text, img, create_at, edit_at, view_count, content_idx, genre_idx, category_idx, userPg_idx)
VALUES (106, 'Oracle DB 성능 튜닝 기본 원칙', '인덱스, 실행 계획, SQL 최적화에 대한 기본적인 내용을 다룹니다.', NULL, SYSDATE - 1, NULL, 45, 1, 1, 1, 28);

-- 7. 반려동물 관련 글
INSERT INTO note (note_idx, title, text, img, create_at, edit_at, view_count, content_idx, genre_idx, category_idx, userPg_idx)
VALUES (107, '우리집 강아지 자랑', '새로운 가족이 된 댕댕이를 소개합니다. 이름은 코코에요!', '/images/notes/puppy.jpg', SYSDATE, NULL, 998, 3, 6, 3, 28);

-- 모든 변경사항을 데이터베이스에 최종 저장

-- 개발/IT
INSERT INTO note (note_idx, title, text, img, create_at, edit_at, view_count, content_idx, genre_idx, category_idx, userPg_idx)
VALUES (108, 'Spring Boot JPA 기본 설정 가이드', 'JPA와 MySQL 연동 시 자주 발생하는 에러와 해결 방법을 정리했습니다.', NULL, SYSDATE - 20, NULL, 255, 1, 1, 1, 28);
INSERT INTO note (note_idx, title, text, img, create_at, edit_at, view_count, content_idx, genre_idx, category_idx, userPg_idx)
VALUES (109, 'Git, 이것만 알면 협업 문제없다!', 'branch, merge, rebase의 개념과 실전 사용법을 다룹니다.', '/images/notes/git_logo.png', SYSDATE - 18, SYSDATE - 17, 480, 1, 1, 1, 28);
INSERT INTO note (note_idx, title, text, img, create_at, edit_at, view_count, content_idx, genre_idx, category_idx, userPg_idx)
VALUES (110, 'Docker 컨테이너 기초', '왜 Docker를 사용해야 할까? 기본 개념부터 실행까지 알아봅니다.', NULL, SYSDATE - 15, NULL, 180, 1, 1, 1, 28);

-- 여행
INSERT INTO note (note_idx, title, text, img, create_at, edit_at, view_count, content_idx, genre_idx, category_idx, userPg_idx)
VALUES (111, '강릉 1박 2일 주말 여행 코스 추천', '안목해변 커피거리와 강문해변, 그리고 초당 순두부 맛집까지!', '/images/notes/gangneung.jpg', SYSDATE - 30, NULL, 890, 2, 2, 2, 28);
INSERT INTO note (note_idx, title, text, img, create_at, edit_at, view_count, content_idx, genre_idx, category_idx, userPg_idx)
VALUES (112, '가을 단풍 구경, 내장산 등산 후기', '케이블카를 타고 정상까지. 감탄이 절로 나오는 풍경이었습니다.', NULL, SYSDATE - 25, NULL, 620, 2, 2, 2, 28);
INSERT INTO note (note_idx, title, text, img, create_at, edit_at, view_count, content_idx, genre_idx, category_idx, userPg_idx)
VALUES (113, '오사카 3박 4일 자유여행 필수코스', '도톤보리, 유니버셜 스튜디오, 오사카성 방문기', '/images/notes/osaka.jpg', SYSDATE - 40, SYSDATE - 39, 1250, 2, 2, 2, 28);

-- 일상/취미
INSERT INTO note (note_idx, title, text, img, create_at, edit_at, view_count, content_idx, genre_idx, category_idx, userPg_idx)
VALUES (114, '주말 베이킹: 레몬 마들렌 만들기', '상큼한 레몬향이 가득한 마들렌 레시피입니다. 커피랑 잘 어울려요.', '/images/notes/madeleine.jpg', SYSDATE - 6, NULL, 95, 3, 3, 3, 28);
INSERT INTO note (note_idx, title, text, img, create_at, edit_at, view_count, content_idx, genre_idx, category_idx, userPg_idx)
VALUES (115, '오늘의 운동 기록 - 헬스장', '가슴 운동 루틴: 벤치프레스, 덤벨프레스, 푸쉬업 3세트 완료.', NULL, SYSDATE - 1, NULL, 35, 3, 5, 3, 28);
INSERT INTO note (note_idx, title, text, img, create_at, edit_at, view_count, content_idx, genre_idx, category_idx, userPg_idx)
VALUES (116, '요즘 읽고 있는 책, "사피엔스"', '인류의 역사에 대한 새로운 관점을 제시하는 책. 추천합니다.', NULL, SYSDATE - 12, NULL, 112, 3, 3, 3, 28);
INSERT INTO note (note_idx, title, text, img, create_at, edit_at, view_count, content_idx, genre_idx, category_idx, userPg_idx)
VALUES (117, '새로운 취미, 식물 키우기 시작!', '몬스테라와 스킨답서스를 새로 들였어요. 잘 키울 수 있겠죠?', '/images/notes/plants.jpg', SYSDATE - 4, NULL, 88, 3, 3, 3, 28);

-- 문화/리뷰
INSERT INTO note (note_idx, title, text, img, create_at, edit_at, view_count, content_idx, genre_idx, category_idx, userPg_idx)
VALUES (118, '뮤지컬 "오페라의 유령" 관람 후기', '웅장한 무대와 배우들의 연기력에 압도당했습니다.', NULL, SYSDATE - 50, SYSDATE - 50, 760, 2, 4, 2, 28);
INSERT INTO note (note_idx, title, text, img, create_at, edit_at, view_count, content_idx, genre_idx, category_idx, userPg_idx)
VALUES (119, '넷플릭스 신작 시리즈 "더 글로리" 정주행 완료', '흡입력 있는 스토리와 배우들의 연기가 일품.', '/images/notes/the_glory.jpg', SYSDATE - 22, NULL, 2300, 2, 4, 2, 28);
INSERT INTO note (note_idx, title, text, img, create_at, edit_at, view_count, content_idx, genre_idx, category_idx, userPg_idx)
VALUES (120, '국립중앙박물관 특별전, "어느 수집가의 초대"', '고즈넉한 분위기 속에서 즐기는 우리 문화재의 아름다움.', NULL, SYSDATE - 16, NULL, 190, 2, 4, 2, 28);
COMMIT;

-- 17. schedule 테이블 데이터 삽입 (schedule_idx 명시적 지정)
INSERT INTO schedule (title, description, start_time, end_time, color, ac_idx) VALUES ( '[업무] 주간 팀 미팅', '프로젝트 진행 상황 공유 및 다음 주 계획 논의', TO_TIMESTAMP('2025-06-03 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-06-03 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), '#FF6B6B', 26);
INSERT INTO schedule (title, description, start_time, end_time, color, ac_idx) VALUES ( '[영화] 액션 신작 관람', '이영희님과 영화관람. CGV C열 12,13번', TO_TIMESTAMP('2025-06-01 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-06-01 16:30:00', 'YYYY-MM-DD HH24:MI:SS'), '#4D96FF', 26);
INSERT INTO schedule (title, description, start_time, end_time, color, ac_idx) VALUES ( '[약속] 파스타 런치', '최유리님과 회사 앞 파스타집에서 점심 식사', TO_TIMESTAMP('2025-06-01 12:30:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-06-01 13:30:00', 'YYYY-MM-DD HH24:MI:SS'), '#6BCB77', 26);
INSERT INTO schedule (title, description, start_time, end_time, color, ac_idx) VALUES ( '[취미] K-POP 댄스 연습', '윤서아님과 함께 연습실에서 신곡 안무 연습', TO_TIMESTAMP('2025-06-02 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-06-02 17:00:00', 'YYYY-MM-DD HH24:MI:SS'), '#FDBA74', 26);
INSERT INTO schedule (title, description, start_time, end_time, color, ac_idx) VALUES ( '[운동] 헬스장 PT', '하체 운동 집중', TO_TIMESTAMP('2025-06-03 19:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-06-03 20:00:00', 'YYYY-MM-DD HH24:MI:SS'), '#8B5CF6', 26);
INSERT INTO schedule (title, description, start_time, end_time, color, ac_idx) VALUES ( '[학습] Java 스터디 모임', 'MVC 패턴 및 JDBC 심화 학습', TO_TIMESTAMP('2025-06-04 19:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-06-04 21:00:00', 'YYYY-MM-DD HH24:MI:SS'), '#A78BFA', 26);
INSERT INTO schedule (title, description, start_time, end_time, color, ac_idx) VALUES ( '[게임] 신작 판타지 RPG 파티 플레이', '박현우님과 함께 보스 레이드 도전!', TO_TIMESTAMP('2025-06-02 20:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-06-02 23:00:00', 'YYYY-MM-DD HH24:MI:SS'), '#EC4899', 26);
INSERT INTO schedule (title, description, start_time, end_time, color, ac_idx) VALUES ( '[약속] 한강 자전거 라이딩', '박민준님과 오후 3시에 한강 공원에서 만나기', TO_TIMESTAMP('2025-06-02 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-06-02 16:30:00', 'YYYY-MM-DD HH24:MI:SS'), '#6BCB77', 26);
INSERT INTO schedule (title, description, start_time, end_time, color, ac_idx) VALUES ( '[개인] 치과 정기 검진', '스케일링 및 충치 검진', TO_TIMESTAMP('2025-06-05 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-06-05 15:30:00', 'YYYY-MM-DD HH24:MI:SS'), '#FDBA74', 26);
INSERT INTO schedule (title, description, start_time, end_time, color, ac_idx) VALUES ( '[업무] 클라이언트 미팅', 'A사 신규 프로젝트 제안 발표', TO_TIMESTAMP('2025-06-06 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-06-06 15:30:00', 'YYYY-MM-DD HH24:MI:SS'), '#FF6B6B', 26);
INSERT INTO schedule (title, description, start_time, end_time, color, ac_idx) VALUES ( '[약속] 저녁 식사 (쭈꾸미)', '강지훈님과 저녁 7시에 회사 앞에서 만나기', TO_TIMESTAMP('2025-06-04 19:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-06-04 20:30:00', 'YYYY-MM-DD HH24:MI:SS'), '#FDBA74', 26);
INSERT INTO schedule (title, description, start_time, end_time, color, ac_idx) VALUES ( '[취미] 쿠킹 클래스', '디저트 베이킹 - 딸기 케이크 만들기', TO_TIMESTAMP('2025-06-08 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-06-08 16:00:00', 'YYYY-MM-DD HH24:MI:SS'), '#EC4899', 26);
INSERT INTO schedule (title, description, start_time, end_time, color, ac_idx) VALUES ( '[여가] VVSync 콘서트', '올림픽공원 KSPO DOME. 스탠딩 A구역', TO_TIMESTAMP('2025-06-15 18:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-06-15 20:30:00', 'YYYY-MM-DD HH24:MI:SS'), '#A78BFA', 26);
INSERT INTO schedule (title, description, start_time, end_time, color, ac_idx) VALUES ( '[약속] 성수동 맛집 탐방', '전지현님과 핫플레이스 카페 및 레스토랑 방문', TO_TIMESTAMP('2025-06-08 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-06-08 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), '#6BCB77', 26);
INSERT INTO schedule (title, description, start_time, end_time, color, ac_idx) VALUES ( '[개인] 도서관 가기', '''사라진 기억'' 원작 소설 대여하기', TO_TIMESTAMP('2025-06-07 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-06-07 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), '#4D96FF', 26);
INSERT INTO schedule (title, description, start_time, end_time, color, ac_idx) VALUES ( '[학습] 코딩 테스트 스터디', '알고리즘 문제 풀이 (백준)', TO_TIMESTAMP('2025-06-08 18:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-06-08 20:00:00', 'YYYY-MM-DD HH24:MI:SS'), '#A78BFA', 26);
INSERT INTO schedule (title, description, start_time, end_time, color, ac_idx) VALUES ( '[취미] 주말 캠핑', '가평 캠핑장. 장비 점검 및 식료품 준비', TO_TIMESTAMP('2025-06-22 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-06-23 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), '#8B5CF6', 26);
INSERT INTO schedule (title, description, start_time, end_time, color, ac_idx) VALUES ( '[약속] 축구 경기 직관', '상암 월드컵 경기장. 대한민국 vs 브라질 친선 경기', TO_TIMESTAMP('2025-06-11 19:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-06-11 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), '#6BCB77', 26);
INSERT INTO schedule (title, description, start_time, end_time, color, ac_idx) VALUES ( '[업무] 분기 실적 보고 회의', NULL, TO_TIMESTAMP('2025-06-17 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-06-17 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), '#FF6B6B', 26);
INSERT INTO schedule (title, description, start_time, end_time, color, ac_idx) VALUES ( '[여가] 웹툰 작가 사인회', '''학교의 비밀'' 작가님 사인회 참석', TO_TIMESTAMP('2025-06-29 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-06-29 16:00:00', 'YYYY-MM-DD HH24:MI:SS'), '#FDBA74', 26);
INSERT INTO schedule (title, description, start_time, end_time, color, ac_idx) VALUES ( '[개인] 미용실 예약', '여름맞이 염색 및 커트', TO_TIMESTAMP('2025-06-14 16:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-06-14 18:00:00', 'YYYY-MM-DD HH24:MI:SS'), '#EC4899', 26);
INSERT INTO schedule (title, description, start_time, end_time, color, ac_idx) VALUES ( '[운동] 요가 클래스', '빈야사 요가. 힐링 타임', TO_TIMESTAMP('2025-06-12 20:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-06-12 21:00:00', 'YYYY-MM-DD HH24:MI:SS'), '#6BCB77', 26);
INSERT INTO schedule (title, description, start_time, end_time, color, ac_idx) VALUES ( '[학습] 영어 회화 스터디', '주제: 최근에 본 영화에 대해 이야기하기', TO_TIMESTAMP('2025-06-13 19:30:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-06-13 21:00:00', 'YYYY-MM-DD HH24:MI:SS'), '#A78BFA', 26);
INSERT INTO schedule (title, description, start_time, end_time, color, ac_idx) VALUES ( '[개인] 은행 업무', '대출 관련 서류 제출', TO_TIMESTAMP('2025-06-18 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-06-18 15:30:00', 'YYYY-MM-DD HH24:MI:SS'), '#4D96FF', 26);
INSERT INTO schedule (title, description, start_time, end_time, color, ac_idx) VALUES ( '[업무] 신입사원 OJT 멘토링', '신규 입사자 개발 환경 세팅 지원', TO_TIMESTAMP('2025-06-19 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-06-19 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), '#FF6B6B', 26);
INSERT INTO schedule (title, description, start_time, end_time, color, ac_idx) VALUES ( '[약속] 쇼핑 약속', '유해진님과 주말 쇼핑', TO_TIMESTAMP('2025-06-08 16:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-06-08 18:00:00', 'YYYY-MM-DD HH24:MI:SS'), '#FDBA74', 26);
INSERT INTO schedule (title, description, start_time, end_time, color, ac_idx) VALUES ( '[취미] 프라모델 조립', '''메카 워리어즈'' 신규 모델 조립 시작', TO_TIMESTAMP('2025-06-16 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-06-16 17:00:00', 'YYYY-MM-DD HH24:MI:SS'), '#EC4899', 26);
INSERT INTO schedule (title, description, start_time, end_time, color, ac_idx) VALUES ( '[여가] 미술관 관람', '현대미술 기획전 관람', TO_TIMESTAMP('2025-06-23 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-06-23 15:30:00', 'YYYY-MM-DD HH24:MI:SS'), '#8B5CF6', 26);
INSERT INTO schedule (title, description, start_time, end_time, color, ac_idx) VALUES ( '[개인] 자동차 정기 점검', '엔진 오일 교체 및 타이어 공기압 체크', TO_TIMESTAMP('2025-06-20 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-06-20 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), '#4D96FF', 26);
INSERT INTO schedule (title, description, start_time, end_time, color, ac_idx) VALUES ( '[약속] 집들이', '새싹개발자 박현우님 집들이 참석', TO_TIMESTAMP('2025-06-29 18:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-06-29 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), '#6BCB77', 26);

COMMIT;

-- todo
INSERT INTO todolist (created_at, text, todo_group, color, ac_idx, status) VALUES ( TO_TIMESTAMP('2025-06-01 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), '새 프로젝트 기획안 작성', '업무', '파란색', 26, FLOOR(DBMS_RANDOM.VALUE(0, 2)));
INSERT INTO todolist (created_at, text, todo_group, color, ac_idx, status) VALUES ( TO_TIMESTAMP('2025-06-01 10:30:00', 'YYYY-MM-DD HH24:MI:SS'), '주간 업무 보고서 제출', '업무', '파란색', 26, FLOOR(DBMS_RANDOM.VALUE(0, 2)));
INSERT INTO todolist (created_at, text, todo_group, color, ac_idx, status) VALUES ( TO_TIMESTAMP('2025-06-01 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), '헬스장 등록하기', '운동', '초록색', 26, FLOOR(DBMS_RANDOM.VALUE(0, 2)));
INSERT INTO todolist (created_at, text, todo_group, color, ac_idx, status) VALUES ( TO_TIMESTAMP('2025-06-01 17:00:00', 'YYYY-MM-DD HH24:MI:SS'), '저녁 장보기 - 채소 및 과일', '개인', '노란색', 26, FLOOR(DBMS_RANDOM.VALUE(0, 2)));
INSERT INTO todolist (created_at, text, todo_group, color, ac_idx, status) VALUES ( TO_TIMESTAMP('2025-06-02 08:00:00', 'YYYY-MM-DD HH24:MI:SS'), '아침 조깅 3km', '운동', '초록색', 26, FLOOR(DBMS_RANDOM.VALUE(0, 2)));
INSERT INTO todolist (created_at, text, todo_group, color, ac_idx, status) VALUES ( TO_TIMESTAMP('2025-06-02 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), '자바 스터디 자료 예습하기 🚀', '학습', '보라색', 26, FLOOR(DBMS_RANDOM.VALUE(0, 2)));
INSERT INTO todolist (created_at, text, todo_group, color, ac_idx, status) VALUES ( TO_TIMESTAMP('2025-06-02 15:30:00', 'YYYY-MM-DD HH24:MI:SS'), '영화 ''우주 대모험'' 예매', '취미', '주황색', 26, FLOOR(DBMS_RANDOM.VALUE(0, 2)));
INSERT INTO todolist (created_at, text, todo_group, color, ac_idx, status) VALUES ( TO_TIMESTAMP('2025-06-03 09:30:00', 'YYYY-MM-DD HH24:MI:SS'), '팀 회의 준비 (발표 자료 점검)', '업무', '파란색', 26, FLOOR(DBMS_RANDOM.VALUE(0, 2)));
INSERT INTO todolist (created_at, text, todo_group, color, ac_idx, status) VALUES ( TO_TIMESTAMP('2025-06-03 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), '도서관에서 기술 서적 대출', '학습', '보라색', 26, FLOOR(DBMS_RANDOM.VALUE(0, 2)));
INSERT INTO todolist (created_at, text, todo_group, color, ac_idx, status) VALUES ( TO_TIMESTAMP('2025-06-03 16:00:00', 'YYYY-MM-DD HH24:MI:SS'), '친구 생일 선물 고르기 🎁', '개인', '분홍색', 26, FLOOR(DBMS_RANDOM.VALUE(0, 2)));
INSERT INTO todolist (created_at, text, todo_group, color, ac_idx, status) VALUES ( TO_TIMESTAMP('2025-06-04 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), '플랭크 챌린지 1일차 💪', '운동', '초록색', 26, FLOOR(DBMS_RANDOM.VALUE(0, 2)));
INSERT INTO todolist (created_at, text, todo_group, color, ac_idx, status) VALUES ( TO_TIMESTAMP('2025-06-04 14:30:00', 'YYYY-MM-DD HH24:MI:SS'), '기타 코드 연습 (C, G, Am, F)', '취미', '하늘색', 26, FLOOR(DBMS_RANDOM.VALUE(0, 2)));
INSERT INTO todolist (created_at, text, todo_group, color, ac_idx, status) VALUES ( TO_TIMESTAMP('2025-06-04 18:00:00', 'YYYY-MM-DD HH24:MI:SS'), '온라인 강의 시청 - 데이터베이스 기초', '학습', '보라색', 26, FLOOR(DBMS_RANDOM.VALUE(0, 2)));
INSERT INTO todolist (created_at, text, todo_group, color, ac_idx, status) VALUES ( TO_TIMESTAMP('2025-06-05 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), '클라이언트 미팅 준비', '업무', '파란색', 26, FLOOR(DBMS_RANDOM.VALUE(0, 2)));
INSERT INTO todolist (created_at, text, todo_group, color, ac_idx, status) VALUES ( TO_TIMESTAMP('2025-06-05 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), '점심 약속 - 김영희와 파스타', '약속', '노란색', 26, FLOOR(DBMS_RANDOM.VALUE(0, 2)));
INSERT INTO todolist (created_at, text, todo_group, color, ac_idx, status) VALUES ( TO_TIMESTAMP('2025-06-05 16:30:00', 'YYYY-MM-DD HH24:MI:SS'), '새로운 레시피로 저녁 요리 도전! 🍳', '취미', '주황색', 26, FLOOR(DBMS_RANDOM.VALUE(0, 2)));
INSERT INTO todolist (created_at, text, todo_group, color, ac_idx, status) VALUES ( TO_TIMESTAMP('2025-06-06 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), '여행 계획 세우기 (여름휴가) ✈️', '개인', '분홍색', 26, FLOOR(DBMS_RANDOM.VALUE(0, 2)));
INSERT INTO todolist (created_at, text, todo_group, color, ac_idx, status) VALUES ( TO_TIMESTAMP('2025-06-06 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), '자전거 라이딩 - 한강공원까지', '운동', '초록색', 26, FLOOR(DBMS_RANDOM.VALUE(0, 2)));
INSERT INTO todolist (created_at, text, todo_group, color, ac_idx, status) VALUES ( TO_TIMESTAMP('2025-06-07 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), '영어 회화 스터디 참여', '학습', '보라색', 26, FLOOR(DBMS_RANDOM.VALUE(0, 2)));
INSERT INTO todolist (created_at, text, todo_group, color, ac_idx, status) VALUES ( TO_TIMESTAMP('2025-06-07 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), '밀린 드라마 정주행 시작! 📺', '여가', '하늘색', 26, FLOOR(DBMS_RANDOM.VALUE(0, 2)));
INSERT INTO todolist (created_at, text, todo_group, color, ac_idx, status) VALUES ( TO_TIMESTAMP('2025-06-07 19:00:00', 'YYYY-MM-DD HH24:MI:SS'), '가족 외식 예약하기', '개인', '노란색', 26, FLOOR(DBMS_RANDOM.VALUE(0, 2)));
INSERT INTO todolist (created_at, text, todo_group, color, ac_idx, status) VALUES ( TO_TIMESTAMP('2025-06-08 09:30:00', 'YYYY-MM-DD HH24:MI:SS'), '주말 대청소 및 정리정돈', '생활', '파란색', 26, FLOOR(DBMS_RANDOM.VALUE(0, 2)));
INSERT INTO todolist (created_at, text, todo_group, color, ac_idx, status) VALUES ( TO_TIMESTAMP('2025-06-08 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), '새로운 카페 탐방 및 블로그 포스팅 ✨', '취미', '주황색', 26, FLOOR(DBMS_RANDOM.VALUE(0, 2)));
INSERT INTO todolist (created_at, text, todo_group, color, ac_idx, status) VALUES ( TO_TIMESTAMP('2025-06-08 17:00:00', 'YYYY-MM-DD HH24:MI:SS'), '코딩 테스트 문제 풀이 (3문제)', '학습', '보라색', 26, FLOOR(DBMS_RANDOM.VALUE(0, 2)));
INSERT INTO todolist (created_at, text, todo_group, color, ac_idx, status) VALUES ( TO_TIMESTAMP('2025-06-09 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), '반려견 산책 시키기 🐕', '반려동물', '초록색', 26, FLOOR(DBMS_RANDOM.VALUE(0, 2)));
INSERT INTO todolist (created_at, text, todo_group, color, ac_idx, status) VALUES ( TO_TIMESTAMP('2025-06-09 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), '미술관 전시회 관람', '여가', '분홍색', 26, FLOOR(DBMS_RANDOM.VALUE(0, 2)));
INSERT INTO todolist (created_at, text, todo_group, color, ac_idx, status) VALUES ( TO_TIMESTAMP('2025-06-09 18:00:00', 'YYYY-MM-DD HH24:MI:SS'), '다음 주 식단 계획하기', '생활', '노란색', 26, FLOOR(DBMS_RANDOM.VALUE(0, 2)));
INSERT INTO todolist (created_at, text, todo_group, color, ac_idx, status) VALUES ( TO_TIMESTAMP('2025-06-10 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), '월요병 극복! 힘내서 출근 준비! 🔥', '일상', '파란색', 26, FLOOR(DBMS_RANDOM.VALUE(0, 2)));
INSERT INTO todolist (created_at, text, todo_group, color, ac_idx, status) VALUES ( TO_TIMESTAMP('2025-06-10 11:30:00', 'YYYY-MM-DD HH24:MI:SS'), '새로운 프로그래밍 언어 탐색', '학습', '보라색', 26, FLOOR(DBMS_RANDOM.VALUE(0, 2)));
INSERT INTO todolist (created_at, text, todo_group, color, ac_idx, status) VALUES ( TO_TIMESTAMP('2025-06-10 16:00:00', 'YYYY-MM-DD HH24:MI:SS'), '퇴근 후 친구와 저녁 약속 🍻', '약속', '주황색', 26, FLOOR(DBMS_RANDOM.VALUE(0, 2)));


commit;

select *
from watchParty;

-- =================================================================
-- message 테이블 더미 데이터 (총 20개)
-- =================================================================

-- 26번 사용자가 보낸 메시지 (10개)
-- 26 -> 27 (서로 주고받는 메시지)
INSERT INTO message (text, time, chk, ac_receiver, ac_sender) VALUES ('안녕하세요! 워크스페이스에서 보고 쪽지 드려요.', SYSTIMESTAMP - INTERVAL '1' DAY, 0, 27, 26);
INSERT INTO message (text, time, chk, ac_receiver, ac_sender) VALUES ('네, 반갑습니다! 어떤 일로 연락주셨나요?', SYSTIMESTAMP - INTERVAL '23' HOUR, 0, 26, 27);
INSERT INTO message (text, time, chk, ac_receiver, ac_sender) VALUES ('다름이 아니라 혹시 주말에 같이 프로젝트 진행 가능할까요?', SYSTIMESTAMP - INTERVAL '22' HOUR, 0, 27, 26);
INSERT INTO message (text, time, chk, ac_receiver, ac_sender) VALUES ('네 좋아요! 시간 맞춰보죠.', SYSTIMESTAMP - INTERVAL '21' HOUR, 1, 26, 27); -- 27번이 보낸 아직 안읽은 메시지

-- 26 -> 그 외 사용자
INSERT INTO message (text, time, chk, ac_receiver, ac_sender) VALUES ('선배님, 지난번에 말씀드린 자료 메일로 보냈습니다!', SYSTIMESTAMP - INTERVAL '2' DAY, 0, 1, 26);
INSERT INTO message (text, time, chk, ac_receiver, ac_sender) VALUES ('친구야 오늘 저녁에 시간 돼?', SYSTIMESTAMP - INTERVAL '5' HOUR, 0, 2, 26);
INSERT INTO message (text, time, chk, ac_receiver, ac_sender) VALUES ('사장님, 요청하신 시안 A, B 두 가지로 만들었습니다.', SYSTIMESTAMP - INTERVAL '3' DAY, 0, 3, 26);
INSERT INTO message (text, time, chk, ac_receiver, ac_sender) VALUES ('스터디 자료 공유 감사합니다!', SYSTIMESTAMP - INTERVAL '1' DAY, 0, 4, 26);
INSERT INTO message (text, time, chk, ac_receiver, ac_sender) VALUES ('혹시 이 부분 코드 좀 봐주실 수 있나요? 에러가 계속 나네요.', SYSTIMESTAMP - INTERVAL '10' HOUR, 1, 1, 26);
INSERT INTO message (text, time, chk, ac_receiver, ac_sender) VALUES ('오늘 회의는 30분 늦게 시작한다고 합니다.', SYSTIMESTAMP - INTERVAL '2' HOUR, 1, 3, 26); -- 아직 안읽은 메시지;

-- 27 -> 그 외 사용자
INSERT INTO message (text, time, chk, ac_receiver, ac_sender) VALUES ('교수님, 과제 제출 기한을 하루만 연장해주실 수 있을까요?', SYSTIMESTAMP - INTERVAL '4' DAY, 0, 5, 27);
INSERT INTO message (text, time, chk, ac_receiver, ac_sender) VALUES ('주문한 상품 배송 언제쯤 시작되나요?', SYSTIMESTAMP - INTERVAL '2' DAY, 0, 6, 27);
INSERT INTO message (text, time, chk, ac_receiver, ac_sender) VALUES ('저번에 빌려준 책 잘 읽고 있어 고마워!', SYSTIMESTAMP - INTERVAL '6' HOUR, 0, 7, 27);
INSERT INTO message (text, time, chk, ac_receiver, ac_sender) VALUES ('이번 주말에 같이 영화나 볼까?', SYSTIMESTAMP - INTERVAL '1' DAY, 1, 8, 27); -- 아직 안읽은 메시지
INSERT INTO message (text, time, chk, ac_receiver, ac_sender) VALUES ('팀장님, 휴가 신청서 결재 부탁드립니다.', SYSTIMESTAMP - INTERVAL '5' DAY, 0, 5, 27);
INSERT INTO message (text, time, chk, ac_receiver, ac_sender) VALUES ('다음 주 세미나 장소가 변경되었습니다.', SYSTIMESTAMP - INTERVAL '3' HOUR, 0, 6, 27);
INSERT INTO message (text, time, chk, ac_receiver, ac_sender) VALUES ('생일 축하해! 오늘 하루 최고로 행복하게 보내!', SYSTIMESTAMP - INTERVAL '12' HOUR, 0, 7, 27);
INSERT INTO message (text, time, chk, ac_receiver, ac_sender) VALUES ('면접 결과 나왔나요? 좋은 소식 있었으면 좋겠네요.', SYSTIMESTAMP - INTERVAL '1' HOUR, 1, 8, 27); -- 아직 안읽은 메시지
INSERT INTO message (text, time, chk, ac_receiver, ac_sender) VALUES ('네, 답장 감사합니다. 그럼 주말에 뵙겠습니다.', SYSTIMESTAMP - INTERVAL '20' HOUR, 0, 26, 27); -- 26에게 보낸 답장
INSERT INTO message (text, time, chk, ac_receiver, ac_sender) VALUES ('자세한 얘기는 만나서 해요!', SYSTIMESTAMP - INTERVAL '19' HOUR, 1, 26, 27); -- 26에게 보낸 아직 안읽은 메시지

commit;



INSERT INTO follows (ac_follow, ac_following) VALUES (26, 2);
INSERT INTO follows (ac_follow, ac_following) VALUES (26, 5);
INSERT INTO follows (ac_follow, ac_following) VALUES (26, 8);
INSERT INTO follows (ac_follow, ac_following) VALUES (26, 12);
INSERT INTO follows (ac_follow, ac_following) VALUES (26, 25);
commit;
