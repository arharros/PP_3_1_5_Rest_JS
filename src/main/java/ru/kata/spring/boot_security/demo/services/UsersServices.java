package ru.kata.spring.boot_security.demo.services;



import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UsersServices extends UserDetailsService {

    void addUser(User user);

    User findUserById(long id);

    User findUserByUserLogin(String userLogin);

    void deleteUserById(long id);

    void updateUser(User user);

    List<User> listOfUsers();

}
