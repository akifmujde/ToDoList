package com.akifmuje.todolisttask.services;

import com.akifmuje.todolisttask.models.User;

import java.util.List;

public interface IUserService {
    List<User> getIdCountFromToken(String token);
    List<User> getUserFromToken(String token);

    void createUser(User model);
}
