package com.vibesync.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.vibesync.domain.TodoVO;

@Mapper
public interface TodoMapper {
    List<TodoVO> getTodoListByUser(int acIdx);
    void addTodo(TodoVO todo);
    void updateTodoStatus(int todoIdx, boolean completed);
    void deleteTodo(int todoIdx);
    void updateTodo(TodoVO todo);
} 