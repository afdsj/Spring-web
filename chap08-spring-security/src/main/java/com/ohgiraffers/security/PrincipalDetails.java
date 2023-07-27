package com.ohgiraffers.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails {

    private int userCode;
    private String userId;
    private String userPass;
    private String role;

    public PrincipalDetails() {
    }

    public PrincipalDetails(int userCode, String userId, String userPass, String role) {
        this.userCode = userCode;
        this.userId = userId;
        this.userPass = userPass;
        this.role = role;
    }

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> role = new ArrayList<>();
        role.add(new SimpleGrantedAuthority(this.role));
        /*권한을 반환*/
        return role;
    }

    @Override
    public String getPassword() {
        /*패스워드를 반환*/
        return this.userPass;
    }

    @Override
    public String getUsername() {
        /*유저 아이디를 반환*/
        return this.userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        /*게정 활성화*/
        return true;
    }

    @Override
    public String toString() {
        return "PrincipalDetails{" +
                "userCode=" + userCode +
                ", userId='" + userId + '\'' +
                ", userPass='" + userPass + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
