import {
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    Grid,
    TextField
} from '@mui/material';
import {useCallback, useEffect, useState, useMemo} from 'react';
import {toastValidationErrors} from "../../fields";
import * as message from "../../messages";
import * as field from "../../fields";

const quesoInicial = {
    id: '',
    codigo: '',
    tipoQueso: '',
    nomenclatura: ''
}

const CargarProductoDialog = ({isCargarQueso, isEditarQueso, onClose, onSubmit, queso}) => {//todo queso

    const [quesoForm, setQuesoForm] = useState(quesoInicial);

    useEffect(() => setQuesoForm(queso), [queso, isCargarQueso]);

    const handleChange = evt => {
        const attribute = evt.target.name;
        const value = evt.target.value;
        if (evt.target.validity.valid) {
            const newQueso = {...quesoForm, [attribute]: value};
            setQuesoForm(newQueso);
        }
    }

    const labelCargar = useMemo(() => {
        return isEditarQueso ? 'Actualizar queso' : 'Cargar queso'
    }, [isEditarQueso]);

    const validateQueso = useCallback(() => {
        const errors = new Map();

        if (quesoForm.tipoQueso === '')
            errors.set(field.tipoQueso, message.valEmptyField)

        if (quesoForm.nomenclatura === '')
            errors.set(field.nomenclatura, message.valEmptyField)

        if (quesoForm.codigo === '')
            errors.set(field.codigo, message.valEmptyField)
        else if (quesoForm.codigo.length !== 3)
            errors.set(field.codigo, message.val3Characters)

        if (errors.size > 0) {
            console.error(errors)
            toastValidationErrors(errors)
            return false;
        }
        return true;
    }, [quesoForm.codigo, quesoForm.nomenclatura, quesoForm.tipoQueso]);

    const onCargar = useCallback(() => {
        if (validateQueso()) {
            onSubmit(quesoForm);
        }
    }, [validateQueso, onSubmit, quesoForm]);

    return (
        <>
            <Dialog open={isCargarQueso || isEditarQueso} onClose={onClose} scroll="body">
                <DialogTitle>Productos</DialogTitle>
                <DialogContent>
                    <DialogContentText paddingBottom={2}>
                        Ingrese los datos del producto
                    </DialogContentText>
                    <Grid container spacing={2}>
                        <Grid item xs={12}>
                            <TextField
                                id="tipoQueso"
                                name="tipoQueso"
                                label="Tipo de queso"
                                fullWidth
                                variant="outlined"
                                value={quesoForm.tipoQueso}
                                onChange={handleChange}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                id="nomenclatura"
                                name="nomenclatura"
                                label="Nomenclatura"
                                fullWidth
                                variant="outlined"
                                value={quesoForm.nomenclatura}
                                onChange={handleChange}/>
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                id="codigo"
                                name="codigo"
                                label="Código"
                                fullWidth
                                variant="outlined"
                                value={quesoForm.codigo}
                                onChange={handleChange}/>
                        </Grid>
                    </Grid>
                </DialogContent>
                <DialogActions>
                    <Button onClick={onClose}>Cancelar</Button>
                    <Button onClick={onCargar}>{labelCargar}</Button>
                </DialogActions>
            </Dialog>
        </>
    );
}

export default CargarProductoDialog;