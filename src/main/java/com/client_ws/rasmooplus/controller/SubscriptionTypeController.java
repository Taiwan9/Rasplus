package com.client_ws.rasmooplus.controller;

import com.client_ws.rasmooplus.exception.NotFoundException;
import com.client_ws.rasmooplus.model.SubscriptionType;
import com.client_ws.rasmooplus.service.SubscriptionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscription-type")
public class SubscriptionTypeController {



    @Autowired
    private SubscriptionTypeService subscriptionTypeService;


    @GetMapping
    public ResponseEntity<List<SubscriptionType>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionTypeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionType> findById(@PathVariable("id") Long id){

        return ResponseEntity.status(HttpStatus.OK).body(subscriptionTypeService.findById(id));

    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> notFoundException(NotFoundException n){
        String errorMessage = n.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

}
