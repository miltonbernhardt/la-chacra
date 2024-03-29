import { Button, ButtonGroup, Grid } from "@mui/material";
import * as React from 'react';
import { useCallback, useEffect, useMemo, useRef, useState } from 'react';
import { Input } from '../../components/Input';
import { Select } from '../../components/Select';
import * as field from '../../resources/fields';
import * as message from "../../resources/messages";
import * as validation from "../../resources/validations";
import CancelIcon from '@mui/icons-material/Cancel';
import AddIcon from '@mui/icons-material/Add';
import AutorenewIcon from '@mui/icons-material/Autorenew';

const precioInicial = {
    id: '',
    valor: '',
    idTipoCliente: '',
    idQueso: ''
}

export const FormPrecios = ({ precio, quesos, clientes, handleSubmit, handleCancelar, isEditing }) => {

    const [precioForm, setPrecioForm] = useState(precioInicial);

    const refPrecio = useRef(null);
    const refSelectCliente = useRef(null);
    const refSelectQueso = useRef(null);

    useEffect(() => {
        setPrecioForm(precio)
    }, [precio])

    const handleCargar = useCallback(() => {
        const errors = new Map();
        const values = {};
        values["id"] = precioForm.id

        refSelectQueso.current.validate(errors, values, [
            { func: validation.emptySelect, msg: message.valEmptyField }
        ])

        refSelectCliente.current.validate(errors, values, [
            { func: validation.emptySelect, msg: message.valEmptyField }
        ])

        refPrecio.current.validate(errors, values, [
            { func: validation.minorToOne, msg: message.valZeroValue }])

        if (errors.size > 0) {
            console.error(errors)
            field.toastValidationErrors(errors)
            return
        }

        handleSubmit(values)
    }, [handleSubmit, precioForm.id]);

    // --- Variables

    const labelCargar = useMemo(() => isEditing ? 'Actualizar Precio' : 'Cargar Precio', [isEditing]);
    const iconCargar = useMemo(() => isEditing ? <AutorenewIcon /> : <AddIcon />, [isEditing])

    return <Grid container spacing={1.5}>
        <Select ref={refSelectQueso}
            value={precioForm.idQueso}
            id={field.backIdQueso}
            label={field.queso}
            options={quesos}
            disabled={isEditing}
            required />
        <Select ref={refSelectCliente}
            value={precioForm.idTipoCliente}
            id={field.backIdTipoCliente}
            label={field.idTipoCliente}
            options={clientes}
            disabled={isEditing}
            required />
        <Input ref={refPrecio}
            id={field.backPrecio}
            label={field.precio}
            value={precioForm.valor}
            required />
        <Grid item xs={12} alignSelf="right" mb={0.5}>
            <ButtonGroup fullWidth variant="contained">
                <Button onClick={handleCancelar} disabled={!isEditing} color="info" startIcon={<CancelIcon />}>Cancelar</Button>
                <Button onClick={handleCargar} color="primary" startIcon={iconCargar}>{labelCargar}</Button>
            </ButtonGroup>
        </Grid>
    </Grid>
}