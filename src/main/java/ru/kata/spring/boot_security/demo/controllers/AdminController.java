package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.services.RolesService;
import ru.kata.spring.boot_security.demo.services.UsersServices;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UsersServices usersServices;

    private final RolesService rolesService;

    @Autowired
    public AdminController(UsersServices usersServices, RolesService rolesService) {
        this.usersServices = usersServices;
        this.rolesService = rolesService;
    }

    @GetMapping(value = "")
    public String pageOfUsers(ModelMap model, Principal principal) {
        model.addAttribute("authorized_user", principal.getName());
        model.addAttribute("list_roles", rolesService.getAllRoles());
        return "/admin/admin";
    }

    @GetMapping(value = "/user_info")
    public String pageUserInfoById(Principal principal, Model model) throws UsernameNotFoundException {
        model.addAttribute("user", usersServices.findUserByUserLogin(principal.getName()));
        model.addAttribute("authorized_user", principal.getName());
        return "/admin/user_info";
    }
}
