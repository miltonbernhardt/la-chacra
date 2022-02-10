package com.brikton.lachacra.dtos;

import com.brikton.lachacra.entities.Cliente;
import com.brikton.lachacra.constants.ValidationMessages;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Mod11Check;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class ClienteDTO {

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @Length(max = 255, message = ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS)
    private String razonSocial;

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @Length(max = 255, message = ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS)
    @Mod11Check(threshold = 7, message = ValidationMessages.INVALID_FORMAT)
    private String cuit;

    @Length(max = 255, message = ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS)
    private String domicilio;

    @Length(max = 6, message = ValidationMessages.MUST_NOT_EXCEED_6_CHARACTERS)
    private String codPostal;

    @Length(max = 255, message = ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS)
    private String localidad;

    @Length(max = 255, message = ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS)
    private String provincia;

    @Length(max = 255, message = ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS)
    private String pais;

    @Length(max = 255, message = ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS)
    private String transporte;

    @Length(max = 255, message = ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS)
    private String senasaUta;

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @Min(value = 0, message = ValidationMessages.CANNOT_BE_LESS_THAN_0)
    private Long idTipoCliente;

    private Long id;

    public ClienteDTO() {
    }

    public ClienteDTO(Cliente cliente) {
        this.setId(cliente.getId());
        this.setRazonSocial(cliente.getRazonSocial());
        this.setCuit(cliente.getCuit());
        this.setDomicilio(cliente.getDomicilio());
        this.setCodPostal(cliente.getCodPostal());
        this.setLocalidad(cliente.getLocalidad());
        this.setProvincia(cliente.getProvincia());
        this.setPais(cliente.getPais());
        this.setTransporte(cliente.getTransporte());
        this.setSenasaUta(cliente.getSenasaUta());
        this.setIdTipoCliente(cliente.getTipoCliente().getId());
    }
}
