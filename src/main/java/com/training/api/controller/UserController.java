package com.training.api.controller;

import com.training.api.model.UserModel;
import com.training.api.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/all")
    public String getAllUsers() {
		return userService.getAllUser();
	}

    @GetMapping("/id")
	public String getUserById(@RequestBody UserModel user) {
		return userService.findById(user);
	}

    @PostMapping("/add")
	public String createUser(@RequestBody UserModel user) {
		return userService.AddUser(user);
	}

    @PostMapping("/update")
	public String updateUser(@RequestBody UserModel user) {
		return userService.updateUser(user);
	}

    @PostMapping("/delete")
	public String deleteUser(@RequestBody UserModel user) {
		return userService.deleteUser(user);
	}
}
