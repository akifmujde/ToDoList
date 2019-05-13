package com.akifmuje.todolisttask.dto.responses;

import com.akifmuje.todolisttask.dto.models.ToDoItem;

import java.util.ArrayList;
import java.util.List;

public class AllDependenciesItemResponse extends BaseResponse {

    public List<ToDoItem> toDoItems;

    public String list_name;

    public AllDependenciesItemResponse(){
        toDoItems = new ArrayList<>();
    }

}
