package com.akifmuje.todolisttask.controllers;

import com.akifmuje.todolisttask.dto.requests.ExitRequest;
import com.akifmuje.todolisttask.dto.requests.LoginRequest;
import com.akifmuje.todolisttask.dto.requests.RegistrationRequest;
import com.akifmuje.todolisttask.dto.responses.ExitResponse;
import com.akifmuje.todolisttask.dto.responses.LoginResponse;
import com.akifmuje.todolisttask.dto.responses.RegistrationResponse;
import com.akifmuje.todolisttask.messages.BaseMessages;
import com.akifmuje.todolisttask.models.User;
import com.akifmuje.todolisttask.services.IUserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@CrossOrigin
public class UserController {

    private final IUserService userService;

    private BCryptPasswordEncoder encoder;

    public UserController(IUserService userService){
        this.userService = userService;
        this.encoder = new BCryptPasswordEncoder();
    }


    public String token_Generator(){

        int length = new Random().nextInt(5)+75;

        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {

            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody LoginResponse userLogin(@RequestBody LoginRequest loginRequest){

        User user;
        LoginResponse loginResponse = new LoginResponse();

        user = userService.getUserFromRequest(loginRequest.mail).stream().findFirst().orElse(null);

        if(user != null){

            if(this.encoder.matches(loginRequest.password,user.getPassword())){
                loginResponse.result = true;
                loginResponse.message = "User was successfully login.";

                String token = token_Generator();

                loginResponse.token = token;
                userService.updateUserToken(token,user.getId());
            }
            else{
                loginResponse.result = false;
                loginResponse.message = "Password is not correct";
            }
        }
        else {

            loginResponse.result = false;
            loginResponse.message = "User not found.";
        }

        return loginResponse;
    }

    @RequestMapping(value = "/registration",method = RequestMethod.POST)
    public @ResponseBody RegistrationResponse userRegistration(@RequestBody RegistrationRequest registrationRequest){

        User user;
        String token;

        BaseMessages baseMessages = new BaseMessages(registrationRequest);
        RegistrationResponse registrationResponse = new RegistrationResponse();
        user = userService.findUserFromMail(registrationRequest.mail).stream().findFirst().orElse(null);

        if (baseMessages.result == true){
            // if this mail address not have a any user
            if(user == null){

                token = token_Generator();

                userService.createUser(new User(
                        registrationRequest.name,
                        registrationRequest.mail,
                        this.encoder.encode(registrationRequest.password),
                        token
                ));

                registrationResponse.token = token;
                registrationResponse.result = true;
                registrationResponse.message = "User was successfully created.";
            }
            else {

                registrationResponse.result = false;
                registrationResponse.message = "This mail address already exist.";
            }
        }
        else {

            registrationResponse.result = baseMessages.result;
            registrationResponse.message = baseMessages.message;
        }

        return registrationResponse;
    }

    @RequestMapping(value = "/exit", method = RequestMethod.POST)
    public @ResponseBody ExitResponse userExit(@RequestBody ExitRequest exitRequest){

        User user;
        ExitResponse exitResponse = new ExitResponse();

        if (exitRequest.token != "" && exitRequest.token != null ){
            user = userService.getUserFromToken(exitRequest.token).stream().findFirst().orElse(null);

            if(user != null){

                userService.updateUserToken("",user.getId());
                exitResponse.result = true;
                exitResponse.message = "User is successfully existed";
            }
            else {
                exitResponse.result = false;
                exitResponse.message = "User not found!";
            }
        }
        else {
            exitResponse.result = false;
            exitResponse.message = "Token is null!";
        }


        return exitResponse;
    }
}
