package com.akifmuje.todolisttask.services;


import com.akifmuje.todolisttask.models.User;
import com.akifmuje.todolisttask.repositores.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository repository;

    @Override
    public List<User> getIdCountFromToken(String token){

        List<User> userCount = (List<User>) repository.getIdCountFromToken(token);
        return userCount;
    }

    @Override
    public List<User> getUserFromToken(String token){

        List<User> user = (List<User>) repository.getUserFromToken(token);
        return  user;
    }

    @Override
    public void createUser(User model) {
        repository.save(model);
    }
}
