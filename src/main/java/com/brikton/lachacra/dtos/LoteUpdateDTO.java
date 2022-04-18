package com.brikton.lachacra.dtos;

import com.brikton.lachacra.constants.ValidationMessages;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class LoteUpdateDTO {

    @NotBlank(message = ValidationMessages.NOT_FOUND)
    @Pattern(regexp = "^[0-9]{12,14}$", message = ValidationMessages.INVALID_FORMAT)
    private String id;

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @PastOrPresent(message = ValidationMessages.CANT_BE_LATER_THAN_TODAY)
    private LocalDate fechaElaboracion;

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @Min(value = 1, message = ValidationMessages.CANNOT_BE_LESS_THAN_1)
    @Max(value = 999, message = ValidationMessages.MUST_BE_LESS_THAN_1000)
    private Integer numeroTina;

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @Min(value = 1, message = ValidationMessages.CANNOT_BE_LESS_THAN_1)
    private Double litrosLeche;

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @Min(value = 1, message = ValidationMessages.CANNOT_BE_LESS_THAN_1)
    private Integer cantHormas;

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @Min(value = 1, message = ValidationMessages.CANNOT_BE_LESS_THAN_1)
    private Integer cantCajas;

    @NotBlank(message = ValidationMessages.NOT_FOUND)
    @Length(min = 3, max = 3, message = ValidationMessages.MUST_HAVE_3_CHARACTERS)
    private String codigoQueso;

    @Length(max = 255, message = ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS)
    private String loteColorante;

    @Length(max = 255, message = ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS)
    private String loteCultivo;

    @Length(max = 255, message = ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS)
    private String loteCalcio;

    @Length(max = 255, message = ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS)
    private String loteCuajo;

    private Double rendimiento;
    private Integer stockLote;
    private Double peso;

    public LoteUpdateDTO() {
    }
}
