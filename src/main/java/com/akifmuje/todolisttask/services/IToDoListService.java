package com.akifmuje.todolisttask.services;

import com.akifmuje.todolisttask.models.ToDoList;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IToDoListService {

    List<ToDoList> getToDoLists(int user_id);
    void deleteToDoList(int id);
    List<ToDoList> getListFromId(int id);
    List<ToDoList> getListFromMailAndName(@Param("name") String name, @Param("user_id") int user_id);

    void save(ToDoList model);
}
