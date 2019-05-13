package com.akifmuje.todolisttask.dto.responses;

import com.akifmuje.todolisttask.dto.models.ToDoItem;

import java.util.ArrayList;
import java.util.List;

public class ListOfToDoItemResponse extends BaseResponse {

    public List<ToDoItem> todoitems;
    public String list_name;
    public ListOfToDoItemResponse(){
        this.todoitems = new ArrayList<>();
    }
}
