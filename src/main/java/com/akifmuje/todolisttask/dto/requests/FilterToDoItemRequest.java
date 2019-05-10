package com.akifmuje.todolisttask.dto.requests;

import java.util.Date;

public class FilterToDoItemRequest extends BaseRequest {

    public int list_id, status_id;
    public String name;
    public Date deadline;

}
