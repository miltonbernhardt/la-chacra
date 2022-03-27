import SearchIcon from '@mui/icons-material/Search';
import { Button, Grid, Paper, Stack } from '@mui/material';
import Typography from '@mui/material/Typography';
import * as React from 'react';
import { useCallback, useRef } from 'react';
import { Input } from "../../components/Input";
import { Select } from "../../components/Select";
import * as field from "../../resources/fields";
import * as message from "../../resources/messages";
import * as validation from "../../resources/validations";

export const RendimientoSearch = ({ fechaInicial, meses, onSearch }) => {

    const refFecha = useRef(null);
    const refMeses = useRef(null);

    const handleSearch = useCallback(() => {
        const errors = new Map();
        const values = {};

        refMeses.current.validate(errors, values, [
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

        onSearch(values.toDate, values.cantidadMeses)
    }, [onSearch])

    //--- Variables ---
    const mesesOptions = [
        { id: 1, value: 1, label: '1' },
        { id: 2, value: 2, label: '2' },
        { id: 3, value: 3, label: '3' },
        { id: 6, value: 6, label: '6' },
        { id: 12, value: 12, label: '12' },
    ]

    return (
        <Grid item xs={12} sm={12} md={4} spacing={2}>
            <Paper
                sx={{
                    p: 2,
                    display: 'flex',
                    flexDirection: 'column',
                    height: '100%',
                    width: '100%'
                }}>
                <Stack minHeight={300} sx={{ height: '100%' }} direction="column" justifyContent="space-between">
                    <Typography variant='h6'>Consultar producción</Typography>
                    <Grid container spacing={2}>
                        <Input
                            ref={refFecha}
                            id={field.backFechaHasta}
                            label={field.fechaHasta}
                            value={fechaInicial}
                            type="date"
                            required/>
                        <Select
                            ref={refMeses}
                            value={meses}
                            id={field.backCantidadMeses}
                            label={field.cantidadMeses}
                            options={mesesOptions}
                            required/>
                    </Grid>
                    <Button
                        startIcon={<SearchIcon/>}
                        variant='contained'
                        onClick={handleSearch}
                        fullWidth>
                        Consultar
                    </Button>
                </Stack>
            </Paper>
        </Grid>
    );
}