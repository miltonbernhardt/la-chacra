package com.brikton.lachacra.controllers;

import com.brikton.lachacra.configs.DatabaseTestConfig;
import com.brikton.lachacra.configs.SecurityConfigTest;
import com.brikton.lachacra.constants.ErrorMessages;
import com.brikton.lachacra.constants.Path;
import com.brikton.lachacra.constants.SuccessfulMessages;
import com.brikton.lachacra.constants.ValidationMessages;
import com.brikton.lachacra.dtos.UserLoginDTO;
import com.brikton.lachacra.responses.ErrorResponse;
import com.brikton.lachacra.responses.SuccessfulResponse;
import com.brikton.lachacra.utils.GeneratedToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({DatabaseTestConfig.class, SecurityConfigTest.class})
@ActiveProfiles("test")
@Sql(scripts = {"classpath:data_test.sql"}, executionPhase = BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate = null;
    private static ObjectMapper mapper = null;

    @BeforeAll
    static void init() {
        restTemplate = new RestTemplate();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @BeforeEach
    void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "");
    }

    @Test
    void Login__OK() {
        var user = new UserLoginDTO();
        user.setUsername("user");
        user.setPassword("user");

        var response = restTemplate.postForEntity(baseUrl + Path.API_LOGIN, user, SuccessfulResponse.class);
        assertEquals(SuccessfulMessages.MSG_USER_LOGGED, requireNonNull(response.getBody()).getMessage());
        assertNotNull(requireNonNull(response.getHeaders()).get("Set-Cookie"));
    }

    @Test
    void Login__Fails_Bad_Password() {
        var user = new UserLoginDTO();
        user.setUsername("user2");
        user.setPassword("admin2");
        var thrown = assertThrows(
                HttpClientErrorException.Unauthorized.class, () -> restTemplate.postForEntity(baseUrl + Path.API_LOGIN, user, SuccessfulResponse.class)
        );
        assertEquals(HttpStatus.UNAUTHORIZED, thrown.getStatusCode());
    }

    @Test
    void Login__Fails_Bad_Username() {
        var user = new UserLoginDTO();
        user.setUsername("user22");
        user.setPassword("user");

        var thrown = assertThrows(
                HttpClientErrorException.Unauthorized.class, () -> restTemplate.postForEntity(baseUrl + Path.API_LOGIN, user, SuccessfulResponse.class)
        );
        assertEquals(HttpStatus.UNAUTHORIZED, thrown.getStatusCode());
    }

    @Test
    void Login__Fails_Bad_Request_1() throws JsonProcessingException {
        var user = new UserLoginDTO();
        user.setUsername("");
        user.setPassword("");

        var thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> restTemplate.postForEntity(baseUrl + Path.API_LOGIN, user, SuccessfulResponse.class)
        );

        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
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
                HttpClientErrorException.BadRequest.class, () -> restTemplate.postForEntity(baseUrl + Path.API_LOGIN, user, SuccessfulResponse.class)
        );

        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(Path.API_LOGIN, response.getPath());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_BODY, response.getMessage());
        assertEquals(2, response.getErrors().size());
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("username"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("password"));
    }

    @Test
    void Get_Permisos__OK() {
        var headers = new HttpHeaders();
        headers.add("Cookie", "authorizationToken=".concat(GeneratedToken.ADMIN));
        var response = restTemplate.exchange(baseUrl + Path.API_PERMISSIONS, HttpMethod.GET, new HttpEntity<String>(headers), SuccessfulResponse.class);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var successfulResponse = mapper.convertValue(response.getBody(), new TypeReference<SuccessfulResponse<List<String>>>() {
        });
        assertEquals("", successfulResponse.getMessage());
        assertTrue(successfulResponse.getData().size() > 0);
    }

    @Test
    void Get_Permisos__Fails() {
        var thrown = assertThrows(
                HttpClientErrorException.Unauthorized.class, () -> restTemplate.getForEntity(baseUrl + Path.API_PERMISSIONS, SuccessfulResponse.class)
        );
        assertEquals(HttpStatus.UNAUTHORIZED, thrown.getStatusCode());
    }
}