package com.brikton.lachacra.services;

import com.brikton.lachacra.constants.ErrorMessages;
import com.brikton.lachacra.dtos.UserLoginDTO;
import com.brikton.lachacra.entities.User;
import com.brikton.lachacra.repositories.UserRepository;
import com.brikton.lachacra.util.CookieUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final CookieUtil cookieUtil;
    private final TokenService tokenService;
    private final UserRepository repository;

    public UserService(CookieUtil cookieUtil, TokenService tokenService, UserRepository repository) {
        this.cookieUtil = cookieUtil;
        this.tokenService = tokenService;
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = repository.findByUsername(username);
        if (user.isEmpty())
            throw new UsernameNotFoundException(ErrorMessages.MSG_USER_NOT_REGISTERED);
        return user.get();
    }

    public List<String> getPermisos(User user) {
        var permisos = new ArrayList<String>();
        for (var role : user.getRoles())
            for (var privilege : role.getPrivileges()) {
                permisos.add(privilege.getPath());
            }
        return permisos;
    }

    public HttpHeaders createCookieHeader(UserLoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        UserDetails user = loadUserByUsername(username);
        var newAccessToken = tokenService.generateAccessToken(user.getUsername());
        var httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.SET_COOKIE, cookieUtil.createAccessTokenCookie(newAccessToken.getTokenValue(), newAccessToken.getDuration()).toString());
        return httpHeaders;
    }
}
