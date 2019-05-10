package com.akifmuje.todolisttask.repositores;


import com.akifmuje.todolisttask.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusRepository extends JpaRepository<Status,Integer> {
    List<Status> getCompletedStatus();
    List<Status> getNotCompletedStatus();
}
