package com.internetx.repository;

import com.internetx.domain.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleRepository {

    int insert(Role role);
    void update(Role role);
    Optional<Role> findByID(long id);

    Optional<Role> findRoleByUserId(long id);


    void deleteRole(int id);
    List<Role> findAll();
    int count();

    void cleanUpDataBaseAfterID(int id);



}
