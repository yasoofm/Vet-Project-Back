package com.coded.VetApp.controllers;

import com.coded.VetApp.bo.auth.authenticationResponse.AuthenticationResponse;
import com.coded.VetApp.bo.auth.authenticationResponse.UserAuthenticationResponse;
import com.coded.VetApp.bo.auth.authenticationResponse.VetAuthenticationResponse;
import com.coded.VetApp.bo.auth.requests.LoginRequest;
import com.coded.VetApp.bo.auth.requests.UserSignupRequest;
import com.coded.VetApp.bo.auth.requests.VetSignupRequest;
import com.coded.VetApp.services.auth.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("user-signup")
    public ResponseEntity<UserAuthenticationResponse> signup(@RequestBody UserSignupRequest signupRequest){
        UserAuthenticationResponse response = authService.signup(signupRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("vet-signup")
    public ResponseEntity<VetAuthenticationResponse> vetSignUp(@RequestBody VetSignupRequest signupRequest){
        VetAuthenticationResponse response = authService.vetSignup(signupRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("signin")
    public ResponseEntity<?> signin(@RequestBody LoginRequest loginRequest){
        AuthenticationResponse<?> response = authService.login(loginRequest);
        return ResponseEntity.ok(response.func());
    }
}
