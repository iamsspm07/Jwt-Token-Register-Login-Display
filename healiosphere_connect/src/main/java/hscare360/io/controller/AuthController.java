package hscare360.io.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import hscare360.io.model.UserDetails;
import hscare360.io.repository.UserRepository;
import hscare360.io.service.AuthService;
import hscare360.io.wrapper.ApiResponse;
import hscare360.io.wrapper.LoginRequest;
import hscare360.io.wrapper.LoginResponse;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    // Register a new user
    @Transactional
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDetails user) {
        // Check if the email or number already exists in the database
        if (userRepository.findByEmail(user.getEmail()).isPresent()
                || userRepository.findByNumber(user.getNumber()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "User already exists"));
        }

        // Set the registration date for the user
        user.setRegistrationDate(LocalDateTime.now());

        // Password Hashing - Hash the password before saving it
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());  // Hash the password
        user.setPassword(hashedPassword);  // Set the hashed password

        // Save the User entity in the database
        userRepository.save(user);

        // Return success response
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "User registered successfully"));
    }

 // User login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Authenticate the user and get the JWT token
        String token = authService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        LoginResponse loginResponse = new LoginResponse(true, "Login successful.", token);
        return ResponseEntity.ok(loginResponse);
    }

    // Get all users
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }
    
    
    
    // User login endpoint
//  @PostMapping("/login")
//  public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
//      // Authenticate the user
//      String token = authService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
//
//      // Return the JWT token in response
//      return ResponseEntity.ok(new ApiResponse(true, "Login successful. Token: " + token));
//  }
}