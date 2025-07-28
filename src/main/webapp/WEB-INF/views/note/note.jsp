<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="path" value="${pageContext.request.contextPath}" />
<c:set var="noteIdForJs" value="${noteIdx ne null ? noteIdx : 0}" />
<c:set var="parentIdForJs" value="${parentNoteIdx ne null ? parentNoteIdx : 0}" />

<div class="note-editor-container">
	<%-- 상단 기능 헤더 --%>
	<div class="note-editor-header">
		<div class="header-left">
			<a href="${path}/board/list${criteria.listLink}" class="back-link">
				<img src="${path}/resources/images/icons/arrow_back.svg" alt="뒤로가기">
			</a>
			<span style="font-weight: bold;">CATEGORY</span>
			<div class="category-selector">
				<select id="note-category-select" name="categoryIdx">
					<c:forEach items="${categoryList}" var="category">
						<option value="${category.categoryIdx}">
							${category.categoryName}
						</option>
					</c:forEach>
				</select>            
			</div>   
		</div>
		
		<div class="header-right"> 
			<button type="button" id="list-btn">LIST</button>
			<button type="button" id="save-btn">SAVE</button>
			<button type="button" id="delete-btn" class="btn-danger">DELETE</button>
		</div>
	</div>

	<%-- 콘텐츠 영역 --%>
	<div class="note-editor-content">
		<input type="text" id="note-title" placeholder="Title">
		<div id="editorjs"></div>
	</div>
	
	<!-- 하위 페이지 추가 -->
	<div class="child-notes-section">
        <h3>Sub-pages</h3>
        <div id="child-notes-list">
            <%-- 하위 노트 목록은 나중에 여기에 표시됩니다 --%>
        </div>
        
        <%-- 현재 글이 저장된 상태일 때만 (noteIdx > 0) 버튼 표시 --%>
        <c:if test="${noteIdx > 0}">
            <button type="button" id="add-child-note-btn">+ Add a page inside</button>
        </c:if>
    </div>
	
</div>

<script>
	const path = '<c:out value="${path}"/>';
	let noteIdx = ${noteIdForJs};
	const parentNoteIdx = ${parentIdForJs};
	const listLink = '<c:out value="${path}/board/list${criteria.listLink}"/>';
	const myHolder = document.querySelector("#editorjs");
	console.log('▶ 내부 path:', path, 'noteIdx:', noteIdx);
</script>
<script>
    window.path = '<c:out value="${path}"/>';
    window.noteIdx = ${noteIdForJs};
    window.csrfHeaderName = "${_csrf.headerName}";
    window.csrfToken = "${_csrf.token}";
