INSERT INTO public.Queso (id_queso, codigo_queso, nomenclatura, tipo_queso, stock)
VALUES (001, '001', 'C', 'Cremoso', 70),
       (002, '002', 'B', 'Barra', 0),
       (003, '003', 'S', 'Sardo', 53);

INSERT INTO public.Lote (fecha_elaboracion, numero_tina, litros_leche, cant_hormas, codigo_queso,
                         id_lote, peso, rendimiento, stock_lote, lote_cultivo, lote_colorante, lote_calcio,
                         lote_cuajo)
VALUES ('2021-10-22', 1, 4900, 124, '001', '221020210011', 526.7, 10.75, 20, 'cultivo1, cultivo2',
        'colorante1, colorante2', 'calcio1, calcio2', 'cuajo1, cuajo2'),
       ('2021-10-23', 2, 6500, 228, '002', '231020210022', 842.5, 12.96, 25, null, null, null, null),
       ('2021-10-24', 3, 6537, 242, '003', '241020210033', 938.8, 14.36, 30, 'cultivo1, cultivo2', null, null,
        'cuajo1, cuajo2');

INSERT INTO public.tipo_cliente (id_tipo_cliente, tipo)
VALUES (1, 'Mayorista'),
       (2, 'Minorista'),
       (3, 'Particular');

INSERT INTO public.cliente (nro_cliente, razon_social, id_tipo_cliente, cuit, domicilio, cod_postal, localidad,
                            provincia,
                            pais, transporte, senasa_uta)
VALUES ('101', 'ABDALA, Gustavo', 1, '20-21641332-7', 'Jacob 2830', '3080', 'Esperanza', 'Santa Fe', 'Argentina',
        'Familia Noroña S.A.', '113754'),
       ('102', 'ALBERTINAZZI, Olga Pompeya', 1, '27-11295032-5', 'Hipólito Yrigoyen 1442', '3560', 'Reconquista',
        'Santa Fe', 'Argentina', 'Cerutti, Pablo', '94265'),
       ('103', 'ALEGRI, José César', 1, '20-11660282-3', 'Av. Centenario 4797', '', 'Espeleta',
        'Buenos Aires', 'Argentina', 'Bianchi', '83200');

INSERT INTO public.precio (id_tipo_cliente, id_queso, precio, id_precio)
VALUES (1, 1, 371.00, 1),
       (1, 2, 486.00, 2),
       (1, 3, 550.00, 3),
       (1, 1, 580.00, 4),
       (1, 2, 451.00, 5),
       (1, 3, 465.00, 6),
       (1, 1, 431.00, 7),
       (1, 2, 850.00, 8),
       (1, 3, 620.00, 9);

-- agregar expedicion
-- agregar remitos
-- agregar devoluciones
-- agregar 	lote_lote_calcio
-- agregar	lote_lote_colorante
-- agregar	lote_lote_cuajo
-- agregar	lote_lote_cultivo
