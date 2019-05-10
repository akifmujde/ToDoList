package com.akifmuje.todolisttask.dto.responses;

import com.akifmuje.todolisttask.dto.models.ToDoList;

import java.util.ArrayList;
import java.util.List;

public class GetToDoListResponse extends BaseResponse{

    public List<ToDoList> lists;

    public GetToDoListResponse(){
        this.lists = new ArrayList<>();
    }
}
