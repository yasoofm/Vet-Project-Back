package com.coded.VetApp.services;

import com.coded.VetApp.bo.*;
import com.coded.VetApp.entities.FavEntity;
import com.coded.VetApp.entities.RequestEntity;
import com.coded.VetApp.entities.UserEntity;
import com.coded.VetApp.entities.VetEntity;

import java.util.List;

public interface UserService {
    List<GetVetsResponse> getVets(Long userId);
    void requestVet(RequestVetRequest request, Long userId);
    List<VetRequestResponse> getHistory(Long userId);
    void addFav(AddFavRequest addFavRequest, Long userId);
    List<GetVetsResponse> getFav(Long userId);
    boolean removeFav(RemoveFavRequest removeFavRequest, Long userId);
}
