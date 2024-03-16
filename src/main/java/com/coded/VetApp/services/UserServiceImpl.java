package com.coded.VetApp.services;

import com.coded.VetApp.bo.*;
import com.coded.VetApp.entities.*;
import com.coded.VetApp.repositories.*;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private final VetRepo vetRepo;
    private final UserRepo userRepo;
    private final RequestRepo requestRepo;
    private final SpecialityRepo specialityRepo;
    private final FavRepo favRepo;
    private final StatusRepo statusRepo;

    public UserServiceImpl(VetRepo vetRepo, UserRepo userRepo, RequestRepo requestRepo, SpecialityRepo specialityRepo, FavRepo favRepo, StatusRepo statusRepo) {
        this.vetRepo = vetRepo;
        this.userRepo = userRepo;
        this.requestRepo = requestRepo;
        this.specialityRepo = specialityRepo;
        this.favRepo = favRepo;
        this.statusRepo = statusRepo;
    }

    @Override
    public List<GetVetsResponse> getVets(Long userId) {
        List<GetVetsResponse> vets =  vetRepo.findAll()
                .stream()
                .map(vetEntity -> {
                    GetVetsResponse vetsResponse = new GetVetsResponse();
                    vetsResponse.setEmail(vetEntity.getUser().getEmail());
                    vetsResponse.setUsername(vetEntity.getUser().getUsername());
                    vetsResponse.setPhoneNumber(vetEntity.getUser().getPhoneNumber());
                    vetsResponse.setId(vetEntity.getId());
                    vetsResponse.setEquipment(vetEntity.getEquipment());
                    vetsResponse.setExperience(vetEntity.getExperience());
                    vetsResponse.setName(vetEntity.getName());
                    vetsResponse.setImage(vetEntity.getImage());
                    vetsResponse.setPassword(vetEntity.getUser().getPassword());
                    return vetsResponse;
                }).collect(Collectors.toList());

        for (GetVetsResponse vet : vets) {
            List<SpecialityEntity> specialities = specialityRepo.findAll()
                    .stream()
                    .filter(specialityEntity -> Objects.equals(specialityEntity.getVet().getId(), vet.getId()))
                    .collect(Collectors.toList());
            SpecialityEntity speciality = specialities.get(0);
            vet.setSpeciality(speciality.getSpeciality());

            List<StatusEntity> statusList = statusRepo.findAll()
                    .stream()
                    .filter(statusEntity -> Objects.equals(statusEntity.getVet().getId(), vet.getId()))
                    .collect(Collectors.toList());
            StatusEntity status = statusList.get(0);
            vet.setStatus(status.getStatus().name());

            List<FavEntity> favList = favRepo.findAll()
                    .stream()
                    .filter(favEntity -> Objects.equals(favEntity.getUser().getId(), userId) && Objects.equals(favEntity.getVet().getId(), vet.getId()))
                    .collect(Collectors.toList());
            if (!favList.isEmpty()) {
                FavEntity fav = favList.get(0);
                vet.setFavorite(fav.getFavorite());
            } else {
                vet.setFavorite(false);
            }

        }
        return vets;
    }

    @Override
    public void requestVet(RequestVetRequest request, Long userId) {
        RequestEntity requestEntity = new RequestEntity();

        UserEntity user = userRepo.getById(userId);
        VetEntity vet = vetRepo.getById(request.getVetId());

        requestEntity.setAnimal(request.getAnimal());
        requestEntity.setVet(vet);
        requestEntity.setUser(user);

        requestRepo.save(requestEntity);
    }

    @Override
    public List<VetRequestResponse> getHistory(Long userId) {
        return requestRepo.findAll()
                .stream()
                .filter(requestEntity -> Objects.equals(requestEntity.getUser().getId(), userId))
                .map(requestEntity -> {
                    VetRequestResponse response = new VetRequestResponse();
                    response.setAnimal(requestEntity.getAnimal());
                    response.setVetName(requestEntity.getVet().getName());
                    response.setCreatedAt(requestEntity.getCreatedAt());
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void addFav(AddFavRequest addFavRequest, Long userId) {
        FavEntity favEntity = new FavEntity();
        favEntity.setUser(userRepo.getById(userId));
        favEntity.setVet(vetRepo.getById(addFavRequest.getVetId()));
        favEntity.setFavorite(true);

        favRepo.save(favEntity);
    }

    @Override
    public List<GetVetsResponse> getFav(Long userId) {
        List<GetVetsResponse> responseList = favRepo.findAll()
                .stream()
                .filter(favEntity -> Objects.equals(favEntity.getUser().getId(), userId))
                .map(favEntity -> {
                    GetVetsResponse response = new GetVetsResponse();
                    response.setId(favEntity.getVet().getId());
                    response.setFavorite(favEntity.getFavorite());
                    response.setEmail(favEntity.getVet().getUser().getEmail());
                    response.setEquipment(favEntity.getVet().getEquipment());
                    response.setImage(favEntity.getVet().getImage());
                    response.setExperience(favEntity.getVet().getExperience());
                    response.setPhoneNumber(favEntity.getUser().getPhoneNumber());
                    response.setName(favEntity.getVet().getName());
                    response.setUsername(favEntity.getUser().getUsername());
                    response.setPassword(favEntity.getUser().getPassword());
                    response.setPhoneNumber(favEntity.getUser().getPhoneNumber());

                    return response;
                })
                .collect(Collectors.toList());

        if (responseList.isEmpty()){
            return responseList;
        } else {
            for (GetVetsResponse selectedResponse : responseList) {
                List<SpecialityEntity> specialities = specialityRepo.findAll()
                        .stream()
                        .filter(specialityEntity -> Objects.equals(specialityEntity.getVet().getId(), selectedResponse.getId()))
                        .collect(Collectors.toList());
                if (!specialities.isEmpty()) {
                    SpecialityEntity speciality = specialities.get(0);
                    selectedResponse.setSpeciality(speciality.getSpeciality());
                }

                List<StatusEntity> statusList = statusRepo.findAll()
                        .stream()
                        .filter(statusEntity -> Objects.equals(statusEntity.getVet().getId(), selectedResponse.getId()))
                        .collect(Collectors.toList());
                if (!statusList.isEmpty()) {
                    StatusEntity status = statusList.get(0);
                    selectedResponse.setStatus(status.getStatus().name());
                }
            }
            return responseList;
        }
    }

    @Override
    public boolean removeFav(RemoveFavRequest removeFavRequest, Long userId) {
        List<FavEntity> favEntityList = favRepo.findAll()
                .stream()
                .filter(favEntity -> Objects.equals(favEntity.getVet().getId(), removeFavRequest.getVetId()) && Objects.equals(favEntity.getUser().getId(), userId))
                .collect(Collectors.toList());
        if(favEntityList.isEmpty())
            return false;
        favRepo.delete(favEntityList.get(0));
        return true;
    }
}
