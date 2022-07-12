INSERT INTO queso (id, codigo, nomenclatura, tipo_queso, stock, fecha_baja)
VALUES (1, '003', 'S', 'Sardo', 53, null),
       (2, '001', 'C', 'Cremoso', 70, null),
       (3, '002', 'B', 'Barra', 20, null),
       (4, '004', 'R', 'Rojo', 0, '2021-10-10');

INSERT INTO lote (fecha_elaboracion, numero_tina, litros_leche, cant_hormas, id_queso,
                  id, peso, rendimiento, stock_lote, lote_cultivo, lote_colorante, lote_calcio,
                  lote_cuajo, fecha_baja)
VALUES ('2021-10-22', 1, 4900, 124, 2, '221020210011', 526.7, 10.75, 20, 'cultivo1, cultivo2',
        'colorante1, colorante2', 'calcio1, calcio2', 'cuajo1, cuajo2', null),
       ('2021-10-23', 2, 6500, 228, 2, '231020210022', 842.5, 12.96, 25, null, null, null, null, null),
       ('2021-10-24', 3, 6537, 242, 3, '241020210033', 938.8, 14.36, 30, 'cultivo1, cultivo2', null, null,
        'cuajo1, cuajo2', null),
       ('2021-10-25', 4, 6537, 242, 4, '251020210045', 332, 12.6, 35, 'cultivo1', null, null, 'cuajo2', '2021-10-10');

INSERT INTO tipo_cliente (id, tipo)
VALUES (1, 'Mayorista'),
       (2, 'Minorista'),
       (3, 'Particular');

INSERT INTO cliente (nro_cliente, razon_social, id_tipo_cliente, cuit, domicilio, cod_postal, localidad,
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

INSERT INTO precio (id_queso, id_tipo_cliente, id, valor)
VALUES (1, 1, 1, 371.00),
       (2, 1, 2, 486.00),
       (3, 1, 3, 550.00),
       (1, 2, 4, 580.00),
       (2, 2, 5, 451.00),
       (3, 2, 6, 465.00),
       (1, 3, 7, 431.00),
       (2, 3, 8, 850.00);

INSERT INTO expedicion (id, fecha_expedicion, cantidad, peso, importe, nro_cliente, id_lote, on_remito)
VALUES (1, '2021-11-08', 100, 600.3, 62658.00, 1, '221020210011', false),
       (2, '2021-11-09', 150, 700.4, 62658.00, 2, '231020210022', false),
       (3, '2021-11-10', 200, 800.5, 62658.00, 3, '241020210033', true);

INSERT INTO remito (id_remito, fecha, importe_total)
VALUES (1, '2022-04-17', 5800.0);

INSERT INTO remito_expediciones (remito_id_remito, expediciones_id)
VALUES(1, 3);

INSERT INTO public.user_details (id, username, enabled, firstname, lastname, password, token_expired)
VALUES (1, 'admin', true, 'admin', 'admin', '$2a$10$T1Abj2rU26bFckix86rrqO312ZaiSnUZRCfTthk7g8zn/dx7UKqaa', true),
       (2, 'seller', true, 'seller', 'seller', '$2a$10$or2h/PmYIdzqF2V1qBaXBOTycch/.f4QZbdu4Vkp5FHTe0ZYXcIgm', true),
       (3, 'user', true, 'user', 'user', '$2a$10$5DjFH2mVRxj9yBId6arDLuAbnDMowDfTpXOHvkVBLQ8ep7X2bFLei', true);

INSERT INTO public.role (name)
VALUES ('ROLE_ADMIN'),
       ('ROLE_SELLER'),
       ('ROLE_USER');

INSERT INTO public.user_roles (user_id, role_name)
VALUES (1, 'ROLE_ADMIN'),
       (2, 'ROLE_SELLER'),
       (3, 'ROLE_USER');

INSERT INTO public.privilege (name, path)
VALUES ('CARGAR_EXPEDICIONES', '/cargar/expedicion'),
       ('CARGAR_LOTES', '/cargar/lotes'),
       ('CARGAR_PRECIOS', '/cargar/precios'),
       ('CARGAR_QUESOS', '/cargar/quesos'),
       ('CLIENTES', '/clientes'),
       ('EMITIR_REMITO', '/emitir/remito'),
       ('MANTENIMIENTO', '/mantenimiento'),
       ('RENDIMIENTO', '/rendimiento'),
       ('STOCK_EMBALAJE', '/stock/embalaje'),
       ('STOCK_PRODUCTOS', '/stock/productos'),
       ('VER_LITROS', '/ver/litros'),
       ('VER_PRODUCCION', '/ver/produccion'),
       ('VER_TRAZABILIDAD', '/ver/trazabilidad'),
       ('VER_VENTAS', '/ver/ventas');

INSERT INTO public.role_privileges (role_name, privilege_name)
VALUES ('ROLE_ADMIN', 'CARGAR_EXPEDICIONES'),
       ('ROLE_ADMIN', 'CARGAR_LOTES'),
       ('ROLE_ADMIN', 'CARGAR_PRECIOS'),
       ('ROLE_ADMIN', 'CARGAR_QUESOS'),
       ('ROLE_ADMIN', 'CLIENTES'),
       ('ROLE_ADMIN', 'CLIENTES'),
       ('ROLE_ADMIN', 'EMITIR_REMITO'),
       ('ROLE_ADMIN', 'MANTENIMIENTO'),
       ('ROLE_ADMIN', 'RENDIMIENTO'),
       ('ROLE_ADMIN', 'STOCK_EMBALAJE'),
       ('ROLE_ADMIN', 'STOCK_PRODUCTOS'),
       ('ROLE_ADMIN', 'VER_LITROS'),
       ('ROLE_ADMIN', 'VER_PRODUCCION'),
       ('ROLE_ADMIN', 'VER_TRAZABILIDAD'),
       ('ROLE_ADMIN', 'VER_VENTAS'),
       ('ROLE_SELLER', 'CARGAR_EXPEDICIONES'),
       ('ROLE_SELLER', 'CARGAR_PRECIOS'),
       ('ROLE_SELLER', 'EMITIR_REMITO'),
       ('ROLE_SELLER', 'RENDIMIENTO'),
       ('ROLE_SELLER', 'VER_LITROS'),
       ('ROLE_SELLER', 'VER_PRODUCCION'),
       ('ROLE_SELLER', 'VER_TRAZABILIDAD'),
       ('ROLE_SELLER', 'VER_VENTAS'),
       ('ROLE_USER', 'CARGAR_LOTES'),
       ('ROLE_USER', 'CARGAR_QUESOS'),
       ('ROLE_USER', 'MANTENIMIENTO'),
       ('ROLE_USER', 'STOCK_EMBALAJE'),
       ('ROLE_USER', 'STOCK_PRODUCTOS');

