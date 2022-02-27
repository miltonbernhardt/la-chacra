package com.brikton.lachacra.services;

import com.brikton.lachacra.constants.ErrorMessages;
import com.brikton.lachacra.dtos.ClienteDTO;
import com.brikton.lachacra.dtos.ClienteUpdateDTO;
import com.brikton.lachacra.entities.Cliente;
import com.brikton.lachacra.entities.TipoCliente;
import com.brikton.lachacra.exceptions.ClienteNotFoundException;
import com.brikton.lachacra.exceptions.TipoClienteNotFoundConflictException;
import com.brikton.lachacra.exceptions.TipoClienteNotFoundException;
import com.brikton.lachacra.repositories.ClienteRepository;
import com.brikton.lachacra.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {ClienteService.class})
public class ClienteServiceTest {

    @Autowired
    ClienteService service;

    @MockBean
    ClienteRepository repository;

    @MockBean
    DateUtil dateUtil;

    @MockBean
    TipoClienteService tipoClienteService;

    @MockBean
    ExpedicionService expedicionService;

    @Test
    void Get_All__OK() {
        var mockTipoCliente1 = new TipoCliente();
        mockTipoCliente1.setId(1L);
        mockTipoCliente1.setTipo("tipo1");

        var mockTipoCliente2 = new TipoCliente();
        mockTipoCliente2.setId(2L);
        mockTipoCliente2.setTipo("tipo2");

        var mockCliente1 = new Cliente();
        mockCliente1.setId(1L);
        mockCliente1.setTipoCliente(mockTipoCliente1);
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

        var mockCliente2 = new Cliente();
        mockCliente2.setId(2L);
        mockCliente2.setTipoCliente(mockTipoCliente2);
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

        var mockClienteDTO1 = new ClienteDTO();
        mockClienteDTO1.setId(1L);
        mockClienteDTO1.setIdTipoCliente(1L);
        mockClienteDTO1.setCuit("99888888887");
        mockClienteDTO1.setCodPostal("3000");
        mockClienteDTO1.setDomicilio("Domicilio 1");
        mockClienteDTO1.setLocalidad("Localidad 1");
        mockClienteDTO1.setPais("Pais 1");
        mockClienteDTO1.setProvincia("Provincia 1");
        mockClienteDTO1.setTransporte("Provincia 1");
        mockClienteDTO1.setSenasaUta("Senasa UTA 1");
        mockClienteDTO1.setTelefono("233334444444");
        mockClienteDTO1.setCelular("233334444444");
        mockClienteDTO1.setFax("233334444444");
        mockClienteDTO1.setEmail("mail1@mail.com");
        mockClienteDTO1.setRazonSocial("Razon social 1");

        var mockClienteDTO2 = new ClienteDTO();
        mockClienteDTO2.setId(2L);
        mockClienteDTO2.setIdTipoCliente(2L);
        mockClienteDTO2.setCuit("887777777776");
        mockClienteDTO2.setCodPostal("3001");
        mockClienteDTO2.setDomicilio("Domicilio 2");
        mockClienteDTO2.setLocalidad("Localidad 2");
        mockClienteDTO2.setPais("Pais 2");
        mockClienteDTO2.setProvincia("Provincia 2");
        mockClienteDTO2.setTransporte("Provincia 2");
        mockClienteDTO2.setSenasaUta("Senasa UTA 2");
        mockClienteDTO2.setTelefono("344445555555");
        mockClienteDTO2.setCelular("344445555555");
        mockClienteDTO2.setFax("344445555555");
        mockClienteDTO2.setEmail("mail2@mail.com");
        mockClienteDTO2.setRazonSocial("Razon social 2");


        when(repository.findAllClientesNotFechaBaja()).thenReturn(List.of(mockCliente1, mockCliente2));
        var tipoClientes = service.getAll();
        assertEquals(2, tipoClientes.size());
        assertEquals(mockClienteDTO1, tipoClientes.get(0));
        assertEquals(mockClienteDTO2, tipoClientes.get(1));
    }

