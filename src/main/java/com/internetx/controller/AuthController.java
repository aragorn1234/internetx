package com.internetx.controller;

import com.internetx.repository.IUserRepository;
import com.internetx.service.IUserService;
import com.internetx.domain.User;
import com.internetx.request.AuthRequest;
import com.internetx.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
// @RequestMapping("/auth")
@Slf4j
public class AuthController {

    private IUserRepository userRepository;

    private IUserService userService;

    private PasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;

    private JwtTokenProvider jwtTokenProvider;

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) {

        /*User newUser = new User();
        newUser.setEmail(authRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(authRequest.getPassword()));

        System.out.println(newUser.getPassword().length());

        userService.save(newUser);*/

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()

                )


        );




        return ResponseEntity.ok(jwtTokenProvider.generateToken(authentication));


    }

    @PostMapping(value = "user")
    public ResponseEntity<String> createUser(@RequestBody User user) {


        user.setId(0);

        if (user.getRole() == null) {

            log.info("Role not present! User not created!");
            return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);

        } else {

            userService.save(user);
            return new ResponseEntity<>("Authorized", HttpStatus.OK);

        }


    }

    // @PreAuthorize("@putPermissionEvaluator2.hasPermission(#authentication, #id, #token, #user)")
    @PreAuthorize("@putPermissionEvaluator.hasPermission(null, #id, #token, 'role_admin') or (@putPermissionEvaluator.hasPermission(null, #id, #token, 'role_developer'))")
    @PutMapping(value = "user/{id}")
    public void putUserIsAdmin(@RequestBody User user, @PathVariable long id, @RequestHeader (name="Authorization") String token) {



        user.setId(id);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);

    }

    @GetMapping(value = "user/{id}")
    @PreAuthorize("@getPermissionEvaluator.hasPermission(null, #id, #token, 'role_admin') or (@getPermissionEvaluator.hasPermission(null, #id, #token, 'role_developer'))")
    public User getUser(@PathVariable int id, @RequestHeader (name="Authorization") String token) {

        //jwtTokenProvider.getUserMailFromToken(token.substring(7));

        return userService.findUserByID(id);

    }

    @DeleteMapping(value = "/user/{id}")
    @PreAuthorize("@deletePermissionEvaluator.hasPermission(null, #id, #token, 'role_admin')")
    public void deleteUser(@PathVariable int id, @RequestHeader (name="Authorization") String token) {

        userService.deleteUser(id);



    }



}
