package com.brikton.lachacra.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Role {

    @Id
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_privileges",
            joinColumns = @JoinColumn(
                    name = "role_name", referencedColumnName = "name"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_name", referencedColumnName = "name"))
    private Collection<Privilege> privileges;

    public Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + ": " + privileges;
    }
}