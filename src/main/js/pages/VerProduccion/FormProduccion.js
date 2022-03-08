import * as React from 'react';
import { ButtonGroup, Button, Grid, TextField, Typography } from "@mui/material";

export const FormProduccion = () => {
    return (
        <>
            <Grid item xs={12}>
                <Typography variant="h6">
                    Producción
                </Typography>
                <Typography variant="h7" color="GrayText">
                    Rango de fechas
                </Typography>
            </Grid>
            <Grid item xs={12} sm={6}>
                <TextField
                    id="fechaDesde"
                    name="fechaDesde"
                    label="Desde"
                    fullWidth
                    type="date"
                    variant="outlined"
                    InputLabelProps={{
                        shrink: true,
                    }}/>
            </Grid>
            <Grid item xs={12} sm={6}>
                <TextField
                    id="fechaHasta"
                    name="fechaHasta"
                    label="Hasta"
                    type="date"
                    fullWidth
                    variant="outlined"
                    InputLabelProps={{
                        shrink: true,
                    }}/>
            </Grid>
            <Grid item xs={12} alignSelf="right" mb={0.5}>
                <ButtonGroup fullWidth variant="contained">
                    <Button onClick={() => {
                    }} color="primary">Buscar</Button>
                </ButtonGroup>
            </Grid>
        </>
    )
}