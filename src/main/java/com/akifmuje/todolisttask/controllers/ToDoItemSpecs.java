package com.akifmuje.todolisttask.controllers;

import com.akifmuje.todolisttask.models.ToDoItem;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ToDoItemSpecs {
    public static Specification<ToDoItem> example(){
        return new Specification<ToDoItem>() {
            @Override
            public Predicate toPredicate(Root<ToDoItem> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                
                return null;
            }
        };
    }
}
