package com.brikton.lachacra.controllers;

import com.brikton.lachacra.configs.DatabaseTestConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(DatabaseTestConfig.class)
@ActiveProfiles("test")
@Sql(scripts = {"classpath:data_test.sql"}, executionPhase = BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";
    private final String path = "/";

    private static RestTemplate restTemplate = null;
    private static ObjectMapper mapper = null;

    @BeforeAll
    static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat(path);
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Test
    void Login__OK() throws JsonProcessingException {
        //TODO: test
    }

    @Test
    void Login__Fails_Bad_Password() throws JsonProcessingException {
        //TODO: test
    }

    @Test
    void Login__Fails_Bad_Username() throws JsonProcessingException {
        //TODO: test
    }

    @Test
    void Get_Permisos__OK() throws JsonProcessingException {
        //TODO: test
    }

    @Test
    void Get_Permisos__Fails() throws JsonProcessingException {
        //TODO: test
    }
}
