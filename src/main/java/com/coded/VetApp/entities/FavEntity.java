package com.coded.VetApp.entities;

import javax.persistence.*;

@Entity
public class FavEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private boolean isFavorite;
    @OneToOne
    @JoinColumn(nullable = false)
    private UserEntity user;
    @OneToOne
    @JoinColumn(nullable = false)
    private VetEntity vet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public VetEntity getVet() {
        return vet;
    }

    public void setVet(VetEntity vet) {
        this.vet = vet;
    }
}
