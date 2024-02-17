package com.example.ContactList.security.service;

import com.example.ContactList.security.AuthenticationRequest;
import com.example.ContactList.security.AuthenticationResponse;
import com.example.ContactList.security.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    public ResponseEntity<AuthenticationResponse> register(RegisterRequest request);
    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request);
}
