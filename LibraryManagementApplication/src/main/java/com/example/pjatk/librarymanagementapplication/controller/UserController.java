package com.example.pjatk.librarymanagementapplication.controller;

import java.util.List;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pjatk.librarymanagementapplication.model.User;
import com.example.pjatk.librarymanagementapplication.model.dto.ResponseDto;
import com.example.pjatk.librarymanagementapplication.model.dto.UserCreateDto;
import com.example.pjatk.librarymanagementapplication.service.UserService;

@Api(tags = "Users", description = "Endpoints for managing users")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @ApiOperation(value = "Create a new user", notes = "Add a new user to the system")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User successfully created"),
            @ApiResponse(code = 400, message = "Validation error"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody UserCreateDto userDto) {
        User user = new User(userDto.getEmail());
        if (userDto.getMembershipType() != null) {
            user.setMembershipType(userDto.getMembershipType());
        }
        return ResponseEntity.ok(userService.createUser(user));
    }


    @GetMapping("/cc")
    public ResponseEntity<List<User>> get(){
        userService.createUser(new User("email@email.com"));
        userService.createUser(new User("email@email.com"));
        userService.createUser(new User("email@email.com"));
        userService.createUser(new User("email@email.com"));
        userService.createUser(new User("email@email.com"));
        userService.createUser(new User("email@email.com"));
        userService.createUser(new User("email@email.com"));

        return ResponseEntity.ok(userService.getAllUsers());
    }


    @ApiOperation(value = "Get all users", notes = "Retrieve a list of all users in the system")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of users"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("/")
    public ResponseEntity<ResponseDto<List<User>>> getAllUsers(){
        ResponseDto<List<User>> responseDto = new ResponseDto<>();
        responseDto.setBody(userService.getAllUsers());
        responseDto.setStatus(HttpStatus.OK);
        return ResponseEntity.ok().body(responseDto);
    }


    @ApiOperation(value = "Get user by ID", notes = "Retrieve a specific user by their unique ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the user"),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<User>> getUserById(@PathVariable("id") Long id){
        ResponseDto<User> responseDto = new ResponseDto<>();
        try {
            responseDto.setBody(userService.getUserById(id));
        }catch (IllegalArgumentException e){
            List<String> errors = responseDto.getErrors();
            errors.add(e.getMessage());
            responseDto.setErrors(errors);
            responseDto.setStatus(HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(responseDto);
        }
        responseDto.setStatus(HttpStatus.OK);
        return ResponseEntity.ok(responseDto);
    }
}

