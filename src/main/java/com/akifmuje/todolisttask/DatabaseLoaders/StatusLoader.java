package com.akifmuje.todolisttask.DatabaseLoaders;

import com.akifmuje.todolisttask.models.Status;
import com.akifmuje.todolisttask.services.IStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StatusLoader implements ApplicationRunner {

    @Autowired
    private IStatusService statusService;

    Status completed;
    Status notompleted;

    public StatusLoader(){
       this.completed = new Status("Completed");
       this.notompleted  = new Status("Not Completed");

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        statusService.createStatus(completed);
        statusService.createStatus(notompleted);

    }
}
