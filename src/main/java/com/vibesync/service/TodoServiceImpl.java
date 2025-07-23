package com.vibesync.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vibesync.domain.TodoVO;
import com.vibesync.mapper.TodoMapper;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class TodoServiceImpl implements TodoService {
    
    @Autowired
    private TodoMapper todoMapper;
    
    @Override
    @Transactional(readOnly = true)
    public List<TodoVO> getTodoListByUser(int acIdx) {
        log.info("사용자별 할 일 목록 조회. 사용자 ID: " + acIdx);
        
        List<TodoVO> todoList = todoMapper.getTodoListByUser(acIdx);
        log.debug("조회된 할 일 개수: " + todoList.size());
        
        return todoList;
    }
    
    @Override
    @Transactional
    public TodoVO addTodo(TodoVO todo) {
        log.info("할 일 추가. 사용자 ID: " + todo.getAcIdx() + ", 내용: " + todo.getText());
        
        todoMapper.addTodo(todo);
        
        // MyBatis의 <insert> 태그에 <selectKey>를 사용하여
        // 생성된 todoIdx가 자동으로 todo 객체에 설정됨
        log.debug("생성된 할 일 ID: " + todo.getTodoIdx());
        
        return todo;
    }
    
    @Override
    @Transactional
    public void updateTodoStatus(int todoIdx, boolean completed) {
        log.info("할 일 상태 업데이트. 할 일 ID: " + todoIdx + ", 완료 상태: " + completed);
        
        todoMapper.updateTodoStatus(todoIdx, completed);
    }
    
    @Override
    @Transactional
    public void deleteTodo(int todoIdx) {
        log.info("할 일 삭제. 할 일 ID: " + todoIdx);
        
        todoMapper.deleteTodo(todoIdx);
    }
    
    @Override
    @Transactional
    public void updateTodo(TodoVO todo) {
        log.info("할 일 수정. 할 일 ID: " + todo.getTodoIdx() + ", 내용: " + todo.getText());
        
        todoMapper.updateTodo(todo);
    }
} 