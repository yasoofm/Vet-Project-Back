package com.coded.VetApp.services;

import com.coded.VetApp.bo.StatusResponse;
import com.coded.VetApp.bo.UpdateStatusRequest;
import com.coded.VetApp.entities.StatusEntity;
import com.coded.VetApp.entities.VetEntity;
import com.coded.VetApp.repositories.StatusRepo;
import com.coded.VetApp.repositories.VetRepo;
import com.coded.VetApp.utils.enums.Status;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class VetServiceImpl implements VetService{

    private final StatusRepo statusRepo;
    private final VetRepo vetRepo;

    public VetServiceImpl(StatusRepo statusRepo, VetRepo vetRepo) {
        this.statusRepo = statusRepo;
        this.vetRepo = vetRepo;
    }

    @Override
    public StatusResponse updateStatus(UpdateStatusRequest updateStatusRequest, Long vetId) {
        List<VetEntity> vets = vetRepo.findAll()
                .stream()
                .filter(vetEntity -> Objects.equals(vetEntity.getUser().getId(), vetId))
                .collect(Collectors.toList());

        StatusEntity status = statusRepo.findByVetId(vets.get(0).getId()).orElseThrow();
        status.setStatus(Status.valueOf(updateStatusRequest.getStatus()));
        statusRepo.save(status);
        StatusResponse response = new StatusResponse();
        response.setStatus(status.getStatus().name());
        return response;
    }

    @Override
    public StatusResponse getStatus(Long vetId) {
        List<VetEntity> vet = vetRepo.findAll()
                .stream()
                .filter(vetEntity -> Objects.equals(vetEntity.getUser().getId(), vetId))
                .collect(Collectors.toList());

        List<StatusResponse> statusList = statusRepo.findAll()
                .stream()
                .filter(statusEntity -> Objects.equals(statusEntity.getVet().getId(), vet.get(0).getId()))
                .map(statusEntity -> {
                    StatusResponse response = new StatusResponse();
                    response.setStatus(statusEntity.getStatus().name());
                    return response;
                }).collect(Collectors.toList());
        return statusList.get(0);
    }
}
