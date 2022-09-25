package com.brikton.lachacra.dtos;

import com.brikton.lachacra.constants.ValidationMessages;
import com.brikton.lachacra.entities.ItemRemito;
import com.brikton.lachacra.entities.Remito;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class RemitoDTO {

    @NotNull(message = ValidationMessages.NOT_FOUND)
    @PastOrPresent(message = ValidationMessages.CANT_BE_LATER_THAN_TODAY)
    private LocalDate fecha;

    private Double importeTotal;
    private Integer cantCajas;
    private Integer cantPallets;
    private Long idCliente;

    private List<ItemRemitoDTO> itemsRemito;

    private Long id;

    public RemitoDTO(Remito remito) {
        this.setId(remito.getId());
        this.setFecha(remito.getFecha());
        this.setImporteTotal(remito.getImporteTotal());
        this.setCantCajas(remito.getCantCajas());
        this.setCantPallets(remito.getCantPallets());
        if (!remito.getExpediciones().isEmpty())
            this.setIdCliente(remito.getExpediciones().get(0).getCliente().getId());
        loadItemsRemitoDTO(remito.getItemsRemito());
    }

    private void loadItemsRemitoDTO(List<ItemRemito> items) {
        List<ItemRemitoDTO> itemsList = new ArrayList<>();
        items.forEach(itemRemito -> itemsList.add(new ItemRemitoDTO(itemRemito)));
        itemsRemito = itemsList;
    }
}
