package com.akifmuje.todolisttask.dto.responses;

import com.akifmuje.todolisttask.dto.models.ToDoItem;

import java.util.ArrayList;
import java.util.List;

public class ShowDependencyItemsResponse extends BaseResponse{

    public List<ToDoItem> not_dependent_items;

    public ShowDependencyItemsResponse(){
        this.not_dependent_items = new ArrayList<>();
    }
}
