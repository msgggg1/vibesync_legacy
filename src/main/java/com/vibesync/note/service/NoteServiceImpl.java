package com.vibesync.note.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vibesync.follow.domain.FollowVO;
import com.vibesync.follow.mapper.FollowMapper;
import com.vibesync.note.domain.ChildNoteListDTO;
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
	public NoteViewDTO getNoteViewData(int noteIdx, CustomUser currentUser) {
        NoteDetailDTO noteDetail = noteMapper.findNoteDetailByIdx(noteIdx);
        List<ChildNoteListDTO> childNoteList = noteMapper.findChildNotesByParentIdx(noteIdx);
        System.out.println("> childNoteList : " + childNoteList);
        noteDetail.setChildNoteList(childNoteList);
        
        boolean isFollowing = false;
        boolean isLiking = false;
        boolean isAuthor = false;
        
        if (currentUser != null) {
            int currentUserId = currentUser.getAcIdx();
            int authorId = noteDetail.getMember().getAcIdx();
            
            if (currentUserId != authorId) {
            	FollowVO vo = FollowVO.builder().followerAcIdx(currentUserId).followedAcIdx(authorId).build();
                isFollowing = followMapper.checkFollowStatus(vo) > 0;
            } else {
            	isAuthor = true;
            }
            // isLiking = likeMapper.checkLike(currentUserId, noteIdx) > 0;
        }
        
        return NoteViewDTO.builder()
                .noteDetail(noteDetail)
                .following(isFollowing)
                .liking(isLiking)
                .author(isAuthor)
                .build();
        
	}

	@Override
	@Transactional
	public Map<String, Object> saveNewNote(NoteSaveRequestDTO saveDTO, int acIdx) {
		int noteIdx = this.noteMapper.selectNextNoteIdx();
		
		NoteVO note = new NoteVO();
		note.setNoteIdx(noteIdx);
        note.setTitle(saveDTO.getTitle());
        note.setText(saveDTO.getContentJson());
        note.setCategoryIdx(saveDTO.getCategoryIdx());
        note.setAcIdx(acIdx);
        note.setParentNoteIdx(saveDTO.getParentNoteIdx());
        if (saveDTO.getParentNoteIdx() != null) {
            // 자식 노트일 경우: 부모의 마지막 자식 순서 + 1
            int lastOrder = noteMapper.getLastDisplayOrder(saveDTO.getParentNoteIdx());
            note.setDisplayOrder(lastOrder + 1);
        } else {
            // 최상위 노트일 경우: 기본값 1
            note.setDisplayOrder(1);
        }
        
        noteMapper.insert(note);
        
        Map<String, Object> result = new HashMap<>();
        result.put("noteIdx", note.getNoteIdx());
        result.put("title", note.getTitle());
        
        System.out.println("> result : " + result);
        
        return result;
	}

	@Override
	@Transactional
	public void updateNote(int noteIdx, NoteSaveRequestDTO saveDTO) {
        NoteVO note = new NoteVO();
        note.setNoteIdx(noteIdx);
        note.setTitle(saveDTO.getTitle());
        note.setText(saveDTO.getContentJson());
        note.setCategoryIdx(saveDTO.getCategoryIdx());
        
        noteMapper.update(note);
	}

	@Override
    @Transactional
    public void deleteNote(int noteIdx) {
        noteMapper.delete(noteIdx);
    }

	@Override
    @Transactional
    public void updateDisplayOrder(List<Integer> orderedNoteIds) {
        for (int i = 0; i < orderedNoteIds.size(); i++) {
            int noteIdx = orderedNoteIds.get(i);
            int displayOrder = i + 1; // 순서는 1부터 시작
            noteMapper.updateDisplayOrder(noteIdx, displayOrder);
        }
    }
	
}
