package com.brikton.lachacra.controllers;

import com.brikton.lachacra.configs.DatabaseTestConfig;
import com.brikton.lachacra.constants.Path;
import com.brikton.lachacra.utils.Rest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(DatabaseTestConfig.class)
@ActiveProfiles("test")
@Sql(scripts = {"classpath:data_test.sql"}, executionPhase = BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RemitoControllerIntegrationTest {

    @LocalServerPort
    private int port;
    private static Rest rest = null;

    @BeforeAll
    static void init() {
        rest = new Rest(Path.API_REMITOS);
    }

    @BeforeEach
    void setUp() {
        rest.setPort(port);
    }

    @Test
    void Generate_Remito__OK() {
        //TODO: test
    }

    @Test
    void Get_Pdf__OK() {
        //TODO: test
    }

    @Test
    void Generate_And_Save__OK() {
        //TODO: test
    }
}
