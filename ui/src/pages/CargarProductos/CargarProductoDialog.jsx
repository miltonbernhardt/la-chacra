import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Grid, TextField } from '@mui/material';

const CargarProductoDialog = ({ open, onClose, onSubmit, queso, updateStateQueso }) => {

    const handleChange = evt => {
        const nombreAtributo = evt.target.name;
        const valorAtributo = evt.target.value;
        if (evt.target.validity.valid) updateStateQueso(nombreAtributo, valorAtributo);
    }

    return (
        <>
            <Dialog open={open} onClose={onClose} scroll="body">
                <DialogTitle>Productos</DialogTitle>
                <DialogContent>
                    <DialogContentText paddingBottom={2}>
                        Ingrese los datos del producto
                    </DialogContentText>
                    <Grid container spacing={2}>
                        <Grid item xs={12} >
                            <TextField
                                id="tipoQueso"
                                name="tipoQueso"
                                label="Nombre"
                                fullWidth
                                variant="outlined"
                                value={queso.tipoQueso}
                                onChange={handleChange}
                            />
                        </Grid>
                        <Grid item xs={12} >
                            <TextField
                                id="nomenclatura"
                                name="nomenclatura"
                                label="Nomenclatura"
                                fullWidth
                                variant="outlined"
                                value={queso.nomenclatura}
                                onChange={handleChange} />
                        </Grid>
                        <Grid item xs={12} >
                            <TextField
                                id="codigo"
                                name="codigo"
                                label="Código"
                                fullWidth
                                variant="outlined"
                                value={queso.codigo}
                                onChange={handleChange} />
                        </Grid>
                    </Grid>
                </DialogContent>
                <DialogActions>
                    <Button onClick={onClose}>Cancelar</Button>
                    <Button onClick={onSubmit}>Cargar Producto</Button>
                </DialogActions>
            </Dialog>
        </>);
}

export default CargarProductoDialog;