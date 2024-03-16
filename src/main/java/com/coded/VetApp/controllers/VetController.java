package com.coded.VetApp.controllers;

import com.coded.VetApp.bo.*;
import com.coded.VetApp.entities.StatusEntity;
import com.coded.VetApp.entities.VetEntity;
import com.coded.VetApp.services.UserService;
import com.coded.VetApp.services.VetService;
import com.coded.VetApp.utils.UserDetailsUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vet")
public class VetController {
    private final UserService userService;
    private final VetService vetService;

    public VetController(UserService userService, VetService vetService) {
        this.userService = userService;
        this.vetService = vetService;
    }

    @GetMapping("vets")
    public ResponseEntity<List<GetVetsResponse>> getVets(){
        List<GetVetsResponse> vets = userService.getVets(UserDetailsUtil.userDetails().getId());
        return ResponseEntity.ok(vets);
    }

    @PatchMapping("set-status")
    public ResponseEntity<StatusResponse> setStatus(@RequestBody UpdateStatusRequest updateStatusRequest){
        StatusResponse status = vetService.updateStatus(updateStatusRequest, UserDetailsUtil.userDetails().getId());
        return ResponseEntity.ok(status);
    }

    @GetMapping("get-status")
    public ResponseEntity<StatusResponse> getStatus(){
        StatusResponse response = vetService.getStatus(UserDetailsUtil.userDetails().getId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("add-favorite")
    public ResponseEntity<String> addFav(@RequestBody AddFavRequest addFavRequest){
        userService.addFav(addFavRequest, UserDetailsUtil.userDetails().getId());
        return ResponseEntity.ok("Vet added to favorites");
    }

    @GetMapping("get-favorite")
    public ResponseEntity<List<GetVetsResponse>> getFav(){
        List<GetVetsResponse> favEntityList = userService.getFav(UserDetailsUtil.userDetails().getId());
        return ResponseEntity.ok(favEntityList);
    }

    @DeleteMapping("remove-favorite")
    public ResponseEntity<String> removeFav(@RequestBody RemoveFavRequest removeFavRequest){
        boolean success = userService.removeFav(removeFavRequest, UserDetailsUtil.userDetails().getId());
        if(success)
            return ResponseEntity.ok("Removed from favorites");
        else
            return ResponseEntity.badRequest().body("Failed to remove");
    }

    @PostMapping("request")
    public ResponseEntity<String> requestVet(@RequestBody RequestVetRequest request){
        userService.requestVet(request, UserDetailsUtil.userDetails().getId());
        return ResponseEntity.ok("Request made successfully");
    }

    @GetMapping("history")
    public ResponseEntity<List<VetRequestResponse>> getHistory(){
        List<VetRequestResponse> history = userService.getHistory(UserDetailsUtil.userDetails().getId());
        return ResponseEntity.ok(history);
    }
}
