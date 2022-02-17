import { Autocomplete, Box, Button, ButtonGroup, Container, Grid, TextField, Typography } from "@mui/material";
import { useEffect } from "react";
import { useCallback, useState } from "react";
import CargarTrazabilidadDialog from "./CargarTrazabilidadDialog";
import validator from "validator";
import toast from 'react-hot-toast';


const Form = ({ quesos, lote, cancelEditing, deleteLote, isEditingLote, handleSubmit }) => {

    const [loteForm, setLoteForm] = useState({});
    const [dialogOpen, setDialogOpen] = useState(false);

    useEffect(() => { setLoteForm(lote) }, [lote]);

    const updateStateLote = (attribute, value) => {
        const newLote = { ...loteForm, [attribute]: value };
        setLoteForm(newLote);
    }

    const handleChange = useCallback(evt => {
        const nombreAtributo = evt.target.name;
        const valorAtributo = evt.target.value;
        if (evt.target.validity.valid) updateStateLote(nombreAtributo, valorAtributo);
    }, [updateStateLote])

    // const onCargar = useCallback(() => setDialogOpen(true), []);
    const onCargar = () => {
        if (validarLote()) setDialogOpen(true);
    }

    const onCloseDialog = useCallback(() => setDialogOpen(false), []);

    const trazabilidad = useCallback(() => {
        return {
            loteCultivo: loteForm.loteCultivo,
            loteColorante: loteForm.loteColorante,
            loteCalcio: loteForm.loteCalcio,
            loteCuajo: loteForm.loteCuajo
        }
    }, [loteForm]);

    const submitLote = (trazabilidadLote) => {
        const newLote = {
            ...loteForm,
            ['loteCultivo']: trazabilidadLote.loteCultivo,
            ['loteColorante']: trazabilidadLote.loteColorante,
            ['loteCalcio']: trazabilidadLote.loteCalcio,
            ['loteCuajo']: trazabilidadLote.loteCuajo
        }
        if (validarLote()) handleSubmit(newLote); //TODO
        setDialogOpen(false);
    }

    const validarLote = () => {
        const current = new Date();
        const date = `${current.getFullYear()}-${current.getMonth() + 1}-${current.getDate()}`;

        const errors = new Map();

        //todo mover estas constantes a un archivo aparte
        const fieldFechaElaboracion = "Fecha de elaboración"
        const fieldHormas = "Cantidad de Hormas"
        const fieldLitrosLeche = "Litros procesados"
        const fieldNumeroTina = "Número de tina"
        const fieldPeso = "Peso del lote"
        const fieldQueso = "Tipo de queso"

        const valEmptyFecha = "Debe elegirse una fecha"
        const valOlderDate = "La fecha no debe ser posterior al día de hoy"
        const valZeroValue = "No puede ser menor a 1"
        const valEmptyCodigoQueso = "No puede estar vacío"

        if (loteForm.fechaElaboracion === '') {
            errors.set(fieldFechaElaboracion, valEmptyFecha)
        } else if (validator.isBefore(date, loteForm.fechaElaboracion)) {
            errors.set(fieldFechaElaboracion, valOlderDate)
        }

        if (loteForm.cantHormas < 1) {
            errors.set(fieldHormas, valZeroValue)
        }

        if (loteForm.litrosLeche < 1) {
            errors.set(fieldLitrosLeche, valZeroValue)
        }

        if (loteForm.numeroTina < 1) {
            errors.set(fieldNumeroTina, valZeroValue)
        }

        if (loteForm.peso < 1) {
            errors.set(fieldPeso, valZeroValue)
        }

        if (loteForm.codigoQueso === '') {
            errors.set(fieldQueso, valEmptyCodigoQueso)
        }


        if (errors.size > 0) {
            errors.forEach(function (msg, field) {
                console.log(`${field}: ${msg}`)
                toast.error(`${field}: ${msg}`)
            })
            return false;
        }
        return true;
    }

    return (
        <>
            <Grid item xs={12}>
                <Autocomplete //TODO bug cuando se selecciona queso para editar
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
                            label="Tipo de queso" />
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
                    }} />
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
                    onChange={handleChange} />
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
                    onChange={handleChange} />
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
                    onChange={handleChange} />
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
                    onChange={handleChange} />
            </Grid>
            <Grid item xs={12} alignSelf="right" mb={0.5}>
                <ButtonGroup fullWidth variant="contained">
                    <Button onClick={cancelEditing} disabled={!isEditingLote} color="info">Cancelar</Button>
                    <Button onClick={deleteLote} disabled={!isEditingLote} color="error">Borrar Lote</Button>
                    <Button onClick={onCargar} disabled={!isEditingLote} color="warning">Actualizar</Button>
                    <Button onClick={onCargar} disabled={isEditingLote}>Cargar Lote</Button>
                </ButtonGroup>
            </Grid>
            <CargarTrazabilidadDialog
                open={dialogOpen}
                onClose={onCloseDialog}
                trazabilidad={trazabilidad}
                submitLote={submitLote}
                isEditing={isEditingLote} />
        </>
    )
}

const LoteForm = ({ quesos, lote, onCargar, isEditingLote, cancelEditing, deleteLote, handleSubmit }) => {

    return (
        <Container maxWidth="sm">
            <Box
                sx={{
                    marginTop: 8,
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                    mt: 3
                }}>
                <Grid container spacing={2} justifyContent="right">
                    <Grid item xs={12}>
                        <Typography variant="h6">
                            Ingreso de producción
                        </Typography>
                    </Grid>
                    <Form
                        lote={lote}
                        quesos={quesos}
                        cancelEditing={cancelEditing}
                        isEditingLote={isEditingLote}
                        deleteLote={deleteLote}
                        onCargar={onCargar}
                        handleSubmit={handleSubmit} />
                </Grid>
            </Box>
        </Container>

    );
}

export default LoteForm;