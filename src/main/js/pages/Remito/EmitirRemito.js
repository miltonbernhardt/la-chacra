import * as React from 'react';
import { Autocomplete, Paper, Box, Button, Container, Grid, TextField, Typography } from "@mui/material";
import { DataGrid } from '@mui/x-data-grid';
import { produccion, tiposDeQueso } from "../../data/data";

const columns = [
    {
        field: "cantidad",
        headerName: "Cantidad",
        type: 'number',
        flex: 1,
        minWidth: 100
    }, {
        field: "producto",
        headerName: "Producto",
        type: 'text',
        flex: 1,
        minWidth: 100
    }, {
        field: "peso",
        headerName: "Peso",
        type: 'number',
        flex: 1,
        minWidth: 100
    }, {
        field: "precio",
        headerName: "Precio Unitario",
        type: 'number',
        flex: 1,
        minWidth: 100
    }, {
        field: "importe",
        headerName: "Importe",
        type: 'number',
        flex: 1,
        minWidth: 100
    },
]

export const EmitirRemito = () => {
    return (<>
        <Paper style={{ width: '100%', height: '100%', padding: 2 }}>
            <Container maxWidth="sm">
                <Box
                    sx={{
                        marginTop: 8,
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                        mt: 3
                    }}
                >
                    <Grid container spacing={2} justifyContent="right">
                        <Grid item xs={12}>
                            <Typography variant="h6">
                                Emitir Remito
                            </Typography>
                        </Grid>
                        <Grid item xs={12}>
                            <Typography variant="h7" color="GrayText">
                                Datos de Remito
                            </Typography>
                        </Grid>
                        <Grid item xs={12}>
                            <Autocomplete
                                disablePortal
                                id="combo-tipoQueso"
                                options={tiposDeQueso}
                                renderInput={(params) => <TextField {...params} label="Cliente"/>}
                            /></Grid>
                        <Grid item xs={12}>
                            <TextField
                                id="fechaRemito"
                                name="fechaRemito"
                                label="Fecha de Remito"
                                fullWidth
                                type="date"
                                variant="outlined"
                                InputLabelProps={{
                                    shrink: true,
                                }}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                id="importeRemito"
                                name="importeRemito"
                                label="Importe Total"
                                fullWidth
                                type="number"
                                variant="outlined"/>
                        </Grid>
                        <Grid item xs={12} sm={4} alignSelf="center" mb={0.5}>
                            <Button variant="contained" fullWidth>Emitir Remito</Button>
                        </Grid>
                    </Grid>
                </Box>
            </Container>
            <Box height={600}
                 sx={{
                     padding: 1,
                     flexDirection: 'column',
                     alignItems: 'center',

                 }}>
                <Typography variant="h6" color="GrayText" padding={1}>
                    Lista de expediciones
                </Typography>
                <DataGrid
                    rows={produccion}
                    columns={columns}
                    pageSize={20}
                    rowsPerPageOptions={[20]}
                    pagination={false}
                    hideFooterPagination
                />
            </Box>
        </Paper>
    </>);
}