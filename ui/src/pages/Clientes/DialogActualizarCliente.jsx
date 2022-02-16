import { Autocomplete, Box, Button, Container, Dialog, DialogActions, DialogContent, DialogTitle, Grid, TextField, Typography } from "@mui/material";
import { tiposDeCliente } from "../../data/data";


const DialogActualizarCliente = ({ open, onClose, onSubmit }) => {

    return (<>
        <Dialog open={open} onClose={onClose} scroll="body">
            <DialogTitle>Actualizar Cliente</DialogTitle>
            <DialogContent>
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
                                Cliente
                            </Typography>
                            <Grid item xs={12} >
                                <TextField
                                    id="numeroCliente"
                                    name="numeroCliente"
                                    label="Número de Cliente"
                                    fullWidth
                                    type="text"
                                    variant="outlined" />
                            </Grid>
                            <Typography variant="h6" paddingLeft={2}>
                                Datos
                            </Typography>
                            <Grid item xs={12} >
                                <TextField
                                    id="razonSocial"
                                    name="razonSocial"
                                    label="Razon Social"
                                    fullWidth
                                    type="text"
                                    variant="outlined" />
                            </Grid>
                            <Grid item xs={12} >
                                <TextField
                                    id="cuit"
                                    name="cuit"
                                    label="CUIT"
                                    fullWidth
                                    type="text"
                                    variant="outlined" />
                            </Grid>
                            <Grid item xs={12}>
                                <Autocomplete
                                    disablePortal
                                    id="combo-tipoCliente"
                                    options={tiposDeCliente}
                                    renderInput={(params) => <TextField {...params} label="Tipo de cliente" />}
                                />
                            </Grid>
                            <Typography variant="h6" paddingLeft={2} mt={2}>
                                Domicilio
                            </Typography>
                            <Grid item xs={12}>
                                <TextField
                                    id="direccion"
                                    name="direccion"
                                    label="Dirección"
                                    fullWidth
                                    type="text"
                                    variant="outlined" />
                            </Grid>
                            <Grid item xs={12} sm={8} >
                                <TextField
                                    id="localidad"
                                    name="localidad"
                                    label="Localidad"
                                    fullWidth
                                    variant="outlined" />
                            </Grid>
                            <Grid item xs={12} sm={4}>
                                <TextField
                                    id="codigoPostal"
                                    name="codigoPostal"
                                    label="Código Postal"
                                    fullWidth
                                    variant="outlined" />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    id="provincia"
                                    name="provincia"
                                    label="Provincia"
                                    fullWidth
                                    variant="outlined" />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    id="pais"
                                    name="pais"
                                    label="Pais"
                                    fullWidth
                                    variant="outlined" />
                            </Grid>
                            <Typography variant="h6" paddingLeft={2} mt={2}>
                                Datos de contacto
                            </Typography>

                            <Grid item xs={12} >
                                <TextField
                                    id="email"
                                    name="email"
                                    label="E-mail"
                                    type="email"
                                    fullWidth
                                    variant="outlined" />
                            </Grid>
                            <Grid item xs={12} >
                                <TextField
                                    id="telefono"
                                    name="telefono"
                                    label="Telefono"
                                    type="tel"
                                    fullWidth
                                    variant="outlined" />
                            </Grid>
                            <Grid item xs={12} >
                                <TextField
                                    id="celular"
                                    name="celular"
                                    label="Celular"
                                    type="tel"
                                    fullWidth
                                    variant="outlined" />
                            </Grid>
                            <Grid item xs={12} >
                                <TextField
                                    id="fax"
                                    name="fax"
                                    label="Fax"
                                    type="tel"
                                    fullWidth
                                    variant="outlined" />
                            </Grid>
                            <Typography variant="h6" paddingLeft={2} mt={2}>
                                Transporte
                            </Typography>

                            <Grid item xs={12} >
                                <TextField
                                    id="transporte"
                                    name="transporte"
                                    label="Transporte"
                                    fullWidth
                                    variant="outlined" />
                            </Grid>
                            <Grid item xs={12} >
                                <TextField
                                    id="habilitacionTransporte"
                                    name="habilitacionTransporte"
                                    label="SENASA/UTA"
                                    fullWidth
                                    variant="outlined" />
                            </Grid>
                        </Grid>
                    </Box>
                </Container>
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose}>Cancelar</Button>
                <Button onClick={onSubmit}>Dar de alta</Button>
            </DialogActions>
        </Dialog>
    </>
    );
}

export default DialogActualizarCliente;