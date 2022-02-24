package com.brikton.lachacra.controllers;

import com.brikton.lachacra.constants.ErrorMessages;
import com.brikton.lachacra.constants.SuccessfulMessages;
import com.brikton.lachacra.dtos.ClienteDTO;
import com.brikton.lachacra.dtos.ClienteUpdateDTO;
import com.brikton.lachacra.exceptions.ClienteNotFoundException;
import com.brikton.lachacra.exceptions.TipoClienteNotFoundConflictException;
import com.brikton.lachacra.services.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = {ClienteController.class})
public class ClienteControllerTest {

    @Autowired
    ClienteController controller;

    @MockBean
    ClienteService service;

    @Test
    void Get_All__OK() {
        var mockCliente1 = new ClienteDTO();
        mockCliente1.setId(1L);
        mockCliente1.setIdTipoCliente(1L);
        mockCliente1.setCuit("99888888887");
        mockCliente1.setCodPostal("3000");
        mockCliente1.setDomicilio("Domicilio 1");
        mockCliente1.setLocalidad("Localidad 1");
        mockCliente1.setPais("Pais 1");
        mockCliente1.setProvincia("Provincia 1");
        mockCliente1.setTransporte("Provincia 1");
        mockCliente1.setSenasaUta("Senasa UTA 1");
        mockCliente1.setTelefono("233334444444");
        mockCliente1.setCelular("233334444444");
        mockCliente1.setFax("233334444444");
        mockCliente1.setEmail("mail1@mail.com");
        mockCliente1.setRazonSocial("Razon social 1");

        var mockCliente2 = new ClienteDTO();
        mockCliente2.setId(2L);
        mockCliente2.setIdTipoCliente(2L);
        mockCliente2.setCuit("887777777776");
        mockCliente2.setCodPostal("3001");
        mockCliente2.setDomicilio("Domicilio 2");
        mockCliente2.setLocalidad("Localidad 2");
        mockCliente2.setPais("Pais 2");
        mockCliente2.setProvincia("Provincia 2");
        mockCliente2.setTransporte("Provincia 2");
        mockCliente2.setSenasaUta("Senasa UTA 2");
        mockCliente2.setTelefono("344445555555");
        mockCliente2.setCelular("344445555555");
        mockCliente2.setFax("344445555555");
        mockCliente2.setEmail("mail2@mail.com");
        mockCliente2.setRazonSocial("Razon social 2");

        when(service.getAll()).thenReturn(List.of(mockCliente1, mockCliente2));
        var actualDTOs = requireNonNull(controller.getAll().getBody()).getData();
        assertEquals(2, actualDTOs.size());
        assertEquals(mockCliente1, actualDTOs.get(0));
        assertEquals(mockCliente2, actualDTOs.get(1));
    }

    @Test
    void Save__OK() throws TipoClienteNotFoundConflictException {
        var mockReturned = new ClienteDTO();
        mockReturned.setId(1L);
        mockReturned.setIdTipoCliente(1L);
        mockReturned.setCuit("99888888887");
        mockReturned.setCodPostal("3000");
        mockReturned.setDomicilio("Domicilio 1");
        mockReturned.setLocalidad("Localidad 1");
        mockReturned.setPais("Pais 1");
        mockReturned.setProvincia("Provincia 1");
        mockReturned.setTransporte("Provincia 1");
        mockReturned.setSenasaUta("Senasa UTA 1");
        mockReturned.setTelefono("233334444444");
        mockReturned.setCelular("233334444444");
        mockReturned.setFax("233334444444");
        mockReturned.setEmail("mail1@mail.com");
        mockReturned.setRazonSocial("Razon social 1");

        var mockSaved = new ClienteDTO();
        mockSaved.setId(1L);
        mockSaved.setIdTipoCliente(1L);
        mockSaved.setCuit("99888888887");
        mockSaved.setCodPostal("3000");
        mockSaved.setDomicilio("Domicilio 1");
        mockSaved.setLocalidad("Localidad 1");
        mockSaved.setPais("Pais 1");
        mockSaved.setProvincia("Provincia 1");
        mockSaved.setTransporte("Provincia 1");
        mockSaved.setSenasaUta("Senasa UTA 1");
        mockSaved.setTelefono("233334444444");
        mockSaved.setCelular("233334444444");
        mockSaved.setFax("233334444444");
        mockSaved.setEmail("mail1@mail.com");
        mockSaved.setRazonSocial("Razon social 1");

        when(service.save(any(ClienteDTO.class))).thenReturn(mockReturned);

        var response = requireNonNull(controller.save(mockSaved).getBody());

        var dtoActual = response.getData();
        String message = response.getMessage();
        assertEquals(mockReturned, dtoActual);
        assertEquals(SuccessfulMessages.MSG_CLIENTE_CREATED, message);
    }

