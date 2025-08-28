package com.mega.mapper;

import com.mega.entity.pjtMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface pjtMenuMapper {
    List<pjtMenu> selectMenusByUser(String userid);
    List<pjtMenu> findAll();
    pjtMenu findById(Long menuId);
    void insertMenu(pjtMenu menu);
    void updateMenu(pjtMenu menu);
    void deleteMenu(Long menuId);
}