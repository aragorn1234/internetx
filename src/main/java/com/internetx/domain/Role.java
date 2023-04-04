package com.internetx.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    protected long id;
    protected long userId;
    protected boolean roleAdmin;
    protected boolean roleDevelop;
    @JsonIgnore
    protected boolean roleCctld;
    @JsonIgnore
    protected boolean roleGtld;
    @JsonIgnore
    protected boolean roleBilling;
    @JsonIgnore
    protected boolean roleRegistry;
    @JsonIgnore
    protected boolean rolePurchase_read;
    @JsonIgnore
    protected boolean rolePurchase_write;
    @JsonIgnore
    protected boolean roleSale_write;
    @JsonIgnore
    protected boolean roleSql;


}
