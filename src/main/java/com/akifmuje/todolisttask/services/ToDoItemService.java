package com.akifmuje.todolisttask.services;

import com.akifmuje.todolisttask.models.ToDoItem;
import com.akifmuje.todolisttask.repositores.ToDoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class ToDoItemService implements IToDoItemService{

    @Autowired
    private ToDoItemRepository repository;

    @PersistenceContext
    EntityManager em;

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

    @Override
    public List<ToDoItem> gelAllDependenciesOfItem(int still_waiting_id) {
        return repository.gelAllDependenciesOfItem(still_waiting_id);
    }

    @Override
    public List<ToDoItem> orderToDoItem(int list_id, String c_name, String order_type) {

        String sqlQueryWithOrderBy = "select * from todo_item where todo_list_id = "+ list_id + " order by " + c_name + " " + order_type;
        Query order = em.createNativeQuery(sqlQueryWithOrderBy,ToDoItem.class);

        return order.getResultList();
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
