package com.example.ContactList.security.service.impl;

import com.example.ContactList.entity.database.Role;
import com.example.ContactList.entity.database.User;
import com.example.ContactList.repository.UserRepository;
import com.example.ContactList.security.AuthenticationRequest;
import com.example.ContactList.security.AuthenticationResponse;
import com.example.ContactList.security.RegisterRequest;
import com.example.ContactList.security.service.AuthenticationService;
import com.example.ContactList.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<AuthenticationResponse> register(RegisterRequest request) {
        User user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        Optional<User> usr = repository.findByEmail(user.getEmail());
        if (usr != null && usr.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        repository.save(user);
        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(AuthenticationResponse.builder()
                .token(token)
                .firstname(user.getFirstName())
                .build());
    }

    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword()
        ));

        Optional<User> user = repository.findByEmail(request.getEmail());
        if (user == null || !user.isPresent()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        String token = jwtService.generateToken(user.get());
        return ResponseEntity.ok(AuthenticationResponse.builder()
                .token(token)
                .firstname(user.get().getFirstName())
                .build());
    }
}
