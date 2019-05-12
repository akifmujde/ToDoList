package com.akifmuje.todolisttask.services;

import com.akifmuje.todolisttask.models.DependencyItem;
import com.akifmuje.todolisttask.models.ToDoItem;
import com.akifmuje.todolisttask.repositores.DependencyItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DependencyItemServiceService implements IDependencyItemService {

    @Autowired
    DependencyItemRepository dependencyItemRepository;

    @Transactional
    @Override
    public void addDependencyItem(ToDoItem stillWaiting, ToDoItem tobeCompleted) {

        dependencyItemRepository.save(new DependencyItem(stillWaiting,tobeCompleted));
    }

    @Transactional
    @Override
    public void deleteListDependencies(int list_id) {
        dependencyItemRepository.deleteListDependencies(list_id);
    }

    @Transactional
    @Override
    public void deleteItemDependencies(int item_id) {
        dependencyItemRepository.deleteItemDependencies(item_id);
    }

    @Transactional
    @Override
    public void deleteDependency(int still_waiting_id, int tobe_completed_id) {
        dependencyItemRepository.deleteDependency(still_waiting_id,tobe_completed_id);
    }

    @Override
    public List<DependencyItem> checkDependency(int still_waiting_id,int tobe_completed_id) {
        return dependencyItemRepository.checkDependency(still_waiting_id,tobe_completed_id);
    }

    @Override
    public List<DependencyItem> checkMarkCondition(int still_waiting_id) {
        return dependencyItemRepository.checkMarkCondition(still_waiting_id);
    }
}
