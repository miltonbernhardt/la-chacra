//package com.brikton.lachacra.security;
//
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import lombok.Setter;
//import org.hibernate.annotations.GenericGenerator;
//import org.springframework.security.core.GrantedAuthority;
//
//import javax.persistence.*;
//import java.util.Collection;
//
//@Entity
//@Getter
//@Setter
//@RequiredArgsConstructor
//public class Role implements GrantedAuthority {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq")
//    @GenericGenerator(name = "seq", strategy = "increment")
//    private Long id;
//
//    private String name;
//
//    @ManyToMany(mappedBy = "roles")
//    private Collection<UserDetails> userDetails;
//
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "rol_privileges",
//            joinColumns = @JoinColumn(
//                    name = "role_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(
//                    name = "privilege_id", referencedColumnName = "id"))
//    private Collection<Privilege> privileges;
//
//    @Override
//    public String getAuthority() {
//        return name;
//    }
//}