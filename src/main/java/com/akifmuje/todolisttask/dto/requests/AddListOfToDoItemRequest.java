package com.akifmuje.todolisttask.dto.requests;

import java.util.Date;

public class AddListOfToDoItemRequest extends BaseRequest {

    public int list_id;
    public String name, description;
    public Date deadline;

}
