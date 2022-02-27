INSERT INTO public.Queso (id, codigo, nomenclatura, tipo_queso, stock, fecha_baja)
VALUES (1, '003', 'S', 'Sardo', 53, null),
       (2, '001', 'C', 'Cremoso', 70, null),
       (3, '002', 'B', 'Barra', 20, null),
       (4, '004', 'R', 'Rojo', 0, '2021-10-10');

INSERT INTO public.Lote (fecha_elaboracion, numero_tina, litros_leche, cant_hormas, id_queso,
                         id, peso, rendimiento, stock_lote, lote_cultivo, lote_colorante, lote_calcio,
                         lote_cuajo, fecha_baja)
VALUES ('2021-10-22', 1, 4900, 124, 2, '221020210011', 526.7, 10.75, 20, 'cultivo1, cultivo2',
        'colorante1, colorante2', 'calcio1, calcio2', 'cuajo1, cuajo2', null),
       ('2021-10-23', 2, 6500, 228, 2, '231020210022', 842.5, 12.96, 25, null, null, null, null, null),
       ('2021-10-24', 3, 6537, 242, 3, '241020210033', 938.8, 14.36, 30, 'cultivo1, cultivo2', null, null,
        'cuajo1, cuajo2', null),
       ('2021-10-25', 4, 6537, 242, 4, '251020210045', 332, 12.6, 35, 'cultivo1', null, null, 'cuajo2', '2021-10-10');

INSERT INTO public.tipo_cliente (id, tipo)
VALUES (1, 'Mayorista'),
       (2, 'Minorista'),
       (3, 'Particular');

INSERT INTO public.cliente (nro_cliente, razon_social, id_tipo_cliente, cuit, domicilio, cod_postal, localidad,
                            provincia, pais, transporte, senasa_uta, telefono, celular, fax, email, fecha_baja)
VALUES (1, 'ABDALA, Gustavo', 1, '20-11111111-7', 'Jacob 2830', '3080', 'Esperanza', 'Santa Fe', 'Argentina',
        'Familia Noroña S.A.', '113754', '233334444444', '233334444444', null, 'mail1@mail.com', null),
       (2, 'ALBERTINAZZI, Olga Pompeya', 2, '27-22222222-5', 'Hipólito Yrigoyen 1442', '3560', 'Reconquista',
        'Santa Fe', 'Argentina', 'Cerutti, Pablo', '94265', '344445555555', '344445555555', '344445555555',
        'mail2@mail.com', null),
       (3, 'ALEGRI, José César', 3, '20-33333333-3', 'Av. Centenario 4797', null, 'Espeleta',
        'Buenos Aires', 'Argentina', 'Bianchi', '83200', '755556666666', '755556666666', '755556666666',
        'mail3@mail.com', null),
       (4, 'NOMBRE', 3, '20-44444444-3', 'DIRECCION', 2000, 'LOCALIDAD',
        'PROVINCIA', 'PAIS', 'TRANSPORTE', '11111', null, null, null, 'mail4@mail.com', '2020-10-10');

INSERT INTO public.precio (id_tipo_cliente, id_queso, valor, id)
VALUES (1, 1, 371.00, 1),
       (1, 2, 486.00, 2),
       (1, 3, 550.00, 3),
       (1, 1, 580.00, 4),
       (1, 2, 451.00, 5),
       (1, 3, 465.00, 6),
       (1, 1, 431.00, 7),
       (1, 2, 850.00, 8),
       (1, 3, 620.00, 9),
       (1, 4, 620.00, 10);

INSERT INTO public.expedicion (id_expedicion, fecha_expedicion, cantidad, peso, importe, nro_cliente, id_lote)
VALUES (1, '2021-11-08', 216, 855.3, 62658.00, 1, '241020210033');
-- agregar expedicion
-- agregar remitos
-- agregar devoluciones
-- agregar 	lote_lote_calcio
-- agregar	lote_lote_colorante
-- agregar	lote_lote_cuajo
-- agregar	lote_lote_cultivo
