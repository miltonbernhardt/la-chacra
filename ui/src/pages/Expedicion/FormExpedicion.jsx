import { Button, ButtonGroup, Grid } from "@mui/material";
import { createRef, useCallback, useEffect, useMemo, useState } from "react";
import Input from "../../components/Input";
import Select from "../../components/Select";
import * as field from "../../resources/fields";
import * as message from "../../resources/messages";
import * as validation from "../../resources/validations";

const FormExpedicion = ({ expedicion, isEditing, clientes, handleSubmit, handleCancelar, handleDelete }) => {

    const [expedicionForm, setExpedicionForm] = useState(expedicion)

    const refIdLote = createRef(null);
    const refSelectCliente = createRef(null);
    const refFechaExpedicion = createRef(null);
    const refCantidad = createRef(null);
    const refPeso = createRef(null);

    useEffect(() => {
        setExpedicionForm(expedicion)
    }, [expedicion]);

    const handleCargar = useCallback(() => {
        const errors = new Map();
        const values = {};
        values["id"] = expedicionForm.id

        refSelectCliente.current.validate(errors, values, [
            { func: validation.emptySelect, msg: message.valEmptyField }
        ])

        refIdLote.current.validate(errors, values, [
            { func: validation.minorToOne, msg: message.valZeroValue }])

        refCantidad.current.validate(errors, values, [
            { func: validation.minorToOne, msg: message.valZeroValue }])

        refPeso.current.validate(errors, values, [
            { func: validation.minorToOne, msg: message.valZeroValue }])

        refFechaExpedicion.current.validate(errors, values, [
            { func: validation.empty, msg: message.valEmptyFecha },
            { func: validation.olderDate, msg: message.valOlderDate }
        ])

        if (errors.size > 0) {
            console.error(errors)
            field.toastValidationErrors(errors)
            return
        }

        handleSubmit(values)
    }, [expedicionForm.id, handleSubmit, refCantidad, refFechaExpedicion, refIdLote, refPeso, refSelectCliente]);

    // --- Variables
    const labelCargar = useMemo(() => isEditing ? 'Actualizar Expedición' : 'Cargar Expedición', [isEditing]);
    const colorCargar = useMemo(() => isEditing ? 'warning' : 'primary', [isEditing]);

    return (
        <Grid container spacing={1.5}>
            <Select ref={refSelectCliente}
                value={expedicionForm.codigoQueso}
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
            <Input ref={refFechaExpedicion}
                id={field.backFechaExpedicion}
                label={field.fechaExpedicion}
                value={expedicionForm.fechaExpedicion}
                type="date"
                required />
            <Grid item xs={12} alignSelf="right" mb={0.5}>
                <ButtonGroup fullWidth variant="contained">
                    <Button onClick={handleCancelar} disabled={!isEditing} color="primary">Cancelar</Button>
                    <Button onClick={handleDelete} disabled={!isEditing} color="error">Borrar Expedición</Button>
                    <Button onClick={handleCargar} color={colorCargar}>{labelCargar}</Button>
                </ButtonGroup>
            </Grid>
        </Grid>
    )
}

export default FormExpedicion