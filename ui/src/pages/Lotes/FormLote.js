import { Button, ButtonGroup, Grid } from "@mui/material";
import * as React from 'react';
import { createRef, useEffect, useMemo, useState } from 'react';
import { Input } from "../../components/Input";
import { Select } from "../../components/Select";
import * as field from "../../resources/fields";
import { toastValidationErrors } from "../../resources/fields";
import * as message from "../../resources/messages";
import * as validation from "../../resources/validations";
import CancelIcon from '@mui/icons-material/Cancel';
import AddIcon from '@mui/icons-material/Add';
import AutorenewIcon from '@mui/icons-material/Autorenew';
import DeleteIcon from '@mui/icons-material/Delete';


export const FormLote = ({ quesos, lote, cancelEditing, deleteLote, isEditingLote, handleSubmit }) => {
    const [loteForm, setLoteForm] = useState(lote)
    const [fechaProduccion, setFechaProduccion] = useState('');

    const refSelectQueso = createRef()
    const refLitros = createRef()
    const refNumTina = createRef()
    const refCantHormas = createRef()
    const refFecha = createRef()
    const refCantCajas = createRef()

    useEffect(() => {
        setLoteForm(lote);
        if (lote.fechaElaboracion !== '') setFechaProduccion(lote.fechaElaboracion);
    }, [lote]);

    const submitLote = () => {
        const errors = new Map();
        const values = {};
        values["id"] = loteForm.id

        refSelectQueso.current.validate(errors, values, [
            { func: validation.emptySelect, msg: message.valEmptyField }
        ])

        refNumTina.current.validate(errors, values, [
            { func: validation.minorToOne, msg: message.valZeroValue },
            { func: validation.biggerThanThousand, msg: message.valBiggerThanThousand }
        ])

        refLitros.current.validate(errors, values, [
            { func: validation.minorToOne, msg: message.valZeroValue }
        ])

        refCantHormas.current.validate(errors, values, [
            { func: validation.minorToOne, msg: message.valZeroValue }
        ])

        refCantCajas.current.validate(errors, values, [
            { func: validation.minorToOne, msg: message.valZeroValue }
        ])

        refFecha.current.validate(errors, values, [
            { func: validation.empty, msg: message.valEmptyFecha },
            { func: validation.olderDate, msg: message.valOlderDate }
        ])

        if (errors.size > 0) {
            console.error(errors)
            toastValidationErrors(errors)
            return
        }

        handleSubmit(values)
    }

    const labelCargar = useMemo(() => isEditingLote ? 'Actualizar' : 'Cargar', [isEditingLote]);
    const colorCargar = useMemo(() => isEditingLote ? 'warning' : 'primary', [isEditingLote]);
    const iconCargar = useMemo(() => isEditingLote ? <AutorenewIcon /> : <AddIcon />, [isEditingLote]);

    return <Grid container spacing={1.5}>
        <Input ref={refFecha}
            id={field.backFechaElaboracion}
            label={field.fechaElaboracion}
            value={fechaProduccion}
            type="date"
            required />
        <Select ref={refSelectQueso}
            value={loteForm.codigoQueso}
            id={field.backCodigoQueso}
            label={field.queso}
            options={quesos}
            required />
        <Input ref={refNumTina}
            id={field.backNumeroTina}
            label={field.numeroTina}
            value={loteForm.numeroTina}
            sm={4}
            required />
        <Input ref={refLitros}
            id={field.backLitrosLeche}
            label={field.litrosLeche}
            value={loteForm.litrosLeche}
            sm={8}
            required />
        <Input ref={refCantHormas}
            id={field.backCantHormas}
            label={field.cantHormas}
            value={loteForm.cantHormas}
            required
            sm={6} />
        <Input ref={refCantCajas}
            id={field.backCantCajas}
            label={field.cantCajas}
            value={loteForm.cantCajas}
            required
            sm={6} />
        <Grid item xs={12} alignSelf="right" mb={0.5}>
            <ButtonGroup fullWidth variant="contained">
                <Button onClick={cancelEditing} disabled={!isEditingLote} color="info" startIcon={<CancelIcon />}>Cancelar</Button>
                <Button onClick={deleteLote} disabled={!isEditingLote} color="error" startIcon={<DeleteIcon />}>Borrar</Button>
                <Button onClick={submitLote} color={colorCargar} startIcon={iconCargar}>{labelCargar}</Button>
            </ButtonGroup>
        </Grid>
    </Grid>
}