package com.internetx.repository.impl;

import com.internetx.domain.Role;
import com.internetx.domain.User;
import com.internetx.repository.IRoleRepository;
import com.internetx.repository.IUserRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Random;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RoleRepositoryTests {

    @Autowired
    IUserRepository userRepository;
    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Test
    @Order(3)
    public void testFindAllRoles() {

        roleRepository.findAll();

    }

    @Test
    @Order(4)
    public void testCountAll() {

        roleRepository.count();

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


        Role role = new Role();
        role.setUserId(user.getId());
        role.setRoleAdmin(true);
        role.setRoleBilling(false);
        role.setRoleDevelop(true);
        role.setRoleGtld(false);
        role.setRoleCctld(true);
        role.setRolePurchase_read(true);
        role.setRolePurchase_write(false);
        role.setRoleRegistry(true);
        role.setRoleSale_write(false);
        role.setRoleSql(true);


        roleRepository.insert(role);

    }

    @Test
    @Order(2)
    public void testUpdate() {

        Role role = new Role();
        role.setUserId(4);
        role.setRoleAdmin(true);
        role.setRoleBilling(false);
        role.setRoleDevelop(true);
        role.setRoleGtld(false);
        role.setRoleCctld(true);
        role.setRolePurchase_read(true);
        role.setRolePurchase_write(false);
        role.setRoleRegistry(true);
        role.setRoleSale_write(false);
        role.setRoleSql(true);

        roleRepository.update(role);

    }

    @Test
    @Order(5)
    public void testRoleById() {

        Optional<Role> roleOptional = roleRepository.findByID(4);

        if (roleOptional.isPresent()) {

            Role role = roleOptional.get();

            role.setRoleAdmin(true);
            roleRepository.update(role);

        }



    }

    @Test
    @Order(6)
    public void testRoleByUserId() {

        Optional<Role> role = roleRepository.findRoleByUserId(4);

        assert role.isPresent();



    }


    /*@Test
    @Order(7)
    public void testDeleterole()  {

        roleRepository.deleteRole(4);

    }*/

    @Test
    @Order(7)
    public void testCleanUpDataBaseAfterID()  {

        userRepository.cleanUpDataBaseAfterID(4);
        roleRepository.cleanUpDataBaseAfterID(4);


    }


}
