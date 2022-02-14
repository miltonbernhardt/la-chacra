import { Autocomplete, Paper, Box, Button, Container, Grid, TextField, Typography } from "@mui/material";
import { DataGrid } from '@mui/x-data-grid';
import { precios, tiposDeCliente, tiposDeQueso } from "../../data/data";

const columns = [
    {
        field: 'tipoQueso',
        headerName: 'Queso',
        type: 'text',
        flex: 1,
        minWidth: 50
    },
    {
        field: 'tipoCliente',
        headerName: 'Cliente',
        type: 'text',
        flex: 1,
        minWidth: 50
    },
    {
        field: 'precio',
        headerName: 'Precio',
        type: 'number',
        flex: 1,
        minWidth: 50
    },
];

const CargarPrecios = () => {

    return (
        <>
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
                        <Grid container spacing={2}>
                            <Typography variant="h6" paddingLeft={2}>
                                Ingreso de precios
                            </Typography>
                            <Grid item xs={12}>
                                <Autocomplete
                                    disablePortal
                                    id="combo-tipoQueso"
                                    options={tiposDeQueso}
                                    renderInput={(params) => <TextField {...params} label="Queso" />}
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <Autocomplete
                                    disablePortal
                                    id="combo-tipoCliente"
                                    options={tiposDeCliente}
                                    renderInput={(params) => <TextField {...params} label="Cliente" />}
                                />
                            </Grid>
                            <Grid item xs={12} sm={8}>
                                <TextField
                                    id="precio"
                                    name="precio"
                                    label="Precio"
                                    fullWidth
                                    type="number"
                                    numeric
                                    variant="outlined" />
                            </Grid>
                            <Grid item xs={12} sm={4} alignSelf="center" mb={0.5}>
                                <Button variant="contained" fullWidth >Cargar Precio</Button>
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
                    <DataGrid
                        rows={precios}
                        columns={columns}
                        pageSize={100}
                        rowsPerPageOptions={[100]}
                        pagination={false}
                        hideFooterPagination
                    />
                </Box>
            </Paper>
        </>
    );
}

export default CargarPrecios;