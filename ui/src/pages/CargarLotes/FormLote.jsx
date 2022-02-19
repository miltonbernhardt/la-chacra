import { Autocomplete, Box, Button, ButtonGroup, Grid, TextField, Typography, Paper } from "@mui/material";
import { useCallback, useEffect, useMemo, useState } from "react";
import validator from "validator";
import DialogCargarTrazabilidad from "./DialogCargarTrazabilidad";
import * as message from "../../messages";
import * as field from "../../fields";
import { toastValidationErrors } from "../../fields";

const loteInicial = {
    id: '',
    fechaElaboracion: '',
    numeroTina: '',
    litrosLeche: '',
    cantHormas: '',
    peso: '',
    loteCultivo: '',
    loteColorante: '',
    loteCalcio: '',
    loteCuajo: '',
    codigoQueso: ''
}

const Form = ({quesos, lote, cancelEditing, deleteLote, isEditingLote, handleSubmit}) => {

    const [loteForm, setLoteForm] = useState({});
    const [dialogOpen, setDialogOpen] = useState(false);

    useEffect(() => {
        setLoteForm(lote)
    }, [lote]);

    // --- STATES ---
    const updateStateLote = useCallback((attribute, value) => {
        const newLote = {...loteForm, [attribute]: value};
        setLoteForm(newLote);
    }, [loteForm]);

    const handleChange = useCallback(evt => {
        const nombreAtributo = evt.target.name;
        const valorAtributo = evt.target.value;
        if (evt.target.validity.valid) updateStateLote(nombreAtributo, valorAtributo);
    }, [updateStateLote])

    const onCargar = () => {
        if (validateLote()) setDialogOpen(true);
    }

    const onCloseDialog = useCallback(() => setDialogOpen(false), []);

    // --- SUBMIT ---
    const submitLote = (trazabilidadLote) => {
        const newLote = {
            ...loteForm,
            ['loteCultivo']: trazabilidadLote.loteCultivo,
            ['loteColorante']: trazabilidadLote.loteColorante,
            ['loteCalcio']: trazabilidadLote.loteCalcio,
            ['loteCuajo']: trazabilidadLote.loteCuajo
        }
        if (validateLote()) {
            handleSubmit(newLote);
            setLoteForm(loteInicial);
        }
        setDialogOpen(false);
    }

    const validateLote = () => {
        const current = new Date();
        const date = `${current.getFullYear()}-${current.getMonth() + 1}-${current.getDate()}`;

        const errors = new Map();

        if (loteForm.codigoQueso === '')
            errors.set(field.queso, message.valEmptyField)

        if (loteForm.litrosLeche < 1)
            errors.set(field.litrosLeche, message.valZeroValue)

        if (loteForm.numeroTina < 1)
            errors.set(field.numeroTina, message.valZeroValue)

        if (loteForm.cantHormas < 1)
            errors.set(field.cantidadHormas, message.valZeroValue)

        if (loteForm.peso < 1)
            errors.set(field.peso, message.valZeroValue)

        if (loteForm.fechaElaboracion === '')
            errors.set(field.fechaElaboracion, message.valEmptyFecha)
        else if (validator.isBefore(date, loteForm.fechaElaboracion))
            errors.set(field.fechaElaboracion, message.valOlderDate)

        if (errors.size > 0) {
            console.error(errors)
            toastValidationErrors(errors)
            return false;
        }
        return true;
    }

    // --- VARIABLES ---
    const trazabilidad = useCallback(() => {
        return {
            loteCultivo: loteForm.loteCultivo,
            loteColorante: loteForm.loteColorante,
            loteCalcio: loteForm.loteCalcio,
            loteCuajo: loteForm.loteCuajo
        }
    }, [loteForm]);

    const labelCargar = useMemo(() => {
        return isEditingLote ? 'Actualizar' : 'Cargar Lote'
    }, [isEditingLote]);
    const colorCargar = useMemo(() => {
        return isEditingLote ? 'warning' : 'primary'
    }, [isEditingLote]);

    return (
        <>
            <Grid item xs={12}>
                <Typography variant="h6">
                    Ingreso de producción
                </Typography>
            </Grid>
            <Grid item xs={12}>
                <Autocomplete //TODO bug queda siempre algo seleccionado
                    id="autocomplete-tipoQueso"
                    name="codigoQueso"
                    options={quesos}
                    autoHighlight
                    getOptionLabel={(option) => option.string || ''}
                    renderOption={(props, option) => (
                        <Box component="li"  {...props}>
                            {option.string}
                        </Box>
                    )}
                    renderInput={(params) => (
                        <TextField
                            {...params}
                            label="Tipo de queso"/>
                    )}
                    value={loteForm.codigoQueso}
                    onChange={(evt, newValue) => {
                        //-- lo paso asi para no chequear validez del campo
                        updateStateLote('codigoQueso', newValue);
                    }}
                    isOptionEqualToValue={(option, value) => {
                        if (value.label) {
                            return option.label === value.label
                        } else {
                            return (option.label === value)
                        }
                        ;
                    }}/>
            </Grid>
            <Grid item xs={12} sm={8}>
                <TextField
                    id="litrosProcesados"
                    name="litrosLeche"
                    label="Litros procesados"
                    fullWidth
                    type="number"
                    numeric
                    variant="outlined"
                    value={loteForm.litrosLeche}
                    onChange={handleChange}
                />
            </Grid>
            <Grid item xs={12} sm={4}>
                <TextField
                    id="tina"
                    name="numeroTina"
                    label="Tina"
                    fullWidth
                    type="number"
                    variant="outlined"
                    value={loteForm.numeroTina}
                    onChange={handleChange}/>
            </Grid>
            <Grid item xs={12}>
                <TextField
                    id="cantidadHormas"
                    name="cantHormas"
                    label="Cantidad de hormas"
                    fullWidth
                    type="number"
                    variant="outlined"
                    value={loteForm.cantHormas}
                    onChange={handleChange}/>
            </Grid>
            <Grid item xs={12}>
                <TextField
                    id="pesoLote"
                    name="peso"
                    label="Peso del lote"
                    fullWidth
                    type="number"
                    variant="outlined"
                    value={loteForm.peso}
                    onChange={handleChange}/>
            </Grid>
            <Grid item xs={12}>
                <TextField
                    id="fechaLote"
                    name="fechaElaboracion"
                    label="Fecha de producción"
                    fullWidth
                    type="date"
                    variant="outlined"
                    InputLabelProps={{
                        shrink: true,
                    }}
                    value={loteForm.fechaElaboracion}
                    onChange={handleChange}/>
            </Grid>
            <Grid item xs={12} alignSelf="right" mb={0.5}>
                <ButtonGroup fullWidth variant="contained">
                    <Button onClick={cancelEditing} disabled={!isEditingLote} color="info">Cancelar</Button>
                    <Button onClick={deleteLote} disabled={!isEditingLote} color="error">Borrar Lote</Button>
                    <Button onClick={onCargar} color={colorCargar}>{labelCargar}</Button>
                </ButtonGroup>
            </Grid>
            <DialogCargarTrazabilidad
                open={dialogOpen}
                onClose={onCloseDialog}
                trazabilidad={trazabilidad}
                submitLote={submitLote}
                isEditing={isEditingLote}/>
        </>
    )
}

const FormLote = ({quesos, lote, onCargar, isEditingLote, cancelEditing, deleteLote, handleSubmit}) => {

    return (
        <>
            <Grid item container direction="row" xs={6} spacing={1}>
                <Form
                    lote={lote}
                    quesos={quesos}
                    cancelEditing={cancelEditing}
                    isEditingLote={isEditingLote}
                    deleteLote={deleteLote}
                    onCargar={onCargar}
                    handleSubmit={handleSubmit}/>
            </Grid>
        </>
    )
}

export default FormLote;