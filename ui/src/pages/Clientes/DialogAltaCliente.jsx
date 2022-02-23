import { Autocomplete, Box, Button, Container, Dialog, DialogActions, DialogContent, DialogTitle, Grid, TextField, Typography } from "@mui/material";
import { createRef, useEffect, useMemo, useState } from 'react';
import Input from "../../components/Input";
import { toastValidationErrors } from "../../resources/fields";
import * as message from "../../resources/messages";
import * as validation from "../../resources/validations";
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


    const refRazonSocial = createRef(null)
    const refCuit = createRef(null)
    const refDomicilio = createRef(null)
    const refCodPostal = createRef(null)
    const refLocalidad = createRef(null)
    const refProvincia = createRef(null)
    const refPais = createRef(null)
    const refTransporte = createRef(null)
    const refSenasaUta = createRef(null)
    const refEmail = createRef(null)
    const refTelefono = createRef(null)
    const refFax = createRef(null)
    const refCelular = createRef(null)

    useEffect(() => setClienteForm(cliente), [cliente]);

    const handleSubmit = () => {
        const errors = new Map();
        const values = {};
        values["id"] = clienteForm.id

        refRazonSocial.current.validate(errors, values, [
            { func: validation.empty, msg: message.valEmptyField }
        ])

        refCuit.current.validate(errors, values, [
            { func: validation.empty, msg: message.valEmptyField }
        ])

        if (clienteForm.idTipoCliente === '') {
            errors.set("Tipo de cliente", message.valEmptyField);
        } else {
            values["idTipoCliente"] = clienteForm.idTipoCliente;
        }

        if (errors.size > 0) {
            console.error(errors)
            toastValidationErrors(errors)
            return
        }

        refDomicilio.current.setValue(values)
        refCodPostal.current.setValue(values)
        refLocalidad.current.setValue(values)
        refProvincia.current.setValue(values)
        refPais.current.setValue(values)
        refTransporte.current.setValue(values)
        refSenasaUta.current.setValue(values)
        refEmail.current.setValue(values)
        refTelefono.current.setValue(values)
        refFax.current.setValue(values)
        refCelular.current.setValue(values)

        onSubmit(values)
    }

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
                                <Input
                                    id="razonSocial"
                                    label="Razon Social"
                                    ref={refRazonSocial}
                                    value={clienteForm.razonSocial}
                                    required
                                    type="text" />
                                <Input
                                    id="cuit"
                                    label="CUIT"
                                    ref={refCuit}
                                    value={clienteForm.cuit}
                                    required
                                    type="text" />
                                <Grid item xs={12}>
                                    {/* Using Autocomplete bc select doesnt work here */}
                                    <Autocomplete
                                        disablePortal
                                        id="idTipoCliente"
                                        name="idTipoCliente"
                                        options={tiposCliente}
                                        autoHighlight
                                        getOptionLabel={(option) => tiposCliente.filter(t => t.value === option).pop() ? tiposCliente.filter(t => t.value === option).pop().label : ''}
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
                                        }} />
                                </Grid>
                                <Typography variant="h6" paddingLeft={2} mt={2}>
                                    Domicilio
                                </Typography>
                                <Input
                                    id="domicilio"
                                    label="Dirección"
                                    ref={refDomicilio}
                                    value={clienteForm.domicilio}
                                    type="text" />
                                <Input
                                    id="localidad"
                                    label="Localidad"
                                    ref={refLocalidad}
                                    value={clienteForm.localidad}
                                    type="text"
                                    sm={8} />
                                <Input
                                    id="codigoPostal"
                                    label="Código Postal"
                                    ref={refCodPostal}
                                    value={clienteForm.codPostal}
                                    sm={4} />
                                <Input
                                    id="provincia"
                                    label="Provincia"
                                    ref={refProvincia}
                                    value={clienteForm.provincia}
                                    type="text"
                                    sm={6} />
                                <Input
                                    id="pais"
                                    label="Pais"
                                    ref={refPais}
                                    value={clienteForm.pais}
                                    type="text"
                                    sm={6} />
                                <Typography variant="h6" paddingLeft={2} mt={2}>
                                    Datos de contacto
                                </Typography>

                                <Input
                                    id="email"
                                    label="E-mail"
                                    type="email"
                                    ref={refEmail}
                                    value={clienteForm.email} />
                                <Input
                                    id="telefono"
                                    label="Telefono"
                                    type="tel"
                                    ref={refTelefono}
                                    value={clienteForm.telefono} />
                                <Input
                                    id="celular"
                                    label="Celular"
                                    type="tel"
                                    ref={refCelular}
                                    value={clienteForm.celular} />
                                <Input
                                    id="fax"
                                    label="Fax"
                                    type="tel"
                                    ref={refFax}
                                    value={clienteForm.fax} />
                                <Typography variant="h6" paddingLeft={2} mt={2}>
                                    Transporte
                                </Typography>

                                <Input
                                    id="transporte"
                                    label="Transporte"
                                    ref={refTransporte}
                                    value={clienteForm.transporte}
                                    type="text" />
                                <Input
                                    id="senasaUta"
                                    label="SENASA/UTA"
                                    ref={refSenasaUta}
                                    value={clienteForm.senasaUta}
                                    type="text" />
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