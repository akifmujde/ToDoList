package com.akifmuje.todolisttask.dto.requests;

import java.util.List;

public class AddDependencyItemRequest extends BaseRequest{
/*
    class list{
        public int list_id;
    }
*/

    public int still_waiting_id;
    public int to_to_completed_id;


    private AddDependencyItemRequest() { }
}
