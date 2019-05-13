package com.akifmuje.todolisttask.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "todoList")
@NamedQueries({
        @NamedQuery(
                name = "ToDoList.getToDoLists",
                query = "select list from ToDoList list where list.user.id =:user_id"
        ),
        @NamedQuery(
                name = "ToDoList.deleteToDoList",
                query = "delete from ToDoList list where list.id =:id"
        ),
        @NamedQuery(
                name="ToDoList.getListFromId",
                query = "select list from ToDoList list where list.id =:id"
        ),
        @NamedQuery(
                name="ToDoList.getListFromMailAndName",
                query = "select list from ToDoList list where list.name =:name and list.user.id =:user_id"
        )
})
@Data
@NoArgsConstructor
public class ToDoList extends BaseEntity{

    @ManyToOne
    @JoinColumn
    private User user;

    @OneToMany(mappedBy = "todoList", cascade = CascadeType.ALL)
    private Set<ToDoItem> toDoItems;

    public ToDoList(String name,User user){

        Date currentDate = new Date();
        setName(name);
        setCreated_date(currentDate);
        setUpdated_date(currentDate);

        this.user = user;
    }

}
