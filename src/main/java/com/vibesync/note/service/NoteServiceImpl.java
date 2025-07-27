package com.vibesync.note.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vibesync.follow.domain.FollowVO;
import com.vibesync.follow.mapper.FollowMapper;
import com.vibesync.note.domain.BoardEditRequestDTO;
import com.vibesync.note.domain.NoteDetailDTO;
import com.vibesync.note.domain.NoteSaveRequestDTO;
import com.vibesync.note.domain.NoteVO;
import com.vibesync.note.domain.NoteViewDTO;
import com.vibesync.note.mapper.NoteMapper;
import com.vibesync.security.domain.CustomUser;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class NoteServiceImpl implements NoteService {
	
	@Autowired
    private NoteMapper noteMapper;
    @Autowired
    private FollowMapper followMapper;
    // @Autowired
    // private LikeMapper likeMapper;

	@Override
	public NoteDetailDTO findNoteByNoteIdx(int noteIdx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int save(NoteSaveRequestDTO dto, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int edit(BoardEditRequestDTO dto, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public NoteViewDTO getNoteViewData(int noteIdx, CustomUser currentUser) {
        NoteDetailDTO noteDetail = noteMapper.findNoteDetailByIdx(noteIdx);
        
        boolean isFollowing = false;
        boolean isLiking = false;
        
        if (currentUser != null) {
            int currentUserId = currentUser.getAcIdx();
            int authorId = noteDetail.getMember().getAcIdx();
            
            if (currentUserId != authorId) {
            	FollowVO vo = FollowVO.builder().followerAcIdx(currentUserId).followedAcIdx(authorId).build();
                isFollowing = followMapper.checkFollowStatus(vo) > 0;
            }
            
            // isLiking = likeMapper.checkLike(currentUserId, noteIdx) > 0;
        }
        
        return NoteViewDTO.builder()
                .noteDetail(noteDetail)
                .following(isFollowing)
                .liking(isLiking)
                .build();
        
	}

	@Override
	public int saveNewNote(NoteSaveRequestDTO saveDTO, int acIdx) {
	     // 1. DTO를 DB에 저장할 VO로 변환
        NoteVO note = new NoteVO();
        note.setTitle(saveDTO.getTitle());
        note.setText(saveDTO.getContentJson()); // text 컬럼에 JSON 저장
        note.setCategoryIdx(saveDTO.getCategoryIdx());
        note.setAcIdx(acIdx);
        
        // 2. 완성된 VO를 Mapper에 전달하여 DB에 저장
        noteMapper.insert(note);
        
        // 3. 생성된 note_idx를 반환
        return note.getNoteIdx();
        
	}
	
}
