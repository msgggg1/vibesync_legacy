package com.vibesync.board.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vibesync.board.domain.BoardEditRequestDTO;
import com.vibesync.board.domain.BoardWriteRequestDTO;
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
	
	/**
	 * 텍스트를 이미지 파일로 생성하는 헬퍼 메소드 : 썸네일이 없는 노트용
	 * @param text 이미지에 쓸 텍스트
	 * @param outFile 저장할 파일 객체 (경로와 이름 포함)
	 * @throws IOException
	 */
	private void generateImageFromText(String text, File outFile) throws IOException {
	    int width = 400; // 이미지 가로 크기
	    int height = 300; // 이미지 세로 크기

	    // 메모리에 이미지 공간 생성
	    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    Graphics2D g2d = image.createGraphics();

	    // 렌더링 힌트 설정 (글자 부드럽게)
	    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

	    // 배경 채우기 (흰색)
	    g2d.setColor(Color.WHITE);
	    g2d.fillRect(0, 0, width, height);

	    // 텍스트 설정 (검은색)
	    g2d.setColor(Color.BLACK);
	    g2d.setFont(new Font("Noto Sans KR", Font.BOLD, 24)); // 폰트는 시스템에 설치된 폰트 사용

	    // 텍스트를 이미지 중앙에 배치하기 위한 계산
	    FontMetrics fm = g2d.getFontMetrics();
	    int x = (width - fm.stringWidth(text)) / 2;
	    int y = (fm.getAscent() + (height - (fm.getAscent() + fm.getDescent())) / 2);

	    // 텍스트 그리기
	    g2d.drawString(text, x, y);

	    // 그래픽 컨텍스트 리소스 해제
	    g2d.dispose();

	    // 최종 이미지를 파일로 저장 (jpg 형식)
	    ImageIO.write(image, "jpg", outFile);
	}

	/**
	 * 썸네일 이미지 저장
	 * @param thumbnailBase64 텍스트 형태로 변환된 썸네일 이미지
	 * @param thumbnailExt 썸네일 이미지 확장자
	 * @param saveDir 썸네일 이미지 저장할 폴더
	 * @param note 해당 썸네일 이미지를 썸네일로 가지는 NoteVO 객체
	 */
	private void saveThumbnailImage(String thumbnailBase64, String thumbnailExt, File saveDir, NoteVO note) {
        String fileName = null;
		
		if (thumbnailBase64 != null && !thumbnailBase64.isEmpty() && thumbnailExt != null) {
            try {
                String[] parts = thumbnailBase64.split(",");
                if (parts.length == 2) {
                    byte[] imageBytes = Base64.getDecoder().decode(parts[1]);
                    String thumbnailFileName = "thumbnail." + thumbnailExt;
                    File outFile = new File(saveDir, thumbnailFileName);
                    try (FileOutputStream fos = new FileOutputStream(outFile)) {
                        fos.write(imageBytes);
                    }
                    fileName = thumbnailFileName;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
        	// 썸네일이 없을 경우, 게시글 제목으로 이미지를 자동 생성
            try {
                String thumbnailFileName = "autoThumbnail.jpg"; // 확장자는 jpg로 고정
                File autoThumbnail = new File(saveDir, thumbnailFileName);
                
                // 헬퍼 메소드 호출
                generateImageFromText(note.getTitle(), autoThumbnail);
                
                fileName = thumbnailFileName; 
                
            } catch (IOException e) {
                log.error("썸네일 자동 생성 실패", e);
            }
        }
		
		// DB에 파일 이름 저장
		note.setTitleimg(fileName);
	}
	
	/**
	 * Summernote 본문 이미지 저장
	 * @param base64Images 텍스트 형태로 변환된 본문 이미지
	 * @param note 해당 이미지 목록을 본문 이미지로 가지는 NoteVO 객체 
	 * @param saveDir 본문 이미지 저장할 폴더 File 객체
	 * @param imgDirPath 본문 이미지를 저장할 상대 경로
	 * @return 저장된 본문 이미지 파일명 목록 문자열
	 */
	private List<String> saveNoteImage(String base64Images, NoteVO note, File saveDir, String imgDirPath) {
        String content = note.getText();
		
		List<String> dbNoteImgList = new ArrayList<>();
        if (base64Images != null && !base64Images.isEmpty()) {
            String[] imageDataArray = base64Images.split("\\|");
            for (String base64Data : imageDataArray) {
                if (base64Data == null || base64Data.isEmpty()) continue;
                try {
                    String[] parts = base64Data.split(",");
                    if (parts.length != 2 || !parts[0].startsWith("data:image/")) continue;

                    String imageType = parts[0].substring("data:image/".length(), parts[0].indexOf(";base64"));
                    byte[] imageBytes = Base64.getDecoder().decode(parts[1]);
                    // UUID를 사용하여 고유한 파일 이름 생성
                    // (기존의 난수로 파일 이름을 생성하는 메서드의 경우 중복된 파일 이름이 나올 가능성 有 -> 삭제 처리)
                    String fileName = UUID.randomUUID().toString() + "." + imageType;
                    File outFile = new File(saveDir, fileName);
                    try (FileOutputStream fos = new FileOutputStream(outFile)) {
                        fos.write(imageBytes);
                    }
                    dbNoteImgList.add(fileName);
                    
                    // content 내의 base64 src를 실제 경로로 교체
                    content = content.replace(base64Data, imgDirPath + "/" + fileName);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        note.setText(content);
        
        return dbNoteImgList;
	}

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
	public int save(BoardWriteRequestDTO dto, HttpServletRequest request) {
		NoteVO note = dto.getNote();
		log.info("게시글 작성. 게시글 제목: " + note.getTitle());
		
		int noteIdx = this.noteMapper.selectNextNoteIdx();
		note.setNoteIdx(noteIdx);
		
		// 썸네일 및 Summernote 이미지 처리
		
		// noteIdx로 noteImg의 하위 폴더를 생성
		// 추후 이미지 파일을 지우기 위해 for문으로 noteImg 폴더 전체를 뒤질 필요 없이 noteIdx로 생성된 폴더 하나만 삭제 처리!!
		String imgDirPath = "/sources/board/noteImg/" + noteIdx;
		
        String base64Images = dto.getImages();
        String thumbnailBase64 = dto.getThumbnailBase64();
        String thumbnailExt = dto.getThumbnailExt();
        
        String saveDirPath = request.getServletContext().getRealPath(imgDirPath);
        File saveDir = new File(saveDirPath);
        if (!saveDir.exists()) saveDir.mkdirs();

        // 썸네일 이미지 저장
        saveThumbnailImage(thumbnailBase64, thumbnailExt, saveDir, note);
        
        // Summernote 본문 이미지 저장
        List<String> dbNoteImgList = saveNoteImage(base64Images, note, saveDir, imgDirPath);
        String imagesForDB = String.join("|", dbNoteImgList);
        note.setImg(imagesForDB);

        return this.noteMapper.insert(note);
        
	}
	
	@Override
	@Transactional
	public int edit(BoardEditRequestDTO dto, HttpServletRequest request) {
		NoteVO updatedNote = dto.getNote();
		log.info("게시글 수정. 게시글 제목: " + updatedNote.getTitle());
		
		String imgDirPath = "/sources/upload/board/noteImg/" + updatedNote.getNoteIdx();
		String saveDirPath = request.getServletContext().getRealPath(imgDirPath);
		
		// 기존 이미지 파일명 목록
		String originalImages = dto.getOriginalImages();
		List<String> oldImages = new ArrayList<>();
	    if (originalImages != null && !originalImages.isEmpty()) {
	        oldImages.addAll(List.of(originalImages.split("\\|")));
	    }
		
	    // 현재 이미지 파일명 목록
	    List<String> currentImages = new ArrayList<>();
	    String existingImages = dto.getExistingImages();
	    if (existingImages != null && !existingImages.isEmpty()){
	    	currentImages.addAll(List.of(existingImages.split("\\|")));
	    }
		
		// 기존 목록에는 있지만, 현재 목록에는 없는 이미지를 찾아 삭제
	    oldImages.removeAll(currentImages);
	    
	    // 실제 파일들을 삭제
	    for (String fileNameToDelete : oldImages) {
	        File file = new File(saveDirPath, fileNameToDelete);
	        if (file.exists()) {
	            file.delete();
	        }
	    }
		
		// 새로 추가된 썸네일/이미지 처리
		String base64Images = dto.getNewImages();
		String thumbnailBase64 = dto.getThumbnailBase64();
		String thumbnailExt = dto.getThumbnailExt();
		
		File saveDir = new File(saveDirPath);
		if (!saveDir.exists()) saveDir.mkdirs();
		
        // 썸네일 이미지 저장
		saveThumbnailImage(thumbnailBase64, thumbnailExt, saveDir, updatedNote);
		
		// Summernote 본문에 추가된 이미지 저장
		List<String> dbNoteImgList = saveNoteImage(base64Images, updatedNote, saveDir, imgDirPath);
		
		// 최종적으로 DB의 img 컬럼에는 existingImages와 새로 저장된 이미지 파일명들을 합쳐서 저장
	    List<String> finalImageList = new ArrayList<>(currentImages);
	    finalImageList.addAll(dbNoteImgList);
	    updatedNote.setImg(String.join("|", finalImageList));
		
		return this.noteMapper.update(updatedNote);
		
	}
	
}
