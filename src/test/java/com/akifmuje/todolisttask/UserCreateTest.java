package com.akifmuje.todolisttask;

import com.akifmuje.todolisttask.models.User;
import com.akifmuje.todolisttask.services.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserCreateTest {

    @Autowired
    IUserService userService;

    private BCryptPasswordEncoder encoder;

    @Test
    public void createUserTest() {
        this.encoder = new BCryptPasswordEncoder();

        String name = "akif";
        String mail = "mail@mail.com";
        String password = "11223341";
        String token = "";

        userService.createUser(new User(name,mail, encoder.encode(password), token));

        User savedUser = userService.findUserFromMail(mail).stream().findFirst().orElse(null);

        assertEquals(name, savedUser.getName());

    }
}
