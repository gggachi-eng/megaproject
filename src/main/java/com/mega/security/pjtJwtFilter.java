package com.mega.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class pjtJwtFilter extends OncePerRequestFilter {

    private final pjtJwtUtil jwtUtil;
    private final UserDetailsService userDetailsService; // Spring Security 기본 UserDetailsService
    
    public pjtJwtFilter(pjtJwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {


        // ✅ 인증이 필요 없는 경로는 필터를 타지 않고 통과
        String path = request.getRequestURI();
        System.out.println("path["+path+"] auth["+request.getHeader("Authorization")+"]");
        if (path.startsWith("/api/auth")) {
            chain.doFilter(request, response);
            return;
        }
        final String authHeader = request.getHeader("Authorization"); // Authorization 헤더에서 JWT 추출
        String userid = null;
        String jwtToken = null;

        // Authorization 헤더에서 "Bearer "로 시작하는 JWT 토큰 확인
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);  // "Bearer " 이후에 있는 토큰만 추출

            try {
                // JWT 토큰에서 사용자 이름을 추출
                userid = jwtUtil.getUseridFromToken(jwtToken);
            } catch (Exception e) {
                logger.warn("JWT 토큰 파싱 실패: " + e.getMessage());
            }
        }

        // 사용자 이름이 있고 SecurityContext에 인증 정보가 없다면 인증 처리
        if (userid != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // UserDetailsService를 사용하여 사용자의 정보를 로드
            UserDetails userDetails = userDetailsService.loadUserByUsername(userid);

            // JWT 토큰이 유효한지 검증
            if (jwtUtil.validateToken(jwtToken, userDetails.getUsername())) {
                // 인증된 사용자의 권한 정보를 기반으로 인증 토큰 생성
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // 인증된 사용자 정보 설정
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // SecurityContext에 인증 정보 저장
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 필터 체인을 계속해서 처리
        chain.doFilter(request, response);
    }
}
