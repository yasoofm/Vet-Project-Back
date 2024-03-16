package com.coded.VetApp.bo.auth.authenticationResponse;

public class AuthenticationResponse<T> {
    T t;
    public AuthenticationResponse(T t){
        this.t = t;
    }
    public T func(){
        return t;
    }
}
