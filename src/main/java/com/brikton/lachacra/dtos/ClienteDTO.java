package com.brikton.lachacra.dtos;

import com.brikton.lachacra.constants.ValidationMessages;
import com.brikton.lachacra.entities.Cliente;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ClienteDTO {

    @NotBlank(message = ValidationMessages.NOT_FOUND)
    @Length(max = 255, message = ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS)
    private String razonSocial;

    @NotBlank(message = ValidationMessages.NOT_FOUND)
    @Length(max = 255, message = ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS)
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

    @Length(max = 255, message = ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS)
    private String telefono;

    @Length(max = 255, message = ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS)
    private String celular;

    @Length(max = 255, message = ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS)
    private String fax;

    @Length(max = 255, message = ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS)
    private String email;

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @Min(value = 1, message = ValidationMessages.CANNOT_BE_LESS_THAN_1)
    private Long idTipoCliente;

    private Long id;

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
        this.setEmail(cliente.getEmail());
        this.setTelefono(cliente.getTelefono());
        this.setCelular(cliente.getCelular());
        this.setFax(cliente.getFax());
    }

    public ClienteDTO(ClienteUpdateDTO dto) {
        this.setId(dto.getId());
        this.setRazonSocial(dto.getRazonSocial());
        this.setCuit(dto.getCuit());
        this.setDomicilio(dto.getDomicilio());
        this.setCodPostal(dto.getCodPostal());
        this.setLocalidad(dto.getLocalidad());
        this.setProvincia(dto.getProvincia());
        this.setPais(dto.getPais());
        this.setTransporte(dto.getTransporte());
        this.setSenasaUta(dto.getSenasaUta());
        this.setIdTipoCliente(dto.getIdTipoCliente());
        this.setEmail(dto.getEmail());
        this.setTelefono(dto.getTelefono());
        this.setCelular(dto.getCelular());
        this.setFax(dto.getFax());
    }
}
