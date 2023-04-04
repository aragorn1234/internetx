package com.internetx.service.impl;

import com.internetx.domain.Role;
import com.internetx.domain.User;
import com.internetx.repository.IRoleRepository;
import com.internetx.repository.IUserRepository;
import com.internetx.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceDefaultImpl implements IUserService {

    @Autowired
    protected IUserRepository userRepository;

    @Autowired
    protected IRoleRepository roleRepository;


    @Override
    public long save(User user) {

        Optional<User> userFound = userRepository.findByID(user.getId());

        if (!userFound.isPresent()) {

            userRepository.insert(user);

        }else {

            userRepository.update(user);

        }
        if (user.getRole() != null) {
            Role role = user.getRole();
            role.setId(0);
            role.setUserId(user.getId());

            save(role);
        }

        return user.getId();
    }

    @Override
    public User findUserByID(int id) {

        Optional<User> userFound = userRepository.findByID(id);
        Optional<Role> roleFound = roleRepository.findRoleByUserId(id);

        User user = null;
        Role role = null;

        if (userFound.isPresent() && roleFound.isPresent()) {

            user = userFound.get();
            role = roleFound.get();
            user.setRole(role);

        }


        return user;
    }

    @Override
    public void deleteUser(int id) {

        userRepository.deleteUser(id);


    }

    @Override
    public List<User> findAllUsers() {

        List<User> userList = userRepository.findAll();
        userList.forEach(it -> it.setRole(roleRepository.findRoleByUserId(it.getId()).get()));

        return userList;
    }

    @Override
    public int countUsers() {
        return userRepository.count();
    }


    @Override
    public Optional<User> findUserByEmail(String email) {


        Optional<User> userFoundOptional = userRepository.findByEmail(email);
        User userFound = null;


        if (userFoundOptional.isPresent()) {

            userFound = userFoundOptional.get();

            Optional<Role> roleFound = roleRepository.findRoleByUserId(userFound.getId());

            if (roleFound.isPresent()) {
                userFound.setRole(roleFound.get());
            }




        }



        return Optional.ofNullable(userFound);
    }

    @Override
    public long save(Role role) {

        Optional<Role> roleFound = roleRepository.findRoleByUserId(role.getUserId());

        if (!roleFound.isPresent()) {

            roleRepository.insert(role);

        }else {

            roleRepository.update(role);

        }

        return role.getId();
    }

    @Override
    public void cleanUpDataBaseAfterID(int id) {
        userRepository.cleanUpDataBaseAfterID(id);
        roleRepository.cleanUpDataBaseAfterID(id);
    }
}
