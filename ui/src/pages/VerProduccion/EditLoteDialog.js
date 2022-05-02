import {
    Button,
    Checkbox,
    Container,
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
import { createRef, useEffect, useState, useCallback } from 'react';
import { Input } from "../../components/Input";
import * as field from "../../resources/fields";
import { toastValidationErrors } from "../../resources/fields";
import * as message from "../../resources/messages";
import * as validation from "../../resources/validations";
import FormControlLabel from '@mui/material/FormControlLabel';

export const EditLoteDialog = ({ lote, open, onClose, onSubmit, onDelete }) => {

    const [loteForm, setLoteForm] = useState(lote);
    const [pesoNoConfiable, setPesoNoConfiable] = useState(lote.pesoNoConfiable);

    const [deleteEnabled, setDeleteEnabled] = useState(false);

    const refLitros = createRef();
    const refCantHormas = createRef();
    const refPeso = createRef();
    const refCantCajas = createRef();
    const refLoteCultivo = createRef();
    const refLoteColorante = createRef();
    const refLoteCalcio = createRef();
    const refLoteCuajo = createRef();

    useEffect(() => {
        setLoteForm(lote);
        setPesoNoConfiable(lote.pesoNoConfiable);
        setDeleteEnabled(false);
    }, [lote]);

    const handleChangeSwitch = (evt) => {
        setDeleteEnabled(evt.target.checked);
    }

    const submitLote = () => {
        const errors = new Map();
        const values = {};
        values["id"] = loteForm.id

        refLitros.current.validate(errors, values, [{ func: validation.minorToOne, msg: message.valZeroValue }])

        refCantHormas.current.validate(errors, values, [{ func: validation.minorToOne, msg: message.valZeroValue }])

        refCantCajas.current.validate(errors, values, [{ func: validation.minorToOne, msg: message.valZeroValue }])

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

        const loteSubmit = {
            ...lote,
            [field.backLitrosLeche]: values.litrosLeche,
            [field.backCantHormas]: values.cantHormas,
            [field.backCantCajas]: values.cantCajas,
            [field.backPeso]: values.peso,
            [field.backLoteCuajo]: values.loteCuajo,
            [field.backLoteColorante]: values.loteColorante,
            [field.backLoteCalcio]: values.loteCalcio,
            [field.backLoteCultivo]: values.loteCultivo,
            [field.backPesoNoConfiable]: pesoNoConfiable
        }

        onSubmit(loteSubmit)
    }

    const changePesoNoConfiable = useCallback(() => {
        const newState = !pesoNoConfiable;
        setPesoNoConfiable(newState)
    }, [pesoNoConfiable])

    return <Dialog
        open={open}
        onClose={onClose}
        scroll="body"
    >
        <DialogTitle>Editar Producción</DialogTitle>
        <DialogContent
            style={{ backgroundColor: deleteEnabled ? "#F0BEBE" : '' }}>
            <Container maxWidth="sm">
                <Grid container spacing={2}>
                    <Grid item xs={8}>
                        <Typography variant="h6" paddingLeft={2}>
                            Lote: {lote.id}
                        </Typography>
                    </Grid>
                    <Grid item xs={3}>
                        <Stack direction="row" justifyContent="right">
                            Habilitar Borrado
                            <Switch
                                checked={deleteEnabled}
                                onChange={handleChangeSwitch} />
                        </Stack>
                    </Grid>
                    <Grid item container spacing={1.5} xs={12} sm={6}>

                        <Input ref={refLitros}
                            id={field.backLitrosLeche}
                            label={field.litrosLeche}
                            value={loteForm.litrosLeche}
                            required />
                        <Input ref={refCantHormas}
                            id={field.backCantHormas}
                            label={field.cantHormas}
                            value={loteForm.cantHormas}
                            required />
                        <Input ref={refCantCajas}
                            id={field.backCantCajas}
                            label={field.cantCajas}
                            value={loteForm.cantCajas}
                            required />
                        <Input ref={refPeso}
                            id={field.backPeso}
                            label={field.peso}
                            value={loteForm.peso} />
                    </Grid>
                    <Grid item container spacing={1.5} xs={12} sm={6}>
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
                    </Grid>
                    <Grid item xs={12}>
                        <FormControlLabel
                            label="Peso no confiable"
                            control={
                                <Checkbox
                                    checked={pesoNoConfiable}
                                    onChange={() => changePesoNoConfiable()} />} />
                    </Grid>
                </Grid>
            </Container>
        </DialogContent>
        <DialogActions>
            <Button onClick={onDelete} disabled={!deleteEnabled}>Borrar Lote</Button>
            <Button onClick={onClose}>Cancelar</Button>
            <Button onClick={submitLote}>Actualizar Lote</Button>
        </DialogActions>
    </Dialog>
}