package com.coded.VetApp.bo;

public class RequestVetRequest {
    private String animal;
    private Long vetId;

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public Long getVetId() {
        return vetId;
    }

    public void setVetId(Long vetId) {
        this.vetId = vetId;
    }
}
