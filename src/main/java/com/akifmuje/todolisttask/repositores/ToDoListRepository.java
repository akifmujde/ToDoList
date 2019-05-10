package com.akifmuje.todolisttask.repositores;
import com.akifmuje.todolisttask.models.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoListRepository extends JpaRepository<ToDoList,Integer> {

    List<ToDoList> getToDoLists(@Param("user_id") int user_id);
    List<ToDoList> getListFromId(@Param("id") int id);

    @Modifying
    void deleteToDoList(@Param("id") int id);

}
