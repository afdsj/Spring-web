package com.ohgiraffers.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthService implements UserDetailsService {

    private final UserDAO userDAO;

    public AuthService(PasswordEncoder passwordEncoder) {
        this.userDAO = new UserDAO(passwordEncoder);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PrincipalDetails user = userDAO.findUser(username);
        if (Objects.isNull(user)){
            throw new UsernameNotFoundException("회원 정보가 없습니다");
        }
        return user;
    }
}
