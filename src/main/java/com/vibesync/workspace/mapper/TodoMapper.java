package com.vibesync.workspace.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.vibesync.workspace.domain.TodoVO;

@Mapper
public interface TodoMapper {
    List<TodoVO> getTodoListByUser(int acIdx);
    int addTodo(TodoVO todo);
    void updateTodoStatus(@Param("todoIdx") int todoIdx, @Param("status") int status);
    void deleteTodo(int todoIdx);
    int updateTodo(TodoVO todo);
} 