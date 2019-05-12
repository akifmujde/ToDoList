package com.akifmuje.todolisttask.services;

import com.akifmuje.todolisttask.models.DependencyItem;
import com.akifmuje.todolisttask.models.ToDoItem;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IDependencyItemService {

    void addDependencyItem(ToDoItem stillWaiting, ToDoItem tobeCompleted);

    void deleteListDependencies(@Param("list_id") int list_id);

    void deleteItemDependencies(@Param("item_id") int item_id);

    void deleteDependency(@Param("still_waiting_id") int still_waiting_id,@Param("tobe_completed_id") int tobe_completed_id);

    List<DependencyItem> checkDependency(@Param("still_waiting_id") int still_waiting_id, @Param("tobe_completed_id") int tobe_completed_id);

    List<DependencyItem> checkMarkCondition(@Param("still_waiting_id") int still_waiting_id);
}
