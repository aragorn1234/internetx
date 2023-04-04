package com.internetx.permissionevaluator;

import com.internetx.domain.Role;
import com.internetx.domain.User;
import com.internetx.security.JwtTokenProvider;
import com.internetx.service.IUserService;
import com.internetx.utils.Constants;
import com.internetx.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;

@Component
@Slf4j
public class GetPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    protected IUserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public GetPermissionEvaluator(JwtTokenProvider jwtTokenProvider, IUserService userService) {

        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;

    }


    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {

        Role role = Utils.getRolesFromToken(targetType);

        if ((targetType == null) || !(permission instanceof String) || role == null) {
            return false;
        }

        String roleStringRepresentation = (String)permission;

        Map<String, Object> userRoleMapping = Utils.getUserAndRoleFromToken(targetType, jwtTokenProvider,
                userService);

        User userUpdating = (User) userRoleMapping.get(Constants.USER_STRING_REPRESENTATION);

        switch (roleStringRepresentation) {

            // admin is allowed to update everthing
            case "role_admin": {

                boolean isAdmin = role.isRoleAdmin();

                if (isAdmin) {

                    log.info("Admin can see every user");

                }
                return isAdmin;

            }

            // developer can update himself
            case "role_developer": {


                if (role.isRoleDevelop() && ((int) targetId) == userUpdating.getId()) {


                    log.info("Developer can see himself");
                    return true;


                } else {

                    log.info("Developer can not see user with id {}. Can only see himself.", (int)targetId);
                    return false;

                }



            }

            default:
                break;

        }




        return false;
    }
}
