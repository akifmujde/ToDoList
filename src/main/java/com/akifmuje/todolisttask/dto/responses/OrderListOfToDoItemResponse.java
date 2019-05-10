package com.akifmuje.todolisttask.dto.responses;



import com.akifmuje.todolisttask.dto.models.ToDoItem;

import java.util.ArrayList;
import java.util.List;

public class OrderListOfToDoItemResponse extends BaseResponse {

    public List<ToDoItem> toDoItems = new ArrayList<>();

    public OrderListOfToDoItemResponse(){}

}
