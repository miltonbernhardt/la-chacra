import { Autocomplete, Box, Button, Container, Dialog, DialogActions, DialogContent, DialogTitle, Grid, TextField, Typography } from "@mui/material";
import { useCallback, useState, useEffect, useMemo } from 'react';
import { toastValidationErrors } from "../../fields";
import * as message from "../../messages";
import * as field from "../../fields";
const clienteInicial = {
    id: '',
    razonSocial: '',
    cuit: '',
    domicilio: '',
    codPostal: '',
    localidad: '',
    provincia: '',
    pais: '',
    transporte: '',
    senasaUta: '',
    email: '',
    telefono: '',
    fax: '',
    celular: '',
    idTipoCliente: ''
}

const DialogAltaCliente = ({ cliente, open, onClose, onSubmit, isEditing, tiposCliente }) => {

    const [clienteForm, setClienteForm] = useState(clienteInicial);

    useEffect(() => setClienteForm(cliente), [cliente]);

    const handleChange = useCallback(evt => {
        const nombreAtributo = evt.target.name;
        const valorAtributo = evt.target.value;
        const newCliente = { ...clienteForm, [nombreAtributo]: valorAtributo };
        setClienteForm(newCliente);
    }, [clienteForm])

    const validarCliente = useCallback(() => {
        const errors = new Map();
        if (clienteForm.razonSocial === '') errors.set(field.razonSocial, message.valEmptyField)
        if (clienteForm.cuit === '') errors.set(field.cuit, message.valEmptyField)
        if (errors.size > 0) {
            console.error(errors)
            toastValidationErrors(errors)
            return false;
        }
        return true;
    }, [clienteForm])

    const handleSubmit = useCallback(() => { if (validarCliente()) onSubmit(clienteForm) }, [clienteForm, onSubmit, validarCliente]);

    // --- VARIABLES ---
    const dialogTitle = useMemo(() => isEditing ? 'Actualizar Cliente' : 'Alta Cliente', [isEditing]);
    const titleCliente = useMemo(() => cliente.id ? `Cliente Número ${cliente.id}` : 'Cliente', [cliente]);
    const labelSubmit = useMemo(() => isEditing ? 'Actualizar' : 'Dar de Alta', [isEditing]);

    return (
        <>
            <Dialog open={open} onClose={onClose} scroll="body">
                <DialogTitle>{dialogTitle}</DialogTitle>
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
                                    {titleCliente}
                                </Typography>
                                <Grid item xs={12} >
                                    <TextField
                                        id="razonSocial"
                                        name="razonSocial"
                                        label="Razon Social"
                                        fullWidth
                                        type="text"
                                        variant="outlined"
                                        value={clienteForm.razonSocial}
                                        onChange={handleChange} />
                                </Grid>
                                <Grid item xs={12} >
                                    <TextField
                                        id="cuit"
                                        name="cuit"
                                        label="CUIT"
                                        fullWidth
                                        type="text"
                                        variant="outlined"
                                        value={clienteForm.cuit}
                                        onChange={handleChange} />
                                </Grid>
                                <Grid item xs={12}>
                                    <Autocomplete
                                        disablePortal
                                        id="idTipoCliente"
                                        name="idTipoCliente"
                                        options={tiposCliente}
                                        getOptionLabel={(option) => option.label || tiposCliente.filter(t => t.value === option).pop().label}
                                        renderInput={(params) => <TextField {...params} label="Tipo de cliente" />}
                                        renderOption={(props, option) => {
                                            return <Box component="li"  {...props}>
                                                {option.label}
                                            </Box>
                                        }}
                                        value={clienteForm.idTipoCliente}
                                        isOptionEqualToValue={(option, value) =>
                                            value.value ? option.value === value.value : option.value === value
                                        }
                                        onChange={(e, value) => {
                                            const newCliente = { ...clienteForm, ['idTipoCliente']: value.value };
                                            setClienteForm(newCliente);
                                        }}
                                    />
                                </Grid>
                                <Typography variant="h6" paddingLeft={2} mt={2}>
                                    Domicilio
                                </Typography>
                                <Grid item xs={12}>
                                    <TextField
                                        id="domicilio"
                                        name="domicilio"
                                        label="Dirección"
                                        fullWidth
                                        type="text"
                                        variant="outlined"
                                        value={clienteForm.domicilio}
                                        onChange={handleChange} />
                                </Grid>
                                <Grid item xs={12} sm={8} >
                                    <TextField
                                        id="localidad"
                                        name="localidad"
                                        label="Localidad"
                                        fullWidth
                                        variant="outlined"
                                        value={clienteForm.localidad}
                                        onChange={handleChange} />
                                </Grid>
                                <Grid item xs={12} sm={4}>
                                    <TextField
                                        id="codigoPostal"
                                        name="codPostal"
                                        label="Código Postal"
                                        fullWidth
                                        variant="outlined"
                                        value={clienteForm.codPostal}
                                        onChange={handleChange} />
                                </Grid>
                                <Grid item xs={12} sm={6}>
                                    <TextField
                                        id="provincia"
                                        name="provincia"
                                        label="Provincia"
                                        fullWidth
                                        variant="outlined"
                                        value={clienteForm.provincia}
                                        onChange={handleChange} />
                                </Grid>
                                <Grid item xs={12} sm={6}>
                                    <TextField
                                        id="pais"
                                        name="pais"
                                        label="Pais"
                                        fullWidth
                                        variant="outlined"
                                        value={clienteForm.pais}
                                        onChange={handleChange} />
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
                                        variant="outlined"
                                        value={clienteForm.email}
                                        onChange={handleChange} />
                                </Grid>
                                <Grid item xs={12} >
                                    <TextField
                                        id="telefono"
                                        name="telefono"
                                        label="Telefono"
                                        type="tel"
                                        fullWidth
                                        variant="outlined"
                                        value={clienteForm.telefono}
                                        onChange={handleChange} />
                                </Grid>
                                <Grid item xs={12} >
                                    <TextField
                                        id="celular"
                                        name="celular"
                                        label="Celular"
                                        type="tel"
                                        fullWidth
                                        variant="outlined"
                                        value={clienteForm.celular}
                                        onChange={handleChange} />
                                </Grid>
                                <Grid item xs={12} >
                                    <TextField
                                        id="fax"
                                        name="fax"
                                        label="Fax"
                                        type="tel"
                                        fullWidth
                                        variant="outlined"
                                        value={clienteForm.fax}
                                        onChange={handleChange} />
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
                                        variant="outlined"
                                        value={clienteForm.transporte}
                                        onChange={handleChange} />
                                </Grid>
                                <Grid item xs={12} >
                                    <TextField
                                        id="senasaUta"
                                        name="senasaUta"
                                        label="SENASA/UTA"
                                        fullWidth
                                        variant="outlined"
                                        value={clienteForm.senasaUta}
                                        onChange={handleChange} />
                                </Grid>
                            </Grid>
                        </Box>
                    </Container>
                </DialogContent>
                <DialogActions>
                    <Button onClick={onClose}>Cancelar</Button>
                    <Button onClick={handleSubmit}>{labelSubmit}</Button>
                </DialogActions>
            </Dialog>
        </>
    );
}

export default DialogAltaCliente;