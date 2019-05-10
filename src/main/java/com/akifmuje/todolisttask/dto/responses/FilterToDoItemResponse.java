package com.akifmuje.todolisttask.dto.responses;

import java.util.Date;

public class FilterToDoItemResponse extends BaseResponse {

    public int list_id, status_id;
    public String name, description;
    public Date deadline, created_date, updated_date;

    public FilterToDoItemResponse() {}

    public FilterToDoItemResponse(boolean result, String message, int list_id, int status_id, String name, String description, Date deadline, Date created_date, Date updated_date){

        this.list_id = list_id;
        this.status_id = status_id;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.created_date = created_date;
        this.updated_date = updated_date;

        this.result = result;
        this.message = message;
    }

}
