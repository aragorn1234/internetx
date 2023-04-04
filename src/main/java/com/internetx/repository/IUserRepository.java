package com.internetx.repository;

import com.internetx.domain.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {

    int insert(User user);
    void update(User user);
    Optional<User> findByID(long id);

    Optional<User> findByEmail(String email);
    void deleteUser(int id);
    List<User> findAll();
    int count();


    void cleanUpDataBaseAfterID(int id);

}
