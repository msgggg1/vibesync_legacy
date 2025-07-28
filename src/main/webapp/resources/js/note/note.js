// 위치: /resources/js/note/note.js
/*
window.onload = function() {
    // EditorJS 라이브러리가 로드되었는지 확인
    if (typeof EditorJS === 'undefined') {
        console.error('Editor.js 라이브러리가 로드되지 않았습니다. note_head.jsp를 확인하세요.');
        return;
    }
    
    // Editor.js 인스턴스 생성
    const editor = new EditorJS({
	    holder: 'editorjs',
	    tools: {
	        header: Header,
	        list: List,
	        linkTool: {
	            class: LinkTool
	        },
	        image: {
	            class: ImageTool,
	            config: {
	                endpoints: {
	                    byFile: `${path}/api/images/upload/note`, // 이미지를 업로드할 백엔드 API 경로
	                },
	                // CSRF 토큰 설정
	                additionalRequestData: {
	                    '${_csrf.parameterName}': '${_csrf.token}'
	                }
	            }
	        }
	    },
	    placeholder: '내용을 입력하세요...'
	    // ...
	});

    // --- 데이터 로딩 (글 보기) ---
    function loadNoteData() {
        // noteIdx가 0이 아니면 (기존 글 보기/수정)
        if (noteIdx && noteIdx > 0) {
            $.ajax({
                url: `${path}/api/notes/${noteIdx}`,
                type: 'GET',
                success: function(response) {
                    const note = response.noteDetail;
                    $('#note-title').val(note.title);
                    
                    // editor.js가 준비되면 DB에서 가져온 JSON 데이터로 내용을 채움
                    editor.isReady.then(() => {
                        editor.render(JSON.parse(note.text));
                    });
                },
                error: function() {
                    alert('게시글을 불러오는 데 실패했습니다.');
                }
            });
        }
        // noteIdx가 0이면 (새 글 쓰기) 아무것도 로드하지 않음.
    }

    // --- 데이터 저장 (글 쓰기/수정) ---
    function saveNoteData() {
        editor.save().then((outputData) => {
            const contentJson = JSON.stringify(outputData);
            
            const saveData = {
                title: $('#note-title').val(),
                contentJson: contentJson,
                categoryIdx: $('#note-category-select').val()
            };

            const isUpdate = (noteIdx && noteIdx > 0);
            const apiUrl = isUpdate ? `${path}/api/notes/${noteIdx}` : `${path}/api/notes`;
            const apiMethod = isUpdate ? 'PUT' : 'POST';

            $.ajax({
                url: apiUrl,
                type: apiMethod,
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(saveData),
                success: function(result) {
                    alert('성공적으로 저장되었습니다.');
                    if (isUpdate) {
                        location.reload(); // 수정일 경우 페이지 새로고침
                    } else {
                        location.href = `${path}/notes/${result}`; // 새 글일 경우 해당 글로 이동
                    }
                },
                error: function() {
                    alert('저장에 실패했습니다.');
                }
            });

        }).catch((error) => {
            console.error('Editor.js 저장 데이터 추출 실패:', error);
        });
    }

    // --- 이벤트 핸들러 ---
    $('#save-btn').on('click', saveNoteData);
    $('#list-btn').on('click', function() {
        location.href = listLink;
    });

    // --- 초기 실행 ---
    loadNoteData();
};
*/