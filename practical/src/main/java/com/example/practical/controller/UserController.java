package com.example.practical.controller;

import com.example.practical.entity.User;
import com.example.practical.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;
@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {
    @Autowired
    UserService userService;

    //get list
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> read(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10")int limit )
    {
        HashMap<String, Object> response = new HashMap<>();
        response.put("page", page);
        response.put("limit", limit);
        response.put("data", userService.findAll());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //create
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody User user){
        userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    //get details
    @RequestMapping(method = RequestMethod.GET, path = "{id}")
    public ResponseEntity<Object> getDetail(@PathVariable int id){
        Optional<User> optionalUser = userService.findById(id);
        if (optionalUser.isPresent()){
            return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    //update
    @RequestMapping(method = RequestMethod.PUT, path = "{id}")
    public ResponseEntity<Object> update(@PathVariable int id, @RequestBody User updateUser){
        Optional<User> optionalUser = userService.findById(id);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setName(updateUser.getName());
            user.setAge(updateUser.getAge());
            user.setSalary(updateUser.getSalary());
            userService.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    //delete
    @RequestMapping(method = RequestMethod.DELETE, path = "{id}")
    public ResponseEntity<Object>  delete(@PathVariable int id) {
        Optional<User> optionalUser = userService.findById(id);
        if (optionalUser.isPresent()) {
            userService.delete(optionalUser.get());
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }
}
