package com.akifmuje.todolisttask.dto.responses;

import com.akifmuje.todolisttask.dto.models.ToDoItem;

import java.util.ArrayList;
import java.util.List;

public class FilterToDoItemResponse extends BaseResponse {

    public List<ToDoItem> toDoItems;

    public FilterToDoItemResponse() { toDoItems = new ArrayList<>(); }

}
