package com.akifmuje.todolisttask.dto.models;

import java.util.Date;

public class ToDoItem extends BaseEntity {

    public String name;
    public String description;
    public Date deadline;
    public Date created_date;
    public Date updated_date;
    public int list_id;
    public int status_id;
}
