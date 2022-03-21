package com.brikton.lachacra.dtos;

import com.brikton.lachacra.entities.ItemRemito;
import lombok.Data;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

@Data
public class ItemRemitoReportDTO {

    private Integer cantidad;
    private String tipoQueso,precio,importe;
    private Double peso;

    public ItemRemitoReportDTO(ItemRemito itemRemito){
        this.cantidad = itemRemito.getCantidad();
        this.tipoQueso = itemRemito.getQueso().getTipoQueso();
        this.peso = itemRemito.getPeso();

        Locale arg = new Locale("es", "AR");
        Currency peso = Currency.getInstance(arg);
        NumberFormat pesoFormat = NumberFormat.getCurrencyInstance(arg);

        this.precio = pesoFormat.format(itemRemito.getPrecio());
        this.importe = pesoFormat.format(itemRemito.getImporte());
    }
}
