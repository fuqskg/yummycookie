package com.cookie.yummy.entity;

import lombok.Getter;

import javax.management.relation.Role;

@Getter
public enum RoleType {

    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    RoleType(String value){
        this.value = value;
    }

    private String value;

}
