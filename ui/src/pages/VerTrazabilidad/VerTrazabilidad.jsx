import { Box, Button, Container, Grid, TextField, Typography } from "@mui/material";
import { DataGrid } from '@mui/x-data-grid';


const VerTrazabilidad = () => {
    return (<>
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
                        Trazabilidad
                    </Typography>
                    <Grid item xs={12}>
                        <Typography variant="h7" color="GrayText">
                            Buscar Lote
                        </Typography>
                    </Grid>
                    <Grid item xs={12} sm={8}>
                        <TextField
                            id="numeroLote"
                            name="numeroLote"
                            label="Lote"
                            fullWidth
                            type="text"
                            variant="outlined" />
                    </Grid>
                    <Grid item xs={12} sm={4} alignSelf="center" mb={0.5}>
                        <Button variant="contained" fullWidth >Buscar Lote</Button>
                    </Grid>
                    <Grid item xs={12}>
                        <Typography variant="h7" color="GrayText">
                            Informacion del Lote
                        </Typography>
                    </Grid>
                    <Grid item xs={12} sm={5}>
                        <TextField
                            id="tipoQueso"
                            name="tipoQueso"
                            label="Tipo de Queso"
                            fullWidth
                            contentEditable={false}
                            variant="outlined" />
                    </Grid>
                    <Grid item xs={12} sm={5}>
                        <TextField
                            id="litrosProcesados"
                            name="litrosProcesados"
                            label="Litros procesados"
                            fullWidth
                            variant="outlined" />
                    </Grid>
                    <Grid item xs={12} sm={2}>
                        <TextField
                            id="tina"
                            name="tina"
                            label="Tina"
                            fullWidth
                            variant="outlined" />
                    </Grid>
                    <Grid item xs={12} sm={4}>
                        <TextField
                            id="cantidadHormas"
                            name="cantidadHormas"
                            label="Cantidad de hormas"
                            fullWidth

                            type="number"
                            variant="outlined" />
                    </Grid>
                    <Grid item xs={12} sm={4}>
                        <TextField
                            id="pesoLote"
                            name="pesoLote"
                            label="Peso del lote"
                            fullWidth
                            type="number"
                            variant="outlined" />
                    </Grid>
                    <Grid item xs={12} sm={4}>
                        <TextField
                            id="fechaLote"
                            name="fechaLote"
                            label="Fecha de producción"
                            fullWidth
                            type="date"
                            variant="outlined"
                            InputLabelProps={{
                                shrink: true,
                            }}
                        />
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
                rows={[]}
                columns={[]}
                pageSize={20}
                rowsPerPageOptions={[20]}
                pagination={false}
                hideFooterPagination
            />
        </Box>
    </>
    );
}

export default VerTrazabilidad;