import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Grid } from '@mui/material';
import * as React from 'react';
import { createRef, useEffect, useMemo, useState } from 'react';
import { Input } from "../../components/Input";
import * as field from "../../resources/fields";
import * as message from "../../resources/messages";
import * as validation from "../../resources/validations";

const quesoInicial = {
    id: '',
    codigo: '',
    tipoQueso: '',
    nomenclatura: ''
}

export const DialogCargarQueso = ({ isCargarQueso, isEditarQueso, onClose, onSubmit, queso }) => {

    const [quesoForm, setQuesoForm] = useState(quesoInicial);

    useEffect(() => setQuesoForm(queso), [queso, isCargarQueso]);

    const refCodigo = createRef()
    const refTipoQueso = createRef()
    const refNomenclatura = createRef()
    const refColor = createRef()

    const labelCargar = useMemo(() => isEditarQueso ? 'Actualizar Producto' : 'Cargar Producto', [isEditarQueso]);

    const onCargar = () => {
        const errors = new Map();
        const values = {};
        values["id"] = quesoForm.id

        refTipoQueso.current.validate(errors, values, [
            { func: validation.empty, msg: message.valEmptyField }
        ])

        refCodigo.current.validate(errors, values, [
            { func: validation.empty, msg: message.valEmptyField }
        ])

        refNomenclatura.current.validate(errors, values, [
            { func: validation.empty, msg: message.valEmptyField }
        ])

        refColor.current.setValue(values);

        if (errors.size > 0) {
            console.error(errors)
            field.toastValidationErrors(errors)
            return
        }

        onSubmit(values)
    }

    return (
        <>
            <Dialog open={isCargarQueso || isEditarQueso} onClose={onClose} scroll="body">
                <DialogTitle>Productos</DialogTitle>
                <DialogContent>
                    <DialogContentText paddingBottom={2}>
                        Ingrese los datos del producto
                    </DialogContentText>
                    <Grid container spacing={2}>
                        <Input
                            id={field.backTipoQueso}
                            label={field.tipoQueso}
                            ref={refTipoQueso}
                            value={quesoForm.tipoQueso}
                            type="text"
                            required/>
                        <Input
                            id={field.backNomenclatura}
                            label={field.nomenclatura}
                            ref={refNomenclatura}
                            value={quesoForm.nomenclatura}
                            type="text"
                            required/>
                        <Input
                            id={field.backCodigo}
                            label={field.codigo}
                            ref={refCodigo}
                            value={quesoForm.codigo}
                            required/>
                        <Input
                            id={field.backColor}
                            label={field.color}
                            ref={refColor}
                            value={quesoForm.color}
                            type="color"/>
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