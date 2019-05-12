package com.akifmuje.todolisttask.messages;

import com.akifmuje.todolisttask.dto.requests.RegistrationRequest;
import com.akifmuje.todolisttask.models.ToDoItem;
import com.akifmuje.todolisttask.models.ToDoList;
import com.akifmuje.todolisttask.models.User;
import com.akifmuje.todolisttask.services.IToDoItemService;
import com.akifmuje.todolisttask.services.IToDoListService;
import com.akifmuje.todolisttask.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseMessages {

    @Autowired
    IUserService userService;

    @Autowired
    IToDoListService toDoListService;

    @Autowired
    IToDoItemService toDoItemService;

    public boolean result;
    public String message;
    private String[] messages = {
            //0
            "Token is wrong.",
            //1
            "List is not found.",
            //2
            "List item is not found.",
            //3
            "User permission error.",
            //4
            "User name is cannot be null.",
            //5
            "User mail is cannot be null.",
            //6
            "User password is cannot be null."
    };

    public BaseMessages(){ }

    // User list confirmation
    public BaseMessages(User user, ToDoList toDoList){

        // Login Confirmation
        if (user != null){

            // List Validation
            if (toDoList != null){

                // Permission
                if ( user == toDoList.getUser()){

                    result = true;
                    message = "";
                }

                // Current user is not have permission
                else {

                    result = false;
                    message = messages[3];
                }
            }

            // List Not Found
            else {

                result = false;
                message = messages[1];
            }
        }

        // User is not logged in
        else {

            result = false;
            message = messages[0];
        }
    }


    // User list item confirmation
    public BaseMessages(User user, ToDoItem toDoItem){

        // Login Confirmation
        if (user != null){

            // List Item Validation
            if ( toDoItem != null ){

                // Permission
                if ( user == toDoItem.getTodoList().getUser() ){

                    result = true;
                    message = "";
                }

                // Current user is not have permission
                else {

                    result = false;
                    message = messages[3];
                }
            }

            // List item is Not Found
            else {

                result = false;
                message = messages[2];
            }
        }

        // User is not logged in
        else {

            result = false;
            message = messages[0];
        }

    }


    // User params confirmation
    public BaseMessages(RegistrationRequest registrationRequest){

        // User name confirmation
        if (registrationRequest.name != ""){

            // User mail confirmation
            if (registrationRequest.mail != ""){

                // User password confirmation
                if ( registrationRequest.password != ""){

                    result = true;
                    message = "";
                }

                //User password is cannot be null
                else {

                    result = false;
                    message = messages[6];
                }
            }

            // User mail is cannot be null
            else {

                result = false;
                message = messages[5];
            }
        }

        // User name is cannot be null.
        else {

            result = false;
            message = messages[4];
        }
    }
}
