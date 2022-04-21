import {
    Button,
    ButtonGroup,
    Grid,
    Checkbox,
} from "@mui/material";
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
import CancelIcon from '@mui/icons-material/Cancel';
import AddIcon from '@mui/icons-material/Add';
import AutorenewIcon from '@mui/icons-material/Autorenew';
import DeleteIcon from '@mui/icons-material/Delete';
import FormControlLabel from '@mui/material/FormControlLabel';

export const FormExpedicion = ({ expedicion, isEditing, clientes, handleSubmit, handleCancelar, handleDelete }) => {

    const [expedicionForm, setExpedicionForm] = useState(expedicion);
    const [fechaExpedicion, setFechaExpedicion] = useState('');
    const [isLoteCompleto, setLoteCompleto] = useState(false);

    const [openScanDialog, setOpenScanDialog] = useState(false);
    const [firstScan, setFirstScan] = useState('');
    const [cliente, setCliente] = useState({});

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

        refFechaExpedicion.current.validate(errors, values, [
            { func: validation.empty, msg: message.valEmptyFecha },
            { func: validation.olderDate, msg: message.valOlderDate }
        ])

        if (isLoteCompleto) {
            values[field.backPesoExpedicion] = 1;
            values[field.backCantidad] = 1;
        } else {

            refCantidad.current.validate(errors, values, [
                { func: validation.minorToOne, msg: message.valZeroValue }])

            refPeso.current.validate(errors, values, [
                { func: validation.minorToOne, msg: message.valZeroValue }])
        }

        if (errors.size > 0) {
            console.error(errors)
            field.toastValidationErrors(errors)
            return
        }

        const loteCompleto = isLoteCompleto;
        setLoteCompleto(false);

        handleSubmit(values, loteCompleto)
    }, [expedicionForm.id, handleSubmit, isLoteCompleto, refCantidad, refFechaExpedicion, refIdLote, refPeso, refSelectCliente]);

    const submitScan = useCallback((values) => {
        setOpenScanDialog(false);
        handleSubmit(values);
    }, [handleSubmit])

    // --- Scan methods ---

    const handleFirstScan = useCallback((firstScan) => {
        const values = {};
        refSelectCliente.current.validate({}, values, []);
        setCliente(values.idCliente);
        setOpenScanDialog(true);
        setFirstScan(firstScan);
    }, [refSelectCliente])

    const handleScanError = useCallback(() => toast.error('Error en la lectura'), []);

    const closeScanDialog = useCallback(() => setOpenScanDialog(false), []);

    const changeLoteCompleto = useCallback(() => {
        const newState = !isLoteCompleto;
        setLoteCompleto(newState);
    }, [isLoteCompleto])

    // --- Variables ---

    const labelCargar = useMemo(() => isEditing ? 'Actualizar' : 'Cargar', [isEditing]);
    const colorCargar = useMemo(() => isEditing ? 'warning' : 'primary', [isEditing]);
    const iconCargar = useMemo(() => isEditing ? <AutorenewIcon /> : <AddIcon />, [isEditing]);

    return (
        <>
            <Grid container spacing={1.5}>
                <Input ref={refFechaExpedicion}
                    id={field.backFechaExpedicion}
                    label={field.fechaExpedicion}
                    value={fechaExpedicion}
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
                    required={!isLoteCompleto} />
                <Input ref={refPeso}
                    id={field.backPesoExpedicion}
                    label={field.pesoExpedicion}
                    value={expedicionForm.peso}
                    sm={6}
                    required={!isLoteCompleto} />
                <Grid item xs={12} sx={{ display: 'flex', justifyContent: 'center' }}>
                    <FormControlLabel
                        label="Lote Completo"
                        control={
                            <Checkbox
                                checked={isLoteCompleto}
                                onChange={() => changeLoteCompleto()} />} />
                </Grid>
                <Grid item xs={12} alignSelf="right" mb={0.5}>
                    <ButtonGroup fullWidth variant="contained">
                        <Button onClick={handleCancelar} disabled={!isEditing} color="info" startIcon={<CancelIcon />}>Cancelar</Button>
                        <Button onClick={handleDelete} disabled={!isEditing} color="error" startIcon={<DeleteIcon />}>Borrar</Button>
                        <Button onClick={handleCargar} color={colorCargar} startIcon={iconCargar}>{labelCargar}</Button>
                    </ButtonGroup>
                </Grid>
            </Grid>
            <ScanDialog
                open={openScanDialog}
                onClose={closeScanDialog}
                onSubmit={submitScan}
                clientes={clientes}
                cliente={cliente}
                fechaExpedicion={fechaExpedicion}
                firstScan={firstScan} />
            {!openScanDialog ? <BarcodeReader
                onScan={handleFirstScan}
                onError={handleScanError} /> : <></>}
        </>
    )
}