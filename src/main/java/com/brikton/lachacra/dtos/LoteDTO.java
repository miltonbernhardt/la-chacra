package com.brikton.lachacra.dtos;

import com.brikton.lachacra.entities.Lote;
import com.brikton.lachacra.constants.ValidationMessages;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class LoteDTO {

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @PastOrPresent(message = ValidationMessages.CANT_BE_LATER_THAN_TODAY)
    private LocalDate fechaElaboracion;

    @NotNull(message = ValidationMessages.NOT_FOUND)
    private Integer numeroTina;

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @Min(value = 0, message = ValidationMessages.CANNOT_BE_LESS_THAN_0)
    private Double litrosLeche;

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @Min(value = 0, message = ValidationMessages.CANNOT_BE_LESS_THAN_0)
    private Integer cantHormas;

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @Min(value = 0, message = ValidationMessages.CANNOT_BE_LESS_THAN_0)
    private Double peso;

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @Length(max = 3, message = ValidationMessages.MUST_NOT_EXCEED_3_CHARACTERS)
    private String codigoQueso;

    @Length(max = 255, message = ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS)
    private String loteColorante;

    @Length(max = 255, message = ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS)
    private String loteCultivo;

    @Length(max = 255, message = ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS)
    private String loteCalcio;

    @Length(max = 255, message = ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS)
    private String loteCuajo;

    private String id;
    private Double rendimiento;
    private Integer stockLote;

    public LoteDTO() {
    }

    public LoteDTO(Lote lote) {
        this.setId(lote.getId());
        this.setFechaElaboracion(lote.getFechaElaboracion());
        this.setNumeroTina(lote.getNumeroTina());
        this.setLitrosLeche(lote.getLitrosLeche());
        this.setCantHormas(lote.getCantHormas());
        this.setStockLote(lote.getStockLote());
        this.setPeso(lote.getPeso());
        this.setRendimiento(lote.getRendimiento());
        this.setLoteCultivo(lote.getLoteCultivo());
        this.setLoteColorante(lote.getLoteColorante());
        this.setLoteCalcio(lote.getLoteCalcio());
        this.setLoteCuajo(lote.getLoteCuajo());
        this.setCodigoQueso(lote.getQueso().getCodigo());
    }
}
