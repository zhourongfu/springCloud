package com.weilus.entity;

import org.springframework.security.core.GrantedAuthority;

public class Auth2RoleEntity implements GrantedAuthority {

    private Long id;
    private Long userId;
    private String authority;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
