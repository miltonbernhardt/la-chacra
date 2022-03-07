//package com.brikton.lachacra.security;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    private final UserDetailsRepository repository;
//
//    public UserDetailsServiceImpl(UserDetailsRepository repository) {
//        this.repository = repository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        var user = repository.findByUsername(username);
//
//        if (user == null)
//            throw new UsernameNotFoundException("Usuario no encontrado para el mail " + username);
//
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getRoles());
//    }
//}
