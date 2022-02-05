package com.brikton.lachacra.dtos;

import com.brikton.lachacra.entities.Lote;
import com.brikton.lachacra.entities.Queso;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;

@Data
public class LoteDTO {
    private long id;
    private LocalDate fechaElaboracion;
    @NotNull
    private int numeroTina;
    @NotNull
    private double litrosLeche;
    @NotNull
    private int cantHormas;
    @NotNull
    private int stockLote;
    @NotNull
    @Min(0)
    private double peso;
    private double rendimiento;

    @Pattern(regexp = "^[\\p{Alnum}]{1,32}$")
    private String cultivo;
    private List<String> loteCultivo;
    private List<String> loteColorante;
    private List<String> loteCalcio;
    private List<String> loteCuajo;

    private String codigoQueso;

    public LoteDTO(Lote lote) {
        this.setId(lote.getId());
        this.setFechaElaboracion(lote.getFechaElaboracion());
        this.setNumeroTina(lote.getNumeroTina());
        this.setLitrosLeche(lote.getLitrosLeche());
        this.setCantHormas(lote.getCantHormas());
        this.setStockLote(lote.getStockLote());
        this.setPeso(lote.getPeso());
        this.setRendimiento(lote.getRendimiento());
        this.setCultivo(lote.getCultivo());
        this.setLoteCultivo(lote.getLoteCultivo());
        this.setLoteColorante(lote.getLoteColorante());
        this.setLoteCalcio(lote.getLoteCalcio());
        this.setLoteCuajo(lote.getLoteCuajo());
        this.setLoteCuajo(lote.getLoteCuajo());
        this.setCodigoQueso(lote.getQueso().getCodigo());
    }
}
