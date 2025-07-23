package com.vibesync.workspace.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import com.vibesync.workspace.domain.TodoVO;

@Mapper
public interface TodoMapper {
    List<TodoVO> getTodoListByUser(int acIdx);
    int addTodo(TodoVO todo);
    int updateTodoStatus(int todoIdx, int status);
    int deleteTodo(int todoIdx);
    int updateTodo(TodoVO todo);
} 