</script>
<script>
$(document).ready(function() {
    let autoSaveTimer; // 자동 저장을 위한 타이머 변수
    const DEBOUNCE_DELAY = 2000; // 2초 (2000ms)
  // Editor.js 인스턴스 생성
    const editor = new EditorJS({
        holder: myHolder,
        placeholder: '내용을 입력하세요...',
        data: { blocks: [] },
        // 추가 플러그인들 설정
        tools: {
            page: PageTool,
			header: {
                class: Header,
                config: {
                    placeholder: 'Header',
                    levels: [1, 2, 3, 4, 5, 6],
                    defaultLevel: 3,
                },
                shortcut: 'CMD+SHIFT+H',
            },
            linkTool: {
                class: LinkTool,
                config: {
                    header: '', // get request header 선택사항
                    //백엔드 데이터 가져오기( Cross Origin에 주의)
                    endpoint: `\${path}/note/new`,
                }
            },
            raw: {
                class: RawTool,
                config: {
                    placeholder: "placeholder"
                }
            },
            simImg: {
                class: SimpleImage
                //No Config
            },
            image: {
                class: ImageTool,
                config: {
                endpoints: {
                        byFile: noteIdx > 0 ? `\${path}/api/images/upload/note/\${noteIdx}` : undefined,
                        byUrl:  noteIdx > 0 ? `\${path}/api/images/upload-by-url/note/\${noteIdx}` : undefined
                },
                additionalRequestData: {
                    '${_csrf.parameterName}': '${_csrf.token}'
                },
				buttonContent: "파일 업로드",
				actions: [
					{
						name: 'new_button',
						icon: '<svg>...</svg>',
						title: 'New Button',
						toggle: true,
						action: (name) => {
							// alert(`${name} button clicked`);
						}
					}
				]
			}
		},
		checklist: {
			class: Checklist,
			inlineToolbar: true
			// No Config
		},
		list: {
			class: EditorjsList,
			inlineToolbar: true,
			config: {
				defaultStyle: 'unordered'
			}
		},
		embed: {
			class: Embed,
			inlineToolbar: true,
			config: {
				services: {
					youtube: true,
					coub: true
				}
			}
		},
		table: {
			class: Table,
			inlineToolbar: true,
			config: {
				rows: 2,
				cols: 3,
				withHeadings: true
			},
		},
		nestedlist: {
			class: NestedList,
			inlineToolbar: true,
			config: {
				defaultStyle: 'unordered'
			},
		},
		delimiter: {
			class: Delimiter
			//No Config
		},
		warning: {
			class: Warning,
			inlineToolbar: true,
			shortcut: 'CMD+SHIFT+W',
			config: {
				titlePlaceholder: 'Title',
				messagePlaceholder: 'Message',
			},
		},
		code: {
			class: CodeTool,
			placeholder: "코드를 작성해주세요..."
		},
		attaches: {
			class: AttachesTool,
			config: {
				/**Custom uploader*/
                    uploader: {
                        /**
                         * Upload file to the server and return an uploaded image data
                         * @param {File} file - file selected from the device or pasted by drag-n-drop
                         * @return {Promise.<{success, file: {url}}>}
                         */
                        uploadByFile(file) {
                            // your own uploading logic here
                            return MyAjax.upload(file).then((response) => {
                            alert('uploaded');
                                return {
                                    success: 1,
                                    file: {
                                        url: response.fileurl
                                        // any data you want
                                        // for example: name, size, title
                                    }
                                };
                            });
                        },
                    }
                }
            },
            marker: {
                class: Marker,
                shortcut: 'CMD+SHIFT+M',
                //No Config
            },
            inlineCode: {
                class: InlineCode,
                shortcut: 'CMD+SHIFT+C',
                //No Config
            },
            underline: {
                class: Underline
                //No Config
            },
            alert: {
                class: Alert,
                inlineToolbar: true,
                shortcut: 'CMD+SHIFT+A',
                config: {
                    defaultType: 'primary',
                    messagePlaceholder: 'Enter something'
                }

            },
            code2 : {
                class: editorjsCodeflask,
            }
        }
    });

	editor.isReady.then(() => {
		console.log('Editor.js is ready.');
		new DragDrop(editor);
		
		// 기존 노트 데이터 로드
		if (noteIdx > 0) {
	        $.ajax({
	            url: `\${path}/api/note/\${noteIdx}`,
	            type: 'GET',
	            dataType: 'json',
	            success: function(response) {
	                let note = response.noteDetail;
	                $('#note-title').val(note.title);
	                $('#note-category-select').val(note.categoryIdx);
	               
	              	// DB에서 가져온 JSON 데이터로 에디터 내용 채우기
	                const contentData = note.text ? JSON.parse(note.text) : { blocks: [] };
	                editor.isReady.then(() => {
	                    editor.render(contentData);
	                });
					
					renderChildNotes(note.childNoteList);
	            },
	            error: () => alert('게시글 로딩 실패')
	        });
	    }
		$('#editorjs').on('change', triggerAutoSave);
		$('#editorjs').on('input', triggerAutoSave);
	}).catch(error => {
        console.error("Editor.js 초기화 또는 준비 과정에서 에러 발생:", error);
    });

	// 하위 노트 목록 렌더링
	function renderChildNotes(childNotes) {
	    const container = $('#child-notes-list');
	    container.empty(); // 기존 목록 비우기
	
	    if (!childNotes || childNotes.length === 0) {
	        container.html('<p>No pages inside.</p>');
	        return;
	    }
	
	    let html = '<ul id="child-note-list-ul">';
	    childNotes.forEach(child => {
	        html += `<li data-note-id=\${child.noteIdx}><a href=\${path}/note/\${child.noteIdx}>\${child.title}</a></li>`;
	    });
	    html += '</ul>';
	    container.html(html);
	    
	    activateDragAndDrop();
	}
	
	function activateDragAndDrop() {
	    $('#child-note-list-ul').sortable({
	        update: function(event, ui) {
	            const newOrder = $(this).sortable('toArray', { attribute: 'data-note-id' });
	            
	            $.ajax({
	                url: `${path}/api/note/reorder`,
	                type: 'PUT',
	                contentType: 'application/json',
	                data: JSON.stringify(newOrder),
	                beforeSend: xhr => xhr.setRequestHeader(csrfHeader, csrfToken),
	                success: () => console.log("페이지 순서가 성공적으로 저장되었습니다."),
	                error: () => alert("순서 저장에 실패했습니다.")
	            });
	        }
	    }).disableSelection();
	}

    // 자동 저장 타이머
    function triggerAutoSave() {
    	clearTimeout(autoSaveTimer);
        autoSaveTimer = setTimeout(() => saveNoteData(true), DEBOUNCE_DELAY);
		console.log('자동저장');
    }

    const csrfToken  = $('meta[name="_csrf"]').attr('content');
    const csrfHeader = $('meta[name="_csrf_header"]').attr('content');

    // 데이터 저장 : 글쓰기/수정
    function saveNoteData(isAutoSave = false) {
    editor.save().then((outputData) => {
            const saveData = {
                    title: $('#note-title').val(),
                    contentJson: JSON.stringify(outputData),
                    categoryIdx: $('#note-category-select').val(),
                    parentNoteIdx: (parentNoteIdx > 0) ? parentNoteIdx : null
            };

            const isUpdate = noteIdx > 0;
            const apiUrl = isUpdate ? `\${path}/api/note/\${noteIdx}` : `\${path}/api/note`;
            const apiMethod = isUpdate ? 'PUT' : 'POST';
            console.log('▶ isUpdate, method, url =', isUpdate, apiMethod, apiUrl);

            $.ajax({
                url: apiUrl,
                type: apiMethod,
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                headers: { [csrfHeader]: csrfToken },
                data: JSON.stringify(saveData),
                success: function(result) {
                    if (!isUpdate) {
                        if (!isAutoSave) {
	                        alert('저장되었습니다. 이미지 업로드가 활성화됩니다.');
	                        location.href = `\${path}/note/\${result.noteIdx}`;
                        } else {
	                        noteIdx = result.noteIdx;
	                        history.pushState({id: noteIdx}, '', `\${path}/note/\${noteIdx}`);
                        }
                    } else {

                    if (!isAutoSave) alert('수정되었습니다.');
                        console.log('자동 저장 완료 (수정)');
                    }
                },

                error: function(jqXHR, textStatus, errorThrown) {
                    if (!isAutoSave) alert('저장에 실패했습니다.');
                    console.error('▶ AJAX error:', textStatus, errorThrown);
                    console.error('▶ ResponseText:', jqXHR.responseText);
                }
            });
        });
    }



    // --- 이벤트 핸들러 ---
    $('#save-btn').on('click', () => saveNoteData(false)); // 수동 저장은 false
    $('#list-btn').on('click', () => location.href = listLink);
    $('#note-title').on('input', triggerAutoSave);
	// $('#editorjs').on('change', triggerAutoSave);
    
    $('#delete-btn').on('click', function() {
        if (confirm("정말로 이 노트를 삭제하시겠습니까?")) {
            $.ajax({
                url: `\${path}/api/note/\${noteIdx}`,
                type: 'DELETE',
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(csrfHeader, csrfToken);
                },
                success: function() {
                    alert("노트가 삭제되었습니다.");
                    location.href = `\${path}/board/list`; // 목록 페이지로 이동
                },
                error: function() {
                    alert("삭제에 실패했습니다.");
                }
            });
        }
    });

	/* 하위 페이지 추가 */
	$('#add-child-note-btn').on('click', function() {
	    // 현재 note의 ID를 부모 ID로 하여 새 글쓰기 페이지로 이동
	    location.href = `\${path}/note/new?parentNoteIdx=\${noteIdx}`;
	});

});
</script>