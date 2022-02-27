import { tiposDeQueso } from "../../data/data";
import { Autocomplete, ButtonGroup, Button, Grid, TextField } from "@mui/material";

//todo replace components for reusable components
const FormExpedicion = () => {
    return (
        <>
            <Grid container spacing={1.5}>
                <Grid item xs={12}>
                    <Autocomplete
                        disablePortal
                        id="combo-tipoQueso"
                        options={tiposDeQueso}
                        renderInput={(params) => <TextField {...params} label="Cliente" />}
                    /></Grid>
                <Grid item xs={12}>
                    <TextField
                        id="numeroLote"
                        name="numeroLote"
                        label="Lote"
                        fullWidth
                        variant="outlined" />
                </Grid>
                <Grid item xs={12} sm={6}>
                    <TextField
                        id="cantidad"
                        name="cantidad"
                        label="Cantidad"
                        fullWidth
                        type="number"
                        variant="outlined" />
                </Grid>
                <Grid item xs={12} sm={6}>
                    <TextField
                        id="pesoExpedicion"
                        name="pesoExpedicion"
                        label="Peso"
                        fullWidth
                        type="number"
                        variant="outlined" />
                </Grid>
                <Grid item xs={12}>
                    <TextField
                        id="importeExpedicion"
                        name="importeExpedicion"
                        label="Importe"
                        fullWidth
                        type="number"
                        variant="outlined" />
                </Grid>
                <Grid item xs={12}>
                    <TextField
                        id="fechaExpedicion"
                        name="fechaExpedicion"
                        label="Fecha de Expedicion"
                        fullWidth
                        type="date"
                        variant="outlined"
                        InputLabelProps={{
                            shrink: true,
                        }}
                    />
                </Grid>
                <Grid item xs={12} alignSelf="right" mb={0.5}>
                    <ButtonGroup fullWidth variant="contained">
                        <Button onClick={() => { }} color="primary">Cargar Expedición</Button>
                    </ButtonGroup>
                </Grid>
            </Grid>
        </>
    )
}

export default FormExpedicion