package com.internetx.security;

import com.internetx.service.IUserService;
import com.internetx.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceDefaultImpl implements UserDetailsService {


    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

            User user = userService.findUserByEmail(email).orElseThrow( () -> new UsernameNotFoundException("User not found by email!")) ;

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                Collections.emptyList());
    }
}
