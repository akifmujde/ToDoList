package com.akifmuje.todolisttask.DatabaseLoaders;

import com.akifmuje.todolisttask.models.User;
import com.akifmuje.todolisttask.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserLoader implements CommandLineRunner {

    @Autowired
    IUserService userService;

    @Override
    public void run(String... args) throws Exception {
        userService.createUser(new User(
                "Nihat Alim",
                "iletisim@nihatalim.com.tr",
                "password",
                "alimToken"
        ));

        userService.createUser(new User(
                "Ahmet Tayyip Müjde",
                "iletisim@tayyipmujde.com.tr",
                "password",
                "tayyipToken"
        ));
    }
}
