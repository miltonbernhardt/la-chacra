package com.brikton.lachacra.controllers;

import com.brikton.lachacra.configs.DatabaseTestConfig;
import com.brikton.lachacra.constants.ErrorMessages;
import com.brikton.lachacra.constants.Path;
import com.brikton.lachacra.constants.SuccessfulMessages;
import com.brikton.lachacra.constants.ValidationMessages;
import com.brikton.lachacra.dtos.UserLoginDTO;
import com.brikton.lachacra.responses.ErrorResponse;
import com.brikton.lachacra.responses.SuccessfulResponse;
import com.brikton.lachacra.utils.Rest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(DatabaseTestConfig.class)
@ActiveProfiles("test")
@Sql(scripts = {"classpath:data_test.sql"}, executionPhase = BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserControllerIntegrationTest {

    @LocalServerPort
    private int port;
    private static Rest rest = null;

    @BeforeAll
    static void init() {
        rest = new Rest("");
    }

    @BeforeEach
    void setUp() {
        rest.setPort(port);
    }

    @Test
    void Login__OK() {
        var user = new UserLoginDTO();
        user.setUsername("user");
        user.setPassword("user");

        var response = rest.postUnauthorized(Path.API_LOGIN, user);
        assertEquals(SuccessfulMessages.MSG_USER_LOGGED, response.getBody().getMessage());
        assertNotNull(requireNonNull(response.getHeaders()).get("Set-Cookie"));
    }

    @Test
    void Login_Logged_Before__OK() {
        var user = new UserLoginDTO();
        user.setUsername("user");
        user.setPassword("user");

        var response = rest.post(Path.API_LOGIN, user);
        assertEquals(SuccessfulMessages.MSG_USER_LOGGED, response.getBody().getMessage());
        assertNotNull(requireNonNull(response.getHeaders()).get("Set-Cookie"));
    }

    @Test
    void Login__Fails_Bad_Password() {
        var user = new UserLoginDTO();
        user.setUsername("user2");
        user.setPassword("admin2");
        var thrown = assertThrows(
                HttpClientErrorException.Unauthorized.class, () -> rest.post(Path.API_LOGIN, user)
        );
        assertEquals(HttpStatus.UNAUTHORIZED, thrown.getStatusCode());
    }

    @Test
    void Login__Fails_Bad_Username() {
        var user = new UserLoginDTO();
        user.setUsername("user22");
        user.setPassword("user");

        var thrown = assertThrows(
                HttpClientErrorException.Unauthorized.class, () -> rest.post(Path.API_LOGIN, user)
        );
        assertEquals(HttpStatus.UNAUTHORIZED, thrown.getStatusCode());
    }

    @Test
    void Login__Fails_Bad_Request_1() throws JsonProcessingException {
        var user = new UserLoginDTO();
        user.setUsername("");
        user.setPassword("");

        var thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> rest.post(Path.API_LOGIN, user)
        );

        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(Path.API_LOGIN, response.getPath());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_BODY, response.getMessage());
        assertEquals(2, response.getErrors().size());
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("username"));
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("password"));
    }

    @Test
    void Login__Fails_Bad_Request_2() throws JsonProcessingException {
        var user = new UserLoginDTO();
        user.setUsername(RandomStringUtils.randomAlphabetic(300));
        user.setPassword(RandomStringUtils.randomAlphabetic(300));

        var thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> rest.post(Path.API_LOGIN, user)
        );

        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(Path.API_LOGIN, response.getPath());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_BODY, response.getMessage());
        assertEquals(2, response.getErrors().size());
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("username"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("password"));
    }

    @Test
    void Get_Permisos__OK() {
        var response = rest.get(Path.API_PERMISSIONS);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var successfulResponse = rest.mapper().convertValue(response.getBody(), new TypeReference<SuccessfulResponse<List<String>>>() {
        });
        assertEquals("", successfulResponse.getMessage());
        assertTrue(successfulResponse.getData().size() > 0);
    }

    @Test
    void Get_Permisos__Fails() {
        var thrown = assertThrows(
                HttpClientErrorException.Unauthorized.class, () -> rest.getUnauthorized(Path.API_PERMISSIONS)
        );
        assertEquals(HttpStatus.UNAUTHORIZED, thrown.getStatusCode());
    }
}