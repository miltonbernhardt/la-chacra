package com.brikton.lachacra.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {UserService.class})
public class TokenServiceTest {

    @Autowired
    UserService userService;

    @Test
    void Generate_Token__OK() {
        //TODO: test
    }

    @Test
    void Get_Username__OK() {
        //TODO: test
    }

    @Test
    void Get_Username__Fails() {
        //TODO: test
    }

    @Test
    void Validate_Token__Is_Valid() {
        //TODO: test
    }

    @Test
    void Validate_Token__Token_Is_Null() {
        //TODO: test
    }

    @Test
    void Validate_Token__Exception_When_Parse_Token() {
        //TODO: test
    }
}
