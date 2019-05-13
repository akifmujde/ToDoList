package com.akifmuje.todolisttask.services;


import com.akifmuje.todolisttask.models.ToDoList;
import com.akifmuje.todolisttask.repositores.ToDoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ToDoListService implements IToDoListService{

    @Autowired
    private ToDoListRepository repository;

    @Override
    public List<ToDoList> getToDoLists(int id) {

        List<ToDoList> lists = repository.getToDoLists(id);
        return lists;
    }

    @Override
    public List<ToDoList> getListFromId(int id) {

        return repository.getListFromId(id);
    }

    @Override
    public List<ToDoList> getListFromMailAndName(String name, int user_id) {
        return repository.getListFromMailAndName(name,user_id);
    }

    @Transactional
    @Override
    public void deleteToDoList(int id) {

        repository.deleteToDoList(id);
    }

    @Override
    public void save(ToDoList model) {

        repository.save(model);
    }
}
