package com.brikton.lachacra.dtos;

import com.brikton.lachacra.entities.Cliente;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Mod11Check;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class ClienteDTO {

    @NotNull(message = "No está presente")
    @Length(max = 255, message = "No debe superar los 255 caracteres")
    private String razonSocial;

    @NotNull(message = "No está presente")
    @Length(max = 255, message = "No debe superar los 255 caracteres")
    @Mod11Check(threshold = 7, message = "probar esto")
    private String cuit;

    @Length(max = 255, message = "No debe superar los 255 caracteres")
    private String domicilio;

    @Length(max = 6, message = "No debe superar los 6 caracteres")
    private String codPostal;

    @Length(max = 255, message = "No debe superar los 255 caracteres")
    private String localidad;

    @Length(max = 255, message = "No debe superar los 255 caracteres")
    private String provincia;

    @Length(max = 255, message = "No debe superar los 255 caracteres")
    private String pais;

    @Length(max = 255, message = "No debe superar los 255 caracteres")
    private String transporte;

    @Length(max = 255, message = "No debe superar los 255 caracteres")
    private String senasaUta;

    @NotNull(message = "No está presente")
    @Min(value = 0, message = "No puede ser menor a 0")
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
