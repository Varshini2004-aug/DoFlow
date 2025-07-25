package com.Springbootpace.Springboot.Entity;

import java.time.LocalDateTime;

/**
 * Lightweight DTO for user profile data.
 * Does NOT contain settings; those are in SettingsDto.
 */
public class UserDto {

    /* ---------- Core fields ---------- */
    private Long id;
    private String username;
    private String password;          // null in responses for security
    private String email;
    private String profilePictureUrl;
    private LocalDateTime createdOn;  // set once on first persist
    private LocalDateTime lastLogin;  // updated on every login

    /* ---------- Constructors ---------- */
    public UserDto() {}

    public UserDto(Long id, String username, String password,
                   String email, String profilePictureUrl,
                   LocalDateTime createdOn, LocalDateTime lastLogin) {
        this.id                 = id;
        this.username           = username;
        this.password           = password;
        this.email              = email;
        this.profilePictureUrl  = profilePictureUrl;
        this.createdOn          = createdOn;
        this.lastLogin          = lastLogin;
    }

    /* ---------- Getters & setters ---------- */
    public Long getId()                      { return id; }
    public void setId(Long id)               { this.id = id; }

    public String getUsername()              { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword()              { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail()                 { return email; }
    public void setEmail(String email)       { this.email = email; }

    public String getProfilePictureUrl()                 { return profilePictureUrl; }
    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public LocalDateTime getCreatedOn()      { return createdOn; }
    public void setCreatedOn(LocalDateTime createdOn) { this.createdOn = createdOn; }

    public LocalDateTime getLastLogin()      { return lastLogin; }
    public void setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; }
}
