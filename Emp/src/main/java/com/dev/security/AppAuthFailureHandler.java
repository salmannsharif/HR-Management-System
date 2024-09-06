//package com.dev.security;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//
//import java.io.IOException;
//
//// This class is no longer needed because we use JWT authentication instead of form-based authentication.
//
//public class AppAuthFailureHandler implements AuthenticationFailureHandler {
//    @Override
//    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
//        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//        response.getWriter().write("Invalid login credentials! Error is : " + exception.getMessage());
//    }
//}