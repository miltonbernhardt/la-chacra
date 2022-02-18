import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Grid, TextField } from '@mui/material';
import { useCallback, useEffect, useState } from 'react';
import { toastValidationErrors } from "../../fields";
import * as message from "../../messages";

const quesoInicial = {
    id: '',
    codigo: '',
    tipoQueso: '',
    nomenclatura: ''
}

const CargarProductoDialog = ({ open, onClose, onSubmit, queso }) => {

    const fieldCodigo = "Código"
    const fieldNomenclatura = "Nomenclatura"
    const fieldTipoQueso = "Tipo queso"
    const [quesoForm, setQuesoForm] = useState(quesoInicial);

    useEffect(() => setQuesoForm(queso), [queso]);

    const handleChange = evt => {
        const attribute = evt.target.name;
        const value = evt.target.value;
        if (evt.target.validity.valid) {
            const newQueso = { ...quesoForm, [attribute]: value };
            setQuesoForm(newQueso);
        };
    }


    const validar = useCallback(() => {
        const errors = new Map();

        if (quesoForm.codigo === '')
            errors.set(fieldCodigo, message.valEmptyField)

        if (quesoForm.nomenclatura === '')
            errors.set(fieldNomenclatura, message.valEmptyField)

        if (quesoForm.tipoQueso === '')
            errors.set(fieldTipoQueso, message.valEmptyField)

        if (errors.size > 0) {
            console.error({ errors })
            toastValidationErrors(errors)
            return false;
        }
        return true;
    }, [quesoForm.codigo, quesoForm.nomenclatura, quesoForm.tipoQueso]);

    const onCargar = useCallback(() => {
        if (validar()) onSubmit(quesoForm);
    }, [validar, onSubmit, quesoForm]);

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
                                value={quesoForm.tipoQueso}
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
                                value={quesoForm.nomenclatura}
                                onChange={handleChange} />
                        </Grid>
                        <Grid item xs={12} >
                            <TextField
                                id="codigo"
                                name="codigo"
                                label="Código"
                                fullWidth
                                variant="outlined"
                                value={quesoForm.codigo}
                                onChange={handleChange} />
                        </Grid>
                    </Grid>
                </DialogContent>
                <DialogActions>
                    <Button onClick={onClose}>Cancelar</Button>
                    <Button onClick={onCargar}>Cargar Producto</Button>
                </DialogActions>
            </Dialog>
        </>
    );
}

export default CargarProductoDialog;