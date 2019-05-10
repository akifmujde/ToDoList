package com.akifmuje.todolisttask.controllers;


import com.akifmuje.todolisttask.dto.requests.AddListOfToDoItemRequest;
import com.akifmuje.todolisttask.dto.requests.DeleteToDoListRequest;
import com.akifmuje.todolisttask.dto.requests.GetToDoListRequest;
import com.akifmuje.todolisttask.dto.responses.*;
import com.akifmuje.todolisttask.messages.BaseMessages;
import com.akifmuje.todolisttask.models.Status;
import com.akifmuje.todolisttask.models.ToDoItem;
import com.akifmuje.todolisttask.models.ToDoList;
import com.akifmuje.todolisttask.models.User;
import com.akifmuje.todolisttask.dto.requests.CreateToDoListRequest;
import com.akifmuje.todolisttask.services.IStatusService;
import com.akifmuje.todolisttask.services.IToDoItemService;
import com.akifmuje.todolisttask.services.IToDoListService;
import com.akifmuje.todolisttask.services.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/todolist")
public class ToDoListController {

    @Autowired
    IUserService userService;

    @Autowired
    IToDoListService toDoListService;

    @Autowired
    IToDoItemService toDoItemService;

    @Autowired
    IStatusService statusService;

    @Autowired
    private ModelMapper modelMapper;

    // 1) Create To Do List by User
    @RequestMapping(value = "/createlist", method = RequestMethod.POST)
    public @ResponseBody CreateToDoListResponse createToDoList(@RequestBody CreateToDoListRequest createToDoListRequest){
        User user = null;
        CreateToDoListResponse createToDoListResponse = new CreateToDoListResponse();

        user = userService.getUserFromToken(createToDoListRequest.token).stream().findFirst().orElse(null);

        if(user != null){

            toDoListService.save(new ToDoList(createToDoListRequest.name,user));
            createToDoListResponse.result = true;
            createToDoListResponse.message = "List is created";
        }
        else{
            createToDoListResponse.result = false;
            createToDoListResponse.message = "Token is wrong.";
        }

        return createToDoListResponse;
    }


    // 2) List all of user's to do list
    @RequestMapping(value = "",method = RequestMethod.POST)
    public @ResponseBody GetToDoListResponse getLists(@RequestBody GetToDoListRequest getToDoListRequest){

        User user = null;
        GetToDoListResponse getToDoListResponse = new GetToDoListResponse();

        user = userService.getUserFromToken(getToDoListRequest.token).stream().findFirst().orElse(null);

        if(user != null){
            List<ToDoList> toDoLists = toDoListService.getToDoLists(user.getId());
            for (ToDoList t: toDoLists) {

                com.akifmuje.todolisttask.dto.models.ToDoList dtoTodoList = new com.akifmuje.todolisttask.dto.models.ToDoList();
                dtoTodoList.id = t.getId();
                dtoTodoList.name = t.getName();
                dtoTodoList.created_date = t.getCreated_date();
                dtoTodoList.updated_date = t.getUpdated_date();
                dtoTodoList.user_id = t.getUser().getId();
                getToDoListResponse.lists.add(dtoTodoList);

                //getToDoListResponse.lists.add(modelMapper.map(t, com.akifmuje.todolisttask.dto.models.ToDoList.class));
            }

            getToDoListResponse.result = true;
            getToDoListResponse.message = "User's lists was successfully listed";

        }
        else{
            getToDoListResponse.result = false;
            getToDoListResponse.message = "Token is wrong.";
        }

        return getToDoListResponse;
    }


    // 3) Delete users's to do list
    @RequestMapping(value = "/deletelist", method = RequestMethod.POST)
    public @ResponseBody DeleteToDoListResponse deleteList(@RequestBody DeleteToDoListRequest deleteToDoListRequest){

        User user;
        ToDoList toDoList;
        DeleteToDoListResponse deleteToDoListResponse = new DeleteToDoListResponse();

        user = userService.getUserFromToken(deleteToDoListRequest.token).stream().findFirst().orElse(null);
        toDoList = toDoListService.getListFromId(deleteToDoListRequest.list_id).stream().findFirst().orElse(null);

        BaseMessages baseMessages = new BaseMessages(user,toDoList);

        if(baseMessages.result == true){

            toDoItemService.deleteListItems(toDoList.getId());
            toDoListService.deleteToDoList(deleteToDoListRequest.list_id);

            deleteToDoListResponse.message = "List was successfully deleted";
        }
        else {
            deleteToDoListResponse.message = baseMessages.message;
        }

        deleteToDoListResponse.result = baseMessages.result;

        return deleteToDoListResponse;
    }


    // 4) Add to do item to existing list
    @RequestMapping(value = "addtodoitem", method = RequestMethod.POST)
    public @ResponseBody AddListOfToDoItemResponse addToDoItem(@RequestBody AddListOfToDoItemRequest addListOfToDoItemRequest){

        User user;
        ToDoList toDoList;
        Status status;
        AddListOfToDoItemResponse addListOfToDoItemResponse = new AddListOfToDoItemResponse();

        user = userService.getUserFromToken(addListOfToDoItemRequest.token).stream().findFirst().orElse(null);
        toDoList = toDoListService.getListFromId(addListOfToDoItemRequest.list_id).stream().findFirst().orElse(null);
        status = statusService.getNotCompletedStatus().stream().findFirst().orElse(null);

        BaseMessages baseMessages = new BaseMessages(user,toDoList);

        if (baseMessages.result == true){

            toDoItemService.save(new ToDoItem(
                    addListOfToDoItemRequest.name,
                    addListOfToDoItemRequest.description,
                    addListOfToDoItemRequest.deadline,
                    toDoList,
                    status
            ));

            addListOfToDoItemResponse.message = "To do item was successfully created";
        }
        else {
            addListOfToDoItemResponse.message = baseMessages.message;
        }
        addListOfToDoItemResponse.result = baseMessages.result;

        return addListOfToDoItemResponse;
    }

}
