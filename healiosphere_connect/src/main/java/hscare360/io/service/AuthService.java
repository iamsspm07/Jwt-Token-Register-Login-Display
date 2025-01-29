//package hscare360.io.service;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import hscare360.io.exception.UserNotFoundException;
//import hscare360.io.model.UserDetails;
//import hscare360.io.repository.UserRepository;
//import java.nio.charset.StandardCharsets;
//import java.security.Key;
//import io.jsonwebtoken.security.Keys;
//
//
//import java.util.Date;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//
//
//@Service
//public class AuthService {
//	
//	private final UserRepository userRepository;
//    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//    
//    @Value("${jwt.secret}")
//    private String jwtSecret;
//
//    @Value("${jwt.expiration}")
//    private long jwtExpiration;
//    
//    
//    public AuthService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//    
//    public String authenticate(String email, String rawPassword) {
//        UserDetails user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new UserNotFoundException("User not found"));
//
//        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
//            throw new UserNotFoundException("Invalid credentials");
//        }
//
//        return generateJwtToken(email);
//    }
//    
//    private String generateJwtToken(String email) {
//        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
//
//        return Jwts.builder()
//                .setSubject(email)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
//                .signWith(key, SignatureAlgorithm.HS512) // Use the updated method
//                .compact();
//    }
//
//    public String hashPassword(String rawPassword) {
//        return passwordEncoder.encode(rawPassword);
//    }
//
//}



package hscare360.io.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import hscare360.io.exception.UserNotFoundException;
import hscare360.io.model.UserDetails;
import hscare360.io.repository.UserRepository;

import java.util.Date;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

@Service
public class AuthService {
    
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;
    
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public String authenticate(String email, String rawPassword) {
        UserDetails user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new UserNotFoundException("Invalid credentials");
        }

        return generateJwtToken(email);
    }
    
    private String generateJwtToken(String email) {
        // Use the secretKeyFor method to generate a secure key for HMAC-SHA algorithms
        Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512); // Generates a 512-bit key

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(secretKey) // Use the generated secure key
                .compact();
    }

    public String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
