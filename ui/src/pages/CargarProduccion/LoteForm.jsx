import {Autocomplete, Box, Button, ButtonGroup, Container, Grid, TextField, Typography} from "@mui/material";


const BotoneraLoteForm = ({cancelEditing, deleteLote, updateLote, onCargar, isEditingLote}) => {
    return <Grid item xs={12} alignSelf="right" mb={0.5}>
        <ButtonGroup fullWidth variant="contained">
            <Button onClick={cancelEditing} disabled={!isEditingLote} color="info">Cancelar</Button>
            <Button onClick={deleteLote} disabled={!isEditingLote} color="error">Borrar Lote</Button>
            <Button onClick={updateLote} disabled={!isEditingLote} color="warning">Actualizar</Button>
            <Button onClick={onCargar} disabled={isEditingLote}>Cargar Lote</Button>
        </ButtonGroup>
    </Grid>;
}

const LoteForm = ({lote, quesos, updateStateLote, onCargar, isEditingLote, cancelEditing, deleteLote, updateLote}) => {

    const quesosAutocomplete = quesos.map((q) => {
        return {
            id: q.id,
            string: q.codigo + ' - ' + q.tipoQueso + ' - ' + q.nomenclatura,
            label: q.codigo
        }
    })

    const handleChange = evt => {
        const nombreAtributo = evt.target.name;
        const valorAtributo = evt.target.value;
        if (evt.target.validity.valid) updateStateLote(nombreAtributo, valorAtributo);
    }

    return (
        <Container maxWidth="sm">
            <Box
                sx={{
                    marginTop: 8,
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                    mt: 3
                }}
            >
                <Grid container spacing={2} justifyContent="right">
                    <Grid item xs={12}>
                        <Typography variant="h6">
                            Ingreso de producción
                        </Typography>
                    </Grid>
                    <Grid item xs={12}>
                        <Autocomplete //TODO bug cuando se selecciona queso para editar
                            id="autocomplete-tipoQueso"
                            name="codigoQueso"
                            options={quesosAutocomplete}
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
                            value={lote.codigoQueso}
                            onChange={(evt, newValue) => {
                                //-- lo paso asi para no chequear validez del campo
                                updateStateLote('codigoQueso', newValue);
                            }}
                            isOptionEqualToValue={(option, value) => {
                                console.log({option: option.label, value: value});
                                if (value.label) {
                                    return option.label === value.label
                                } else {
                                    return (option.label === value)
                                }
                                ;
                            }}
                        />
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
                            value={lote.litrosLeche}
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
                            value={lote.numeroTina}
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
                            value={lote.cantHormas}
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
                            value={lote.peso}
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
                            value={lote.fechaElaboracion}
                            onChange={handleChange}/>
                    </Grid>
                    <BotoneraLoteForm cancelEditing={cancelEditing} isEditingLote={isEditingLote} deleteLote={deleteLote}
                                  updateLote={updateLote} onCargar={onCargar}/>
                </Grid>
            </Box>
        </Container>
    );
}

export default LoteForm;