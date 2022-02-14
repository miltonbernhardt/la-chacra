import { Autocomplete, Paper, Box, Button, Container, Grid, TextField, Typography } from "@mui/material";
import { DataGrid } from '@mui/x-data-grid';
import { produccion, tiposDeQueso } from "../../data/data";

const CargarExpedicion = () => {
    return (
        <>
            <Paper style={{ width: '100%', height: '100%', padding: 2 }}>
                {/* Formulario */}
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
                        <Grid container spacing={2} justifyContent="right" >
                            <Grid item xs={12}>
                                <Typography variant="h6" >
                                    Ingreso de expedición
                                </Typography>
                            </Grid>
                            <Grid item xs={12}>
                                <Autocomplete
                                    disablePortal
                                    id="combo-tipoQueso"
                                    options={tiposDeQueso}
                                    renderInput={(params) => <TextField {...params} label="Cliente" />}
                                /></Grid>
                            <Grid item xs={12}>
                                <TextField
                                    id="numeroLote"
                                    name="numeroLote"
                                    label="Lote"
                                    fullWidth
                                    variant="outlined" />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    id="cantidad"
                                    name="cantidad"
                                    label="Cantidad"
                                    fullWidth
                                    type="number"
                                    variant="outlined" />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    id="pesoExpedicion"
                                    name="pesoExpedicion"
                                    label="Peso"
                                    fullWidth
                                    type="number"
                                    variant="outlined" />
                            </Grid>
                            <Grid item xs={12} >
                                <TextField
                                    id="importeExpedicion"
                                    name="importeExpedicion"
                                    label="Importe"
                                    fullWidth
                                    type="number"
                                    variant="outlined" />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    id="fechaExpedicion"
                                    name="fechaExpedicion"
                                    label="Fecha de Expedicion"
                                    fullWidth
                                    type="date"
                                    variant="outlined"
                                    InputLabelProps={{
                                        shrink: true,
                                    }}
                                />
                            </Grid>
                            <Grid item xs={12} sm={4} alignSelf="center" mb={0.5}>
                                <Button variant="contained" fullWidth >Cargar Expedición</Button>
                            </Grid>
                        </Grid>
                    </Box>
                </Container>
                {/* Tabla */}
                <Box height={600}
                    sx={{
                        padding: 1,
                        flexDirection: 'column',
                        alignItems: 'center',

                    }}>
                    <DataGrid
                        rows={produccion}
                        columns={[]}
                        pageSize={20}
                        rowsPerPageOptions={[20]}
                        pagination={false}
                        hideFooterPagination
                    />
                </Box>
            </Paper>
        </>);
}

export default CargarExpedicion;