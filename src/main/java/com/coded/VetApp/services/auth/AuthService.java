package com.coded.VetApp.services.auth;

import com.coded.VetApp.bo.auth.authenticationResponse.AuthenticationResponse;
import com.coded.VetApp.bo.auth.authenticationResponse.UserAuthenticationResponse;
import com.coded.VetApp.bo.auth.authenticationResponse.VetAuthenticationResponse;
import com.coded.VetApp.bo.auth.requests.LoginRequest;
import com.coded.VetApp.bo.auth.requests.UserSignupRequest;
import com.coded.VetApp.bo.auth.requests.VetSignupRequest;

public interface AuthService {
    UserAuthenticationResponse signup(UserSignupRequest signupRequest);
    VetAuthenticationResponse vetSignup(VetSignupRequest signupRequest);

    AuthenticationResponse<?> login(LoginRequest loginRequest);
}
