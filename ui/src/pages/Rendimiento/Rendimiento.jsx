import {Box, Grid, Paper} from "@mui/material";
import RendimientoCard from "./RendimientoCard";
import Chart from '../../components/Chart'
import {quesos} from "../../data/data";
import RendimientoQuesoCard from "./RendimientoQuesoCard";

const Rendimiento = () => {

    const data = [
        {dia: 1, rendimientoS1: 13, rendimientoS2: 12.7, rendimientoS3: 13.2},
        {dia: 2, rendimientoS1: 12, rendimientoS2: 13.1, rendimientoS3: 13.7},
        {dia: 3, rendimientoS1: 14, rendimientoS2: 13.5, rendimientoS3: 12.8},
        {dia: 4, rendimientoS1: 13.5, rendimientoS2: 14.3, rendimientoS3: 12.9},
        {dia: 5, rendimientoS1: 13.75, rendimientoS2: 14.2, rendimientoS3: 13.5},
        {dia: 6, rendimientoS1: 12.8, rendimientoS2: 14, rendimientoS3: 13.8},
        {dia: 7, rendimientoS1: 14, rendimientoS2: 13.7, rendimientoS3: 13},
        {dia: 8, rendimientoS1: 13.7, rendimientoS2: 13.9, rendimientoS3: 13.9},
        {dia: 9, rendimientoS1: 13.2, rendimientoS2: 13, rendimientoS3: 14},
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
                        descripcion="Rendimiento promedio de las últimas 3 semanas"
                    />
                </Grid>
            </Grid>
            {/*Chart*/}
            <Grid item container spacing={2}>
                <Grid item xs={12}>
                    <Box
                        sx={{
                            display: 'flex',
                            flexDirection: 'column',
                            alignItems: 'center',
                        }}
                    >
                        <Grid container spacing={2}>
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
                                        yLabel="Rendimiento"
                                        xLabel="Dias"
                                        data={data}
                                        xDataKey="dia"
                                        dataKey="rendimientoS1"
                                        dataKey1="rendimientoS2"
                                        dataKey2="rendimientoS3"
                                    />
                                </Paper>
                            </Grid>
                        </Grid>
                    </Box>
                </Grid>
            </Grid>
            <Grid item container spacing={2}>
                {quesos.map((card) => (
                    <Grid item key={card.codigo} xs={12} sm={6} md={4} lg={3} xl={2}>
                        <RendimientoQuesoCard queso={card}//nombre de queso
                                              stockQueso={100}/>
                    </Grid>
                ))}
            </Grid>
        </Grid>
    );
}

export default Rendimiento;