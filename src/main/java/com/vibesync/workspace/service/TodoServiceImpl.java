package com.vibesync.workspace.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vibesync.workspace.domain.TodoVO;
import com.vibesync.workspace.mapper.TodoMapper;

@Service
public class TodoServiceImpl implements TodoService {
    
    @Autowired
    private TodoMapper todoMapper;
    
    @Override
    public List<TodoVO> getTodoListByUser(int acIdx) {
        return todoMapper.getTodoListByUser(acIdx);
    }
    
    @Override
    public boolean addTodo(TodoVO todo) {
        return todoMapper.addTodo(todo) > 0;
    }
    
    @Override
    public boolean updateTodoStatus(int todoIdx, boolean isCompleted) {
        return todoMapper.updateTodoStatus(todoIdx, isCompleted ? 1 : 0) > 0;
    }
    
    @Override
    public boolean deleteTodo(int todoIdx) {
        return todoMapper.deleteTodo(todoIdx) > 0;
    }
    
    @Override
    public boolean updateTodo(TodoVO todo) {
        return todoMapper.updateTodo(todo) > 0;
    }
} 