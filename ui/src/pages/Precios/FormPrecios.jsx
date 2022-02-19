import { tiposDeCliente, tiposDeQueso } from "../../data/data";
import { Autocomplete, Button, Grid, TextField, Typography, ButtonGroup } from "@mui/material";

const FormPrecios = () => {
    return (
        <>
            <Grid container spacing={2}>
                <Grid item xs={12}>
                    <Typography variant="h6">
                        Ingreso de precios
                    </Typography>
                </Grid>
                <Grid item xs={12}>
                    <Autocomplete
                        disablePortal
                        id="combo-tipoQueso"
                        options={tiposDeQueso}
                        renderInput={(params) => <TextField {...params} label="Queso"/>}
                    />
                </Grid>
                <Grid item xs={12}>
                    <Autocomplete
                        disablePortal
                        id="combo-tipoCliente"
                        options={tiposDeCliente}
                        renderInput={(params) => <TextField {...params} label="Cliente"/>}
                    />
                </Grid>
                <Grid item xs={12}>
                    <TextField
                        id="precio"
                        name="precio"
                        label="Precio"
                        fullWidth
                        type="number"
                        numeric
                        variant="outlined"/>
                </Grid>
                <Grid item xs={12} alignSelf="right" mb={0.5}>
                    <ButtonGroup fullWidth variant="contained">
                        <Button onClick={() => {}} color="primary">Cargar Precio</Button>
                    </ButtonGroup>
                </Grid>
            </Grid>
        </>
    )
}

export default FormPrecios