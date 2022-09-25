package com.brikton.lachacra.services;

import com.brikton.lachacra.constants.ErrorMessages;
import com.brikton.lachacra.dtos.UserLoginDTO;
import com.brikton.lachacra.entities.Privilege;
import com.brikton.lachacra.entities.Role;
import com.brikton.lachacra.entities.Token;
import com.brikton.lachacra.entities.User;
import com.brikton.lachacra.repositories.UserRepository;
import com.brikton.lachacra.util.CookieUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {UserService.class})
public class UserServiceTest {

    @MockBean
    CookieUtil cookieUtil;

    @MockBean
    TokenService tokenService;

    @MockBean
    UserRepository repository;

    @Autowired
    UserService userService;

    @Test
    void Load_User_By_Username__OK() {
        var username = "user";
        when(repository.findByUsername(username)).thenReturn(Optional.of(mockUser()));
        var user = userService.loadUserByUsername(username);
        assertEquals(mockUser(), user);
    }

    @Test
    void Load_User_By_Username__Fails() {
        var username = "user";
        when(repository.findByUsername(username)).thenReturn(Optional.empty());
        var thrown = assertThrows(
                UsernameNotFoundException.class, () -> userService.loadUserByUsername(username)
        );
        assertEquals(ErrorMessages.MSG_USER_NOT_REGISTERED, thrown.getMessage());
    }

    @Test
    void Get_Permisos__OK() {
        var permisos = userService.getPermisos(mockUser());
        assertEquals(List.of("/cargar/lotes"), permisos);
    }

    @Test
    void Create_Cookie_Header__OK() {
        var username = "user";
        when(repository.findByUsername(username)).thenReturn(Optional.of(mockUser()));

        var token = new Token();
        token.setTokenValue("tokenValue");
        token.setDuration(1L);

        when(tokenService.generateAccessToken(username)).thenReturn(token);
        when(cookieUtil.createAccessTokenCookie(any(), any())).thenReturn(new HttpCookie("tokenName", "tokenValue"));

        var dto = new UserLoginDTO();
        dto.setUsername("user");
        dto.setPassword("user");
        var header = userService.createCookieHeader(dto);

        assertEquals(List.of("tokenName=tokenValue"), header.get(HttpHeaders.SET_COOKIE));
    }

    User mockUser() {
        var user = new User();
        user.setId(1L);
        user.setRoles(List.of(mockRole()));
        user.setFirstname("user");
        user.setLastname("user");
        user.setUsername("user");
        user.setPassword("user");
        user.setTokenExpired(false);
        user.setEnabled(true);
        return user;
    }

    Role mockRole() {
        var privilege = new Privilege();
        privilege.setName("CARGAR_LOTES");
        privilege.setPath("/cargar/lotes");

        var role = new Role();
        role.setName("ROLE_USER");
        role.setPrivileges(List.of(privilege));
        return role;
    }
}
