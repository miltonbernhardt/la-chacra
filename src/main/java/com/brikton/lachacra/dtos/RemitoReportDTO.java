package com.brikton.lachacra.dtos;

import com.brikton.lachacra.entities.ItemRemito;
import com.brikton.lachacra.entities.Remito;
import lombok.Data;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

@Data
public class RemitoReportDTO {

    private LocalDate fecha;
    private String importeTotal;
    private List<ItemRemitoReportDTO> itemsRemito;

    private Long id;

    public RemitoReportDTO() {
    }

    public RemitoReportDTO(Remito remito) {
        this.setId(remito.getId());
        this.setFecha(remito.getFecha());

        Locale arg = new Locale("es", "AR");
        Currency peso = Currency.getInstance(arg);
        NumberFormat pesoFormat = NumberFormat.getCurrencyInstance(arg);

        this.setImporteTotal(pesoFormat.format(remito.getImporteTotal()));
        loadItemsRemitoReportDTO(remito.getItemsRemito());
    }

    private void loadItemsRemitoReportDTO(List<ItemRemito> items) {
        List<ItemRemitoReportDTO> itemsList = new ArrayList<>();
        items.forEach(itemRemito -> itemsList.add(new ItemRemitoReportDTO(itemRemito)));
        itemsRemito = itemsList;
    }

}
