package com.mega.service.impl;

import com.mega.service.pjtAuthService;
import com.mega.mapper.pjtUserMapper;
import com.mega.entity.pjtUser;
import com.mega.security.pjtJwtUtil;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class pjtAuthServiceImpl implements pjtAuthService {
    private final pjtUserMapper mapper;
    private final PasswordEncoder encoder;
    private final pjtJwtUtil jwtUtil;

    public pjtAuthServiceImpl(pjtUserMapper mapper,
                              PasswordEncoder encoder,
                              pjtJwtUtil jwtUtil) {
        this.mapper = mapper;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }
    @Override
    public void registerUser(pjtUser user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("USER");
        mapper.insertUser(user);
    }
    @Override
    public void signup(pjtUser user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        mapper.insertUser(user);
    }

    @Override
    public String login(String userid, String password) {
        pjtUser dbUser = mapper.findByUserid(userid);
        if (dbUser == null || !encoder.matches(password, dbUser.getPassword())) {
            throw new UsernameNotFoundException("Invalid credentials");
        }
        return jwtUtil.generateToken(userid);
    }

    @Override
    public pjtUser loadUserByUserid(String userid) {
        return mapper.findByUserid(userid);
    }
}
