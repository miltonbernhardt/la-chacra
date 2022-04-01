package com.brikton.lachacra.controllers;

import com.brikton.lachacra.configs.DatabaseTestConfig;
import com.brikton.lachacra.configs.NotSecurityConfigTest;
import com.brikton.lachacra.constants.Path;
import com.brikton.lachacra.dtos.ClienteDTO;
import com.brikton.lachacra.dtos.TipoClienteDTO;
import com.brikton.lachacra.responses.SuccessfulResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({DatabaseTestConfig.class, NotSecurityConfigTest.class})
@ActiveProfiles("test")
@Sql(scripts = {"classpath:data_test.sql"}, executionPhase = BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TipoClienteControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";
    private final String path = Path.API_TIPOS_CLIENTE.concat("/");

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
    void Get_All__OK() {
        var mockDTO1 = new TipoClienteDTO();
        mockDTO1.setId(1L);
        mockDTO1.setTipo("Mayorista");

        var mockDTO2 = new TipoClienteDTO();
        mockDTO2.setId(2L);
        mockDTO2.setTipo("Minorista");

        var mockDTO3 = new TipoClienteDTO();
        mockDTO3.setId(3L);
        mockDTO3.setTipo("Particular");

        var expectedTiposClientes = List.of(mockDTO1, mockDTO2, mockDTO3);
        var response = restTemplate.getForEntity(baseUrl, SuccessfulResponse.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var successfulResponse = mapper.convertValue(response.getBody(), new TypeReference<SuccessfulResponse<List<TipoClienteDTO>>>() {});
        assertEquals("", successfulResponse.getMessage());
        assertEquals(expectedTiposClientes, successfulResponse.getData());
    }
}
