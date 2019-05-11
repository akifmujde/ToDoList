package com.akifmuje.todolisttask.services;

import com.akifmuje.todolisttask.models.DependencyItem;
import com.akifmuje.todolisttask.models.ToDoItem;
import com.akifmuje.todolisttask.repositores.DependencyItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DependencyItemService implements IDependencyItem {

    @Autowired
    DependencyItemRepository dependencyItemRepository;

    @Override
    public void addDependencyItem(ToDoItem stillWaiting, ToDoItem tobeCompleted) {

        dependencyItemRepository.save(new DependencyItem(stillWaiting,tobeCompleted));
    }

    @Override
    public void deleteDependencies(int still_waiting_id, int tobe_completed_id) {
        dependencyItemRepository.deleteDependencies(still_waiting_id,tobe_completed_id);
    }

    @Override
    public void deleteDependency(int still_waiting_id, int tobe_completed_id) {
        dependencyItemRepository.deleteDependency(still_waiting_id,tobe_completed_id);
    }

    @Override
    public List<DependencyItem> checkDependency(int still_waiting_id,int tobe_completed_id) {
        return dependencyItemRepository.checkDependency(still_waiting_id,tobe_completed_id);
    }
}
