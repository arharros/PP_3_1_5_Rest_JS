package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UsersServices;

import java.util.List;

@RestController
@RequestMapping("/admin/api")
public class AdminRestController {

    private final UsersServices usersServices;


    @Autowired
    public AdminRestController(UsersServices usersServices) {
        this.usersServices = usersServices;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> listOfUsers = usersServices.findAllUsers();
        return listOfUsers != null & !listOfUsers.isEmpty()
                ? new ResponseEntity<>(listOfUsers, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> findUserById(@PathVariable long id) {
        User user = usersServices.findUserById(id);
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/users")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        user.setUserLogin(user.getUserLogin().toLowerCase());
        if (usersServices.findUserByUserLogin(user.getUserLogin().toLowerCase()) != null) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        usersServices.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User userForUpdate) {
        usersServices.updateUser(userForUpdate);
        return new ResponseEntity<>(userForUpdate, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable long id) {
        usersServices.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
