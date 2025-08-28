package com.mega.controller;

import com.mega.service.pjtMenuService;
import com.mega.entity.pjtMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class pjtMenuController {

    @Autowired
    private pjtMenuService menuService;

    @GetMapping("/user")
    public List<pjtMenu> getUserMenus(@RequestHeader("Authorization") String authHeader) {
        return menuService.getMenusByToken(authHeader);
    }

    // ✅ 2. 전체 메뉴 목록 (flat 구조)
    @GetMapping("/menus")
    public List<pjtMenu> getAllMenus() {
        return menuService.getAll();
    }

    // ✅ 3. 메뉴 등록
    @PostMapping
    public void createMenu(@RequestBody pjtMenu menu) {
        menuService.create(menu);
    }

    // ✅ 4. 메뉴 수정
    @PutMapping
    public void updateMenu(@RequestBody pjtMenu menu) {
        menuService.update(menu);
    }

    // ✅ 5. 메뉴 삭제
    @DeleteMapping("/{menuid}")
    public void deleteMenu(@PathVariable Long menuid) {
        menuService.delete(menuid);
    }
}