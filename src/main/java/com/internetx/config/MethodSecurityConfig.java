package com.internetx.config;

import com.internetx.permissionevaluator.DeletePermissionEvaluator;
import com.internetx.permissionevaluator.GetPermissionEvaluator;
import com.internetx.permissionevaluator.PutPermissionEvaluator;
import com.internetx.security.JwtTokenProvider;
import com.internetx.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {


    @Autowired
    private ApplicationContext context;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private IUserService userService;

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler =
                new DefaultMethodSecurityExpressionHandler();
        // expressionHandler.setPermissionEvaluator(new PutPermissionEvaluator2(jwtTokenProvider, userService));
        expressionHandler.setApplicationContext(context);
        return expressionHandler;
    }
    @Bean
    public PermissionEvaluator putPermissionEvaluator2() {

        return new PutPermissionEvaluator(jwtTokenProvider, userService);

    }

    @Bean
    public PermissionEvaluator getPermissionEvaluator() {

        return new GetPermissionEvaluator(jwtTokenProvider, userService);

    }

    @Bean
    public PermissionEvaluator deletePermissionEvaluator() {

        return new DeletePermissionEvaluator(jwtTokenProvider, userService);

    }




}
