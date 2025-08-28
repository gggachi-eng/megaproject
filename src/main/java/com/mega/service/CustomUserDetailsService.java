package com.mega.service;

import com.mega.entity.pjtUser;
import com.mega.mapper.pjtUserMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  private final pjtUserMapper userMapper;
  //private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
    pjtUser u = userMapper.findByUserid(userid);
    if (u == null) throw new UsernameNotFoundException(userid);
    return new org.springframework.security.core.userdetails.User(
      u.getUserid(), u.getPassword(), List.of(new SimpleGrantedAuthority(u.getRole())));
  }
}
