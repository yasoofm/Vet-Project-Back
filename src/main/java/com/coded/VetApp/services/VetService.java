package com.coded.VetApp.services;

import com.coded.VetApp.bo.StatusResponse;
import com.coded.VetApp.bo.UpdateStatusRequest;
import com.coded.VetApp.entities.StatusEntity;

import java.util.List;

public interface VetService {
    StatusResponse updateStatus(UpdateStatusRequest updateStatusRequest, Long vetId);
    StatusResponse getStatus(Long vetId);
}
