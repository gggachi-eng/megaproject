package com.mega.service.impl;

import com.mega.entity.pjtMenu;
import com.mega.mapper.pjtMenuMapper;
import com.mega.security.pjtJwtUtil;
import com.mega.service.pjtMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class pjtMenuServiceImpl implements pjtMenuService {

    @Autowired
    private pjtMenuMapper menuMapper;

    @Autowired
    private pjtJwtUtil jwtUtil;

    @Override
    public List<pjtMenu> getMenusByToken(String tokenHeader) {
        String token = tokenHeader.replace("Bearer ", "");
        String userid = jwtUtil.getUseridFromToken(token);
        List<pjtMenu> flatList =  menuMapper.selectMenusByUser(userid);
        return buildMenuTree(flatList);
    }

    private List<pjtMenu> buildMenuTree(List<pjtMenu> flatList) {
        Map<String, pjtMenu> map = new HashMap<>();
        List<pjtMenu> rootMenus = new ArrayList<>();

        for (pjtMenu menu : flatList) {
            menu.setChildren(new ArrayList<>());
            map.put(menu.getMenuid(), menu);
        }

        for (pjtMenu menu : flatList) {
            if (menu.getParent_id() == null) {
                rootMenus.add(menu);
            } else {
                pjtMenu parent = map.get(menu.getParent_id());
                if (parent != null) {
                    parent.getChildren().add(menu);
                }
            }
        }

        return rootMenus;
    }

    // ✅ 2. 전체 메뉴 flat 목록 조회
    @Override
    public List<pjtMenu> getAll() {
        return menuMapper.findAll();
    }

    // ✅ 3. 메뉴 등록
    @Override
    public void create(pjtMenu menu) {
        menuMapper.insertMenu(menu);
    }

    // ✅ 4. 메뉴 수정
    @Override
    public void update(pjtMenu menu) {
        menuMapper.updateMenu(menu);
    }

    // ✅ 5. 메뉴 삭제
    @Override
    public void delete(Long menuId) {
        menuMapper.deleteMenu(menuId);
    }
}