package com.dev.controller;

import com.dev.exception.UserAlreadyExistException;
import com.dev.model.MyUser;
import com.dev.security.TokenManager;
import com.dev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final String ERROR_KEY = "error";

    private final UserService userService;
    private final AuthenticationManager authenticateManager;
    private final TokenManager tokenManager;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager, TokenManager tokenManager) {
        this.userService = userService;
        this.authenticateManager = authenticationManager;
        this.tokenManager = tokenManager;
    }

    @PostMapping("/signup")
    public ResponseEntity<MyUser> createUser(@RequestBody MyUser user) {
        try {
            MyUser createdUser = userService.addUser(user);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (UserAlreadyExistException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody MyUser user) {
        try {
            Authentication authentication = authenticateManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            String token = tokenManager.generateToken((UserDetails) authentication.getPrincipal());
            String refreshToken = tokenManager.generateRefreshToken((UserDetails) authentication.getPrincipal());

            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", token);
            tokens.put("refreshToken", refreshToken);

            return ResponseEntity.ok(tokens);
        } catch (AuthenticationException e) {
            return createErrorResponse("Invalid login credentials", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");
        if (refreshToken == null || refreshToken.isEmpty()) {
            return createErrorResponse("Refresh token is missing", HttpStatus.BAD_REQUEST);
        }

        try {
            String username = tokenManager.extractUsername(refreshToken);
            UserDetails userDetails = (UserDetails) userService.getUserByUsername(username);

            if (userDetails != null && tokenManager.isValidToken(refreshToken, userDetails)) {
                String newToken = tokenManager.generateToken(userDetails);
                Map<String, String> newTokenResponse = new HashMap<>();
                newTokenResponse.put("accessToken", newToken);
                return ResponseEntity.ok(newTokenResponse);
            } else {
                return createErrorResponse("Invalid refresh token", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return createErrorResponse("Invalid refresh token", HttpStatus.UNAUTHORIZED);
        }
    }

    private ResponseEntity<Map<String, String>> createErrorResponse(String message, HttpStatus status) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put(ERROR_KEY, message);
        return new ResponseEntity<>(errorResponse, status);
    }
}
