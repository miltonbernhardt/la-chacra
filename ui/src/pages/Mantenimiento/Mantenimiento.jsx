import {Button, ButtonGroup, Grid, TextField} from "@mui/material";
import PageFormTable from '../../components/PageFormTable'
import MantenimientoCardList from "./MantenimientoCardList";

const Mantenimiento = () => {
    return (
        <PageFormTable
            titleForm={"Mantenimiento"}
            form={
                <>
                    <Grid container spacing={1.5}>
                        <Grid item xs={12}>
                            <TextField fullWidth
                                       variant="outlined"
                                       label="Fecha"
                                       type="date"
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField fullWidth
                                       variant="outlined"
                                       label="Equipo"/>
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                multiline
                                fullWidth
                                variant="outlined"
                                label="Mantenimiento"/>
                        </Grid>
                        <Grid item xs={12} mb={0.5}>
                            <ButtonGroup fullWidth variant="contained">
                                <Button color="info">Cancelar</Button>
                                <Button color="error">Borrar</Button>
                                <Button>Cargar</Button>
                            </ButtonGroup>
                        </Grid>
                    </Grid>
                </>
            }

            table={
                <MantenimientoCardList/>
            }
        ></PageFormTable>
    );
}

export default Mantenimiento;