package com.Springbootpace.Springboot.Controller;

import com.Springbootpace.Springboot.Entity.SettingsDto;
import com.Springbootpace.Springboot.Entity.User;
import com.Springbootpace.Springboot.Entity.UserDto;
import com.Springbootpace.Springboot.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    /* ---------- constructor & cache ---------- */
    private final UserService svc;
    private final Map<Long, SettingsDto> settingsCache = new ConcurrentHashMap<>();

    public UserController(UserService svc) { this.svc = svc; }

    /* ---------- 1. GET profile ---------- */
    @GetMapping("/{id}")
    public ResponseEntity<?> getProfile(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(svc.get(id));      // returns UserDto
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(Map.of("message", ex.getMessage()));
        }
    }

    /* ---------- 2. UPDATE profile fields ---------- */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable Long id,
                                           @RequestBody UserDto dto) {
        try {
            User updated = svc.updateProfile(id, dto);
            return ResponseEntity.ok(svc.toDto(updated));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(Map.of("message", ex.getMessage()));
        }
    }

    /* ---------- 3. UPLOAD / REPLACE avatar ---------- */
    @PostMapping("/{id}/picture")                     // <‑‑ front‑end must POST here
    public ResponseEntity<?> uploadAvatar(@PathVariable Long id,
                                          @RequestParam("file") MultipartFile file) {
        if (file.isEmpty())
            return ResponseEntity.badRequest()
                                 .body(Map.of("message", "File is empty"));

        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path dest = Paths.get("uploads").resolve(fileName);
            Files.createDirectories(dest.getParent());
            Files.copy(file.getInputStream(), dest, StandardCopyOption.REPLACE_EXISTING);

            User updated = svc.updateProfilePicture(id, "/uploads/" + fileName);
            return ResponseEntity.ok(svc.toDto(updated));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("message", "Upload failed: " + ex.getMessage()));
        }
    }
    
    
    
    

    /* ---------- 4. USER settings (optional demo) ---------- */
    @GetMapping("/{id}/settings")
    public SettingsDto getSettings(@PathVariable Long id) {
        return settingsCache.getOrDefault(id, defaultSettings());
    }

    @PutMapping("/{id}/settings")
    public SettingsDto saveSettings(@PathVariable Long id,
                                    @RequestBody SettingsDto dto) {
        settingsCache.put(id, dto);
        return dto;
    }

    /* ---------- helper: default settings ---------- */
    private SettingsDto defaultSettings() {
        SettingsDto d = new SettingsDto();
        d.setDarkMode(false);
        d.setFontSize("MEDIUM");
        d.setEmailNotif(false);
        d.setTaskReminder(false);
        d.setAutoSave(true);
        d.setFocusMode(false);
        return d;
    }
}
