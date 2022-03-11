import { Box, Grid, Container, Typography } from "@material-ui/core";
import { Autocomplete, Button, ButtonGroup, Stack, TextField } from "@mui/material";
import { maxWidth } from "@mui/system";
import { quesos } from "../../data/data";
import MantenimientoCard from "./MantenimientoCard";

const MantenimientoCardList = () => {

    const mantenimientoList = [
        { equipo: 'Equipo 1', fecha: '22-03-2021', mantenimiento: 'Se coloco un nuevo pico en la manguera que sale hace la boca del aspersor porque el otro ya estaba muy gastado' },
        { equipo: 'Equipo 2', fecha: '23-06-2021', mantenimiento: 'Se realizo la limpieza del interior de los tanques' },
        { equipo: 'Aparato 1', fecha: '10-05-2021', mantenimiento: 'mantenimiento preventivo' },
        { equipo: 'Computadora', fecha: '08-12-2021', mantenimiento: 'Se instalo nuevo software para mantener los equipos actualizados y seguros' },
    ];

    return (
        <>
            <Grid container
                style={{
                    justifyContent: "center",
                    alignItems: "center",
                    padding: 10
                }}
                spacing={2}>
                <Grid item xs={12} sm={2} >
                    <Typography variant='h6' style={{ paddingLeft: 2 }}>Historial</Typography>
                </Grid>
                <Grid item xs={12} sm={6}>
                    <TextField fullWidth />
                </Grid>
                <Grid item xs={12} sm={4} mb={0.5}>
                    <ButtonGroup variant="contained" fullWidth>
                        <Button variant="contained" fullWidth>Buscar</Button>
                    </ButtonGroup>
                </Grid>
            </Grid>
            <Grid item xs={12} alignSelf={"center"}>

                <Box sx={{ overflowY: 'auto', maxHeight: 800, padding: 2, }}>

                    <Container sx={{ py: 8, innerHeight: 800 }} style={{
                        backgroundColor: "#fafafa",
                    }} maxWidth="xl">
                        {/* End hero unit */}
                        <Grid container spacing={2}>
                            {mantenimientoList.map((card) => (
                                <Grid item key={card.id} xs={12}>
                                    <MantenimientoCard
                                        item={card} />
                                </Grid>
                            ))}
                        </Grid>
                    </Container>
                </Box>
            </Grid>
        </>
    );
}

export default MantenimientoCardList;