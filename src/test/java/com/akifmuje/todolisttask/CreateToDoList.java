package com.akifmuje.todolisttask;
import com.akifmuje.todolisttask.models.ToDoList;
import com.akifmuje.todolisttask.models.User;
import com.akifmuje.todolisttask.services.IToDoListService;
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
public class CreateToDoList {

    @Autowired
    IUserService userService;

    @Autowired
    IToDoListService toDoListService;

    private BCryptPasswordEncoder encoder;

    User user;
    ToDoList toDoList;

    @Before
    public void bef(){
        this.encoder = new BCryptPasswordEncoder();

        String name = "name";
        String mail = "mail@mail.com";
        String password = "password";
        String token = "test_token";

        userService.createUser(new User(name,mail, encoder.encode(password), token));

        user = userService.findUserFromMail(mail).stream().findFirst().orElse(null);

    }

    @Test
    public void create_to_do_list_test() {

        String name = "list_name";

        toDoListService.save(new ToDoList(name,user));

        toDoList = toDoListService.getListFromMailAndName(name,user.getId()).stream().findFirst().orElse(null);

        assertEquals(name,toDoList.getName());

    }

    @After
    public void aft(){
        toDoListService.deleteToDoList(toDoList.getId());
        userService.deleteUserFromMail(user.getMail());
    }
}
