package com.energetik.app.sntapplication.security;


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
    private Set<User> usersList;

    public Role() {
    }

    public Role(Long id, String rolename, Set<User> usersList) {
        this.id = id;
        this.rolename = rolename;
        this.usersList = usersList;
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

    public Set<User> getUsersList() {
        return usersList;
    }

    public void setUsersList(Set<User> usersList) {
        this.usersList = usersList;
    }

    @Override
    public String getAuthority() {
        return rolename;
    }
}
