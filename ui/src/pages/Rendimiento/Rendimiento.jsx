import { Grid, Box, Paper } from "@mui/material";
import RendimientoCard from "./RendimientoCard";
import Chart from '../../components/Chart'
const Rendimiento = () => {

    const data = [
        { semana: 1, rendimiento: 13 },
        { semana: 2, rendimiento: 12 },
        { semana: 3, rendimiento: 14 },
        { semana: 4, rendimiento: 13.5 },
        { semana: 5, rendimiento: 13.75 },
        { semana: 6, rendimiento: 12.8 },
        { semana: 7, rendimiento: 14 },
        { semana: 8, rendimiento: 13.7 },
        { semana: 9, rendimiento: 13.2 },
    ]

    return (
        // Rendimiento
        // Rendimiento promedio de la semana
        // Rendimiento promedio de las tres ultimas semanas (en grafico por dias)
        // (a este ultimo lo hacemos tambien por quesos, pero con indicadores)
        <Grid container
            direction="row"
            spacing={2}
            paddingRight={2}
            style={{
                minHeight: "92%",
                maxWidth: "98%",
                margin: "1%",
                boxSizing: "border-box",
            }}>
            <Grid item container spacing={2}>
                <Grid item xs={12} sm={6}>
                    <RendimientoCard
                        titulo="rendimiento"
                        valor="13.5"
                        descripcion="Rendimiento promedio de la semana"
                    />

                </Grid>
                <Grid item xs={12} sm={6}>
                    <RendimientoCard
                        titulo="rendimiento"
                        valor="12.8"
                        descripcion="Rendimiento promedio de las ultimas 3 semanas"
                    />
                </Grid>
            </Grid>
            <Grid item container spacing={2}>
                <Grid item xs={12} >
                    <Box
                        sx={{
                            // marginTop: 8,
                            display: 'flex',
                            flexDirection: 'column',
                            alignItems: 'center',
                            // mt: 1,
                            // padding: 1
                        }}
                    >
                        <Grid container spacing={2}>
                            {/* Chart */}
                            <Grid item xs={12}>
                                <Paper
                                    sx={{
                                        p: 2,
                                        display: 'flex',
                                        flexDirection: 'column',
                                        height: 300,
                                    }}
                                >
                                    <Chart
                                        title="Rendimiento"
                                        yLabel="Cantidad"
                                        data={data}
                                        xDataKey="semana"
                                        dataKey="rendimiento"
                                    />
                                </Paper>
                            </Grid>
                        </Grid>
                    </Box>
                </Grid>

            </Grid>
        </Grid>
    );
}

export default Rendimiento;