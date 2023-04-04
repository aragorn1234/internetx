package com.internetx;

import com.internetx.domain.Role;
import com.internetx.domain.User;
import com.internetx.service.IUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InternetxApplication {

    public static void main(String[] args) {
        SpringApplication.run(InternetxApplication.class, args);
    }

    @Bean
    CommandLineRunner run(IUserService userService) {

        return args -> {

          userService.save(new User(1, "admin", "$2a$10$X8sXdh3HqXthmNMYClpXLuOS9.3kph5yhoD16JoaK3bJm1JJzK/V6",
                  "Max", "Admin", "admin@internetx.com", null));

          userService.save(new User(2, "developer", "$2a$10$X8sXdh3HqXthmNMYClpXLuOS9.3kph5yhoD16JoaK3bJm1JJzK/V6",
                    "Peter", "Entwickler", "developer@internetx.com", null));

          userService.save(new User(3, "tester", "$2a$10$X8sXdh3HqXthmNMYClpXLuOS9.3kph5yhoD16JoaK3bJm1JJzK/V6",
                    "Ludwig", "Tester", "tester@internetx.com", null));

            userService.save(new User(4, "productowner", "$2a$10$X8sXdh3HqXthmNMYClpXLuOS9.3kph5yhoD16JoaK3bJm1JJzK/V6",
                    "Alexander", "ProductOwner", "productowner@internetx.com", null));


          userService.save(new Role(1, 1, true, false, false,
                    false, false, false, false,
                    false, false, false));

          userService.save(new Role(2, 2, false, true, false,
                    false, false, false, false,
                    false, false, false));


            userService.save(new Role(3, 3, false, false, false,
                    false, false, false, false,
                    false, false, false));

            userService.save(new Role(4, 4, false, false, false,
                    false, false, false, false,
                    false, false, false));

        };

    }


}
