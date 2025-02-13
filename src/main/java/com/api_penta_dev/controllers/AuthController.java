package com.api_penta_dev.controllers;

import com.api_penta_dev.dto.LoginRequestDTO;
import com.api_penta_dev.dto.RegisterRequestDTO;
import com.api_penta_dev.dto.ResponseDTO;
import com.api_penta_dev.models.User;
import com.api_penta_dev.repositories.UserRepository;
import com.api_penta_dev.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<ResponseDTO> login(@Valid @RequestBody LoginRequestDTO body) {
        Optional<User> optionalUser = userRepository.findByEmail(body.email());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(404).body(new ResponseDTO("User not found", null, null));
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(body.password(), user.getPassword())) {
            return ResponseEntity.status(401).body(new ResponseDTO("Invalid credentials", null, null));
        }

        String token = tokenService.generateToken(user);

        return ResponseEntity.ok(new ResponseDTO(user.getName(), token, user.getId().toString()));
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@Valid @RequestBody RegisterRequestDTO body) {
        if (userRepository.findByEmail(body.email()).isPresent()) {
            return ResponseEntity.status(400).body(new ResponseDTO("Email already registered", null, null));
        }
        User newUser = new User();
        newUser.setName(body.name());
        newUser.setEmail(body.email());
        newUser.setPassword(passwordEncoder.encode(body.password()));
        newUser.setRoomsCreated(List.of());

        userRepository.save(newUser);

        String token = tokenService.generateToken(newUser);

        return ResponseEntity.ok(new ResponseDTO(newUser.getName(), token, newUser.getId().toString()));
    }

    @PostMapping("/guest")
    public ResponseEntity<ResponseDTO> guestLogin() {
        String guestId = UUID.randomUUID().toString().substring(0, 8);
        String guestName = "Guest_" + guestId;

        String guestEmail = guestName + "@guest.com";

        String guestPassword = UUID.randomUUID().toString();

        User guestUser = new User();
        guestUser.setName(guestName);
        guestUser.setEmail(guestEmail);
        guestUser.setPassword(passwordEncoder.encode(guestPassword));
        guestUser.setRoomsCreated(List.of());

        userRepository.save(guestUser);

        String token = tokenService.generateToken(guestUser);

        return ResponseEntity.ok(new ResponseDTO(guestName, token, guestUser.getId().toString()));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseDTO> getUserDetails(@PathVariable String userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(404).body(new ResponseDTO("User not found", null, null));
        }

        User user = optionalUser.get();

        String token = tokenService.generateToken(user);

        return ResponseEntity.ok(new ResponseDTO(user.getName(), token, user.getId().toString()));
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDTO> logout (@RequestHeader(name = "Authorization", required = false) String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(400).body(new ResponseDTO("Invalid token format", null, null));
        }
        String authToken = token.substring(7);
        return ResponseEntity.ok(new ResponseDTO("Logged out successfully", null, null));
    }

}