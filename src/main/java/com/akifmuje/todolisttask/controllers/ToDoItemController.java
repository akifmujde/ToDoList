package com.akifmuje.todolisttask.controllers;

import com.akifmuje.todolisttask.dto.requests.DeleteOfToDoListItemRequest;
import com.akifmuje.todolisttask.dto.requests.MarkToDoItemRequest;
import com.akifmuje.todolisttask.dto.requests.OrderListOfToDoItemRequest;
import com.akifmuje.todolisttask.dto.responses.BaseResponse;
import com.akifmuje.todolisttask.dto.responses.DeleteOfToDoListItemResponse;
import com.akifmuje.todolisttask.dto.responses.MarkToDoItemResponse;
import com.akifmuje.todolisttask.dto.responses.OrderListOfToDoItemResponse;
import com.akifmuje.todolisttask.messages.BaseMessages;
import com.akifmuje.todolisttask.models.Status;
import com.akifmuje.todolisttask.models.ToDoItem;
import com.akifmuje.todolisttask.models.User;
import com.akifmuje.todolisttask.services.IStatusService;
import com.akifmuje.todolisttask.services.IToDoItemService;
import com.akifmuje.todolisttask.services.IToDoListService;
import com.akifmuje.todolisttask.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/todolist/todoitem")
public class ToDoItemController {

    @Autowired
    IUserService userService;

    @Autowired
    IToDoListService toDoListService;

    @Autowired
    IToDoItemService toDoItemService;

    @Autowired
    IStatusService statusService;


    // 5) Mark to do item completed.
    @RequestMapping(value = "/markcompleted",method = RequestMethod.POST)
    public @ResponseBody MarkToDoItemResponse martToDoItem(@RequestBody MarkToDoItemRequest markToDoItemRequest){

        User user;
        ToDoItem toDoItem;
        Status status;
        MarkToDoItemResponse markToDoItemResponse = new MarkToDoItemResponse();

        user = userService.getUserFromToken(markToDoItemRequest.token).stream().findFirst().orElse(null);
        toDoItem = toDoItemService.getToDoItemFromId(markToDoItemRequest.todo_item_id).stream().findFirst().orElse(null);
        status = statusService.getCompletedStatus().stream().findFirst().orElse(null);

        BaseMessages baseMessages = new BaseMessages(user,toDoItem);

        if (baseMessages.result == true){
            toDoItemService.updateStatus(status.getId(),new Date());
            markToDoItemResponse.message = "List item was successfully updated.";
        }
        else {
            markToDoItemResponse.message = baseMessages.message;
        }

        markToDoItemResponse.result = baseMessages.result;

        return markToDoItemResponse;
    }


    // 7) Order to do item.
    @RequestMapping(value = "/orderitem",method = RequestMethod.POST)
    public @ResponseBody OrderListOfToDoItemResponse orderToDoItem(@RequestBody OrderListOfToDoItemRequest orderListOfToDoItemRequest){
        List<ToDoItem> list = toDoItemService.orderToDoItem(orderListOfToDoItemRequest.list_id,orderListOfToDoItemRequest.column_name,orderListOfToDoItemRequest.order_type);
        OrderListOfToDoItemResponse orderListOfToDoItemResponse = new OrderListOfToDoItemResponse();

        for (ToDoItem t: list) {
            com.akifmuje.todolisttask.dto.models.ToDoItem dto = new com.akifmuje.todolisttask.dto.models.ToDoItem();

            dto.name = t.getName();
            dto.description = t.getDescription();
            dto.deadline = t.getDeadline();
            dto.created_date = t.getCreated_date();
            dto.updated_date = t.getUpdated_date();
            dto.list_id = t.getTodoList().getId();
            dto.status_id = t.getStatus().getId();
        }




        return orderListOfToDoItemResponse;
    }

    // 8) Delete list item.
    @RequestMapping(value = "deleteitem", method = RequestMethod.POST)
    public  @ResponseBody DeleteOfToDoListItemResponse deleteItem(@RequestBody DeleteOfToDoListItemRequest deleteOfToDoListItemRequest){

        User user;
        ToDoItem toDoItem;
        DeleteOfToDoListItemResponse deleteOfToDoListItemResponse = new DeleteOfToDoListItemResponse();

        user = userService.getUserFromToken(deleteOfToDoListItemRequest.token).stream().findFirst().orElse(null);
        toDoItem = toDoItemService.getToDoItemFromId(deleteOfToDoListItemRequest.id).stream().findFirst().orElse(null);

        BaseMessages baseMessages = new BaseMessages(user, toDoItem);

        if (baseMessages.result == true){

            toDoItemService.deleteItem(toDoItem.getId());
            deleteOfToDoListItemResponse.message = "List item was successfully deleted.";
        }
        else {
            deleteOfToDoListItemResponse.message = baseMessages.message;
        }

        deleteOfToDoListItemResponse.result = baseMessages.result;

        return  deleteOfToDoListItemResponse;
    }
}
