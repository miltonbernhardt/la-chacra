package com.brikton.lachacra.dtos;

import com.brikton.lachacra.constants.ValidationMessages;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ClienteUpdateDTO {

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @Min(value = 1, message = ValidationMessages.CANNOT_BE_LESS_THAN_1)
    private Long id;

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

    public ClienteUpdateDTO() {
    }
}
