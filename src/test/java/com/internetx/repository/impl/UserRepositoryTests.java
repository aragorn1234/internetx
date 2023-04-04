package com.internetx.repository.impl;

import com.internetx.domain.User;
import com.internetx.repository.IUserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Random;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryTests {


    @Autowired
    IUserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @Order(3)
    public void testFindAllUsers() {

        userRepository.findAll();

    }

    @Test
    @Order(4)
    public void testCountAll() {

        userRepository.count();

    }

    @Test
    @Order(1)
    public void testInsert() {

        User user = new User();
        user.setLogin("login");
        user.setPassword(passwordEncoder.encode("Password"));
        user.setFname("Fname" + new Random().nextInt(1000));
        user.setLname("Lname" + new Random().nextInt(1000));
        user.setEmail("email@mail.de" + new Random().nextInt(1000));

        userRepository.insert(user);

    }

    @Test
    @Order(2)
    public void testUpdate() {

        User user = new User();
        user.setId(1);
        user.setLogin("loginloginlogin");
        user.setPassword(passwordEncoder.encode("Password"));
        user.setFname("Fname"+ new Random().nextInt(1000));
        user.setLname("Lname"+ new Random().nextInt(1000));
        user.setEmail("email@startrac.de" + new Random().nextInt(1000));

        userRepository.update(user);

    }

    @Test
    @Order(5)
    public void testUserById() {

        Optional<User> user = userRepository.findByID(2);

        assert user.isPresent();


    }

    @Test
    @Order(6)
    public void testUserByEmail() {

        Optional<User> optional = userRepository.findByEmail("email@startrac.de306");

        if (optional.isPresent()) {

            User user = optional.get();


        }




    }


    @Test
    @Order(7)
    public void testDeleteUser()  {

        userRepository.deleteUser(110);

    }

    @Test
    @Order(8)
    public void testCleanUpDatabase() {

        System.out.println("sdflkjjkdlf");
        userRepository.cleanUpDataBaseAfterID(4);


    }



}
