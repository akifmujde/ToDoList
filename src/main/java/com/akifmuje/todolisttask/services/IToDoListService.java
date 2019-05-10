package com.akifmuje.todolisttask.services;

import com.akifmuje.todolisttask.models.ToDoList;

import java.util.List;

public interface IToDoListService {

    List<ToDoList> getToDoLists(int user_id);
    void deleteToDoList(int id);
    List<ToDoList> getListFromId(int id);

    void save(ToDoList model);
}
