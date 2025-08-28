package com.mega.service;

import com.mega.entity.pjtMenu;
import java.util.List;

public interface pjtMenuService {
    List<pjtMenu> getMenusByToken(String token);
    List<pjtMenu> getAll();
    void create(pjtMenu m);
    void update(pjtMenu m);
    void delete(Long menuId);
}