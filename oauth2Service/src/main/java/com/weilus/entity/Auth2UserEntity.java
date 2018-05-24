package com.weilus.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created by liutq on 2018/5/23.
 */
public class Auth2UserEntity implements UserDetails{

    private String username;
    private String password;
    private Integer locked; //0=nonlock 1=locked 账号是否锁定
    private Integer enable; //0=enable  1=disable 账号是否有效
    private Integer accountExpired; //0=nonExpired 1=expired  账号是否过期

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return null != accountExpired && 0 == accountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return null != locked && 0 == locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    @Override
    public boolean isEnabled() {
        return null != enable && 0 == enable;
    }

    public Integer getAccountExpired() {
        return accountExpired;
    }

    public void setAccountExpired(Integer accountExpired) {
        this.accountExpired = accountExpired;
    }


    @Override
    public String toString() {
        return "UserEntity{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", locked=" + locked +
                ", enable=" + enable +
                ", accountExpired=" + accountExpired +
                '}';
    }
}
