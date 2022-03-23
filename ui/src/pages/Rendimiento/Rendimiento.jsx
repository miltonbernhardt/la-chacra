import { Grid, Box, Paper } from "@mui/material";
import RendimientoCard from "./RendimientoCard";
import Chart from '../../components/Chart'
import RendimientoQuesoCard from "./RendimientoQuesoCard";
import { useState, useEffect, useCallback, useMemo } from 'react'
import { getRendimientoByDia, getRendimientoByQueso, getAllQuesos } from '../../services/RestServices'
import Loading from '../../components/Loading'
import toast from 'react-hot-toast'

const Rendimiento = () => {

    const [listaRendimientos, setListaRendimientos] = useState([]);
    const [rendimientosQuesos, setRendimientosQuesos] = useState([]);

    const [isLoadingRendimientos, setLoadingRendimientos] = useState(true);
    const [isLoadingRendimientoQueso, setLoadingRendimientoQueso] = useState(true)

    const fetchRendimientos = useCallback(() => {
        const currentDate = new Date();
        const today = `${currentDate.getFullYear()}-${currentDate.getMonth() + 1}-${currentDate.getDate()}`;
        const pastMonth = `${currentDate.getFullYear()}-${currentDate.getMonth()}-${currentDate.getDate()}`;
        const fechaDesde = '2020-01-01';
        const fechaHasta = '2021-12-12';
        getRendimientoByDia(fechaDesde, fechaHasta)//TODO change this
            .then(({ data }) => {
                setListaRendimientos(data);
            })
            .catch(() => toast.error('No se pudo cargar rendimientos'))
            .finally(() => setLoadingRendimientos(false));
        getRendimientoByQueso(fechaDesde, fechaHasta)//TODO change this
            .then(({ data }) => {
                setRendimientosQuesos(data);
            })
            .catch(() => toast.error('No se pudo cargar rendimientos de quesos'))
            .finally(() => setLoadingRendimientoQueso(false));
    }, [])

    useEffect(() => {
        fetchRendimientos();
    }, [fetchRendimientos])//TODO quitar hardcode


    // --- Variables ---

    const rendimientoPromedio = useMemo(() => {
        var sum = 0.0;
        for (var i in listaRendimientos) {
            sum = (Math.round((sum + listaRendimientos[i].rendimiento) * 100) / 100);
        }
        const rendimiento = (Math.round((sum / listaRendimientos.length) * 100) / 100);
        return rendimiento;
    }, [listaRendimientos])

    const ultimoRendimientoPromedio = useMemo(() => {
        var sum = 0.0;
        const ultimaSemana = listaRendimientos.slice(-5);
        for (var i in ultimaSemana) {
            sum = (Math.round((sum + ultimaSemana[i].rendimiento) * 100) / 100);
        }
        const rendimiento = (Math.round((sum / ultimaSemana.length) * 100) / 100);
        return rendimiento;
    }, [listaRendimientos])

    const listaByQuesoFormatted =
        rendimientosQuesos.map((item) => {
            return {
                ...item,
                rendimiento: (Math.round(item.rendimiento * 100) / 100)
            }
        })

    const rendimientoChartFormatted = useMemo(() => {
        const semana1 = listaRendimientos.slice(-16, -10);
        const semana2 = listaRendimientos.slice(-11, -5)
        const ultimaSemana = listaRendimientos.slice(-6);
        return ultimaSemana.map((value, index) => {
            return {
                dia: index,
                "10 dias anteriores": semana1.at(index).rendimiento,
                "5 dias anteriores": semana2.at(index).rendimiento,
                "Ultimos 5 dias": value.rendimiento
            }
        })
    }, [listaRendimientos]);

    return (
        isLoadingRendimientoQueso ||
            isLoadingRendimientos ? <Loading /> :
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
                    <RendimientoCard
                        titulo="rendimiento"
                        valor={ultimoRendimientoPromedio}
                        descripcion="Rendimiento promedio de la semana" />
                    <RendimientoCard
                        titulo="rendimiento"
                        valor={rendimientoPromedio}
                        descripcion="Rendimiento promedio del último mes" />
                </Grid>
                {/*Chart*/}
                <Grid item container spacing={2}>
                    <Grid item xs={12} md={6}>
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
                                            data={rendimientoChartFormatted}
                                            xDataKey="dia"
                                            dataKey="Ultimos 5 dias"
                                            dataKey1="5 dias anteriores"
                                            dataKey2="10 dias anteriores"
                                        />
                                    </Paper>
                                </Grid>
                            </Grid>
                        </Box>
                    </Grid>
                    <Grid item xs={12} md={6}>
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
                                            xLabel="Fecha"
                                            data={listaRendimientos}
                                            xDataKey="fecha"
                                            dataKey="rendimiento"
                                            dataKey1="rendimientoS2"
                                            dataKey2="rendimientoS3"
                                        />
                                    </Paper>
                                </Grid>
                            </Grid>
                        </Box>
                    </Grid>
                </Grid>{/*Chart*/}
                <Grid item container spacing={2}>
                    {listaByQuesoFormatted.map((rendimiento) => (
                        <Grid item key={rendimiento.queso.id} xs={12} sm={6} md={4} lg={3} xl={2}>
                            <RendimientoQuesoCard queso={rendimiento.queso}//nombre de queso
                                rendimiento={rendimiento.rendimiento} />
                        </Grid>
                    ))}
                </Grid>
            </Grid>
    );
}

export default Rendimiento;