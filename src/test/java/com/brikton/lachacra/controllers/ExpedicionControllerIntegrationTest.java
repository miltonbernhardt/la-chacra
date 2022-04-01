package com.brikton.lachacra.controllers;

import com.brikton.lachacra.configs.DatabaseTestConfig;
import com.brikton.lachacra.configs.NotSecurityConfigTest;
import com.brikton.lachacra.constants.Path;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.Assert;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({DatabaseTestConfig.class, NotSecurityConfigTest.class})
@ActiveProfiles("test")
@Sql(scripts = {"classpath:data_test.sql"}, executionPhase = BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ExpedicionControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";
    private final String path = Path.API_EXPEDICIONES.concat("/");

    private static RestTemplate restTemplate = null;
    private static ObjectMapper mapper = null;

    <T> ResponseEntity<T> putForEntity(String url, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return nonNull(restTemplate.execute(url, HttpMethod.PUT, requestCallback, responseExtractor, uriVariables));
    }

    <T> ResponseEntity<T> deleteForEntity(String url, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RequestCallback requestCallback = restTemplate.httpEntityCallback(null, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return nonNull(restTemplate.execute(url, HttpMethod.DELETE, requestCallback, responseExtractor, uriVariables));
    }

    static <T> T nonNull(@Nullable T result) {
        Assert.state(result != null, "No result");
        return result;
    }

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
