package com.akifmuje.todolisttask.DatabaseLoaders;

import com.akifmuje.todolisttask.models.Status;
import com.akifmuje.todolisttask.services.IStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StatusLoader implements CommandLineRunner {

    @Autowired
    IStatusService statusService;

    @Override
    public void run(String... args) throws Exception {
        statusService.createStatus(new Status(
                "completed"
        ));

        statusService.createStatus(new Status(
                "not completed"
        ));
    }
}
