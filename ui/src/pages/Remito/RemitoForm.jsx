import { Autocomplete, Button, Grid, TextField, Typography } from "@mui/material";
import { tiposDeQueso } from "../../data/data";

const RemitoForm = () => {
    return (
        <Grid container spacing={1.5}>
            <Grid item xs={12}>
                <Typography variant="h7" color="GrayText">
                    Datos del Remito
                </Typography>
            </Grid>
            <Grid item xs={12}>
                <Autocomplete
                    disablePortal
                    id="combo-tipoQueso"
                    options={tiposDeQueso}
                    renderInput={(params) => <TextField {...params} label="Cliente" />}
                /></Grid>
            <Grid item xs={12}>
                <TextField
                    id="fechaRemito"
                    name="fechaRemito"
                    label="Fecha de Remito"
                    fullWidth
                    type="date"
                    variant="outlined"
                    InputLabelProps={{
                        shrink: true,
                    }}
                />
            </Grid>
            <Grid item xs={12}>
                <TextField
                    id="importeRemito"
                    name="importeRemito"
                    label="Importe Total"
                    fullWidth
                    type="number"
                    variant="outlined" />
            </Grid>
            <Grid item xs={12} alignSelf="center" mb={0.5}>
                <Button variant="contained" fullWidth>Emitir Remito</Button>
            </Grid>
        </Grid>
    );
}

export default RemitoForm;