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
@CrossOrigin
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

                dto.id = i.getId();
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


    // 3) Order to do item.
    @RequestMapping(value = "/orderitem",method = RequestMethod.POST)
    public @ResponseBody OrderListOfToDoItemResponse orderToDoItem(@RequestBody OrderListOfToDoItemRequest orderListOfToDoItemRequest){

        User user;
        ToDoList toDoList;
        List<ToDoItem> order_lists = toDoItemService.orderToDoItem(orderListOfToDoItemRequest.list_id,orderListOfToDoItemRequest.column_name,orderListOfToDoItemRequest.order_type);

        user = userService.getUserFromToken(orderListOfToDoItemRequest.token).stream().findFirst().orElse(null);
        toDoList = toDoListService.getListFromId(orderListOfToDoItemRequest.list_id).stream().findFirst().orElse(null);

        OrderListOfToDoItemResponse orderListOfToDoItemResponse = new OrderListOfToDoItemResponse();
        BaseMessages baseMessages = new BaseMessages(user,toDoList);

        if (baseMessages.result){

            for (ToDoItem order_item: order_lists) {
                com.akifmuje.todolisttask.dto.models.ToDoItem dto = new com.akifmuje.todolisttask.dto.models.ToDoItem();

                dto.id = order_item.getId();
                dto.name = order_item.getName();
                dto.description = order_item.getDescription();
                dto.deadline = order_item.getDeadline();
                dto.created_date = order_item.getCreated_date();
                dto.updated_date = order_item.getUpdated_date();
                dto.list_id = order_item.getTodoList().getId();
                dto.status_id = order_item.getStatus().getId();

                orderListOfToDoItemResponse.toDoItems.add(dto);

            }

            orderListOfToDoItemResponse.message = "List was successfully ordered.";
            orderListOfToDoItemResponse.result = true;

        }
        else {

            orderListOfToDoItemResponse.message = baseMessages.message;
            orderListOfToDoItemResponse.result = false;
        }

        return orderListOfToDoItemResponse;
    }



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


    // 6) Show lis
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

    // 8) List all list items
    @RequestMapping(value = "listoftodoitem",method = RequestMethod.POST)
    public @ResponseBody ListOfToDoItemResponse listToDoItem(@RequestBody ListOfToDoItemRequest listOfToDoItemRequest){

        User user;
        ListOfToDoItemResponse listOfToDoItemResponse = new ListOfToDoItemResponse();
        List<ToDoItem> toDoItem;
        ToDoList toDoList;

        user = userService.getUserFromToken(listOfToDoItemRequest.token).stream().findFirst().orElse(null);
        toDoList = toDoListService.getListFromId(listOfToDoItemRequest.list_id).stream().findFirst().orElse(null);

        BaseMessages baseMessages = new BaseMessages(user,toDoList);

        if (baseMessages.result==true){
            toDoItem = toDoItemService.getToDoItemsFromList_Id(listOfToDoItemRequest.list_id);

            for (ToDoItem i: toDoItem) {

                com.akifmuje.todolisttask.dto.models.ToDoItem dto = new com.akifmuje.todolisttask.dto.models.ToDoItem();

                dto.id = i.getId();
                dto.description = i.getDescription();
                dto.name = i.getName();
                dto.deadline = i.getDeadline();
                dto.created_date = i.getCreated_date();
                dto.updated_date = i.getUpdated_date();
                dto.list_id = i.getTodoList().getId();
                dto.status_id = i.getStatus().getId();

                listOfToDoItemResponse.todoitems.add(dto);

            }

            listOfToDoItemResponse.list_name = toDoList.getName();
            listOfToDoItemResponse.message = "List item was successfully listed.";
        }
        else {
            listOfToDoItemResponse.message = baseMessages.message;
        }

        listOfToDoItemResponse.result = baseMessages.result;

        return listOfToDoItemResponse;
    }

    // 9) List all dependencies of to do item
    @RequestMapping(value = "/alldepencenciesitem",method = RequestMethod.POST)
    public @ResponseBody AllDependenciesItemResponse allDependencies(@RequestBody AllDependenciesItemRequest allDependenciesItemRequest){

        User user;
        AllDependenciesItemResponse allDependenciesItemResponse = new AllDependenciesItemResponse();
        ToDoItem toDoItem;

        user = userService.getUserFromToken(allDependenciesItemRequest.token).stream().findFirst().orElse(null);
        toDoItem = toDoItemService.getToDoItemFromId(allDependenciesItemRequest.todo_item_id).stream().findFirst().orElse(null);
        BaseMessages baseMessages = new BaseMessages(user,toDoItem);

        if (baseMessages.result == true){

            List<ToDoItem> items= toDoItemService.gelAllDependenciesOfItem(allDependenciesItemRequest.todo_item_id);
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

                allDependenciesItemResponse.toDoItems.add(dto);
            }

            allDependenciesItemResponse.result = true;
            allDependenciesItemResponse.message= "Dependency items was successfully listed.";
            allDependenciesItemResponse.list_name = toDoItem.getTodoList().getName();
        }
        else {
            allDependenciesItemResponse.result = false;
            allDependenciesItemResponse.message= baseMessages.message;
        }

        return allDependenciesItemResponse;
    }

}
