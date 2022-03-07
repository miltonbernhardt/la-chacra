import { ButtonGroup, Button, Grid, TextField, Typography } from "@mui/material";

const FormTrazabilidad = () => {
    return (
        <Grid container spacing={1.5}>
            <Grid item xs={12}>
                <Typography variant="h7" color="GrayText">
                    Buscar Lote
                </Typography>
            </Grid>
            <Grid item xs={12}>
                <TextField
                    id="numeroLote"
                    name="numeroLote"
                    label="Lote"
                    fullWidth
                    type="text"
                    variant="outlined" />
            </Grid>
            <Grid item xs={12}>
                <Typography variant="h7" color="GrayText">
                    Información del Lote
                </Typography>
            </Grid>
            <Grid item xs={12} sm={6}>
                <TextField
                    id="tipoQueso"
                    name="tipoQueso"
                    label="Tipo de Queso"
                    fullWidth
                    contentEditable={false}
                    variant="outlined" />
            </Grid>
            <Grid item xs={12} sm={6}>
                <TextField
                    id="litrosProcesados"
                    name="litrosProcesados"
                    label="Litros procesados"
                    fullWidth
                    variant="outlined" />
            </Grid>
            <Grid item xs={12} sm={6}>
                <TextField
                    id="tina"
                    name="tina"
                    label="Tina"
                    fullWidth
                    variant="outlined" />
            </Grid>
            <Grid item xs={12} sm={6}>
                <TextField
                    id="cantidadHormas"
                    name="cantidadHormas"
                    label="Cantidad de hormas"
                    fullWidth
                    type="number"
                    variant="outlined" />
            </Grid>
            <Grid item xs={12} sm={6}>
                <TextField
                    id="pesoLote"
                    name="pesoLote"
                    label="Peso del lote"
                    fullWidth
                    type="number"
                    variant="outlined" />
            </Grid>
            <Grid item xs={12} sm={6}>
                <TextField
                    id="fechaLote"
                    name="fechaLote"
                    label="Fecha de producción"
                    fullWidth
                    type="date"
                    variant="outlined"
                    InputLabelProps={{
                        shrink: true,
                    }}
                />
            </Grid>
            <Grid item xs={12} sm={6}>
                <TextField
                    id="cultivo"
                    name="cultivo"
                    label="Cultivo"
                    fullWidth
                    type="number"
                    variant="outlined" />
            </Grid>
            <Grid item xs={12} sm={6}>
                <TextField
                    id="colorante"
                    name="colorante"
                    label="Colorante"
                    fullWidth
                    type="number"
                    variant="outlined" />
            </Grid>
            <Grid item xs={12} sm={6}>
                <TextField
                    id="calcio"
                    name="calcio"
                    label="Calcio"
                    fullWidth
                    type="number"
                    variant="outlined" />
            </Grid>
            <Grid item xs={12} sm={6}>
                <TextField
                    id="cuajo"
                    name="cuajo"
                    label="Cuajo"
                    fullWidth
                    type="number"
                    variant="outlined" />
            </Grid>
            <Grid item xs={12} alignSelf="right">
                <ButtonGroup fullWidth variant="contained">
                    <Button
                        onClick={() => {
                        }}
                        color="primary">Buscar Lote</Button>
                </ButtonGroup>
            </Grid>
        </Grid>
    )
}

export default FormTrazabilidad