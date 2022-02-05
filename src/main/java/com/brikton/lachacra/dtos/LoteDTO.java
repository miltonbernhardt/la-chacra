package com.brikton.lachacra.dtos;

import com.brikton.lachacra.entities.Lote;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Data
public class LoteDTO {

    @Past(message = "La fecha de elaboración no puede ser posterior al día de hoy") //todo falta
    private LocalDate fechaElaboracion;

    @NotNull(message = "Faltan el número de tina")
    private Integer numeroTina;

    @NotNull(message = "Faltan la cantidad de litros de leche")
    @Min(value = 0, message = "Los litros de leche no pueden ser menor a 0")
    private Double litrosLeche;

    @NotNull(message = "Faltan la cantidad de hormas")
    @Min(value = 0, message = "La cantidad de hormas no puede ser menor a 0")
    private Integer cantHormas;

    @NotNull(message = "Falta el stock")
    @Min(value = 0, message = "El stock no puede ser menor a 0")
    private Integer stockLote;

    @NotNull(message = "Falta el peso")
    @Min(value = 0, message = "El peso no puede ser menor a 0")
    private Double peso;

    @NotNull(message = "Falta el rendimiento")
    @Min(value = 0, message = "El rendimiento no puede ser menor a 0")
    @Digits(integer = 2, fraction = 2, message = "El rendimiento debe tener como máximo dos números antes y después de la coma")
    private Double rendimiento; // todo fijarse como seteamos el rendimiento,

    @Size(min = 3, max = 3, message = "El código de queso debe ser de 3 caracteres")
    private String codigoQueso;

    private long id;
    private String cultivo;
    private List<String> loteCultivo;
    private List<String> loteColorante;
    private List<String> loteCalcio;
    private List<String> loteCuajo;

    public LoteDTO() {
    }

    public LoteDTO(Lote lote) {
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
        this.setCodigoQueso(lote.getQueso().getCodigo());
    }
}
