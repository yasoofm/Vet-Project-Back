package com.coded.VetApp.entities;

import com.coded.VetApp.utils.enums.Role;

import javax.persistence.*;

@Entity
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Role title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getTitle() {
        return title;
    }

    public void setTitle(Role title) {
        this.title = title;
    }
}
