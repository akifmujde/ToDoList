package com.akifmuje.todolisttask.services;

import com.akifmuje.todolisttask.models.ToDoItem;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface IToDoItemService {

    List<ToDoItem> getToDoItemFromId(@Param("todo_item_id") int todo_item_id);

    List<ToDoItem> orderToDoItem(int list_id, String column_name, String order_type);

    void deleteItem(@Param("id") int id);

    void deleteListItems(@Param("list_id") int list_id);

    void updateStatus(@Param("status_id") int status_id, @Param("updated_date") Date updated_date);

    void save(ToDoItem model);
}
