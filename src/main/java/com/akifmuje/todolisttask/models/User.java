package com.akifmuje.todolisttask.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "user")
@NamedQueries({
        @NamedQuery(
                name = "User.findUserFromMail",
                query = "select u from User u where u.mail =:mail"
        ),
        @NamedQuery(
                name = "User.getUserFromToken",
                query = "select u from User u where u.token =:token"
        ),
        @NamedQuery(
                name = "User.getUserFromRequest",
                query = "select u from User u where u.mail =:mail"
        ),
        @NamedQuery(
                name = "User.updateUserToken",
                query = "update User u set u.token =:token where u.id =:user_id"
        ),
        @NamedQuery(
                name = "User.deleteUserFromMail",
                query = "delete from User u where u.mail =:mail"
        )
})

@Data
@NoArgsConstructor
public class User extends BaseEntity{

    @NotNull
    private String mail;

    @NotNull
    private String password;

    private String token;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<ToDoList> toDoLists;

    public User(String name, String mail, String password, String token){

        Date currentDate = new Date();
        setName(name);
        setCreated_date(currentDate);
        setUpdated_date(currentDate);

        this.mail = mail;
        this.password = password;
        this.token = token;

    }

}
