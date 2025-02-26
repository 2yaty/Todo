package com.example.demo.service;


import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final TokenService jwtUtil;

    public AuthService(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService, PasswordEncoder passwordEncoder,
            UserRepository userRepository, UserService userService,
            TokenService jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse authenticate(AuthRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getMail(), request.getPassword()));
        String token = jwtUtil.generateJwt(auth);
        return new AuthResponse(token);
    }

    public AuthResponse register(RegisterRequest request) {
        if (userService.existsByUsername(request.getMail())) {
            throw new RuntimeException("Username already taken");
        }

        User user = new User();
        user.setPhone(request.getPhone());
        user.setMail(request.getMail());
        user.setPassword(request.getPassword());
        userService.registerUser(user);

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getMail(), request.getPassword()));
        String token = jwtUtil.generateJwt(auth);
        return new AuthResponse(token);
    }
}