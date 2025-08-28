package com.mega.controller;

import com.mega.entity.pjtUser;
import com.mega.service.pjtAuthService;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class pjtAuthController {

    private final pjtAuthService authService;

    // public pjtAuthController(pjtAuthService authService) {
    //     this.authService = authService;
    // }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody pjtUser user){
        authService.registerUser(user);
        return ResponseEntity.ok().body("registered");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody pjtUser user) {
        authService.signup(user);
        return ResponseEntity.ok().build();
    }

    @Data
    static class LoginRequest {
        private String userid;
        private String password;
    }

    @Data
    static class LoginResponse {
        private String token;
        public LoginResponse(String token) { this.token = token; }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
        String token = authService.login(req.getUserid(), req.getPassword());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}