import SearchIcon from '@mui/icons-material/Search';
import { Button, Grid } from '@mui/material';
import Typography from '@mui/material/Typography';
import * as React from 'react';
import { useCallback, useRef } from 'react';
import * as field from "../resources/fields";
import * as message from "../resources/messages";
import * as validation from "../resources/validations";
import { Input } from "./Input";
import { Select } from "./Select";

export const SearchByWeeks = ({ fechaInicial, semanas, onSearch, title }) => {

    const refFecha = useRef(null);
    const refSemanas = useRef(null);

    const handleSearch = useCallback(() => {
        const errors = new Map();
        const values = {};

        refSemanas.current.validate(errors, values, [
            { func: validation.emptySelect, msg: message.valEmptyField }
        ])

        refFecha.current.validate(errors, values, [
            { func: validation.empty, msg: message.valEmptyFecha },
            { func: validation.olderDate, msg: message.valOlderDate }
        ])

        if (errors.size > 0) {
            console.error(errors)
            field.toastValidationErrors(errors)
            return
        }

        onSearch(values.toDate, values.cantidadSemanas)
    }, [onSearch])

    //--- Variables ---
    const semanasOptions = [
        { id: 1, value: 1, label: '1 Semana' },
        { id: 2, value: 2, label: '2 Semanas' },
        { id: 4, value: 4, label: '1 Mes' },
        { id: 8, value: 8, label: '2 Meses' },
        { id: 16, value: 16, label: '4 Meses' },
        { id: 24, value: 24, label: '6 Meses' },
        { id: 48, value: 48, label: '1 Año' },
    ]

    return (
        <Grid container spacing={2}>
            {title ? <Typography variant='h6'>{title}</Typography> : <></>}
            <Input
                ref={refFecha}
                id={field.backFechaHasta}
                label={field.fechaHasta}
                value={fechaInicial}
                type="date"
                required />
            <Select
                ref={refSemanas}
                value={semanas}
                id={field.backCantidadSemanas}
                label={field.cantidadSemanas}
                options={semanasOptions}
                required />
            <Grid item xs={12}>
                <Button
                    startIcon={<SearchIcon />}
                    variant='contained'
                    onClick={handleSearch}
                    fullWidth>
                    Consultar
                </Button>
            </Grid>
        </Grid>
    );
}