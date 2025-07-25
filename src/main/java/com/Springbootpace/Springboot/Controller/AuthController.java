package com.Springbootpace.Springboot.Controller;

import com.Springbootpace.Springboot.Entity.UserDto;
import com.Springbootpace.Springboot.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto user) {
        System.out.println("⚡ Incoming signup: " + user.getUsername() + " | " + user.getEmail());
        try {
            UserDto saved = userService.registerUser(user);
            System.out.println("✅ Registered: " + saved.getId());
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            System.out.println("❌ Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
        try {
            String email = payload.get("email");
            String password = payload.get("password");

            if (email == null || password == null) {
                throw new RuntimeException("Email and password are required");
            }

            UserDto dto = userService.loginWithEmail(email.trim(), password.trim());
            return ResponseEntity.ok(dto); // ✅ Return full profile on success

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(Map.of("message", e.getMessage()));
        }
    }
}
