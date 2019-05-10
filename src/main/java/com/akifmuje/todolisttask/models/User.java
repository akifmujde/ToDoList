package com.akifmuje.todolisttask.models;

import lombok.AllArgsConstructor;
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
                name = "User.getIdCountFromToken",
                query = "select count(u.id) from User u where u.token =:token"
        ),
        @NamedQuery(
                name = "User.getUserFromToken",
                query = "select u from User u where u.token =:token"
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
