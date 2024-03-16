package com.coded.VetApp.bo;

public class FavoriteResponse {
    private Long id, vetPhoneNumber;
    private String vetName, vetEmail, vetImage, vetEquipment, vetUsername;
    private int vetExperience;
    private boolean isFavorite;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVetPhoneNumber() {
        return vetPhoneNumber;
    }

    public void setVetPhoneNumber(Long vetPhoneNumber) {
        this.vetPhoneNumber = vetPhoneNumber;
    }

    public String getVetName() {
        return vetName;
    }

    public void setVetName(String vetName) {
        this.vetName = vetName;
    }

    public String getVetEmail() {
        return vetEmail;
    }

    public void setVetEmail(String vetEmail) {
        this.vetEmail = vetEmail;
    }

    public String getVetImage() {
        return vetImage;
    }

    public void setVetImage(String vetImage) {
        this.vetImage = vetImage;
    }

    public String getVetEquipment() {
        return vetEquipment;
    }

    public void setVetEquipment(String vetEquipment) {
        this.vetEquipment = vetEquipment;
    }

    public String getVetUsername() {
        return vetUsername;
    }

    public void setVetUsername(String vetUsername) {
        this.vetUsername = vetUsername;
    }

    public int getVetExperience() {
        return vetExperience;
    }

    public void setVetExperience(int vetExperience) {
        this.vetExperience = vetExperience;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
