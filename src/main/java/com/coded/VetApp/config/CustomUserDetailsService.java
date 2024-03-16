package com.coded.VetApp.config;

import com.coded.VetApp.bo.auth.CustomUserDetails;
import com.coded.VetApp.entities.UserEntity;
import com.coded.VetApp.repositories.UserRepo;
import javassist.NotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;

    public CustomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    private CustomUserDetails buildCustomUserDetailsOfUsername(String username) throws NotFoundException {
        UserEntity user = userRepo.findByUsername(username).orElseThrow();
        if(user == null){
            throw new NotFoundException("User not found");
        }
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setUsername(user.getUsername());
        customUserDetails.setEmail(user.getEmail());
        customUserDetails.setPassword(user.getPassword());
        customUserDetails.setId(user.getId());
        customUserDetails.setRole(user.getRole().getTitle().toString());
        customUserDetails.setPhoneNumber(user.getPhoneNumber());
        return customUserDetails;
    }
    @Override
    public CustomUserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        try {
            return buildCustomUserDetailsOfUsername(s);
        } catch (NotFoundException e){
            throw new RuntimeException(e);
        }
    }
}
