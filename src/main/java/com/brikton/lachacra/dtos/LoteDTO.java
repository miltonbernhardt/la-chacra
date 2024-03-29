package com.brikton.lachacra.dtos;

import com.brikton.lachacra.constants.ValidationMessages;
import com.brikton.lachacra.entities.Lote;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class LoteDTO {

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

    private String id;
    private Double rendimiento;
    private Integer stockLote;
    private Double peso;
    private Boolean pesoNoConfiable;

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
        this.setCantCajas(lote.getCantCajas());
        this.setPesoNoConfiable(lote.getPesoNoConfiable());
    }

    public LoteDTO(LoteUpdateDTO dto) {
        this.setId(dto.getId());
        this.setFechaElaboracion(dto.getFechaElaboracion());
        this.setNumeroTina(dto.getNumeroTina());
        this.setLitrosLeche(dto.getLitrosLeche());
        this.setCantHormas(dto.getCantHormas());
        this.setStockLote(dto.getStockLote());
        this.setPeso(dto.getPeso());
        this.setRendimiento(dto.getRendimiento());
        this.setLoteCultivo(dto.getLoteCultivo());
        this.setLoteColorante(dto.getLoteColorante());
        this.setLoteCalcio(dto.getLoteCalcio());
        this.setLoteCuajo(dto.getLoteCuajo());
        this.setCodigoQueso(dto.getCodigoQueso());
        this.setCantCajas(dto.getCantCajas());
        this.setPesoNoConfiable(dto.getPesoNoConfiable());
    }

    public String getCodigoQueso() {
        return codigoQueso != null ? codigoQueso.toUpperCase() : null;
    }
}
