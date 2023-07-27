package com.ohgiraffers.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

//@Repository
public class UserDAO {

    private final PasswordEncoder passwordEncoder;

    public UserDAO(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public PrincipalDetails findUser(String username){
        PrincipalDetails user = null;

        if(username.equals("user")){
            String pass = passwordEncoder.encode("pass");
            user = new PrincipalDetails(1,"user",pass,"ADMIN");
        }

        return user;
    }
}