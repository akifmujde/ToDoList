package com.akifmuje.todolisttask.services;


import com.akifmuje.todolisttask.models.Status;
import com.akifmuje.todolisttask.repositores.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService implements IStatusService{

    @Autowired
    private StatusRepository repository;

    @Override
    public List<Status> getCompletedStatus() {
        return repository.getCompletedStatus();
    }

    @Override
    public List<Status> getNotCompletedStatus() {
        return repository.getNotCompletedStatus();
    }

    @Override
    public void createStatus(Status model) {
        repository.save(model);
    }
}
