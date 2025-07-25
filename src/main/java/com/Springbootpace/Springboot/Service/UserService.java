package com.Springbootpace.Springboot.Service;

import com.Springbootpace.Springboot.Entity.User;
import com.Springbootpace.Springboot.Entity.UserDto;
import com.Springbootpace.Springboot.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository repo;
    public UserService(UserRepository repo) { this.repo = repo; }

    /* ---------- Register ---------- */
    public UserDto registerUser(UserDto in) {
        String username = in.getUsername() != null ? in.getUsername().trim() : "";
        String email    = in.getEmail()    != null ? in.getEmail().trim().toLowerCase() : "";

        if (!username.isBlank() && repo.existsByUsername(username))
            throw new RuntimeException("Username already taken");
        if (!email.isBlank() && repo.existsByEmail(email))throw new RuntimeException("E‑mail already taken");

        User u = new User();
        u.setUsername(username);
        u.setEmail(email.isBlank() ? null : email);
        u.setPassword(in.getPassword() != null ? in.getPassword().trim() : "");
        u.setProfilePictureUrl(in.getProfilePictureUrl());

        return toDto(repo.save(u));
    }

    /* ---------- Login with E‑mail ---------- */
    /* ---------- Login with E‑mail ---------- */
    public UserDto loginWithEmail(String email, String password) {

        /* ---- 1. Validate inputs ---- */
        if (email == null || email.isBlank())
            throw new RuntimeException("Email is required");
        if (password == null || password.isBlank())
            throw new RuntimeException("Password is required");

        /* ---- 2. Normalise e‑mail (trim + lower‑case) ---- */
        String cleanEmail = email.strip().toLowerCase();

        /* ---- 3. Look up user, case‑insensitively ---- */
        User user = repo.findByEmailIgnoreCase(cleanEmail)
                        .orElseThrow(() -> new RuntimeException("Invalid credentials (email)"));

        /* ---- 4. Verify password (plain text for now) ---- */
        if (!password.equals(user.getPassword()))
            throw new RuntimeException("Invalid credentials (password)");

        /* ---- 5. Update last‑login & return DTO ---- */
        user.setLastLogin(LocalDateTime.now());
        return toDto(repo.save(user));
    }

    /* ---------- (legacy) Login with Username ---------- */
    public UserDto login(String username, String password) {
        if (username == null || username.isEmpty())
            throw new RuntimeException("Username is required");
        if (password == null || password.isEmpty())
            throw new RuntimeException("Password is required");

        User u = repo.findByUsername(username)
                     .orElseThrow(() -> new RuntimeException("Invalid credentials (username)"));

        if (!u.getPassword().equals(password))
            throw new RuntimeException("Invalid credentials (password)");

        u.setLastLogin(LocalDateTime.now());
        return toDto(repo.save(u));
    }

    /* ---------- DTO mapping ---------- */
    public UserDto toDto(User u) {
        UserDto d = new UserDto();
        d.setId(u.getId());
        d.setUsername(u.getUsername());
        d.setEmail(u.getEmail());
        d.setProfilePictureUrl(u.getProfilePictureUrl());
        d.setCreatedOn(u.getCreatedOn());
        d.setLastLogin(u.getLastLogin());
        return d;
    }

    /* ---------- Misc helper methods (unchanged) ---------- */
    public UserDto get(Long id)            { return toDto(repo.findById(id).orElseThrow(() -> new RuntimeException("User not found"))); }
    public User   updateProfilePicture(Long id, String url) { User u=repo.findById(id).orElseThrow(() -> new RuntimeException("User not found")); u.setProfilePictureUrl(url); return repo.save(u); }
    public User   updateProfile(Long id, UserDto dto) {
        User u = repo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        if (dto.getUsername()!=null && !dto.getUsername().isBlank()) u.setUsername(dto.getUsername().trim());
        if (dto.getPassword()!=null && !dto.getPassword().isBlank()) u.setPassword(dto.getPassword().trim());
        if (dto.getProfilePictureUrl()!=null && !dto.getProfilePictureUrl().isBlank()) u.setProfilePictureUrl(dto.getProfilePictureUrl());
        return repo.save(u);
    }
}
