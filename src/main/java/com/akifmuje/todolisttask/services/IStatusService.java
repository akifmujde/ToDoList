package com.akifmuje.todolisttask.services;

import com.akifmuje.todolisttask.models.Status;

import java.util.List;

public interface IStatusService {
    List<Status> getCompletedStatus();
    List<Status> getNotCompletedStatus();

    void createStatus(Status model);
}
