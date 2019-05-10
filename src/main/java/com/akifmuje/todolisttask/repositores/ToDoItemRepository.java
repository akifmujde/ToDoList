package com.akifmuje.todolisttask.repositores;


import com.akifmuje.todolisttask.models.ToDoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ToDoItemRepository extends JpaRepository<ToDoItem,Integer>, JpaSpecificationExecutor<ToDoItem> {

    @Modifying
    void deleteListItems(@Param("list_id") int list_id);

    @Modifying
    void updateStatus(@Param("status_id") int status_id, @Param("updated_date")Date updated_date);

    @Modifying
    void deleteItem(@Param("id") int id);

    List<ToDoItem> getToDoItemFromId(@Param("todo_item_id") int todo_item_id);

    List<ToDoItem> orderToDoItem(int list_id, String column_name, String order_type);

}
