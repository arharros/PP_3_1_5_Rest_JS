package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RolesService;
import ru.kata.spring.boot_security.demo.services.UsersServices;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
        model.addAttribute("list_of_users", usersServices.listOfUsers());
        model.addAttribute("authorized_user", principal.getName());
        model.addAttribute("list_roles", rolesService.getAllRoles());
        return "/admin/admin";
    }

    @PostMapping(value = "/save")
    public String saveUser(HttpServletRequest request) {
        User newUser = new User();
        List<Role> userRoles = new ArrayList<>();
        if (usersServices.findUserByUserLogin(request.getParameter("login").toLowerCase()) != null) {
            return "/admin/user_exists";
        }
        newUser.setUserLogin(request.getParameter("login").toLowerCase());
        newUser.setPassword(request.getParameter("password"));
        addRolesForUser(request, userRoles, newUser);
        usersServices.addUser(newUser);
        return "redirect:/admin";
    }

    @GetMapping(value = "/user_info")
    public String pageUserInfoById(Principal principal, Model model) throws UsernameNotFoundException {
        model.addAttribute("user", usersServices.findUserByUserLogin(principal.getName()));
        model.addAttribute("authorized_user", principal.getName());
        return "/admin/user_info";
    }

    @PostMapping("/delete")
    public String deleteUserById(@RequestParam(name = "id") long id) {
        usersServices.deleteUserById(id);
        return "redirect:/admin";
    }

    @PostMapping("/update")
    public String updateUserInfo(HttpServletRequest request) {
        List<Role> userRoles = new ArrayList<>();
        User userForEdit = usersServices.findUserById(Long.parseLong(request.getParameter("id_user")));
        addRolesForUser(request, userRoles, userForEdit);
        usersServices.updateUser(userForEdit);
        return "redirect:/admin";
    }

    private void addRolesForUser(HttpServletRequest request, List<Role> userRoles, User userForEdit) {
        userForEdit.setFirstName(request.getParameter("first_name"));
        userForEdit.setLastName(request.getParameter("last_name"));
        userForEdit.setEmail(request.getParameter("email"));
        for (String idString : request.getParameterValues("user_roles")) {
            userRoles.add(rolesService.findRoleById(Long.parseLong(idString)));
        }
        userForEdit.setUserRoles(userRoles);
    }
}
