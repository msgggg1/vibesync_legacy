// 위치: /resources/js/editor-tools/page-tool.js

class PageTool {
    constructor({ api }) {
        this.api = api;
        // this.button = null;
        this.input = document.createElement('input');
        this.input.type = 'text';
        this.input.placeholder = '새 페이지 제목을 입력하고 Enter를 누르세요...';
        this.input.className = 'page-tool-input';
        
        this.input.addEventListener('keydown', (event) => {
            if (event.key === 'Enter') {
                event.preventDefault();
                this.createPage(event.target.value);
            }
        });
    }

    // '+' 버튼 메뉴에 표시될 아이콘과 이름
    static get toolbox() {
        return {
            title: 'Page',
            icon: '<svg width="17" height="17" viewBox="0 0 24 24"><path d="M14 2H6c-1.1 0-1.99.9-1.99 2L4 20c0 1.1.89 2 1.99 2H18c1.1 0 2-.9 2-2V8l-6-6zM6 20V4h7v5h5v11H6z"/></svg>'
        };
    }

    // 에디터에 블록이 추가될 때 호출되는 함수
    render() {
        return this.input;
    }

    // 새 페이지를 생성하는 AJAX 함수
    createPage(title) {
        // 현재 노트의 ID를 부모 ID로 사용
        const parentNoteIdx = window.noteIdx;
        
        const self = this;
        const currentIndex = this.api.blocks.getCurrentBlockIndex();
        
        $.ajax({
            url: `${window.path}/api/note`,
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                title: title,
                contentJson: '{"blocks":[]}', // 빈 내용
                categoryIdx: $('#note-category-select').val(),
                parentNoteIdx: parentNoteIdx
            }),
            beforeSend: function(xhr) {
                if (csrfHeaderName && csrfToken) {
                    xhr.setRequestHeader(csrfHeaderName, csrfToken);
                }
            },
            success: (response) => {
                // 성공 시, 현재 블록을 LinkTool 블록으로 교체
                const $xml = $(response);
    
			    // find()를 사용해 XML 태그 안의 텍스트를 가져옵니다.
			    const nId = $xml.find('noteIdx').text();
			    const nTitle = $xml.find('title').text();
                self.api.blocks.delete(currentIndex);
                this.api.blocks.insert(
                    'linkTool',
                    {
                        link: `${window.path + '/note/' + nId}`,
                        meta: {
                            title: nTitle
                        }
                    },
                    {},
                    currentIndex,
                    true
                );
            },
            error: () => alert('페이지 생성에 실패했습니다.')
        });
    }

    // 이 블록이 저장될 때 호출되는 함수
    save() {
        return {};
    }
}