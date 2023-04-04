package com.internetx.service;

import com.internetx.domain.Role;
import com.internetx.domain.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    // user stuff
    long save(User user);
    User findUserByID(int id);

    Optional<User> findUserByEmail(String email);
    void deleteUser(int id);
    List<User> findAllUsers();
    int countUsers();

    // count stuff
    long save(Role role);

    void cleanUpDataBaseAfterID(int id);



}
