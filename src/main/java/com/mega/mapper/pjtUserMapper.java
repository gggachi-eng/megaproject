package com.mega.mapper;

//import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mega.entity.pjtUser;

@Mapper
public interface pjtUserMapper {
    //pjtUser findByUsername(String username);
    //int insertUser(Map<String,Object> param);
    void insertUser(pjtUser user);
    pjtUser findByUserid(@Param("userid") String userid);
}