    @Test
    void Save__Tipo_Cliente_Not_Exists() throws TipoClienteNotFoundConflictException {
        var dto = new ClienteDTO();
        when(service.save(dto)).thenThrow(new TipoClienteNotFoundConflictException());
        TipoClienteNotFoundConflictException thrown = assertThrows(
                TipoClienteNotFoundConflictException.class, () -> controller.save(dto)
        );
        assertEquals(ErrorMessages.MSG_TIPO_CLIENTE_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Update__OK() throws TipoClienteNotFoundConflictException, ClienteNotFoundException {
        var mockReturned = new ClienteDTO();
        mockReturned.setId(1L);
        mockReturned.setIdTipoCliente(1L);
        mockReturned.setCuit("99888888887");
        mockReturned.setCodPostal("3000");
        mockReturned.setDomicilio("Domicilio 1");
        mockReturned.setLocalidad("Localidad 1");
        mockReturned.setPais("Pais 1");
        mockReturned.setProvincia("Provincia 1");
        mockReturned.setTransporte("Provincia 1");
        mockReturned.setSenasaUta("Senasa UTA 1");
        mockReturned.setTelefono("233334444444");
        mockReturned.setCelular("233334444444");
        mockReturned.setFax("233334444444");
        mockReturned.setEmail("mail1@mail.com");
        mockReturned.setRazonSocial("Razon social 1");

        var mockUpdated = new ClienteUpdateDTO();
        mockUpdated.setId(1L);
        mockUpdated.setIdTipoCliente(1L);
        mockUpdated.setCuit("99888888887");
        mockUpdated.setCodPostal("3000");
        mockUpdated.setDomicilio("Domicilio 1");
        mockUpdated.setLocalidad("Localidad 1");
        mockUpdated.setPais("Pais 1");
        mockUpdated.setProvincia("Provincia 1");
        mockUpdated.setTransporte("Provincia 1");
        mockUpdated.setSenasaUta("Senasa UTA 1");
        mockUpdated.setTelefono("233334444444");
        mockUpdated.setCelular("233334444444");
        mockUpdated.setFax("233334444444");
        mockUpdated.setEmail("mail1@mail.com");
        mockUpdated.setRazonSocial("Razon social 1");

        when(service.update(any(ClienteUpdateDTO.class))).thenReturn(mockReturned);

        var response = requireNonNull(controller.update(mockUpdated).getBody());

        var dtoActual = response.getData();
        String message = response.getMessage();
        assertEquals(mockReturned, dtoActual);
        assertEquals(SuccessfulMessages.MSG_CLIENTE_UPDATED, message);
    }

    @Test
    void Update__Tipo_Cliente_Not_Exists() throws TipoClienteNotFoundConflictException, ClienteNotFoundException {
        var dto = new ClienteUpdateDTO();
        when(service.update(dto)).thenThrow(new TipoClienteNotFoundConflictException());
        TipoClienteNotFoundConflictException thrown = assertThrows(
                TipoClienteNotFoundConflictException.class, () -> controller.update(dto)
        );
        assertEquals(ErrorMessages.MSG_TIPO_CLIENTE_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Update__Client_Not_Exists() throws TipoClienteNotFoundConflictException, ClienteNotFoundException {
        var mockToUpdate = new ClienteUpdateDTO();
        mockToUpdate.setId(1L);
        mockToUpdate.setIdTipoCliente(1L);
        when(service.update(mockToUpdate)).thenThrow(new ClienteNotFoundException());
        ClienteNotFoundException thrown = assertThrows(
                ClienteNotFoundException.class, () -> service.update(mockToUpdate)
        );
        assertEquals(ErrorMessages.MSG_CLIENTE_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Delete__OK() throws ClienteNotFoundException {
        when(service.delete(1L)).thenReturn(1L);
        var response = requireNonNull(controller.delete(1L).getBody());
        var actualID = response.getData();
        var message = response.getMessage();
        assertEquals(1L, actualID);
        assertEquals(SuccessfulMessages.MSG_CLIENTE_DELETED, message);
    }

    @Test
    void Delete__Client_Not_Found() throws ClienteNotFoundException {
        when(service.delete(1L)).thenThrow(new ClienteNotFoundException());
        ClienteNotFoundException thrown = assertThrows(
                ClienteNotFoundException.class, () -> controller.delete(1L)
        );
        assertEquals(ErrorMessages.MSG_CLIENTE_NOT_FOUND, thrown.getMessage());
    }

}
