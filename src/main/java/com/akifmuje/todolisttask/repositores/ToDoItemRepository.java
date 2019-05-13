package com.akifmuje.todolisttask.repositores;


import com.akifmuje.todolisttask.models.ToDoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ToDoItemRepository extends JpaRepository<ToDoItem,Integer>{

    String filterItem = "select item from ToDoItem item where " +
            "item.todoList.id = ?1 and " +
            "item.status.id = ?2 and " +
            "item.name like CONCAT('%',CONCAT(?3, '%'))";
    @Query(value = filterItem)
    List<ToDoItem> filterItem(int list_id, int status_id, String name);

    @Modifying
    void deleteListItems(@Param("list_id") int list_id);

    @Modifying
    void updateStatus(@Param("status_id") int status_id, @Param("updated_date")Date updated_date,@Param("todo_item_id") int todo_item_id);

    @Modifying
    void deleteItem(@Param("id") int id);

    List<ToDoItem> getToDoItemFromId(@Param("todo_item_id") int todo_item_id);

    List<ToDoItem> getNotDependencyItems(@Param("still_waiting_id") int still_waiting_id);

    List<ToDoItem> getToDoItemsFromList_Id(@Param("list_id") int list_id);

    List<ToDoItem> gelAllDependenciesOfItem(@Param("still_waiting_id") int still_waiting_id);

}
