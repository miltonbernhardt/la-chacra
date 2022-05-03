import {
    Button, Container,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    Grid,
    Stack,
    Switch,
    Typography
} from "@mui/material";
import * as React from 'react';
import { createRef, useState, useEffect, useCallback } from 'react';
import { Input } from "../../components/Input";
import { Select } from "../../components/Select";
import * as field from "../../resources/fields";
import * as validation from "../../resources/validations";
import * as message from "../../resources/messages";


export const EditExpedicionDialog = ({ open, onClose, expedicion, clientes, handleSubmit, onDelete }) => {

    const [expedicionForm, setExpedicionForm] = useState(expedicion);

    const [deleteEnabled, setDeleteEnabled] = useState(false);

    const refIdLote = createRef()
    const refSelectCliente = createRef()
    const refFechaExpedicion = createRef()
    const refCantidad = createRef()
    const refPeso = createRef()

    useEffect(() => {
        setExpedicionForm(expedicion)
        setDeleteEnabled(false);
    }, [expedicion]);

    const handleChangeSwitch = (evt) => {
        setDeleteEnabled(evt.target.checked);
    }

    const submitExpedicion = useCallback(() => {
        const errors = new Map();
        const values = {};
        values["id"] = expedicionForm.id

        refSelectCliente.current.validate(errors, values, [
            { func: validation.emptySelect, msg: message.valEmptyField }
        ])

        refIdLote.current.validate(errors, values, [
            { func: validation.minorToOne, msg: message.valZeroValue }])

        refFechaExpedicion.current.validate(errors, values, [
            { func: validation.empty, msg: message.valEmptyFecha },
            { func: validation.olderDate, msg: message.valOlderDate }
        ])


        refCantidad.current.validate(errors, values, [
            { func: validation.minorToOne, msg: message.valZeroValue }])

        refPeso.current.validate(errors, values, [
            { func: validation.minorToOne, msg: message.valZeroValue }])


        if (errors.size > 0) {
            console.error(errors)
            field.toastValidationErrors(errors)
            return
        }


        handleSubmit(values)
    }, [expedicionForm.id, handleSubmit, refCantidad, refFechaExpedicion, refIdLote, refPeso, refSelectCliente]);



    return (<Dialog
        open={open}
        onClose={onClose}
        scroll="body"    >
        <DialogTitle>Editar Producción</DialogTitle>
        <DialogContent
            style={{ backgroundColor: deleteEnabled ? "#F0BEBE" : '' }}>
            <Container maxWidth="sm">
                <Grid container spacing={2}>
                    <Grid item xs={12} sx={{ display: 'flex', justifyContent: 'right' }}>
                        <Stack direction="row" >
                            Habilitar Borrado
                            <Switch
                                checked={deleteEnabled}
                                onChange={handleChangeSwitch} />
                        </Stack>
                    </Grid>
                    <Grid item container spacing={1.5} xs={12} >
                        <Input ref={refFechaExpedicion}
                            id={field.backFechaExpedicion}
                            label={field.fechaExpedicion}
                            value={expedicionForm.fechaExpedicion}
                            type="date"
                            required />
                        <Select ref={refSelectCliente}
                            value={expedicionForm.idCliente}
                            id={field.backIdCliente}
                            label={field.cliente}
                            options={clientes}
                            required />
                        <Input ref={refIdLote}
                            id={field.backIdLote}
                            label={field.numeroLote}
                            value={expedicionForm.idLote}
                            required />
                        <Input ref={refCantidad}
                            id={field.backCantidad}
                            label={field.cantidad}
                            value={expedicionForm.cantidad}
                            sm={6}
                            required />
                        <Input ref={refPeso}
                            id={field.backPesoExpedicion}
                            label={field.pesoExpedicion}
                            value={expedicionForm.peso}
                            sm={6}
                            required />
                    </Grid>
                </Grid>
            </Container>
        </DialogContent>
        <DialogActions>
            <Button onClick={onDelete} disabled={!deleteEnabled}>Borrar Expedicion</Button>
            <Button onClick={onClose}>Cancelar</Button>
            <Button onClick={submitExpedicion}>Actualizar Expedicion</Button>
        </DialogActions>
    </Dialog>);
}
