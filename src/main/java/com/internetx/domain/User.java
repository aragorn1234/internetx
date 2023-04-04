package com.internetx.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    protected long id;
    protected String login;
    protected String password;
    protected String fname;
    protected String lname;
    // @JsonIgnore
    protected String email;

    protected Role role;

}
