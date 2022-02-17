package com.example.springboot.utility;

import com.example.springboot.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class JWTUser {

    private final JWTUtility jwtUtility;

    private final CustomUserDetailsService customUserDetailsService;

    public String getId(HttpServletRequest httpServletRequest) {
        String authorization = httpServletRequest.getHeader("Authorization");
        String token = null;
        String username = null;

        if (null != authorization && authorization.startsWith("Bearer ")) {
            token = authorization.substring(7);
            username = jwtUtility.getUsernameFromToken(token);
        }
        return username;
    }
}
