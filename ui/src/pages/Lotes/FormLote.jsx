import { Autocomplete, Box, Button, ButtonGroup, Grid, TextField, Typography, Paper } from "@mui/material";
import { useCallback, useEffect, useMemo, useState } from "react";
import validator from "validator";
import DialogCargarTrazabilidad from "./DialogCargarTrazabilidad";
import * as message from "../../resources/messages";
import * as field from "../../resources/fields";
import { toastValidationErrors } from "../../resources/fields";

// const loteTrazabilidad = {
//     loteCultivo: '',
//     loteColorante: '',
//     loteCalcio: '',
//     loteCuajo: ''
// }

const FormLote = ({quesos, lote, cancelEditing, deleteLote, isEditingLote, handleSubmit}) => {

    const [loteTrazabilidad, setLoteTrazabilidad] = useState({});
    const [selectCodigoQueso, setSelectCodigoQueso] = useState({});
    const [litros, setLitros] = useState(lote.litrosLeche);
    const [numeroTina, setNumeroTina] = useState(lote.numeroTina);
    const [cantHormas, setCantHormas] = useState(lote.cantHormas);
    const [peso, setPeso] = useState(lote.peso);
    const [fechaElaboracion, setFechaElaboracion] = useState(lote.fechaElaboracion);
    const [dialogOpen, setDialogOpen] = useState(false);

    useEffect(() => {
        console.log("ENTRO A USE EFFECT")
        console.log({lote})
        if (lote.codigoQueso) {
            const quesito = quesos.filter((o) => o.value === lote.codigoQueso).pop()
            setSelectCodigoQueso(quesito);
        } else
            setSelectCodigoQueso({});
        setLitros(lote.litrosLeche);
        setNumeroTina(lote.numeroTina);
        setCantHormas(lote.cantHormas);
        setPeso(lote.peso);
        setFechaElaboracion(lote.fechaElaboracion);
        setDialogOpen(false);
    }, [lote], isEditingLote);

    const validateLote = () => {
        const errors = new Map();

        if (!selectCodigoQueso || !selectCodigoQueso.value || selectCodigoQueso.value === '')
            errors.set(field.queso, message.valEmptyField)

        if (litros < 1)
            errors.set(field.litrosLeche, message.valZeroValue)

        if (numeroTina < 1)
            errors.set(field.numeroTina, message.valZeroValue)
        else if (numeroTina > 999)
            errors.set(field.numeroTina, message.valLessThanThousand)

        if (cantHormas < 1)
            errors.set(field.cantidadHormas, message.valZeroValue)

        if (peso < 1)
            errors.set(field.peso, message.valZeroValue)

        const currentDate = new Date();
        const today = `${currentDate.getFullYear()}-${currentDate.getMonth() + 1}-${currentDate.getDate()}`;

        if (fechaElaboracion === '')
            errors.set(field.fechaElaboracion, message.valEmptyFecha)
        else if (validator.isBefore(today, fechaElaboracion))
            errors.set(field.fechaElaboracion, message.valOlderDate)

        if (errors.size > 0) {
            console.error(errors)
            toastValidationErrors(errors)
            return false;
        }
        return true;
    }

    const onCargar = () => {
        if (validateLote()) setDialogOpen(true);
    }

    const onCloseDialog = useCallback(() => setDialogOpen(false), []);

    const submitLote = (trazabilidadLote) => {
        const newLote = {
            [field.backCodigoQueso]: selectCodigoQueso.value,
            [field.backLitrosLeche]: litros,
            [field.backNumeroTina]: numeroTina,
            [field.backCantidadHormas]: cantHormas,
            [field.backPeso]: peso,
            [field.backFechaElaboracion]: fechaElaboracion,
            [field.backLoteCuajo]: trazabilidadLote.loteCultivo,
            [field.backLoteCuajo]: trazabilidadLote.loteColorante,
            [field.backLoteCuajo]: trazabilidadLote.loteCalcio,
            [field.backLoteCuajo]: trazabilidadLote.loteCuajo
        }

        if (validateLote())
            handleSubmit(newLote);
        setDialogOpen(false);
    }

    const trazabilidad = useCallback(() => {
        return {
            loteCultivo: loteTrazabilidad.loteCultivo,
            loteColorante: loteTrazabilidad.loteColorante,
            loteCalcio: loteTrazabilidad.loteCalcio,
            loteCuajo: loteTrazabilidad.loteCuajo
        }
    }, [loteTrazabilidad]);

    const labelCargar = useMemo(() => isEditingLote ? 'Actualizar' : 'Cargar Lote', [isEditingLote]);
    const colorCargar = useMemo(() => isEditingLote ? 'warning' : 'primary', [isEditingLote]);

    return (
        <>
            <Grid item xs={12}>
                <Typography variant="h6">
                    Ingreso de producción
                </Typography>
            </Grid>
            <Grid item xs={12}>
                <Autocomplete
                    id="autocomplete-tipoQueso"
                    name="codigoQueso"
                    options={quesos}
                    autoHighlight
                    getOptionLabel={(option) => option.label || ''}
                    renderOption={(props, option) => {
                        return <Box component="li"  {...props}>
                            {option.label}
                        </Box>
                    }}
                    renderInput={(params) => (
                        <TextField
                            {...params}
                            label="Tipo de queso"/>
                    )}
                    value={selectCodigoQueso}
                    onChange={(e, value) => {
                        setSelectCodigoQueso(value)
                    }}
                    isOptionEqualToValue={(option, value) =>
                        value.value ? option.value === value.value : option.value === value
                    }
                />
            </Grid>
            <Grid item xs={12} sm={8}>
                <TextField
                    id="litrosProcesados"
                    name="litrosLeche"
                    label="Litros procesados"
                    fullWidth
                    type="number"
                    variant="outlined"
                    numeric={litros.toString()}
                    value={litros}
                    onChange={e => setLitros(e.target.value)}
                    required
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
                    numeric={numeroTina.toString()}
                    value={numeroTina}
                    onChange={e => setNumeroTina(e.target.value)}
                    required
                />
            </Grid>
            <Grid item xs={12}>
                <TextField
                    id="cantidadHormas"
                    name="cantHormas"
                    label="Cantidad de hormas"
                    fullWidth
                    type="number"
                    variant="outlined"
                    numeric={cantHormas.toString()}
                    value={cantHormas}
                    onChange={e => setCantHormas(e.target.value)}
                    required
                />
            </Grid>
            <Grid item xs={12}>
                <TextField
                    id="pesoLote"
                    name="peso"
                    label="Peso del lote"
                    fullWidth
                    type="number"
                    variant="outlined"
                    numeric={peso.toString()}
                    value={peso}
                    onChange={e => setPeso(e.target.value)}
                    required
                />
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
                    value={fechaElaboracion}
                    onChange={e => setFechaElaboracion(e.target.value)}
                    required
                />
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

export default FormLote;