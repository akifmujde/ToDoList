package com.akifmuje.todolisttask.controllers;

import com.akifmuje.todolisttask.dto.requests.AddDependencyItemRequest;
import com.akifmuje.todolisttask.dto.requests.DeleteOfToDoListItemRequest;
import com.akifmuje.todolisttask.dto.requests.FilterToDoItemRequest;
import com.akifmuje.todolisttask.dto.requests.MarkToDoItemRequest;
import com.akifmuje.todolisttask.dto.responses.*;
import com.akifmuje.todolisttask.messages.BaseMessages;
import com.akifmuje.todolisttask.models.*;
import com.akifmuje.todolisttask.services.*;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    IDependencyItem dependencyItem;


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
            toDoItemService.updateStatus(status.getId(),new Date(),markToDoItemRequest.todo_item_id);
            markToDoItemResponse.message = "List item was successfully updated.";
        }
        else {
            markToDoItemResponse.message = baseMessages.message;
        }

        markToDoItemResponse.result = baseMessages.result;

        return markToDoItemResponse;
    }

    // 6) Filter item
    @RequestMapping(value = "/filteritem",method = RequestMethod.POST)
    public @ResponseBody FilterToDoItemResponse lifterItem(@RequestBody FilterToDoItemRequest filterToDoItemRequest){

        User user = userService.getUserFromToken(filterToDoItemRequest.token).stream().findFirst().orElse(null);
        ToDoList toDoList = toDoListService.getListFromId(filterToDoItemRequest.list_id).stream().findFirst().orElse(null);
        FilterToDoItemResponse filterToDoItemResponse = new FilterToDoItemResponse();

        BaseMessages baseMessages = new BaseMessages(user, toDoList);

        if (baseMessages.result == true){

            List<ToDoItem> filterItems =    toDoItemService.filterItem(
                    filterToDoItemRequest.list_id,
                    filterToDoItemRequest.status_id,
                    filterToDoItemRequest.name
            );

            for (ToDoItem i: filterItems) {

                com.akifmuje.todolisttask.dto.models.ToDoItem dto = new com.akifmuje.todolisttask.dto.models.ToDoItem();

                dto.name = i.getName();
                dto.description = i.getDescription();
                dto.deadline = i.getDeadline();
                dto.created_date = i.getCreated_date();
                dto.updated_date = i.getUpdated_date();
                dto.list_id = i.getTodoList().getId();
                dto.status_id = i.getStatus().getId();
                filterToDoItemResponse.toDoItems.add(dto);

            }

            filterToDoItemResponse.message = "List items was successfully filtered.";

        }
        else {

            filterToDoItemResponse.message = baseMessages.message;
        }

        filterToDoItemResponse.result = baseMessages.result;

        return filterToDoItemResponse;

    }

/*
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
*/


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


    // 9) Add dependency list item
    @RequestMapping(value = "adddependency",method = RequestMethod.POST)
    public @ResponseBody AddDependencyItemResponse addDependencyItem(@RequestBody AddDependencyItemRequest addDependencyItemRequest){

        User user;
        ToDoItem stillWaitingItem;
        ToDoItem tobeCompletedItem;
        DependencyItem tobeCompletedDependencyItem;
        AddDependencyItemResponse addDependencyItemResponse = new AddDependencyItemResponse();

        user = userService.getUserFromToken(addDependencyItemRequest.token).stream().findFirst().orElse(null);

        stillWaitingItem = toDoItemService.getToDoItemFromId(addDependencyItemRequest.still_waiting_id).stream().findFirst().orElse(null);
        tobeCompletedItem = toDoItemService.getToDoItemFromId(addDependencyItemRequest.to_to_completed_id).stream().findFirst().orElse(null);

        tobeCompletedDependencyItem = dependencyItem.checkDependency(addDependencyItemRequest.still_waiting_id,addDependencyItemRequest.to_to_completed_id).stream().findFirst().orElse(null);

        BaseMessages baseMessages = new BaseMessages(user,stillWaitingItem);

        if (baseMessages.result == true){

            // if to be completed item not have any dependency via still waiting item
            if (tobeCompletedDependencyItem == null){

                dependencyItem.addDependencyItem(stillWaitingItem,tobeCompletedItem);

                addDependencyItemResponse.result = true;
                addDependencyItemResponse.message = "Dependency was successfully added.";
            }
            else {
                addDependencyItemResponse.result = false;
                addDependencyItemResponse.message = "Dependency item have dependency on selected item.";
            }
        }
        else {

            addDependencyItemResponse.message = baseMessages.message;
            addDependencyItemResponse.result = false;
        }

        return addDependencyItemResponse;

    }

}
