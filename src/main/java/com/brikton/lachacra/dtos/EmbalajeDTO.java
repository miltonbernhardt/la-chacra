package com.brikton.lachacra.dtos;

import com.brikton.lachacra.entities.Embalaje;
import com.brikton.lachacra.entities.TipoEmbalaje;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EmbalajeDTO {

    private Long id;
    private TipoEmbalaje tipoEmbalaje;
    private Integer stock;
    private List<QuesoDTO> listaQuesos;

    public EmbalajeDTO(Embalaje embalaje){
        this.id = embalaje.getId();
        this.tipoEmbalaje = embalaje.getTipoEmbalaje();
        this.stock = embalaje.getStock();
        var quesosDTO = new ArrayList<QuesoDTO>();
        embalaje.getListaQuesos().forEach(queso -> quesosDTO.add(new QuesoDTO(queso)));
        listaQuesos = quesosDTO;
    }
}
