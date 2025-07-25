package com.vibesync.board.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vibesync.board.domain.BoardWriteDTO;
import com.vibesync.board.domain.NoteDetailDTO;
import com.vibesync.board.domain.NoteListDTO;
import com.vibesync.board.domain.NoteVO;
import com.vibesync.board.mapper.BoardNoteMapper;
import com.vibesync.common.domain.Criteria;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class NoteServiceImpl implements NoteService {
	
	@Autowired
	private BoardNoteMapper noteMapper;

	@Override
	public List<NoteListDTO> findAllNotesWithPaging(Criteria criteria) {
		log.info("페이징 처리된 게시글 목록 조회 요청. 조건: " + criteria);
		
		log.debug("NoteMapper.selectWithPaging() 실행. 파라미터: " + criteria);
		List<NoteListDTO> noteList = this.noteMapper.selectWithPaging(criteria);
		
		log.debug("현재 페이지에서 조회된 게시글 개수: " + noteList.size());
		log.info("현재 페이지에서 조회된 게시글 : " + noteList);
		
		return noteList;
	}

	@Override
	public int getTotalCount(Criteria criteria) {
		log.info("조건에 맞는 전체 게시글 개수 조회. 조건: " + criteria);
		
		return this.noteMapper.selectTotalCount(criteria);
	}

	@Override
	public NoteDetailDTO findNoteByNoteIdx(int noteIdx) {
		log.info("게시글 상세보기. 게시글 번호: " + noteIdx);
		
		return this.noteMapper.selectByNoteIdx(noteIdx);
	}

	@Override
	@Transactional
	public int save(BoardWriteDTO dto, HttpServletRequest request) {
		NoteVO note = dto.getNote();
		log.info("게시글 작성. 게시글 제목: " + note.getTitle());
		
		int noteIdx = this.noteMapper.selectNextNoteIdx();
		note.setNoteIdx(noteIdx);
		
		// 썸네일 및 Summernote 이미지 처리
		String imgDirPath = "/sources/board/noteImg/" + noteIdx;
		
        String processedContent = note.getText();
        String base64Images = dto.getImages();
        
        String thumbnailBase64 = dto.getThumbnailBase64();
        String thumbnailExt = dto.getThumbnailExt();
        
        String saveDirPath = request.getServletContext().getRealPath(imgDirPath);
        File saveDir = new File(saveDirPath);
        if (!saveDir.exists()) saveDir.mkdirs();

        // 썸네일 이미지 저장
        if (thumbnailBase64 != null && !thumbnailBase64.isEmpty() && thumbnailExt != null) {
            try {
                String[] parts = thumbnailBase64.split(",");
                if (parts.length == 2) {
                    byte[] imageBytes = Base64.getDecoder().decode(parts[1]);
                    String thumbnailFileName = "thumnail." + thumbnailExt;
                    File outFile = new File(saveDir, thumbnailFileName);
                    try (FileOutputStream fos = new FileOutputStream(outFile)) {
                        fos.write(imageBytes);
                    }
                    note.setTitleimg(thumbnailFileName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        // Summernote 본문 이미지 저장
        List<String> dbNoteImgList = new ArrayList<>();
        if (base64Images != null && !base64Images.isEmpty()) {
            String[] imageDataArray = base64Images.split("\\|");
            int i = 1;
            for (String base64Data : imageDataArray) {
                if (base64Data == null || base64Data.isEmpty()) continue;
                try {
                    String[] parts = base64Data.split(",");
                    if (parts.length != 2 || !parts[0].startsWith("data:image/")) continue;

                    String imageType = parts[0].substring("data:image/".length(), parts[0].indexOf(";base64"));
                    byte[] imageBytes = Base64.getDecoder().decode(parts[1]);
                    String fileName = String.format("image%d.%s", i++, ".", imageType);
                    File outFile = new File(saveDir, fileName);
                    try (FileOutputStream fos = new FileOutputStream(outFile)) {
                        fos.write(imageBytes);
                    }
                    dbNoteImgList.add(fileName);
                    
                    // content 내의 base64 src를 실제 경로로 교체
                    processedContent = processedContent.replace(base64Data, "${pageContext.request.contextPath}" + imgDirPath + "/" + fileName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        String imagesForDB = String.join("|", dbNoteImgList);
        note.setImg(imagesForDB);

        return this.noteMapper.insert(note);
        
	}
	
}