    @Test
    void Save__OK() {
        var mockTipoCliente = new TipoCliente();
        mockTipoCliente.setId(1L);
        mockTipoCliente.setTipo("tipo1");

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

        var dtoClienteExpected = new ClienteDTO();
        dtoClienteExpected.setId(1L);
        dtoClienteExpected.setIdTipoCliente(1L);
        dtoClienteExpected.setCuit("99888888887");
        dtoClienteExpected.setCodPostal("3000");
        dtoClienteExpected.setDomicilio("Domicilio 1");
        dtoClienteExpected.setLocalidad("Localidad 1");
        dtoClienteExpected.setPais("Pais 1");
        dtoClienteExpected.setProvincia("Provincia 1");
        dtoClienteExpected.setTransporte("Provincia 1");
        dtoClienteExpected.setSenasaUta("Senasa UTA 1");
        dtoClienteExpected.setTelefono("233334444444");
        dtoClienteExpected.setCelular("233334444444");
        dtoClienteExpected.setFax("233334444444");
        dtoClienteExpected.setEmail("mail1@mail.com");
        dtoClienteExpected.setRazonSocial("Razon social 1");

        var mockCliente = new Cliente();
        mockCliente.setId(1L);
        mockCliente.setTipoCliente(mockTipoCliente);
        mockCliente.setCuit("99888888887");
        mockCliente.setCodPostal("3000");
        mockCliente.setDomicilio("Domicilio 1");
        mockCliente.setLocalidad("Localidad 1");
        mockCliente.setPais("Pais 1");
        mockCliente.setProvincia("Provincia 1");
        mockCliente.setTransporte("Provincia 1");
        mockCliente.setSenasaUta("Senasa UTA 1");
        mockCliente.setTelefono("233334444444");
        mockCliente.setCelular("233334444444");
        mockCliente.setFax("233334444444");
        mockCliente.setEmail("mail1@mail.com");
        mockCliente.setRazonSocial("Razon social 1");

        when(tipoClienteService.getEntity(1L)).thenReturn(mockTipoCliente);
        when(repository.save(any(Cliente.class))).thenReturn(mockCliente);
        var dtoClienteActual = service.save(dtoClienteExpected);

        assertEquals(dtoClienteExpected, dtoClienteActual);
    }

