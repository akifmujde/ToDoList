package com.akifmuje.todolisttask.services;

import com.akifmuje.todolisttask.models.ToDoItem;
import com.akifmuje.todolisttask.repositores.ToDoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class ToDoItemService implements IToDoItemService{

    @Autowired
    private ToDoItemRepository repository;

    @Override
    public List<ToDoItem> getToDoItemFromId(int todo_item_id) { return repository.getToDoItemFromId(todo_item_id); }

    @Override
    public List<ToDoItem> filterItem(int list_id, int status_id, String name) {
        return repository.filterItem(list_id,status_id,name);
    }

    @Override
    public List<ToDoItem> getNotDependencyItems(int still_waiting_id) {
        return repository.getNotDependencyItems(still_waiting_id);
    }

    @Override
    public List<ToDoItem> getToDoItemsFromList_Id(int list_id) {
        return repository.getToDoItemsFromList_Id(list_id);
    }

    @Transactional
    @Override
    public void deleteItem(int id) { repository.deleteItem(id);}

    @Transactional
    @Override
    public void deleteListItems(int list_id) {
        repository.deleteListItems(list_id);
    }

    @Transactional
    @Override
    public void updateStatus(int status_id, Date updated_date, int todo_item_id) {
        repository.updateStatus(status_id,updated_date,todo_item_id);
    }

    @Override
    public void save(ToDoItem model) {
        repository.save(model);
    }
}
