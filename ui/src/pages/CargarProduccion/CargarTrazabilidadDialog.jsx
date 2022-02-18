import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Grid, TextField } from '@mui/material';
import { useEffect, useState, useMemo } from 'react';

const CargarTrazabilidadDialog = ({ open, onClose, submitLote, isEditing, trazabilidad }) => {

    const [trazabilidadForm, setTrazabilidad] = useState({});

    useEffect(() => setTrazabilidad(trazabilidad), [trazabilidad])

    const handleChange = evt => {
        const attribute = evt.target.name;
        const value = evt.target.value;
        if (evt.target.validity.valid) {
            const newTrazabilidad = { ...trazabilidadForm, [attribute]: value }
            setTrazabilidad(newTrazabilidad);
        }
    }

    const onCargar = () => submitLote(trazabilidadForm);

    const labelCargar = useMemo(() => { return isEditing ? 'Actualizar' : 'Cargar Lote' }, [isEditing]);

    return (
        <div>
            <Dialog open={open} onClose={onClose} scroll="body">
                <DialogTitle>Trazabilidad</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        Ingrese los datos de los insumos
                    </DialogContentText>
                    <Grid container spacing={2}>
                        <Grid item xs={12} >
                            <TextField
                                id="loteCultivo"
                                name="loteCultivo"
                                label="Lote de cultivo"
                                fullWidth
                                variant="outlined"
                                value={trazabilidadForm.loteCultivo}
                                onChange={handleChange} />
                        </Grid>
                        <Grid item xs={12} >
                            <TextField
                                id="loteColorante"
                                name="loteColorante"
                                label="Lote de colorante"
                                fullWidth
                                variant="outlined"
                                value={trazabilidadForm.loteColorante}
                                onChange={handleChange} />
                        </Grid>
                        <Grid item xs={12} >
                            <TextField
                                id="loteCalcio"
                                name="loteCalcio"
                                label="Lote de calcio"
                                fullWidth
                                variant="outlined"
                                value={trazabilidadForm.loteCalcio}
                                onChange={handleChange} />
                        </Grid>
                        <Grid item xs={12} >
                            <TextField
                                id="loteCuajo"
                                name="loteCuajo"
                                label="Lote de cuajo"
                                fullWidth
                                variant="outlined"
                                value={trazabilidadForm.loteCuajo}
                                onChange={handleChange} />
                        </Grid>
                    </Grid>
                </DialogContent>
                <DialogActions>
                    <Button onClick={onClose}>Cancelar</Button>
                    <Button onClick={onCargar}>{labelCargar}</Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}

export default CargarTrazabilidadDialog;