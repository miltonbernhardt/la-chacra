package com.brikton.lachacra.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Collection;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Privilege implements GrantedAuthority {
    @Id
    private String name;
    private String path;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

    @Override
    public String getAuthority() {
        return path;
    }
}