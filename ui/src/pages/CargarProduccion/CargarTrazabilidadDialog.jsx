import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Grid, TextField } from '@mui/material';

const CargarTrazabilidadDialog = ({ open, onClose, onSubmit, lote, updateStateLote }) => {

    const handleChange = evt => {
        const nombreAtributo = evt.target.name;
        const valorAtributo = evt.target.value;
        if (evt.target.validity.valid) updateStateLote(nombreAtributo, valorAtributo);
    }

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
                                value={lote.loteCultivo}
                                onChange={handleChange} />
                        </Grid>
                        <Grid item xs={12} >
                            <TextField
                                id="loteColorante"
                                name="loteColorante"
                                label="Lote de colorante"
                                fullWidth
                                variant="outlined"
                                value={lote.loteColorante}
                                onChange={handleChange} />
                        </Grid>
                        <Grid item xs={12} >
                            <TextField
                                id="loteCalcio"
                                name="loteCalcio"
                                label="Lote de calcio"
                                fullWidth
                                variant="outlined"
                                value={lote.loteCalcio}
                                onChange={handleChange} />
                        </Grid>
                        <Grid item xs={12} >
                            <TextField
                                id="loteCuajo"
                                name="loteCuajo"
                                label="Lote de cuajo"
                                fullWidth
                                variant="outlined"
                                value={lote.loteCuajo}
                                onChange={handleChange} />
                        </Grid>
                    </Grid>
                </DialogContent>
                <DialogActions>
                    <Button onClick={onClose}>Cancelar</Button>
                    <Button onClick={onSubmit}>Cargar Lote</Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}

export default CargarTrazabilidadDialog;