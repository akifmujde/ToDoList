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
                name = "DependencyItem.deleteItemDependencies",
                query = "delete from DependencyItem di " +
                        "where " +
                        "di.stillWaiting.id =:item_id " +
                        "or " +
                        "di.tobeCompleted.id =:item_id "
        ),
        @NamedQuery(
                name = "DependencyItem.deleteListDependencies",
                query = "delete from DependencyItem di " +
                        "where " +
                        "di.stillWaiting.id in (select i.id from ToDoItem i where i.todoList.id =:list_id) " +
                        "or " +
                        "di.tobeCompleted.id in (select i.id from ToDoItem i where i.todoList.id =:list_id) "
        ),
        @NamedQuery(
                name = "DependencyItem.checkDependency",
                query = "select di from DependencyItem di " +
                        "where " +
                        // user can not add a two way dependency
                        "(di.stillWaiting.id =:tobe_completed_id and di.tobeCompleted.id =:still_waiting_id) " +
                        "or" +
                        // user can not add a existing dependency
                        "(di.stillWaiting.id =:still_waiting_id and di.tobeCompleted.id =:tobe_completed_id) "
                        //"or" +
                        // user can not add a self dependency
                        //"(di.stillWaiting.id =:still_waiting_id and di.tobeCompleted.id =:still_waiting_id) "

        ),
        @NamedQuery(
                name = "DependencyItem.checkMarkCondition",
                query = "select di from DependencyItem di " +
                        "inner join ToDoItem i on di.tobeCompleted.id = i.id " +
                        "inner join  Status s on i.status.id = s.id " +
                        "where di.stillWaiting.id =:still_waiting_id " +
                        " and s.id = 2"
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
