import {
    Box,
    Button,
    Container,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    Grid,
    Typography
} from "@mui/material";
import {createRef, useEffect, useMemo, useState} from 'react';
import Input from "../../components/Input";
import Select from '../../components/Select';
import * as field from "../../resources/fields";
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

const DialogAltaCliente = ({cliente, open, onClose, onSubmit, isEditing, tiposCliente}) => {

    const [clienteForm, setClienteForm] = useState(clienteInicial);

    const refSelectCliente = createRef(null)
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
            {func: validation.empty, msg: message.valEmptyField}
        ])

        refCuit.current.validate(errors, values, [
            {func: validation.empty, msg: message.valEmptyField}
        ])

        refSelectCliente.current.validate(errors, values, [
            {func: validation.emptySelect, msg: message.valEmptyField}
        ])

        if (errors.size > 0) {
            console.error(errors)
            field.toastValidationErrors(errors)
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
                                    id={field.backRazonSocial}
                                    label={field.razonSocial}
                                    ref={refRazonSocial}
                                    value={clienteForm.razonSocial}
                                    required
                                    type="text"/>
                                <Input
                                    id={field.backCuit}
                                    label={field.cuit}
                                    ref={refCuit}
                                    value={clienteForm.cuit}
                                    required
                                    type="text"/>
                                <Select ref={refSelectCliente}
                                        value={clienteForm.idTipoCliente}
                                        id={field.backIdTipoCliente}
                                        label={field.idTipoCliente}
                                        options={tiposCliente}
                                        required/>
                                <Typography variant="h6" paddingLeft={2} mt={2}>
                                    Domicilio
                                </Typography>
                                <Input
                                    id={field.backDomicilio}
                                    label={field.domicilio}
                                    ref={refDomicilio}
                                    value={clienteForm.domicilio}
                                    type="text"/>
                                <Input
                                    id={field.backLocalidad}
                                    label={field.localidad}
                                    ref={refLocalidad}
                                    value={clienteForm.localidad}
                                    type="text"
                                    sm={8}/>
                                <Input
                                    id={field.backCodPostal}
                                    label={field.codPostal}
                                    ref={refCodPostal}
                                    value={clienteForm.codPostal}
                                    sm={4}/>
                                <Input
                                    id={field.backProvincia}
                                    label={field.provincia}
                                    ref={refProvincia}
                                    value={clienteForm.provincia}
                                    type="text"
                                    sm={6}/>
                                <Input
                                    id={field.backPais}
                                    label={field.pais}
                                    ref={refPais}
                                    value={clienteForm.pais}
                                    type="text"
                                    sm={6}/>
                                <Typography variant="h6" paddingLeft={2} mt={2}>
                                    Datos de contacto
                                </Typography>

                                <Input
                                    id={field.backEmail}
                                    label={field.email}
                                    type="email"
                                    ref={refEmail}
                                    value={clienteForm.email}/>
                                <Input
                                    id={field.backTelefono}
                                    label={field.telefono}
                                    type="tel"
                                    ref={refTelefono}
                                    value={clienteForm.telefono}/>
                                <Input
                                    id={field.backCelular}
                                    label={field.celular}
                                    type="tel"
                                    ref={refCelular}
                                    value={clienteForm.celular}/>
                                <Input
                                    id={field.backFax}
                                    label={field.fax}
                                    type="tel"
                                    ref={refFax}
                                    value={clienteForm.fax}/>
                                <Typography variant="h6" paddingLeft={2} mt={2}>
                                    Transporte
                                </Typography>

                                <Input
                                    id={field.backTransporte}
                                    label={field.transporte}
                                    ref={refTransporte}
                                    value={clienteForm.transporte}
                                    type="text"/>
                                <Input
                                    id={field.backSenasaUta}
                                    label={field.senasaUta}
                                    ref={refSenasaUta}
                                    value={clienteForm.senasaUta}
                                    type="text"/>
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