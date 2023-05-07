package com.cookie.yummy.user;

public class SiteUserDTO {
    private Long id;
    private String username;
    private String email;

    public SiteUserDTO(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
