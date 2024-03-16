package com.coded.VetApp.services.auth;

import com.coded.VetApp.bo.auth.*;
import com.coded.VetApp.bo.auth.authenticationResponse.AuthenticationResponse;
import com.coded.VetApp.bo.auth.authenticationResponse.UserAuthenticationResponse;
import com.coded.VetApp.bo.auth.authenticationResponse.VetAuthenticationResponse;
import com.coded.VetApp.bo.auth.requests.LoginRequest;
import com.coded.VetApp.bo.auth.requests.UserSignupRequest;
import com.coded.VetApp.bo.auth.requests.VetSignupRequest;
import com.coded.VetApp.config.CustomUserDetailsService;
import com.coded.VetApp.config.JWTUtil;
import com.coded.VetApp.entities.*;
import com.coded.VetApp.repositories.*;
import com.coded.VetApp.utils.enums.Role;
import com.coded.VetApp.utils.enums.Status;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService{

    private final UserRepo userRepo;
    private final StatusRepo statusRepo;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JWTUtil jwtUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepo roleRepo;
    private final VetRepo vetRepo;
    private final SpecialityRepo specialityRepo;
    public AuthServiceImpl(UserRepo userRepo, StatusRepo statusRepo, AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService, JWTUtil jwtUtil, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepo roleRepo, VetRepo vetRepo, SpecialityRepo specialityRepo) {
        this.userRepo = userRepo;
        this.statusRepo = statusRepo;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtil = jwtUtil;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepo = roleRepo;
        this.vetRepo = vetRepo;
        this.specialityRepo = specialityRepo;
    }

    @Override
    public UserAuthenticationResponse signup(UserSignupRequest signupRequest) {
        if(signupRequest.getUsername().isEmpty())
            throw new NullPointerException("username is empty");
        if(signupRequest.getPassword().isEmpty())
            throw new NullPointerException(("password is empty"));

        RoleEntity role = roleRepo.findRoleEntityByTitle(Role.user.name()).orElseThrow();
        UserEntity user = new UserEntity();
        user.setUsername(signupRequest.getUsername().toLowerCase());
        user.setPassword(bCryptPasswordEncoder.encode(signupRequest.getPassword()));
        user.setEmail(signupRequest.getEmail());
        user.setPhoneNumber(signupRequest.getPhoneNumber());
        user.setRole(role);
        userRepo.save(user);

        String username = signupRequest.getUsername().toLowerCase();

        CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(username);
        String accessToken = jwtUtil.generateToken(customUserDetails);

        UserAuthenticationResponse response = new UserAuthenticationResponse();
        response.setUsername(signupRequest.getUsername());
        response.setEmail(signupRequest.getEmail());
        response.setId(customUserDetails.getId());
        response.setRole(Role.user.name());
        response.setToken(accessToken);
        response.setPhoneNumber(signupRequest.getPhoneNumber());

        return response;
    }

    @Override
    public VetAuthenticationResponse vetSignup(VetSignupRequest signupRequest){
        if(signupRequest.getUsername().isEmpty())
            throw new NullPointerException("username is empty");
        if(signupRequest.getPassword().isEmpty())
            throw new NullPointerException(("password is empty"));

        RoleEntity role = roleRepo.findRoleEntityByTitle(Role.vet.name()).orElseThrow();

        UserEntity user = new UserEntity();
        user.setUsername(signupRequest.getUsername().toLowerCase());
        user.setEmail(signupRequest.getEmail());
        user.setRole(role);
        user.setPassword(bCryptPasswordEncoder.encode(signupRequest.getPassword()));
        user.setPhoneNumber(signupRequest.getPhoneNumber());

        userRepo.save(user);

        VetEntity vet = new VetEntity();
        vet.setExperience(signupRequest.getExperience());
        vet.setImage(signupRequest.getImage());
        vet.setName(signupRequest.getName());
        vet.setEquipment(signupRequest.getEquipment());
        vet.setUser(user);

        vetRepo.save(vet);

        SpecialityEntity speciality = new SpecialityEntity();
        speciality.setSpeciality(signupRequest.getSpeciality());
        speciality.setVet(vet);

        specialityRepo.save(speciality);

        StatusEntity status = new StatusEntity();
        status.setStatus(Status.Online);
        status.setVet(vet);

        statusRepo.save(status);

        String username = signupRequest.getUsername().toLowerCase();

        CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(username);
        String accessToken = jwtUtil.generateToken(customUserDetails);

        return getVetAuthenticationResponse(signupRequest, accessToken, customUserDetails);
    }

    private static VetAuthenticationResponse getVetAuthenticationResponse(VetSignupRequest signupRequest, String accessToken, CustomUserDetails customUserDetails) {
        VetAuthenticationResponse response = new VetAuthenticationResponse();
        response.setUsername(signupRequest.getUsername());
        response.setEmail(signupRequest.getEmail());
        response.setName(signupRequest.getName());
        response.setExperience(signupRequest.getExperience());
        response.setImage(signupRequest.getImage());
        response.setEquipment(signupRequest.getEquipment());
        response.setRole(Role.vet.name());
        response.setToken(accessToken);
        response.setId(customUserDetails.getId());
        response.setPhoneNumber(signupRequest.getPhoneNumber());
        response.setStatus(Status.Online.name());
        return response;
    }

    private void authentication(String username, String password){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @Override
    public AuthenticationResponse<?> login(LoginRequest loginRequest) {
        if(loginRequest.getUsername().isEmpty())
            throw new NullPointerException("username is empty");
        if(loginRequest.getPassword().isEmpty())
            throw new NullPointerException(("password is empty"));
        String username = loginRequest.getUsername().toLowerCase();
        String password = loginRequest.getPassword();

        authentication(username, password);

        CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(username);
        String accessToken = jwtUtil.generateToken(customUserDetails);

        if(customUserDetails.getRole().equals(Role.vet.name())){
            VetAuthenticationResponse vetResponse = new VetAuthenticationResponse();
            vetResponse.setEmail(customUserDetails.getEmail());
            vetResponse.setUsername(customUserDetails.getUsername());
            vetResponse.setId(customUserDetails.getId());
            vetResponse.setPhoneNumber(customUserDetails.getPhoneNumber());
            vetResponse.setToken(accessToken);
            vetResponse.setRole(customUserDetails.getRole());
            List<VetEntity> vets = vetRepo.findAll()
                    .stream()
                    .filter(vetEntity -> Objects.equals(vetEntity.getUser().getId(), customUserDetails.getId()))
                    .collect(Collectors.toList());
            VetEntity vet = vets.get(0);
            vetResponse.setName(vet.getName());
            vetResponse.setExperience(vet.getExperience());
            vetResponse.setImage(vet.getImage());
            vetResponse.setEquipment(vet.getEquipment());
            List<StatusEntity> statusEntities = statusRepo.findAll()
                    .stream()
                    .filter(statusEntity -> Objects.equals(statusEntity.getVet().getId(), vet.getId()))
                    .collect(Collectors.toList());
            StatusEntity status = statusEntities.get(0);
            vetResponse.setStatus(status.getStatus().name());

            List<SpecialityEntity> specialityList = specialityRepo.findAll()
                    .stream()
                    .filter(specialityEntity -> Objects.equals(specialityEntity.getVet().getId(), vet.getId()))
                    .collect(Collectors.toList());
            SpecialityEntity speciality = specialityList.get(0);
            vetResponse.setSpeciality(speciality.getSpeciality());

            return new AuthenticationResponse<>(vetResponse);
        }
        UserAuthenticationResponse userResponse = new UserAuthenticationResponse();
        userResponse.setEmail(customUserDetails.getEmail());
        userResponse.setUsername(customUserDetails.getUsername());
        userResponse.setPhoneNumber(customUserDetails.getPhoneNumber());
        userResponse.setId(customUserDetails.getId());
        userResponse.setRole(customUserDetails.getRole());
        userResponse.setToken(accessToken);

        return new AuthenticationResponse<>(userResponse);
    }
}
