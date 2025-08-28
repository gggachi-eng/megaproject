package com.mega.entity;

import lombok.*;

import java.time.LocalDateTime;

//import lombok.Data;

@Data
// @Getter
// @Setter
// @NoArgsConstructor
// @AllArgsConstructor
public class pjtUser {
    private Long id;
    private String userid;
    private String username;
    private String password; // JDBC 저장 시 BCrypt 인코딩
    private String name;
    private String email;
    private String phone;
    private String role;     // ROLE_USER 등
    private LocalDateTime createdAt;
}