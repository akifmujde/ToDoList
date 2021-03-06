package com.akifmuje.todolisttask;

import com.akifmuje.todolisttask.models.User;
import com.akifmuje.todolisttask.services.IUserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserFindTest {

    @Autowired
    IUserService userService;

    private BCryptPasswordEncoder encoder;

    User user;

    @Before
    public void bef(){
        this.encoder = new BCryptPasswordEncoder();

        String name = "name";
        String mail = "mail@mail.com";
        String password = "password";
        String token = "";

        user = new User(name,mail,password,token);

        userService.createUser(new User(name,mail, encoder.encode(password), token));

    }

    @Test
    public void find_user_test() {

        User finduser = userService.findUserFromMail(user.getMail()).stream().findFirst().orElse(null);

        assertEquals(user.getMail(),finduser.getMail());

    }

    @After
    public void aft(){
        
        userService.deleteUserFromMail(user.getMail());

    }

}
