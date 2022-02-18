import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Grid, TextField } from '@mui/material';
import { useEffect } from 'react';
import { useState } from 'react';
import { useCallback } from 'react';
import toast from 'react-hot-toast';
const quesoInicial = {
    id: '',
    codigo: '',
    tipoQueso: '',
    nomenclatura: ''
}

const CargarProductoDialog = ({ open, onClose, onSubmit, queso }) => {

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

        //todo mover estas constantes a un archivo aparte
        const fieldTipoQueso = "Nombre"
        const fieldCodigo = "Código"
        const fieldNomenclatura = "Nomenclatura"

        const valEmpty = "No puede estar vacío"

        if (quesoForm.tipoQueso === '') {
            errors.set(fieldTipoQueso, valEmpty)
        }

        if (quesoForm.nomenclatura === '') {
            errors.set(fieldNomenclatura, valEmpty)
        }

        if (quesoForm.codigo === '') {
            errors.set(fieldCodigo, valEmpty)
        }

        if (errors.size > 0) {
            errors.forEach(function (msg, field) {
                console.log(`${field}: ${msg}`)
                toast.error(`${field}: ${msg}`)
            })
            return false;
        }
        return true;
    }, [quesoForm]);

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