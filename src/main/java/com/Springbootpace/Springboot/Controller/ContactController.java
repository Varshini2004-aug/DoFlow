package com.Springbootpace.Springboot.Controller;

import com.Springbootpace.Springboot.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ContactController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/contact")
    public ResponseEntity<String> contact(@RequestBody Map<String, String> payload) {
        String name = payload.get("name");
        String email = payload.get("email");
        String msg = payload.get("msg");

        // Mail subject and message body
        String subject = "New Contact Message from " + name;
        String body = "Sender Email: " + email + "\n\nMessage:\n" + msg;

        // Use your Mailtrap inbox email as recipient
        String recipient = "your-inbox@mailtrap.io"; // 🔁 Replace this

        emailService.sendContactMessage(recipient, subject, body);

        return ResponseEntity.ok("Message sent successfully!");
    }
}
