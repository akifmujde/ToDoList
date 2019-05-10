package com.akifmuje.todolisttask.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "todoItem")
@NamedQueries({
        @NamedQuery(
                name = "ToDoItem.getToDoItemFromId",
                query = "select item from ToDoItem item where id =:todo_item_id"
        ),
        @NamedQuery(
                name = "ToDoItem.deleteItem",
                query = "delete from ToDoItem item where item.id =:id"
        ),
        @NamedQuery(
                name = "ToDoItem.deleteListItems",
                query = "delete from ToDoItem item where item.todoList.id =:list_id"
        ),
        @NamedQuery(
                name = "ToDoItem.updateStatus",
                query = "update ToDoItem item set item.status.id =:status_id, item.updated_date =:updated_date"
        ),
        @NamedQuery(
                name="ToDoItem.orderToDoItem",
                query = "select * from ToDoItem td where td.todoList.id = ?1 order by ?2 ?3"
        )
})

@Data
@NoArgsConstructor
public class ToDoItem extends BaseEntity{

    private String description;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date deadline;

    @ManyToOne
    @JoinColumn
    private ToDoList todoList;

    @ManyToOne
    @JoinColumn
    private Status status;

    @ManyToMany
    @JoinTable(
            name = "dependent_items",
            joinColumns = @JoinColumn(name = "still_waiting_id"),
            inverseJoinColumns = @JoinColumn(name = "tobe_completed_id"))
    Set<ToDoItem> toDoItemSet;

    public ToDoItem(String name, String description, Date deadline,ToDoList toDoList, Status status){

        Date currentDate = new Date();
        setName(name);
        setCreated_date(currentDate);
        setUpdated_date(currentDate);

        this.description = description;
        this.deadline = deadline;
        this.todoList = toDoList;
        this.status = status;
    }


}
