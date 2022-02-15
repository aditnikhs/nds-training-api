package com.training.api.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.training.api.entity.User;
import com.training.api.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class Controller {
    @Autowired
    UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
		try {
			List<User> users = new ArrayList<User>();
            userService.findAll().forEach(users::add);
			if (users.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

    @GetMapping("/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
		Optional<User> user = userService.findById(id);
		if (user.isPresent()) {
			return new ResponseEntity<>(user.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

    @PostMapping("/users")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		try {
			User _user = userService
					.save(new User(user.getUser_name(), 
                    new Timestamp(new Date().getTime()), 
                    user.getCreated_by(), 
                    new java.sql.Date(new Date().getTime()),
                    user.getUpdated_by(),
                    user.getDeleted_date(),
                    "A"));
			return new ResponseEntity<>(_user, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

    @PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
		Optional<User> userData = userService.findById(id);
		if (userData.isPresent()) {
			User _user = userData.get();
			_user.setUser_name(user.getUser_name());
			_user.setUpdated_date(new java.sql.Date(new Date().getTime()));
			_user.setUpdated_by(user.getUpdated_by());
			return new ResponseEntity<>(userService.save(_user), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

    @DeleteMapping("/users/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable("id") long id, @RequestBody User user) {
		Optional<User> userData = userService.findById(id);
		if (userData.isPresent()) {
			User _user = userData.get();
			_user.setDeleted_date(new java.sql.Date(new Date().getTime()));
			_user.setRec_status("N");
			return new ResponseEntity<>(userService.save(_user), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
