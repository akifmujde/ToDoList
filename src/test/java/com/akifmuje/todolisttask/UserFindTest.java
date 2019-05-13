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

    @Before
    public void bef(){
        System.out.println("BEF");
    }

    @Test
    public void find_user_test() {
        System.out.println("TEST");
    }

    @After
    public void aft(){
        System.out.println("AFT");
    }

}
