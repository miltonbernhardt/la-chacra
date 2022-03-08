import * as React from 'react';
import { useEffect, useMemo, useState, createRef } from "react";
import { Button, ButtonGroup, Grid } from "@mui/material";
import * as message from "../../resources/messages";
import * as field from "../../resources/fields";
import * as validation from "../../resources/validations";
import { toastValidationErrors } from "../../resources/fields";
import { Input } from "../../components/Input";
import { Select } from "../../components/Select";

export const FormLote = ({ quesos, lote, cancelEditing, deleteLote, isEditingLote, handleSubmit }) => {
    const [loteForm, setLoteForm] = useState(lote)

    const refSelectQueso = createRef()
    const refLitros = createRef()
    const refNumTina = createRef()
    const refCantHormas = createRef()
    const refPeso = createRef()
    const refFecha = createRef()

    const refLoteCultivo = createRef()
    const refLoteColorante = createRef()
    const refLoteCalcio = createRef()
    const refLoteCuajo = createRef()

    useEffect(() => setLoteForm(lote), [lote]);

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

        refPeso.current.validate(errors, values, [
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

        refLoteCultivo.current.setValue(values)
        refLoteColorante.current.setValue(values)
        refLoteCalcio.current.setValue(values)
        refLoteCuajo.current.setValue(values)

        handleSubmit(values)
    }

    const labelCargar = useMemo(() => isEditingLote ? 'Actualizar' : 'Cargar Lote', [isEditingLote]);
    const colorCargar = useMemo(() => isEditingLote ? 'warning' : 'primary', [isEditingLote]);

    return (
        <>
            <Grid container spacing={1.5}>
                <Select ref={refSelectQueso}
                        value={loteForm.codigoQueso}
                        id={field.backCodigoQueso}
                        label={field.queso}
                        options={quesos}
                        required/>
                <Input ref={refLitros}
                       id={field.backLitrosLeche}
                       label={field.litrosLeche}
                       value={loteForm.litrosLeche}
                       sm={8}
                       required/>
                <Input ref={refNumTina}
                       id={field.backNumeroTina}
                       label={field.numeroTina}
                       value={loteForm.numeroTina}
                       sm={4}
                       required/>
                <Input ref={refCantHormas}
                       id={field.backCantHormas}
                       label={field.cantHormas}
                       value={loteForm.cantHormas}
                       required/>
                <Input ref={refPeso}
                       id={field.backPeso}
                       label={field.peso}
                       value={loteForm.peso}
                       required/>
                <Input ref={refFecha}
                       id={field.backFechaElaboracion}
                       label={field.fechaElaboracion}
                       value={loteForm.fechaElaboracion}
                       type="date"
                       required/>
                <Input ref={refLoteCultivo}
                       id={field.backLoteCultivo}
                       label={field.loteCultivo}
                       value={loteForm.loteCultivo}
                       type="text"/>
                <Input ref={refLoteColorante}
                       id={field.backLoteColorante}
                       label={field.loteColorante}
                       value={loteForm.loteColorante}
                       type="text"/>
                <Input ref={refLoteCalcio}
                       id={field.backLoteCalcio}
                       label={field.loteCalcio}
                       value={loteForm.loteCalcio}
                       type="text"/>
                <Input ref={refLoteCuajo}
                       id={field.backLoteCuajo}
                       label={field.loteCuajo}
                       value={loteForm.loteCuajo}
                       type="text"/>
                <Grid item xs={12} alignSelf="right" mb={0.5}>
                    <ButtonGroup fullWidth variant="contained">
                        <Button onClick={cancelEditing} disabled={!isEditingLote} color="info">Cancelar</Button>
                        <Button onClick={deleteLote} disabled={!isEditingLote} color="error">Borrar Lote</Button>
                        <Button onClick={submitLote} color={colorCargar}>{labelCargar}</Button>
                    </ButtonGroup>
                </Grid>
            </Grid>
        </>
    )
}