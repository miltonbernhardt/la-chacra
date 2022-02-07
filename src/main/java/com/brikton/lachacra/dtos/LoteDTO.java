package com.brikton.lachacra.dtos;

import com.brikton.lachacra.entities.Lote;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class LoteDTO {

    @NotNull(message = "No está presente")
    @PastOrPresent(message = "No puede ser posterior al día de hoy")
    private LocalDate fechaElaboracion;

    @NotNull(message = "No está presente")
    private Integer numeroTina;

    @NotNull(message = "No está presente")
    @Min(value = 0, message = "No puede ser menor a 0")
    private Double litrosLeche;

    @NotNull(message = "No está presente")
    @Min(value = 0, message = "No puede ser menor a 0")
    private Integer cantHormas;

    @NotNull(message = "No está presente")
    @Min(value = 0, message = "No puede ser menor a 0")
    private Double peso;

    @NotNull(message = "No está presente")
    @Min(value = 0, message = "No puede ser menor a 0")
    private Long idQueso;

    @Length(max = 255, message = "No debe superar los 255 caracteres")
    private String loteColorante;

    @Length(max = 255, message = "No debe superar los 255 caracteres")
    private String loteCultivo;

    @Length(max = 255, message = "No debe superar los 255 caracteres")
    private String loteCalcio;

    @Length(max = 255, message = "No debe superar los 255 caracteres")
    private String loteCuajo;

    private Long id;
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
        this.setIdQueso(lote.getQueso().getId());
    }
}
