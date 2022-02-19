import { Box, Button, Container, Grid, TextField, Typography } from "@mui/material";
import Paper from '@mui/material/Paper';
import { DataGrid } from '@mui/x-data-grid';
import * as React from 'react';

import { produccion as data } from '../../data/data';

const columns = [
    {
        field: 'identificadorLote',
        headerName: 'Lote',
        flex: 1,
        minWidth: 50
    },
    {
        field: 'fechaElaboracion',
        headerName: 'Fecha de elaboracion',
        type: 'date',
        flex: 1,
        minWidth: 50
    },
    {
        field: 'tipoDeQueso',
        headerName: 'Queso',
        flex: 0.5,
        minWidth: 50
    },
    {
        field: 'tina',
        headerName: 'Tina',
        flex: 0.5,
        minWidth: 50
    },
    {
        field: 'litrosProcesados',
        headerName: 'Litros Procesados',
        type: 'number',
        flex: 0.5,
        minWidth: 50
    },
    {
        field: 'cantidadDeHormas',
        headerName: 'Hormas',
        type: 'number',
        flex: 0.5,
        minWidth: 50
    },
    {
        field: 'saldoLote',
        headerName: 'Saldo',
        type: 'number',
        flex: 0.5,
        minWidth: 50,
        renderCell: (cellValues) => {
            return (
                <div
                    style={{
                        color: "blue",
                        // fontSize: 18,
                        width: "100%",
                        textAlign: "right"
                    }}
                >
                    {cellValues.value}
                </div>
            );
        }
    },
    {
        field: 'pesoLote',
        headerName: 'Peso',
        type: 'number',
        flex: 0.5,
        minWidth: 50
    },
    {
        field: 'rendimientoLote',
        headerName: 'Rendimiento',
        type: 'number',
        flex: 0.5,
        minWidth: 50
    },
    // {
    //     field: 'fullName',
    //     headerName: 'Full name',
    //     description: 'This column has a value getter and is not sortable.',
    //     sortable: false,
    //     flex: 1,
    //     valueGetter: (params) =>
    //         `${params.getValue(params.id, 'firstName') || ''} ${params.getValue(params.id, 'lastName') || ''
    //         }`,
    // },
];

const VerProduccion = () => {

    return (
        <>
            <Container maxWidth="sm">
                <Box
                    sx={{
                        marginTop: 8,
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                        mt: 1
                    }}
                >
                    <Paper
                        sx={{
                            p: 2,
                            display: 'flex',
                            flexDirection: 'column',

                        }}
                    >

                        <Grid container spacing={2}>
                            <Typography variant="h6" paddingLeft={2} paddingTop={1}>
                                Producción
                            </Typography>
                            <Grid item xs={12}>
                                <Typography variant="h7" color="GrayText">
                                    Rango de fechas
                                </Typography>
                            </Grid>
                            <Grid item xs={12} sm={4}>
                                <TextField
                                    id="fechaDesde"
                                    name="fechaDesde"
                                    label="Desde"
                                    fullWidth
                                    type="date"
                                    variant="outlined"
                                    InputLabelProps={{
                                        shrink: true,
                                    }} />
                            </Grid>
                            <Grid item xs={12} sm={4}>
                                <TextField
                                    id="fechaHasta"
                                    name="fechaHasta"
                                    label="Hasta"
                                    type="date"
                                    fullWidth
                                    variant="outlined"
                                    InputLabelProps={{
                                        shrink: true,
                                    }} />
                            </Grid>
                            <Grid item xs={12} sm={4} alignSelf="center" mb={0.5}>
                                <Button variant="contained" fullWidth >Buscar</Button>
                            </Grid>
                        </Grid>
                    </Paper>
                </Box>
            </Container>
            <Box height={600}
                sx={{
                    padding: 1,
                    flexDirection: 'column',
                    alignItems: 'center',

                }}>
                <Grid item xs={12}>
                    <Paper
                        sx={{
                            p: 2,
                            display: 'flex',
                            flexDirection: 'column',
                            height: 600,
                        }}
                    >
                        <DataGrid
                            rows={data}
                            columns={columns}
                            pageSize={20}
                            rowsPerPageOptions={[]}
                            pagination={false}
                        >
                        </DataGrid>
                    </Paper>
                </Grid>
            </Box>
        </>
    );
}

export default VerProduccion;