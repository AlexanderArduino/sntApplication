package com.energetik.app.sntapplication.entity;


import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String rolename;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<Gardener> gardenerSet;

    public Role() {
    }

    public Role(Long id, String rolename, Set<Gardener> gardenerSet) {
        this.id = id;
        this.rolename = rolename;
        this.gardenerSet = gardenerSet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public Set<Gardener> getGardenerSet() {
        return gardenerSet;
    }

    public void setGardenerSet(Set<Gardener> gardenerSet) {
        this.gardenerSet = gardenerSet;
    }

    @Override
    public String getAuthority() {
        return rolename;
    }
}
