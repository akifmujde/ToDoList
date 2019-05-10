package com.akifmuje.todolisttask.services;

import com.akifmuje.todolisttask.models.ToDoItem;
import com.akifmuje.todolisttask.repositores.ToDoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
    public List<ToDoItem> orderToDoItem(int id, String column_name, String order_type) {
        return repository.orderToDoItem(id,column_name,order_type);
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
    public void updateStatus(int status_id, Date updated_date) { repository.updateStatus(status_id,updated_date);}

    @Override
    public void save(ToDoItem model) {
        repository.save(model);
    }
}
