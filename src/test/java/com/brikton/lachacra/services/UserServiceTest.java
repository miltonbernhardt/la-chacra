package com.brikton.lachacra.services;

import com.brikton.lachacra.repositories.UserRepository;
import com.brikton.lachacra.util.CookieUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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
        //TODO: test
    }

    @Test
    void Load_User_By_Username__Fails() {
        //TODO: test
    }

    @Test
    void Get_Permisos__OK() {
        //TODO: test
    }

    @Test
    void Get_Permisos__Fails() {
        //TODO: test
    }

    @Test
    void Create_Cookie_Header__OK() {
        //TODO: test
    }
}
