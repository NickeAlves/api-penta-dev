package com.api_penta_dev.controllers;

import com.api_penta_dev.dto.LoginRequestDTO;
import com.api_penta_dev.dto.RegisterRequestDTO;
import com.api_penta_dev.dto.ResponseDTO;
import com.api_penta_dev.models.User;
import com.api_penta_dev.repositories.UserRepository;
import com.api_penta_dev.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequestDTO body) {
        User user = userRepository.findByEmail(body.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = tokenService.generateToken(user.getId(), user.getName(), false);
            return ResponseEntity.ok(new ResponseDTO(user.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody RegisterRequestDTO body) {
        Optional<User> existingUser = userRepository.findByEmail(body.email());
        if (existingUser.isEmpty()) {
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setEmail(body.email());
            newUser.setName(body.name());
            userRepository.save(newUser);

            String token = tokenService.generateToken(newUser.getId(), newUser.getName(), false);
            return ResponseEntity.ok(new ResponseDTO(newUser.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/guest")
    public ResponseEntity<ResponseDTO> guestLogin() {
        String guestId = UUID.randomUUID().toString();
        String guestName = "Guest_" + guestId.substring(0, 5);
        String token = tokenService.generateToken(guestId, guestName, true);

        return ResponseEntity.ok(new ResponseDTO(guestName, token));
    }
}
