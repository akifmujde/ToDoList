package com.akifmuje.todolisttask.dto.models;

import java.util.Date;

public class ToDoItem extends BaseEntity {

    public String description;
    public Date deadline;
    public int list_id;
    public int status_id;
}
