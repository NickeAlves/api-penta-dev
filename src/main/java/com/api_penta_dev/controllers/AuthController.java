package com.api_penta_dev.controllers;

import com.api_penta_dev.dto.LoginRequestDTO;
import com.api_penta_dev.dto.RegisterRequestDTO;
import com.api_penta_dev.dto.ResponseDTO;
import com.api_penta_dev.models.User;
import com.api_penta_dev.repositories.UserRepository;
import com.api_penta_dev.infra.security.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequestDTO body) {
        Optional<User> optionalUser = userRepository.findByEmail(body.email());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(404).body(new ResponseDTO("User not found", null));
        }

        User user = optionalUser.get();
        if (!passwordEncoder.matches(body.password(), user.getPassword())) {
            return ResponseEntity.status(401).body(new ResponseDTO("Invalid credentials", null));
        }

        String token = tokenService.generateToken(user);
        return ResponseEntity.ok(new ResponseDTO(user.getName(), token));
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody RegisterRequestDTO body) {
        if (userRepository.findByEmail(body.email()).isPresent()) {
            return ResponseEntity.status(400).body(new ResponseDTO("Email already registered", null));
        }

        User newUser = new User();
        newUser.setPassword(passwordEncoder.encode(body.password()));
        newUser.setEmail(body.email());
        newUser.setName(body.name());
        userRepository.save(newUser);

        String token = tokenService.generateToken(newUser);
        return ResponseEntity.ok(new ResponseDTO(newUser.getName(), token));
    }

    @PostMapping("/guest")
    public ResponseEntity<ResponseDTO> guestLogin() {
        String guestId = UUID.randomUUID().toString().substring(0, 5);
        String guestName = "Guest_" + guestId;

        User guestUser = new User();
        guestUser.setName(guestName);
        guestUser.setEmail(guestName + "@guest.com");
        guestUser.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
        userRepository.save(guestUser);

        String token = tokenService.generateToken(guestUser);
        return ResponseEntity.ok(new ResponseDTO(guestName, token));
    }
}
