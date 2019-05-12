package com.akifmuje.todolisttask.controllers;

import com.akifmuje.todolisttask.dto.requests.*;
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
    IDependencyItemService dependencyItemService;


    // 1) Mark to do item completed.
    @RequestMapping(value = "/markcompleted",method = RequestMethod.POST)
    public @ResponseBody MarkToDoItemResponse martToDoItem(@RequestBody MarkToDoItemRequest markToDoItemRequest){

        User user;
        ToDoItem toDoItem;
        Status status;
        DependencyItem dependencyItem;
        MarkToDoItemResponse markToDoItemResponse = new MarkToDoItemResponse();

        user = userService.getUserFromToken(markToDoItemRequest.token).stream().findFirst().orElse(null);
        toDoItem = toDoItemService.getToDoItemFromId(markToDoItemRequest.todo_item_id).stream().findFirst().orElse(null);
        status = statusService.getCompletedStatus().stream().findFirst().orElse(null);
        dependencyItem = dependencyItemService.checkMarkCondition(markToDoItemRequest.todo_item_id).stream().findFirst().orElse(null);

        BaseMessages baseMessages = new BaseMessages(user,toDoItem);

        if (baseMessages.result == true){

            if (dependencyItem == null){
                toDoItemService.updateStatus(status.getId(),new Date(),markToDoItemRequest.todo_item_id);

                markToDoItemResponse.result = baseMessages.result;
                markToDoItemResponse.message = "List item was successfully updated.";
            }
            else {

                markToDoItemResponse.result = false;
                markToDoItemResponse.message = "This item have a depended items. Please first mark them items";
            }
        }
        else {
            markToDoItemResponse.result = baseMessages.result;
            markToDoItemResponse.message = baseMessages.message;
        }



        return markToDoItemResponse;
    }

    // 2) Filter item
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
    // 3) Order to do item.
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


    // 4) Delete list item.
    @RequestMapping(value = "deleteitem", method = RequestMethod.POST)
    public  @ResponseBody DeleteOfToDoListItemResponse deleteItem(@RequestBody DeleteOfToDoListItemRequest deleteOfToDoListItemRequest){

        User user;
        ToDoItem toDoItem;
        DeleteOfToDoListItemResponse deleteOfToDoListItemResponse = new DeleteOfToDoListItemResponse();

        user = userService.getUserFromToken(deleteOfToDoListItemRequest.token).stream().findFirst().orElse(null);
        toDoItem = toDoItemService.getToDoItemFromId(deleteOfToDoListItemRequest.id).stream().findFirst().orElse(null);

        BaseMessages baseMessages = new BaseMessages(user, toDoItem);

        if (baseMessages.result == true){

            dependencyItemService.deleteItemDependencies(toDoItem.getId());
            toDoItemService.deleteItem(toDoItem.getId());
            deleteOfToDoListItemResponse.message = "List item was successfully deleted.";
        }
        else {
            deleteOfToDoListItemResponse.message = baseMessages.message;
        }

        deleteOfToDoListItemResponse.result = baseMessages.result;

        return  deleteOfToDoListItemResponse;
    }


    // 5) Add dependency list item
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

        tobeCompletedDependencyItem = dependencyItemService.checkDependency(addDependencyItemRequest.still_waiting_id,addDependencyItemRequest.to_to_completed_id).stream().findFirst().orElse(null);

        BaseMessages stillWaitingMessages = new BaseMessages(user,stillWaitingItem);
        BaseMessages tobeCompletedMessages = new BaseMessages(user,tobeCompletedItem);

        if (stillWaitingMessages.result == true){

            if (tobeCompletedMessages.result == true){
                // if to be completed item not have any dependency via still waiting item
                if (tobeCompletedDependencyItem == null){

                    dependencyItemService.addDependencyItem(stillWaitingItem,tobeCompletedItem);

                    addDependencyItemResponse.result = true;
                    addDependencyItemResponse.message = "Dependency was successfully added.";
                }
                else {
                    addDependencyItemResponse.result = false;
                    addDependencyItemResponse.message = "Item have already exist dependency";
                }
            }
            else{
                addDependencyItemResponse.message = tobeCompletedMessages.message;
                addDependencyItemResponse.result = false;
            }
        }
        else {

            addDependencyItemResponse.message = stillWaitingMessages.message;
            addDependencyItemResponse.result = false;
        }

        return addDependencyItemResponse;

    }


    // 6) Show list of can be added dependency items
    @RequestMapping(value = "showdependencies",method = RequestMethod.POST)
    public @ResponseBody ShowDependencyItemsResponse showDependencyItems(@RequestBody ShowDependencyItemsRequest showDependencyItemsRequest){

        User user;
        ShowDependencyItemsResponse showDependencyItemsResponse = new ShowDependencyItemsResponse();
        ToDoItem toDoItem;

        user = userService.getUserFromToken(showDependencyItemsRequest.token).stream().findFirst().orElse(null);
        toDoItem = toDoItemService.getToDoItemFromId(showDependencyItemsRequest.todo_item_id).stream().findFirst().orElse(null);


        BaseMessages baseMessages = new BaseMessages(user,toDoItem);

        if (baseMessages.result == true){

            List<ToDoItem> items= toDoItemService.getNotDependencyItems(showDependencyItemsRequest.todo_item_id);
            for (ToDoItem i : items) {
                com.akifmuje.todolisttask.dto.models.ToDoItem dto = new com.akifmuje.todolisttask.dto.models.ToDoItem();

                dto.id = i.getId();
                dto.name = i.getName();
                dto.description = i.getDescription();
                dto.created_date = i.getCreated_date();
                dto.updated_date = i.getUpdated_date();
                dto.deadline = i.getDeadline();
                dto.list_id = i.getTodoList().getId();
                dto.status_id = i.getStatus().getId();

                showDependencyItemsResponse.not_dependent_items.add(dto);
            }

            showDependencyItemsResponse.result = true;
            showDependencyItemsResponse.message= "Dependency items was successfully listed.";
        }
        else {
            showDependencyItemsResponse.result = baseMessages.result;
            showDependencyItemsResponse.message= baseMessages.message;
        }

        return showDependencyItemsResponse;
    }

    // 7) Delete dependency
    @RequestMapping(value = "deletedependency",method = RequestMethod.POST)
    public @ResponseBody DeleteDependencyResponse deleteDependency(@RequestBody DeleteDependencyRequest deleteDependencyRequest){

        User user;
        ToDoItem stillWaitingItem;
        ToDoItem tobeCompletedItem;
        DeleteDependencyResponse deleteDependencyResponse = new DeleteDependencyResponse();

        user = userService.getUserFromToken(deleteDependencyRequest.token).stream().findFirst().orElse(null);
        stillWaitingItem = toDoItemService.getToDoItemFromId(deleteDependencyRequest.still_waiting_id).stream().findFirst().orElse(null);
        tobeCompletedItem = toDoItemService.getToDoItemFromId(deleteDependencyRequest.tobe_completed_id).stream().findFirst().orElse(null);

        BaseMessages stillWaitingMessages = new BaseMessages(user,stillWaitingItem);
        BaseMessages tobeCompletedMessages = new BaseMessages(user,tobeCompletedItem);

        if (stillWaitingMessages.result == true){

            if (tobeCompletedMessages.result == true){

                dependencyItemService.deleteDependency(deleteDependencyRequest.still_waiting_id,deleteDependencyRequest.tobe_completed_id);

                deleteDependencyResponse.result=true;
                deleteDependencyResponse.message="Dependency was successfully deleted.";
            }
            else {
                deleteDependencyResponse.result=false;
                deleteDependencyResponse.message=tobeCompletedMessages.message;
            }
        }
        else {
            deleteDependencyResponse.result=false;
            deleteDependencyResponse.message=stillWaitingMessages.message;
        }

        return deleteDependencyResponse;
    }
}
