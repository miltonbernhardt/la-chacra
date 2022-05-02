package com.brikton.lachacra.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Collection;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Privilege privilege = (Privilege) o;
        return Objects.equals(name, privilege.name) && Objects.equals(path, privilege.path) && Objects.equals(roles, privilege.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, path, roles);
    }

    @Override
    public String toString() {
        return "Privilege{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", roles=" + roles +
                '}';
    }
}