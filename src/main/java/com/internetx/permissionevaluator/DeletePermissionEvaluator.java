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
public class DeletePermissionEvaluator implements PermissionEvaluator {

    @Autowired
    protected IUserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public DeletePermissionEvaluator(JwtTokenProvider jwtTokenProvider, IUserService userService) {

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

            // admin is allowed to delete every user except himself
            case "role_admin": {

                boolean isAdmin = role.isRoleAdmin();

                if (isAdmin && ((int) targetId) == userUpdating.getId()) {

                    log.info("Admin can delete every user except himself");
                    return false;

                } else if(isAdmin) {

                    log.info("Admins can delete every user.");

                } else {

                    log.info("Only admins can delete users. This user does not have admin rights!");

                }

                return isAdmin;

            }

            default:
                break;

        }




        return false;
    }
}
