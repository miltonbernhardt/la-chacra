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
public class ExpedicionControllerIntegrationTest {

    @LocalServerPort
    private int port;
    private static Rest rest = null;

    @BeforeAll
    static void init() {
        rest = new Rest(Path.API_EXPEDICIONES);
    }

    @BeforeEach
    void setUp() {
        rest.setPort(port);
    }

    @Test
    void Get_All__OK() {
        //TODO
    }

    @Test
    void Save__OK() {
        //todo
    }

    @Test
    void Save__Cliente_Not_Found() {
        //TODO
    }

    @Test
    void Save__Lote_Not_Found() {
        //TODO
    }

    @Test
    void Save__Precio_Not_Found() {
        //TODO
    }

    @Test
    void Save__Invalid_Fields__1() {
        //todo
    }

    @Test
    void Save__Invalid_Fields__2() {
        //todo
    }

    @Test
    void Update_Same_Lote_Same_Quantity__OK() {
        //TODO
    }

    @Test
    void Update_Same_Lote_Different_Quantity__OK() {
        //TODO
    }

    @Test
    void Update_Different_Lote__OK() {
        //TODO
    }

    @Test
    void Update__Expedicion_Not_Found() {
        //TODO
    }

    @Test
    void Update__Cliente_Not_Found() {
        //TODO
    }

    @Test
    void Update__Lote_Not_Found() {
        //TODO
    }

    @Test
    void Update__Precio_Not_Found() {
        //TODO
    }

    @Test
    void Update__Invalid_Fields__1() {
        //TODO
    }

    @Test
    void Update__Invalid_Fields__2() {
        //TODO
    }

    @Test
    void Delete__OK() {
        //TODO
    }

    @Test
    void Delete_Remito_Dependency__Cannot_Be_Deleted() {
        //TODO
    }

    @Test
    void Delete__Expedicion_Not_Found() {
        //TODO
    }

    @Test
    void Delete__Bad_ID() {
        //TODO
    }

    @Test
    void Delete__OK___After_That__Expedicion_Doesnt_Show_In_Get_All() {
        //TODO
    }
}
