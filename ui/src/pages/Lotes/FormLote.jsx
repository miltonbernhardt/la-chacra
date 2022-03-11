import { Button, ButtonGroup, Grid } from "@mui/material";
import { useEffect, useMemo, useState, createRef } from "react";
import * as message from "../../resources/messages";
import * as field from "../../resources/fields";
import * as validation from "../../resources/validations";
import { toastValidationErrors } from "../../resources/fields";
import Input from "../../components/Input";
import Select from "../../components/Select";

const FormLote = ({ quesos, lote, cancelEditing, deleteLote, isEditingLote, handleSubmit }) => {

    const [loteForm, setLoteForm] = useState(lote)
    const [fechaProduccion, setFechaProduccion] = useState('');

    const refSelectQueso = createRef(null)
    const refLitros = createRef(null)
    const refNumTina = createRef(null)
    const refCantHormas = createRef(null)
    const refPeso = createRef(null)
    const refFecha = createRef(null)
    const refCantCajas = createRef(null)
    const refLoteCultivo = createRef(null)
    const refLoteColorante = createRef(null)
    const refLoteCalcio = createRef(null)
    const refLoteCuajo = createRef(null)

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

        refPeso.current.setValue(values)
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
                <Input ref={refPeso}
                    id={field.backPeso}
                    label={field.peso}
                    value={loteForm.peso} />
                Poner cajas
                <Input ref={refLoteCultivo}
                    id={field.backLoteCultivo}
                    label={field.loteCultivo}
                    value={loteForm.loteCultivo}
                    type="text" />
                <Input ref={refLoteColorante}
                    id={field.backLoteColorante}
                    label={field.loteColorante}
                    value={loteForm.loteColorante}
                    type="text" />
                <Input ref={refLoteCalcio}
                    id={field.backLoteCalcio}
                    label={field.loteCalcio}
                    value={loteForm.loteCalcio}
                    type="text" />
                <Input ref={refLoteCuajo}
                    id={field.backLoteCuajo}
                    label={field.loteCuajo}
                    value={loteForm.loteCuajo}
                    type="text" />
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

export default FormLote;