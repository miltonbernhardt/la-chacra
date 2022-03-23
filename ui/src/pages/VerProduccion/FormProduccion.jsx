import { ButtonGroup, Button, Grid, TextField, Typography } from "@mui/material";
import { useRef, useState } from "react";
import Input from '../../components/Input'
import toast from 'react-hot-toast'
import * as message from "../../resources/messages";
import * as field from "../../resources/fields";
import validator from 'validator';
import * as validation from "../../resources/validations";
import { toastValidationErrors } from "../../resources/fields";

const FormProduccion = ({ onBuscar, initialDate }) => {

    const refFechaDesde = useRef(null);
    const refFechaHasta = useRef(null);

    const handleBuscar = () => {
        const errors = new Map();
        const values = {};

        refFechaDesde.current.validate(errors, values, [
            { func: validation.empty, msg: message.valEmptyFecha }
        ])
        refFechaHasta.current.validate(errors, values, [
            { func: validation.empty, msg: message.valEmptyFecha }
        ])

        if (errors.size > 0) {
            console.error(errors)
            toastValidationErrors(errors)
            return
        }

        if (validator.isBefore(values.fechaHasta, values.fechaDesde))
            toast.error('El rango de fechas no es válido');
        else {
            onBuscar(values.fechaDesde, values.fechaHasta);
        }
    }

    return (
        <Grid container spacing={1.5}>
            <Grid item xs={12}>
                <Typography variant="h7" color="GrayText">
                    Rango de fechas
                </Typography>
            </Grid>
            <Input ref={refFechaDesde}
                id={'fechaDesde'}
                label={field.fechaDesde}
                value={initialDate}
                type="date"
                required />
            <Input ref={refFechaHasta}
                id={'fechaHasta'}
                label={field.fechaHasta}
                value={initialDate}
                type="date"
                required />

            <Grid item xs={12} alignSelf="right" mb={0.5}>
                <ButtonGroup fullWidth variant="contained">
                    <Button color="primary" onClick={handleBuscar}>Buscar</Button>
                </ButtonGroup>
            </Grid>
        </Grid >
    )
}

export default FormProduccion