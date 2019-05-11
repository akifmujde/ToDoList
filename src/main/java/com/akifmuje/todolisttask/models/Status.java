package com.akifmuje.todolisttask.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "status")
@NamedQueries({
        @NamedQuery(
                name = "Status.getCompletedStatus",
                query = "select s from Status s where s.id = 1"
        ),
        @NamedQuery(
                name = "Status.getNotCompletedStatus",
                query = "select s from Status s where s.id = 2"
        )
})
@Data
@NoArgsConstructor

public class Status extends BaseEntity {

    @OneToMany(mappedBy = "status",cascade = CascadeType.ALL)
    Set<ToDoItem> todoItems;

    public Status(String name){
        Date currentDate = new Date();
        setName(name);
        setCreated_date(currentDate);
        setUpdated_date(currentDate);
    }

}
