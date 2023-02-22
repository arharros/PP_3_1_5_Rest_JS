package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UsersServices;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    private UsersServices usersServices;

    @Autowired
    public void setUsersServices(UsersServices usersServices) {
        this.usersServices = usersServices;
    }

    @GetMapping("")
    public String pageOfUser(Principal principal, Model model) {
        User userByUserLogin = usersServices.findUserByUserLogin(principal.getName());
        model.addAttribute("user_by_login", userByUserLogin);
        return "/users/user";
    }
}
