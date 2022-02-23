package com.brikton.lachacra.controllers;

import com.brikton.lachacra.configs.DatabaseTestConfig;
import com.brikton.lachacra.constants.ErrorMessages;
import com.brikton.lachacra.constants.ValidationMessages;
import com.brikton.lachacra.dtos.ClienteDTO;
import com.brikton.lachacra.responses.ErrorResponse;
import com.brikton.lachacra.responses.SuccessfulResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(DatabaseTestConfig.class)
@ActiveProfiles("test")
@Sql(scripts = {"classpath:data_test.sql"}, executionPhase = BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ClienteControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";
    private final String path = "/api/v1/clientes/";

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
    void Get_All__OK() throws JsonProcessingException {
        var mockCliente1 = new ClienteDTO();
        mockCliente1.setId(1L);
        mockCliente1.setIdTipoCliente(1L);
        mockCliente1.setCuit("20-11111111-7");
        mockCliente1.setCodPostal("3080");
        mockCliente1.setDomicilio("Jacob 2830");
        mockCliente1.setLocalidad("Esperanza");
        mockCliente1.setPais("Argentina");
        mockCliente1.setProvincia("Santa Fe");
        mockCliente1.setTransporte("Familia Noroña S.A.");
        mockCliente1.setSenasaUta("113754");
        mockCliente1.setTelefono("233334444444");
        mockCliente1.setCelular("233334444444");
        mockCliente1.setFax(null);
        mockCliente1.setEmail("mail1@mail.com");
        mockCliente1.setRazonSocial("ABDALA, Gustavo");

        var mockCliente2 = new ClienteDTO();
        mockCliente2.setId(2L);
        mockCliente2.setIdTipoCliente(2L);
        mockCliente2.setCuit("27-22222222-5");
        mockCliente2.setCodPostal("3560");
        mockCliente2.setDomicilio("Hipólito Yrigoyen 1442");
        mockCliente2.setLocalidad("Reconquista");
        mockCliente2.setPais("Argentina");
        mockCliente2.setProvincia("Santa Fe");
        mockCliente2.setTransporte("Cerutti, Pablo");
        mockCliente2.setSenasaUta("94265");
        mockCliente2.setTelefono("344445555555");
        mockCliente2.setCelular("344445555555");
        mockCliente2.setFax("344445555555");
        mockCliente2.setEmail("mail2@mail.com");
        mockCliente2.setRazonSocial("ALBERTINAZZI, Olga Pompeya");

        var mockCliente3 = new ClienteDTO();
        mockCliente3.setId(3L);
        mockCliente3.setIdTipoCliente(3L);
        mockCliente3.setCuit("20-33333333-3");
        mockCliente3.setCodPostal(null);
        mockCliente3.setDomicilio("Av. Centenario 4797");
        mockCliente3.setLocalidad("Espeleta");
        mockCliente3.setPais("Argentina");
        mockCliente3.setProvincia("Buenos Aires");
        mockCliente3.setTransporte("Bianchi");
        mockCliente3.setSenasaUta("83200");
        mockCliente3.setTelefono("755556666666");
        mockCliente3.setCelular("755556666666");
        mockCliente3.setFax("755556666666");
        mockCliente3.setEmail("mail3@mail.com");
        mockCliente3.setRazonSocial("ALEGRI, José César");

        String expectedClientes = mapper.writeValueAsString(List.of(mockCliente1, mockCliente2, mockCliente3));
        var response = restTemplate.getForEntity(baseUrl, SuccessfulResponse.class);
        var actualClientes = mapper.writeValueAsString(requireNonNull(response.getBody()).getData());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedClientes, actualClientes);
    }

    @Test
    void Save__OK() throws JsonProcessingException {
        var mockToSave = new ClienteDTO();
        mockToSave.setIdTipoCliente(1L);
        mockToSave.setCuit("99888888887");
        mockToSave.setCodPostal("3000");
        mockToSave.setDomicilio("Domicilio 1");
        mockToSave.setLocalidad("Localidad 1");
        mockToSave.setPais("Pais 1");
        mockToSave.setProvincia("Provincia 1");
        mockToSave.setTransporte("Provincia 1");
        mockToSave.setSenasaUta("Senasa UTA 1");
        mockToSave.setTelefono("233334444444");
        mockToSave.setCelular("233334444444");
        mockToSave.setFax("233334444444");
        mockToSave.setEmail("mail1@mail.com");
        mockToSave.setRazonSocial("Razon social 1");

        var expectedCliente = new ClienteDTO();
        expectedCliente.setId(4L);
        expectedCliente.setIdTipoCliente(1L);
        expectedCliente.setCuit("99888888887");
        expectedCliente.setCodPostal("3000");
        expectedCliente.setDomicilio("Domicilio 1");
        expectedCliente.setLocalidad("Localidad 1");
        expectedCliente.setPais("Pais 1");
        expectedCliente.setProvincia("Provincia 1");
        expectedCliente.setTransporte("Provincia 1");
        expectedCliente.setSenasaUta("Senasa UTA 1");
        expectedCliente.setTelefono("233334444444");
        expectedCliente.setCelular("233334444444");
        expectedCliente.setFax("233334444444");
        expectedCliente.setEmail("mail1@mail.com");
        expectedCliente.setRazonSocial("Razon social 1");

        var expectedClienteString = mapper.writeValueAsString(expectedCliente);

        var response = restTemplate.postForEntity(baseUrl, mockToSave, SuccessfulResponse.class);
        var actualLote = mapper.writeValueAsString(Objects.requireNonNull(response.getBody()).getData());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedClienteString, actualLote);
    }

    @Test
    void Save__Tipo_Cliente_Not_Exists() throws JsonProcessingException {
        var dtoToSave = new ClienteDTO();
        dtoToSave.setIdTipoCliente(4L);
        dtoToSave.setCuit("99888888887");
        dtoToSave.setCodPostal("3000");
        dtoToSave.setDomicilio("Domicilio 1");
        dtoToSave.setLocalidad("Localidad 1");
        dtoToSave.setPais("Pais 1");
        dtoToSave.setProvincia("Provincia 1");
        dtoToSave.setTransporte("Provincia 1");
        dtoToSave.setSenasaUta("Senasa UTA 1");
        dtoToSave.setTelefono("233334444444");
        dtoToSave.setCelular("233334444444");
        dtoToSave.setFax("233334444444");
        dtoToSave.setEmail("mail1@mail.com");
        dtoToSave.setRazonSocial("Razon social 1");

        HttpClientErrorException.Conflict thrown = assertThrows(
                HttpClientErrorException.Conflict.class, () -> restTemplate.postForEntity(baseUrl, dtoToSave, SuccessfulResponse.class)
        );
        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.CONFLICT, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_TIPO_CLIENTE_NOT_FOUND, response.getMessage());
        assertEquals(path, response.getPath());
    }

    @Test
    void Save__Invalid_Fields__1() throws JsonProcessingException {
        var dtoToSave = new ClienteDTO();
        dtoToSave.setCodPostal("3000");
        dtoToSave.setDomicilio("Domicilio 1");
        dtoToSave.setLocalidad("Localidad 1");
        dtoToSave.setPais("Pais 1");
        dtoToSave.setProvincia("Provincia 1");
        dtoToSave.setTransporte("Provincia 1");
        dtoToSave.setSenasaUta("Senasa UTA 1");
        dtoToSave.setTelefono("233334444444");
        dtoToSave.setCelular("233334444444");
        dtoToSave.setFax("233334444444");
        dtoToSave.setEmail("mail1@mail.com");

        HttpClientErrorException.BadRequest thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> restTemplate.postForEntity(baseUrl, dtoToSave, SuccessfulResponse.class)
        );

        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(path, response.getPath());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_BODY, response.getMessage());
        assertEquals(3, response.getErrors().size());
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("razonSocial"));
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("cuit"));
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("idTipoCliente"));
    }

    @Test
    void Save__Invalid_Fields__Fields_Too_Large() throws JsonProcessingException {
        var dtoToSave = new ClienteDTO();
        dtoToSave.setRazonSocial(RandomStringUtils.randomAlphabetic(300));
        dtoToSave.setCuit(RandomStringUtils.randomAlphabetic(300));
        dtoToSave.setCodPostal(RandomStringUtils.randomAlphabetic(300));
        dtoToSave.setDomicilio(RandomStringUtils.randomAlphabetic(300));
        dtoToSave.setLocalidad(RandomStringUtils.randomAlphabetic(300) );
        dtoToSave.setPais(RandomStringUtils.randomAlphabetic(300));
        dtoToSave.setProvincia(RandomStringUtils.randomAlphabetic(300));
        dtoToSave.setTransporte(RandomStringUtils.randomAlphabetic(300));
        dtoToSave.setSenasaUta(RandomStringUtils.randomAlphabetic(300));
        dtoToSave.setTelefono(RandomStringUtils.randomAlphabetic(300));
        dtoToSave.setCelular(RandomStringUtils.randomAlphabetic(300));
        dtoToSave.setFax(RandomStringUtils.randomAlphabetic(300));
        dtoToSave.setEmail(RandomStringUtils.randomAlphabetic(300));
        dtoToSave.setIdTipoCliente(0L);

        HttpClientErrorException.BadRequest thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> restTemplate.postForEntity(baseUrl, dtoToSave, SuccessfulResponse.class)
        );

        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(path, response.getPath());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_BODY, response.getMessage());
        assertEquals(14, response.getErrors().size());
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("razonSocial"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("cuit"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("codPostal"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("domicilio"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("localidad"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("pais"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("provincia"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("transporte"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("senasaUta"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("telefono"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("celular"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("fax"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("email"));
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_1, response.getErrors().get("idTipoCliente"));
    }
}