    @Test
    void Save__Tipo_Cliente_Not_Exists() {
        var mockToSave = new ClienteDTO();
        mockToSave.setIdTipoCliente(1L);
        when(tipoClienteService.getEntity(1L)).thenThrow(new TipoClienteNotFoundException());
        TipoClienteNotFoundConflictException thrown = assertThrows(
                TipoClienteNotFoundConflictException.class, () -> service.save(mockToSave)
        );
        assertEquals(ErrorMessages.MSG_TIPO_CLIENTE_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Update__OK() {
        var mockTipoCliente = new TipoCliente();
        mockTipoCliente.setId(1L);
        mockTipoCliente.setTipo("tipo1");

        var dtoClienteToUpdate = new ClienteUpdateDTO();
        dtoClienteToUpdate.setId(1L);
        dtoClienteToUpdate.setIdTipoCliente(1L);
        dtoClienteToUpdate.setCuit("99888888887");
        dtoClienteToUpdate.setCodPostal("3000");
        dtoClienteToUpdate.setDomicilio("Domicilio 1");
        dtoClienteToUpdate.setLocalidad("Localidad 1");
        dtoClienteToUpdate.setPais("Pais 1");
        dtoClienteToUpdate.setProvincia("Provincia 1");
        dtoClienteToUpdate.setTransporte("Provincia 1");
        dtoClienteToUpdate.setSenasaUta("Senasa UTA 1");
        dtoClienteToUpdate.setTelefono("233334444444");
        dtoClienteToUpdate.setCelular("233334444444");
        dtoClienteToUpdate.setFax("233334444444");
        dtoClienteToUpdate.setEmail("mail1@mail.com");
        dtoClienteToUpdate.setRazonSocial("Razon social 1");

        var dtoClienteExpected = new ClienteDTO();
        dtoClienteExpected.setId(1L);
        dtoClienteExpected.setIdTipoCliente(1L);
        dtoClienteExpected.setCuit("99888888887");
        dtoClienteExpected.setCodPostal("3000");
        dtoClienteExpected.setDomicilio("Domicilio 1");
        dtoClienteExpected.setLocalidad("Localidad 1");
        dtoClienteExpected.setPais("Pais 1");
        dtoClienteExpected.setProvincia("Provincia 1");
        dtoClienteExpected.setTransporte("Provincia 1");
        dtoClienteExpected.setSenasaUta("Senasa UTA 1");
        dtoClienteExpected.setTelefono("233334444444");
        dtoClienteExpected.setCelular("233334444444");
        dtoClienteExpected.setFax("233334444444");
        dtoClienteExpected.setEmail("mail1@mail.com");
        dtoClienteExpected.setRazonSocial("Razon social 1");

        var mockCliente = new Cliente();
        mockCliente.setId(1L);
        mockCliente.setTipoCliente(mockTipoCliente);
        mockCliente.setCuit("99888888887");
        mockCliente.setCodPostal("3000");
        mockCliente.setDomicilio("Domicilio 1");
        mockCliente.setLocalidad("Localidad 1");
        mockCliente.setPais("Pais 1");
        mockCliente.setProvincia("Provincia 1");
        mockCliente.setTransporte("Provincia 1");
        mockCliente.setSenasaUta("Senasa UTA 1");
        mockCliente.setTelefono("233334444444");
        mockCliente.setCelular("233334444444");
        mockCliente.setFax("233334444444");
        mockCliente.setEmail("mail1@mail.com");
        mockCliente.setRazonSocial("Razon social 1");

        when(repository.existsByIdNotFechaBaja(1L)).thenReturn(true);
        when(tipoClienteService.getEntity(1L)).thenReturn(mockTipoCliente);
        when(repository.save(any(Cliente.class))).thenReturn(mockCliente);
        var dtoClienteActual = service.update(dtoClienteToUpdate);
        assertEquals(dtoClienteExpected, dtoClienteActual);
    }

    @Test
    void Update__Tipo_Cliente_Not_Exists() {
        var mockToUpdate = new ClienteUpdateDTO();
        mockToUpdate.setId(1L);
        mockToUpdate.setIdTipoCliente(1L);
        when(repository.existsByIdNotFechaBaja(1L)).thenReturn(true);
        when(tipoClienteService.getEntity(1L)).thenThrow(new TipoClienteNotFoundException());
        TipoClienteNotFoundConflictException thrown = assertThrows(
                TipoClienteNotFoundConflictException.class, () -> service.update(mockToUpdate)
        );
        assertEquals(ErrorMessages.MSG_TIPO_CLIENTE_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Update__Client_Not_Exists() {
        var mockToUpdate = new ClienteUpdateDTO();
        mockToUpdate.setId(1L);
        mockToUpdate.setIdTipoCliente(1L);
        when(repository.existsByIdNotFechaBaja(1L)).thenReturn(false);
        ClienteNotFoundException thrown = assertThrows(
                ClienteNotFoundException.class, () -> service.update(mockToUpdate)
        );
        assertEquals(ErrorMessages.MSG_CLIENTE_NOT_FOUND, thrown.getMessage());
    }

    @Test
    void Delete_With_Expedicion_Dependency__OK() {
        var mockCliente = new Cliente();
        mockCliente.setId(1L);
        mockCliente.setCuit("99888888887");
        mockCliente.setCodPostal("3000");
        mockCliente.setDomicilio("Domicilio 1");
        mockCliente.setLocalidad("Localidad 1");
        mockCliente.setPais("Pais 1");
        mockCliente.setProvincia("Provincia 1");
        mockCliente.setTransporte("Provincia 1");
        mockCliente.setSenasaUta("Senasa UTA 1");
        mockCliente.setTelefono("233334444444");
        mockCliente.setCelular("233334444444");
        mockCliente.setFax("233334444444");
        mockCliente.setEmail("mail1@mail.com");
        mockCliente.setRazonSocial("Razon social 1");

        var mockDeleted = new Cliente();
        mockCliente.setId(1L);
        mockCliente.setFechaBaja(LocalDate.of(2020, 10, 10));
        mockCliente.setCuit("99888888887");
        mockCliente.setCodPostal("3000");
        mockCliente.setDomicilio("Domicilio 1");
        mockCliente.setLocalidad("Localidad 1");
        mockCliente.setPais("Pais 1");
        mockCliente.setProvincia("Provincia 1");
        mockCliente.setTransporte("Provincia 1");
        mockCliente.setSenasaUta("Senasa UTA 1");
        mockCliente.setTelefono("233334444444");
        mockCliente.setCelular("233334444444");
        mockCliente.setFax("233334444444");
        mockCliente.setEmail("mail1@mail.com");
        mockCliente.setRazonSocial("Razon social 1");

        when(repository.existsByIdNotFechaBaja(1L)).thenReturn(true);
        when(repository.getById(1L)).thenReturn(mockCliente);
        when(expedicionService.existsByCliente(mockCliente)).thenReturn(true);
        when(dateUtil.now()).thenReturn(LocalDate.of(2020, 10, 10));
        when(repository.save(mockDeleted)).thenReturn(mockDeleted);

        var actualID = service.delete(1L);
        assertEquals(1L, actualID);
    }

    @Test
    void Delete_Without_Dependency__OK() {
        var mockCliente = new Cliente();
        mockCliente.setId(1L);
        mockCliente.setCuit("99888888887");
        mockCliente.setCodPostal("3000");
        mockCliente.setDomicilio("Domicilio 1");
        mockCliente.setLocalidad("Localidad 1");
        mockCliente.setPais("Pais 1");
        mockCliente.setProvincia("Provincia 1");
        mockCliente.setTransporte("Provincia 1");
        mockCliente.setSenasaUta("Senasa UTA 1");
        mockCliente.setTelefono("233334444444");
        mockCliente.setCelular("233334444444");
        mockCliente.setFax("233334444444");
        mockCliente.setEmail("mail1@mail.com");
        mockCliente.setRazonSocial("Razon social 1");

        when(repository.existsByIdNotFechaBaja(1L)).thenReturn(true);
        when(repository.getById(1L)).thenReturn(mockCliente);
        when(expedicionService.existsByCliente(mockCliente)).thenReturn(false);
        when(dateUtil.now()).thenReturn(LocalDate.of(2020, 10, 10));

        var actualID = service.delete(1L);
        assertEquals(null, actualID);
    }

    @Test
    void Delete__Client_Not_Exists() {
        when(repository.existsByIdNotFechaBaja(1L)).thenReturn(false);
        ClienteNotFoundException thrown = assertThrows(
                ClienteNotFoundException.class, () -> service.delete(1L)
        );
        assertEquals(ErrorMessages.MSG_CLIENTE_NOT_FOUND, thrown.getMessage());
    }
}
