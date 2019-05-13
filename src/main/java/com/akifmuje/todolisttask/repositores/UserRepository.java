package com.akifmuje.todolisttask.repositores;


import com.akifmuje.todolisttask.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    List<User> findUserFromMail(@Param("mail") String mail);
    List<User> getUserFromToken(@Param("token") String token);
    List<User> getUserFromRequest(@Param("mail") String mail);


    @Modifying
    void updateUserToken(@Param("token") String token,@Param("user_id") int user_id);

    @Modifying
    void deleteUserFromMail(@Param("mail") String mail);
}
