package com.Springbootpace.Springboot.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "users")
public class User {

    /* ---------- Fields ---------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String email;

    private String profilePictureUrl;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdOn;

    private LocalDateTime lastLogin;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    /* ---------- Constructors ---------- */
    public User() {}

    public User(String username, String password, String profilePictureUrl) {
        this.username = username;
        this.password = password;
        this.profilePictureUrl = profilePictureUrl;
    }

    public User(String username, String password, String email, String profilePictureUrl) {
        this(username, password, profilePictureUrl);
        this.email = email;
    }

    /* ---------- Lifecycle Hooks ---------- */
    @PrePersist
    protected void onCreate() {
        this.createdOn = LocalDateTime.now();
    }

    /* ---------- Getters & Setters ---------- */
    public Long getId() { return id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getProfilePictureUrl() { return profilePictureUrl; }
    public void setProfilePictureUrl(String profilePictureUrl) { this.profilePictureUrl = profilePictureUrl; }

    public LocalDateTime getCreatedOn() { return createdOn; }

    public LocalDateTime getLastLogin() { return lastLogin; }
    public void setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; }

    public List<Task> getTasks() { return tasks; }
    public void setTasks(List<Task> tasks) { this.tasks = tasks; }
}
