package com.brikton.lachacra.dtos;

import com.brikton.lachacra.entities.ItemRemito;
import com.brikton.lachacra.entities.Remito;
import com.brikton.lachacra.constants.ValidationMessages;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class RemitoDTO {

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @PastOrPresent(message = ValidationMessages.CANT_BE_LATER_THAN_TODAY)
    private LocalDate fecha;

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @Min(value = 1, message = ValidationMessages.CANNOT_BE_LESS_THAN_1)
    private Double importeTotal;

    private List<ItemRemitoDTO> itemsRemito;

    private Long id;

    public RemitoDTO() {
    }

    public RemitoDTO(Remito remito) {
        this.setId(remito.getId());
        this.setFecha(remito.getFecha());
        this.setImporteTotal(remito.getImporteTotal());
        loadItemsRemitoDTO(remito.getItemsRemito());
    }

    private void loadItemsRemitoDTO(List<ItemRemito> items) {
        List <ItemRemitoDTO> itemsList = new ArrayList<>();
        items.forEach(itemRemito -> itemsList.add(new ItemRemitoDTO(itemRemito)));
        itemsRemito = itemsList;
    }

}
