package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UsersServicesImp implements UsersServices {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersServicesImp(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User findUserById(long id) {
        Optional<User> findUser = userRepository.findById(id);
        return findUser.orElse(null);
    }

    @Override
    public User findUserByUserLogin(String userLogin) {
        return userRepository.findUserByUserLogin(userLogin);
    }

    @Transactional
    @Override
    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void updateUser(User userForUpdate) {
        User user = findUserById(userForUpdate.getIdUser());
        userForUpdate.setUserLogin(user.getUserLogin());
        userForUpdate.setPassword(user.getPassword());
        userRepository.save(userForUpdate);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll(Sort.by(Sort.Order.by("idUser")));
    }

    @Override
    public UserDetails loadUserByUsername(String userLogin) throws UsernameNotFoundException {
        User user = findUserByUserLogin(userLogin);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s not found", userLogin));
        }
        return new org.springframework.security.core.userdetails.User(user.getUserLogin(),
                user.getPassword(), user.getAuthorities());
    }
}
