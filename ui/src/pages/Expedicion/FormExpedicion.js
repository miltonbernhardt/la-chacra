import { Button, ButtonGroup, Grid } from "@mui/material";
import * as React from "react";
import { createRef, useCallback, useEffect, useMemo, useState } from "react";
import BarcodeReader from 'react-barcode-reader';
import toast from "react-hot-toast";
import { Input } from "../../components/Input";
import { Select } from "../../components/Select";
import * as field from "../../resources/fields";
import * as message from "../../resources/messages";
import * as validation from "../../resources/validations";
import { ScanDialog } from "./ScanDialog";

export const FormExpedicion = ({ expedicion, isEditing, clientes, handleSubmit, handleCancelar, handleDelete }) => {

    const [expedicionForm, setExpedicionForm] = useState(expedicion);
    const [fechaExpedicion, setFechaExpedicion] = useState('');
    const [openScanDialog, setOpenScanDialog] = useState(false);
    const [firstScan, setFirstScan] = useState('');

    const refIdLote = createRef()
    const refSelectCliente = createRef()
    const refFechaExpedicion = createRef()
    const refCantidad = createRef()
    const refPeso = createRef()

    useEffect(() => {
        setExpedicionForm(expedicion)
        if (expedicion.fechaExpedicion !== '') setFechaExpedicion(expedicion.fechaExpedicion);
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

    const submitScan = useCallback((values) => {
        setOpenScanDialog(false);
        handleSubmit(values);
    }, [handleSubmit])

    // --- Scan methods ---

    const handleFirstScan = useCallback((firstScan) => {
        setOpenScanDialog(true);
        setFirstScan(firstScan);
    }, [])

    const handleScanError = useCallback(() => toast.error('Error en la lectura'), []);

    const closeScanDialog = useCallback(() => setOpenScanDialog(false), []);
    // --- Variables
    const labelCargar = useMemo(() => isEditing ? 'Actualizar Expedición' : 'Cargar Expedición', [isEditing]);
    const colorCargar = useMemo(() => isEditing ? 'warning' : 'primary', [isEditing]);

    return (
        <>
            <Grid container spacing={1.5}>
                <Input ref={refFechaExpedicion}
                       id={field.backFechaExpedicion}
                       label={field.fechaExpedicion}
                       value={fechaExpedicion}
                       type="date"
                       required/>
                <Select ref={refSelectCliente}
                        value={expedicionForm.idCliente}
                        id={field.backIdCliente}
                        label={field.cliente}
                        options={clientes}
                        required/>
                <Input ref={refIdLote}
                       id={field.backIdLote}
                       label={field.numeroLote}
                       value={expedicionForm.idLote}
                       required/>
                <Input ref={refCantidad}
                       id={field.backCantidad}
                       label={field.cantidad}
                       value={expedicionForm.cantidad}
                       sm={6}
                       required/>
                <Input ref={refPeso}
                       id={field.backPesoExpedicion}
                       label={field.pesoExpedicion}
                       value={expedicionForm.peso}
                       sm={6}
                       required/>
                <Grid item xs={12} alignSelf="right" mb={0.5}>
                    <ButtonGroup fullWidth variant="contained">
                        <Button onClick={handleCancelar} disabled={!isEditing} color="primary">Cancelar</Button>
                        <Button onClick={handleDelete} disabled={!isEditing} color="error">Borrar Expedición</Button>
                        <Button onClick={handleCargar} color={colorCargar}>{labelCargar}</Button>
                    </ButtonGroup>
                </Grid>
            </Grid>
            <ScanDialog
                open={openScanDialog}
                onClose={closeScanDialog}
                onSubmit={submitScan}
                clientes={clientes}
                firstScan={firstScan}/>
            {!openScanDialog ? <BarcodeReader
                onScan={handleFirstScan}
                onError={handleScanError}/> : <></>}
        </>
    )
}