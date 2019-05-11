package com.akifmuje.todolisttask.services;


import com.akifmuje.todolisttask.models.User;
import com.akifmuje.todolisttask.repositores.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository repository;

    @Override
    public List<User> getIdCountFromToken(String token){

        List<User> userCount = repository.getIdCountFromToken(token);
        return userCount;
    }

    @Override
    public List<User> getUserFromToken(String token){

        List<User> user = repository.getUserFromToken(token);
        return  user;
    }

    @Override
    public List<User> getUserFromRequest(String mail, String password) {
        return repository.getUserFromRequest(mail,password);
    }

    @Override
    public void createUser(User model) {
        repository.save(model);
    }

    @Transactional
    @Override
    public void updateUserToken(String token, int user_id) {
        repository.updateUserToken(token,user_id);
    }
}
