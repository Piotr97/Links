package com.authorization.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role", schema = "finally")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    private String role;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
    private Set<Profiles> profiles = new HashSet<>();


    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public Set<Profiles> getProfiles() {
        return profiles;
    }

    public void setProfiles(Set<Profiles> profiles) {
        this.profiles = profiles;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", role='" + role + '\'' +
                ", profiles=" + profiles +
                '}';
    }
}