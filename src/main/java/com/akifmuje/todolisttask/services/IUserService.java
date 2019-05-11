package com.akifmuje.todolisttask.services;

import com.akifmuje.todolisttask.models.User;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IUserService {
    List<User> getIdCountFromToken(String token);
    List<User> getUserFromToken(String token);
    List<User> getUserFromRequest(String mail, String password);

    void createUser(User model);
    void updateUserToken(@Param("token") String token, @Param("user_id") int user_id);
}
