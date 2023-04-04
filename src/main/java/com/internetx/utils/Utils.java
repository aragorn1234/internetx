package com.internetx.utils;

import com.internetx.domain.Role;
import com.internetx.domain.User;
import com.internetx.security.JwtTokenProvider;
import com.internetx.service.IUserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Utils {

    public static Role getRolesFromToken(String token) {


        String[] chunks = token.substring(7).split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();


        String header = new String(decoder.decode(chunks[1]));


        Role role = null;

        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String,Object> map = mapper.readValue(header, Map.class);


            String roles = (String)map.get("roles");
            Map<String, Object> mapping = mapper.readValue(roles.replaceAll("\\'", "\\\""), Map.class);

            role = getFromMapping(mapping);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        return role;


    }

    public static Map<String, Object> getUserAndRoleFromToken(String token, JwtTokenProvider jwtTokenProvider,
                                                              IUserService userService) {

        Map<String, Object> resultMapping = new HashMap<>();

        String email = jwtTokenProvider.getUserMailFromToken(token.substring(7));

        Optional<User> optional = userService.findUserByEmail(email);

        if (optional.isPresent()) {

            User user = optional.get();
            resultMapping.put(Constants.USER_STRING_REPRESENTATION, user);
            resultMapping.put(Constants.ROLE_STRING_REPRESENTATION, user.getRole());

            return resultMapping;

        } else {

            resultMapping.put(Constants.USER_STRING_REPRESENTATION, null);
            resultMapping.put(Constants.ROLE_STRING_REPRESENTATION, null);

            return resultMapping;

        }

    }



    private static Role getFromMapping(Map<String, Object> mapping) {

        Role role = new Role((int)mapping.get("id"), (int)mapping.get("userId"), (boolean)mapping.get("roleAdmin"),
                (boolean) mapping.get("roleDevelop"), false,
                false, false,
                false,false,
                false, false,
                false);

        return role;

    }


}
