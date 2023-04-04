package com.internetx.service.impl;

import com.internetx.domain.Role;
import com.internetx.domain.User;
import com.internetx.service.IUserService;
import org.junit.jupiter.api.MethodOrderer;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Random;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTests
{

    @Autowired
    protected IUserService userService;

    @Test
    @Order(1)
    public void testSaveUser() {

        User user = new User();
        user.setLogin("loginusertestuser");
        user.setPassword("Password");
        user.setFname("fname" + new Random().nextLong(1000));
        user.setLname("lname" + new Random().nextLong(1000));
        user.setEmail("mail@mail.de" + new Random().nextLong(1000));

        Role role = new Role();
        role.setRoleAdmin(false);
        role.setRoleDevelop(false);

        user.setRole(role);

        userService.save(user);

    }

    @Test
    @Order(2)
    public void testFindUSerById() {

        userService.findUserByID(1);

    }

    /*@Test

    public void testDeleteUser() {

        business.deleteUser(112);

    }*/

    @Test
    @Order(3)
    public void testCountUsers() {

        userService.countUsers();

    }

    @Test
    @Order(4)
    public void testFindAllUsers() {

        userService.findAllUsers();

    }

    @Test
    @Order(5)
    public void testGeneratePassword() {

        String password = "oW663@ZFRhV1";

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        System.out.println(encoder.encode(password));



    }

    @Test
    @Order(6)
    public void testCleanUpDatabase() {

        userService.cleanUpDataBaseAfterID(4);



    }

}
