package com.akifmuje.todolisttask.services;

import com.akifmuje.todolisttask.models.User;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IUserService {

    List<User> findUserFromMail(@Param("mail") String mail);
    List<User> getUserFromToken(@Param("token") String token);
    List<User> getUserFromRequest(@Param("mail") String mail);

    void createUser(User model);
    void updateUserToken(@Param("token") String token, @Param("user_id") int user_id);
    void deleteUserFromMail(@Param("mail") String mail);
}
