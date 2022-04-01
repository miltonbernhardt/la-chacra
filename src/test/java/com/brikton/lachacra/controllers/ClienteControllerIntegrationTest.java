package com.brikton.lachacra.controllers;

import com.brikton.lachacra.configs.DatabaseTestConfig;
import com.brikton.lachacra.configs.NotSecurityConfigTest;
import com.brikton.lachacra.constants.ErrorMessages;
import com.brikton.lachacra.constants.Path;
import com.brikton.lachacra.constants.SuccessfulMessages;
import com.brikton.lachacra.constants.ValidationMessages;
import com.brikton.lachacra.dtos.ClienteDTO;
import com.brikton.lachacra.dtos.ClienteUpdateDTO;
import com.brikton.lachacra.responses.ErrorResponse;
import com.brikton.lachacra.responses.SuccessfulResponse;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.Assert;
import org.springframework.web.client.*;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({DatabaseTestConfig.class, NotSecurityConfigTest.class})
@ActiveProfiles("test")
@Sql(scripts = {"classpath:data_test.sql"}, executionPhase = BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ClienteControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";
    private final String path = Path.API_CLIENTES.concat("/");

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
        var dto1 = new ClienteDTO();
        dto1.setId(1L);
        dto1.setIdTipoCliente(1L);
        dto1.setCuit("20-11111111-7");
        dto1.setCodPostal("3080");
        dto1.setDomicilio("Jacob 2830");
        dto1.setLocalidad("Esperanza");
        dto1.setPais("Argentina");
        dto1.setProvincia("Santa Fe");
        dto1.setTransporte("Familia Noroña S.A.");
        dto1.setSenasaUta("113754");
        dto1.setTelefono("233334444444");
        dto1.setCelular("233334444444");
        dto1.setFax(null);
        dto1.setEmail("mail1@mail.com");
        dto1.setRazonSocial("ABDALA, Gustavo");

        var dto2 = new ClienteDTO();
        dto2.setId(2L);
        dto2.setIdTipoCliente(2L);
        dto2.setCuit("27-22222222-5");
        dto2.setCodPostal("3560");
        dto2.setDomicilio("Hipólito Yrigoyen 1442");
        dto2.setLocalidad("Reconquista");
        dto2.setPais("Argentina");
        dto2.setProvincia("Santa Fe");
        dto2.setTransporte("Cerutti, Pablo");
        dto2.setSenasaUta("94265");
        dto2.setTelefono("344445555555");
        dto2.setCelular("344445555555");
        dto2.setFax("344445555555");
        dto2.setEmail("mail2@mail.com");
        dto2.setRazonSocial("ALBERTINAZZI, Olga Pompeya");

        var dto3 = new ClienteDTO();
        dto3.setId(3L);
        dto3.setIdTipoCliente(3L);
        dto3.setCuit("20-33333333-3");
        dto3.setCodPostal(null);
        dto3.setDomicilio("Av. Centenario 4797");
        dto3.setLocalidad("Espeleta");
        dto3.setPais("Argentina");
        dto3.setProvincia("Buenos Aires");
        dto3.setTransporte("Bianchi");
        dto3.setSenasaUta("83200");
        dto3.setTelefono("755556666666");
        dto3.setCelular("755556666666");
        dto3.setFax("755556666666");
        dto3.setEmail("mail3@mail.com");
        dto3.setRazonSocial("ALEGRI, José César");

        var expectedClientes = List.of(dto1, dto2, dto3);
        var response = restTemplate.getForEntity(baseUrl, SuccessfulResponse.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var successfulResponse = mapper.convertValue(response.getBody(), new TypeReference<SuccessfulResponse<List<ClienteDTO>>>() {});
        assertEquals("", successfulResponse.getMessage());
        assertEquals(expectedClientes, successfulResponse.getData());
    }

    @Test
    void Save__OK() {
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
        expectedCliente.setId(5L);
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

        var response = restTemplate.postForEntity(baseUrl, mockToSave, SuccessfulResponse.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var successfulResponse = mapper.convertValue(response.getBody(), new TypeReference<SuccessfulResponse<ClienteDTO>>() {});
        assertEquals(SuccessfulMessages.MSG_CLIENTE_CREATED, successfulResponse.getMessage());
        assertEquals(expectedCliente, successfulResponse.getData());
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
        dtoToSave.setRazonSocial(RandomStringUtils.randomAlphabetic(256));
        dtoToSave.setCuit(RandomStringUtils.randomAlphabetic(256));
        dtoToSave.setCodPostal(RandomStringUtils.randomAlphabetic(7));
        dtoToSave.setDomicilio(RandomStringUtils.randomAlphabetic(256));
        dtoToSave.setLocalidad(RandomStringUtils.randomAlphabetic(256));
        dtoToSave.setPais(RandomStringUtils.randomAlphabetic(256));
        dtoToSave.setProvincia(RandomStringUtils.randomAlphabetic(256));
        dtoToSave.setTransporte(RandomStringUtils.randomAlphabetic(256));
        dtoToSave.setSenasaUta(RandomStringUtils.randomAlphabetic(256));
        dtoToSave.setTelefono(RandomStringUtils.randomAlphabetic(256));
        dtoToSave.setCelular(RandomStringUtils.randomAlphabetic(256));
        dtoToSave.setFax(RandomStringUtils.randomAlphabetic(256));
        dtoToSave.setEmail(RandomStringUtils.randomAlphabetic(256));
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
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_6_CHARACTERS, response.getErrors().get("codPostal"));
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

    @Test
    void Update__OK() {
        var dtoToUpdate = new ClienteUpdateDTO();
        dtoToUpdate.setId(2L);
        dtoToUpdate.setIdTipoCliente(2L);
        dtoToUpdate.setCuit("relate");
        dtoToUpdate.setCodPostal("guilt");
        dtoToUpdate.setDomicilio("coffee");
        dtoToUpdate.setLocalidad("chief");
        dtoToUpdate.setPais("point");
        dtoToUpdate.setProvincia("Provincia 1");
        dtoToUpdate.setTransporte("Provincia 1");
        dtoToUpdate.setSenasaUta("Senasa UTA 1");
        dtoToUpdate.setTelefono("233334444444");
        dtoToUpdate.setCelular("233334444444");
        dtoToUpdate.setFax("233334444444");
        dtoToUpdate.setEmail("mail1@mail.com");
        dtoToUpdate.setRazonSocial("Razon social 1");

        var expectedCliente = new ClienteDTO();
        expectedCliente.setId(2L);
        expectedCliente.setIdTipoCliente(2L);
        expectedCliente.setCuit("relate");
        expectedCliente.setCodPostal("guilt");
        expectedCliente.setDomicilio("coffee");
        expectedCliente.setLocalidad("chief");
        expectedCliente.setPais("point");
        expectedCliente.setProvincia("Provincia 1");
        expectedCliente.setTransporte("Provincia 1");
        expectedCliente.setSenasaUta("Senasa UTA 1");
        expectedCliente.setTelefono("233334444444");
        expectedCliente.setCelular("233334444444");
        expectedCliente.setFax("233334444444");
        expectedCliente.setEmail("mail1@mail.com");
        expectedCliente.setRazonSocial("Razon social 1");

        var response = putForEntity(baseUrl, dtoToUpdate, SuccessfulResponse.class);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var successfulResponse = mapper.convertValue(response.getBody(), new TypeReference<SuccessfulResponse<ClienteDTO>>() {});
        assertEquals(SuccessfulMessages.MSG_CLIENTE_UPDATED, successfulResponse.getMessage());
        assertEquals(expectedCliente, successfulResponse.getData());
    }

    @Test
    void Update__Tipo_Cliente_Not_Exists() throws JsonProcessingException {
        var dtoToUpdate = new ClienteUpdateDTO();
        dtoToUpdate.setId(1L);
        dtoToUpdate.setIdTipoCliente(5L);
        dtoToUpdate.setCuit("99888888887");
        dtoToUpdate.setCodPostal("3000");
        dtoToUpdate.setDomicilio("Domicilio 1");
        dtoToUpdate.setLocalidad("Localidad 1");
        dtoToUpdate.setPais("Pais 1");
        dtoToUpdate.setProvincia("Provincia 1");
        dtoToUpdate.setTransporte("Provincia 1");
        dtoToUpdate.setSenasaUta("Senasa UTA 1");
        dtoToUpdate.setTelefono("233334444444");
        dtoToUpdate.setCelular("233334444444");
        dtoToUpdate.setFax("233334444444");
        dtoToUpdate.setEmail("mail1@mail.com");
        dtoToUpdate.setRazonSocial("Razon social 1");

        HttpClientErrorException.Conflict thrown = assertThrows(
                HttpClientErrorException.Conflict.class, () -> putForEntity(baseUrl, dtoToUpdate, SuccessfulResponse.class)
        );
        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.CONFLICT, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_TIPO_CLIENTE_NOT_FOUND, response.getMessage());
        assertEquals(path, response.getPath());
    }

    @Test
    void Update__Invalid_Fields__1() throws JsonProcessingException {
        var dtoToUpdate = new ClienteUpdateDTO();
        dtoToUpdate.setCodPostal("3000");
        dtoToUpdate.setDomicilio("Domicilio 1");
        dtoToUpdate.setLocalidad("Localidad 1");
        dtoToUpdate.setPais("Pais 1");
        dtoToUpdate.setProvincia("Provincia 1");
        dtoToUpdate.setTransporte("Provincia 1");
        dtoToUpdate.setSenasaUta("Senasa UTA 1");
        dtoToUpdate.setTelefono("233334444444");
        dtoToUpdate.setCelular("233334444444");
        dtoToUpdate.setFax("233334444444");
        dtoToUpdate.setEmail("mail1@mail.com");

        HttpClientErrorException.BadRequest thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> putForEntity(baseUrl, dtoToUpdate, SuccessfulResponse.class)
        );

        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(path, response.getPath());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_BODY, response.getMessage());
        assertEquals(4, response.getErrors().size());
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("id"));
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("razonSocial"));
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("cuit"));
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("idTipoCliente"));
    }

    @Test
    void Update__Invalid_Fields__Fields_Too_Large() throws JsonProcessingException {
        var dtoToUpdate = new ClienteUpdateDTO();
        dtoToUpdate.setRazonSocial(RandomStringUtils.randomAlphabetic(256));
        dtoToUpdate.setCuit(RandomStringUtils.randomAlphabetic(256));
        dtoToUpdate.setCodPostal(RandomStringUtils.randomAlphabetic(7));
        dtoToUpdate.setDomicilio(RandomStringUtils.randomAlphabetic(256));
        dtoToUpdate.setLocalidad(RandomStringUtils.randomAlphabetic(256));
        dtoToUpdate.setPais(RandomStringUtils.randomAlphabetic(256));
        dtoToUpdate.setProvincia(RandomStringUtils.randomAlphabetic(256));
        dtoToUpdate.setTransporte(RandomStringUtils.randomAlphabetic(256));
        dtoToUpdate.setSenasaUta(RandomStringUtils.randomAlphabetic(256));
        dtoToUpdate.setTelefono(RandomStringUtils.randomAlphabetic(256));
        dtoToUpdate.setCelular(RandomStringUtils.randomAlphabetic(256));
        dtoToUpdate.setFax(RandomStringUtils.randomAlphabetic(256));
        dtoToUpdate.setEmail(RandomStringUtils.randomAlphabetic(256));
        dtoToUpdate.setId(0L);
        dtoToUpdate.setIdTipoCliente(0L);

        HttpClientErrorException.BadRequest thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> putForEntity(baseUrl, dtoToUpdate, SuccessfulResponse.class)
        );

        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(path, response.getPath());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_BODY, response.getMessage());
        assertEquals(15, response.getErrors().size());
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("razonSocial"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("cuit"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_6_CHARACTERS, response.getErrors().get("codPostal"));
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
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_1, response.getErrors().get("id"));
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_1, response.getErrors().get("idTipoCliente"));
    }

    @Test
    void Delete_Client_Without_Dependencies__OK() throws JsonProcessingException {
        var expectedMessage = mapper.writeValueAsString(SuccessfulMessages.MSG_CLIENTE_DELETED);

        var response = deleteForEntity(baseUrl.concat("2"), SuccessfulResponse.class);
        var actualMessage = mapper.writeValueAsString(requireNonNull(response.getBody()).getMessage());

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void Delete_Client_With_Dependencies__OK() throws JsonProcessingException {
        var expectedMessage = mapper.writeValueAsString(SuccessfulMessages.MSG_CLIENTE_DELETED);

        var response = deleteForEntity(baseUrl.concat("1"), SuccessfulResponse.class);
        var actualMessage = mapper.writeValueAsString(requireNonNull(response.getBody()).getMessage());

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void Delete__Client_Not_Found() throws JsonProcessingException {
        HttpClientErrorException.NotFound thrown = assertThrows(
                HttpClientErrorException.NotFound.class, () -> deleteForEntity(baseUrl.concat("5"), SuccessfulResponse.class)
        );
        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_CLIENTE_NOT_FOUND, response.getMessage());
        assertEquals(path.concat("5"), response.getPath());
    }

    @Test
    void Delete__Client_Already_Deleted() throws JsonProcessingException {
        HttpClientErrorException.NotFound thrown = assertThrows(
                HttpClientErrorException.NotFound.class, () -> deleteForEntity(baseUrl.concat("4"), SuccessfulResponse.class)
        );
        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_CLIENTE_NOT_FOUND, response.getMessage());
        assertEquals(path.concat("4"), response.getPath());
    }

    @Test
    void Delete__Bad_ID() throws JsonProcessingException {
        HttpClientErrorException.BadRequest thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> deleteForEntity(baseUrl.concat("0"), SuccessfulResponse.class)
        );
        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_PARAMS, response.getMessage());
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_1, response.getErrors().get("id"));
        assertEquals(path.concat("0"), response.getPath());
    }

    @Test
    void Delete__OK___After_That__Cliente_Doesnt_Show_In_Get_All() {
        var dto1 = new ClienteDTO();
        dto1.setId(1L);
        dto1.setIdTipoCliente(1L);
        dto1.setCuit("20-11111111-7");
        dto1.setCodPostal("3080");
        dto1.setDomicilio("Jacob 2830");
        dto1.setLocalidad("Esperanza");
        dto1.setPais("Argentina");
        dto1.setProvincia("Santa Fe");
        dto1.setTransporte("Familia Noroña S.A.");
        dto1.setSenasaUta("113754");
        dto1.setTelefono("233334444444");
        dto1.setCelular("233334444444");
        dto1.setFax(null);
        dto1.setEmail("mail1@mail.com");
        dto1.setRazonSocial("ABDALA, Gustavo");

        var dto2 = new ClienteDTO();
        dto2.setId(2L);
        dto2.setIdTipoCliente(2L);
        dto2.setCuit("27-22222222-5");
        dto2.setCodPostal("3560");
        dto2.setDomicilio("Hipólito Yrigoyen 1442");
        dto2.setLocalidad("Reconquista");
        dto2.setPais("Argentina");
        dto2.setProvincia("Santa Fe");
        dto2.setTransporte("Cerutti, Pablo");
        dto2.setSenasaUta("94265");
        dto2.setTelefono("344445555555");
        dto2.setCelular("344445555555");
        dto2.setFax("344445555555");
        dto2.setEmail("mail2@mail.com");
        dto2.setRazonSocial("ALBERTINAZZI, Olga Pompeya");

        var dto3 = new ClienteDTO();
        dto3.setId(3L);
        dto3.setIdTipoCliente(3L);
        dto3.setCuit("20-33333333-3");
        dto3.setCodPostal(null);
        dto3.setDomicilio("Av. Centenario 4797");
        dto3.setLocalidad("Espeleta");
        dto3.setPais("Argentina");
        dto3.setProvincia("Buenos Aires");
        dto3.setTransporte("Bianchi");
        dto3.setSenasaUta("83200");
        dto3.setTelefono("755556666666");
        dto3.setCelular("755556666666");
        dto3.setFax("755556666666");
        dto3.setEmail("mail3@mail.com");
        dto3.setRazonSocial("ALEGRI, José César");

        var expectedClientes = List.of(dto1, dto2, dto3);
        var response = restTemplate.getForEntity(baseUrl, SuccessfulResponse.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var actualClientes = mapper.convertValue(response.getBody(), new TypeReference<SuccessfulResponse<List<ClienteDTO>>>() {}).getData();
        assertEquals(expectedClientes, actualClientes);

        //cliente dado de baja
        response = deleteForEntity(baseUrl.concat("1"), SuccessfulResponse.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        //cliente borrado
        response = deleteForEntity(baseUrl.concat("2"), SuccessfulResponse.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        expectedClientes = List.of(dto3);
        response = restTemplate.getForEntity(baseUrl, SuccessfulResponse.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        actualClientes = mapper.convertValue(response.getBody(), new TypeReference<SuccessfulResponse<List<ClienteDTO>>>() {}).getData();
        assertEquals(expectedClientes, actualClientes);
    }

}
