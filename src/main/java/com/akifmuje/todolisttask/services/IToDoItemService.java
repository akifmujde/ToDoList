package com.akifmuje.todolisttask.services;

import com.akifmuje.todolisttask.models.ToDoItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface IToDoItemService {

    List<ToDoItem> getToDoItemFromId(@Param("todo_item_id") int todo_item_id);

    String QUERY = "select item from ToDoItem item where " +
            "item.todoList.id = ?1 and " +
            "item.status.id = ?2 and " +
            "item.name like CONCAT('%',CONCAT(?3, '%'))";
    @Query(value = QUERY)
    List<ToDoItem> filterItem(int list_id, int status_id, String name);

    List<ToDoItem> getNotDependencyItems(@Param("still_waiting_id") int still_waiting_id);

    List<ToDoItem> getToDoItemsFromList_Id(@Param("list_id") int list_id);

    List<ToDoItem> gelAllDependenciesOfItem(@Param("still_waiting_id") int still_waiting_id);

    List<ToDoItem> orderToDoItem(int list_id, String c_name, String order_type);

    void deleteItem(@Param("id") int id);

    void deleteListItems(@Param("list_id") int list_id);

    void updateStatus(@Param("status_id") int status_id, @Param("updated_date") Date updated_date,@Param("todo_item_id") int todo_item_id);

    void save(ToDoItem model);
}
