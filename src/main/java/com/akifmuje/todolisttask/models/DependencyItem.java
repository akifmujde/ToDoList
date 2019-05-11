package com.akifmuje.todolisttask.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "dependencyItem")
@Data
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "DependencyItem.deleteDependency",
                query = "delete from DependencyItem di " +
                        "where " +
                        "di.stillWaiting.id =:still_waiting_id " +
                        "and " +
                        "di.tobeCompleted.id =:tobe_completed_id "
        ),
        @NamedQuery(
                name = "DependencyItem.deleteDependencies",
                query = "delete from DependencyItem di " +
                        "where " +
                        "di.stillWaiting.id in :still_waiting_ids " +
                        "or " +
                        "di.tobeCompleted.id in :tobe_completed_ids "
        ),
        @NamedQuery(
                name = "DependencyItem.checkDependency",
                query = "select di from DependencyItem di " +
                        "where " +
                        "di.stillWaiting.id =:tobe_completed_id " + // user not add a two way dependency
                        "or" +
                        "(di.stillWaiting.id =:still_waiting_id and di.tobeCompleted.id =:tobe_completed_id) " + // user not add a existing dependency
                        "or" +
                        "(di.stillWaiting.id =:still_waiting_id and di.tobeCompleted.id =:still_waiting_id)" // user not add a self dependency
        )
})
public class DependencyItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn
    private ToDoItem stillWaiting;

    @ManyToOne
    @JoinColumn
    private ToDoItem tobeCompleted;

    public DependencyItem(ToDoItem stillWaiting,ToDoItem tobeCompleted){

        this.stillWaiting = stillWaiting;
        this.tobeCompleted = tobeCompleted;
    }

}
