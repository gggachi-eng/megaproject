package com.mega.service;

//import org.springframework.security.core.userdetails.UserDetailsService;
import com.mega.entity.pjtUser;

public interface pjtAuthService /*extends UserDetailsService*/ {
    void registerUser(pjtUser user);
    void signup(pjtUser user);
    String login(String userid, String password);
    pjtUser loadUserByUserid(String userid);
}