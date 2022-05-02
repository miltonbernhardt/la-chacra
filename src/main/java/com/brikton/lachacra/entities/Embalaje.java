package com.brikton.lachacra.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Embalaje {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq")
    @GenericGenerator(name = "seq", strategy = "increment")
    @Column
    private Long id;
    private TipoEmbalaje tipoEmbalaje;
    private Integer stock;
    @ManyToMany
    private List<Queso> listaQuesos;

    public Embalaje(){
        listaQuesos = new ArrayList<>();
    }

}
