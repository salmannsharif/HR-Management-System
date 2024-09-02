package com.dev.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

// This class is no longer needed because we use JWT authentication instead of form-based authentication.

public class AppAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final String jwtSecret;

    private final long EXPIRATION_DATE = 1000 * 60 * 24 * 7; // for 7 days set by Salman

    public AppAuthSuccessHandler(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws java.io.IOException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        String role = userDetails.getAuthorities().iterator().next().getAuthority();

//        String token = generateToken(username, role);

        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json");
//        response.addHeader("Authorization", "Bearer " + token);
        String jsonResponse = "{ \"status\" : \"Login successful\" }";
        response.getWriter().write(jsonResponse);
    }

}
