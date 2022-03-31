import { Grid } from "@mui/material";
import { useCallback, useEffect, useMemo, useState } from 'react';
import toast from 'react-hot-toast';
import Loading from '../../components/Loading';
import { getRendimientoByDia, getRendimientoByQueso } from '../../services/RestServices';
import RendimientoCard from "./RendimientoCard";
import RendimientoChart from "./RendimientoChart";
import RendimientoQuesoCard from "./RendimientoQuesoCard";
import RendimientoSearch from "./RendimientoSearch";

const Rendimiento = () => {

    const [listaRendimientos, setListaRendimientos] = useState([]);
    const [rendimientosQuesos, setRendimientosQuesos] = useState([]);

    const [isLoadingRendimientos, setLoadingRendimientos] = useState(true);
    const [isLoadingRendimientoQueso, setLoadingRendimientoQueso] = useState(true)

    const fetchRendimientos = useCallback((fechaHasta, meses) => {
        const currentDate = new Date(fechaHasta);
        currentDate.setDate(currentDate.getDate() - Math.floor(30.5 * meses));
        const year = currentDate.getFullYear();
        const month = currentDate.getMonth();
        var date = currentDate.getDate();
        if (month === 1 && date === 28) date = 27;
        const fechaDesde = `${year}-${padTo2Digits(month + 1)}-${padTo2Digits(date + 1)}`;
        getRendimientoByDia(fechaDesde, fechaHasta)
            .then(({ data }) => {
                data.length > 16 ? setListaRendimientos(data) :
                    toast.error('No se poseen suficientes datos');
            })
            .catch(() => toast.error('No se pudo cargar rendimientos'))
            .finally(() => setLoadingRendimientos(false));
        getRendimientoByQueso(fechaDesde, fechaHasta)
            .then(({ data }) => {
                setRendimientosQuesos(data);
            })
            .catch(() => toast.error('No se pudo cargar rendimientos de quesos'))
            .finally(() => setLoadingRendimientoQueso(false));
    }, [])

    const padTo2Digits = (num) => {
        return num.toString().padStart(2, '0');
    }

    const fechaInicial = useMemo(() => {
        const currentDate = new Date();
        const year = currentDate.getFullYear();
        const month = currentDate.getMonth();
        const date = currentDate.getDate();
        return `${year}-${padTo2Digits(month + 1)}-${padTo2Digits(date,)}`;
    }, [])

    useEffect(() => {
        fetchRendimientos(fechaInicial, 1);
    }, [fechaInicial, fetchRendimientos])

    const handleSearch = useCallback((fechaHasta, meses) => {
        fetchRendimientos(fechaHasta, meses);
    }, [fetchRendimientos])

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

    const rendimientoMultilineFormatted = useMemo(() => {
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

    const rendimientoFormatted = useMemo(() => {
        return listaRendimientos.map((value, index) => {
            return {
                ...value,
                fecha: value.fecha.at(8) + value.fecha.at(9) + '-' +
                    value.fecha.at(5) + value.fecha.at(6)
            }
        })
    }, [listaRendimientos]);

    const domain = useMemo(() => {
        const dataArray = listaRendimientos.slice(-16);
        let bottom = 11;
        let top = 12;
        for (var i in dataArray) {
            if (bottom > dataArray[i].rendimiento) bottom = dataArray[i].rendimiento;
            if (top < dataArray[i].rendimiento) top = dataArray[i].rendimiento;
        }
        bottom = Math.ceil(bottom * 1) / 1
        top = Math.floor(top * 1) / 1
        return [bottom, top];
    }, [listaRendimientos])

    return (
        isLoadingRendimientoQueso ||
            isLoadingRendimientos ? <Loading /> :
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
                    <RendimientoSearch
                        fechaInicial={fechaInicial}
                        meses={1}
                        onSearch={handleSearch} />
                    <RendimientoCard
                        titulo="rendimiento"
                        valor={ultimoRendimientoPromedio}
                        descripcion="Rendimiento promedio de la semana" />
                    <RendimientoCard
                        titulo="rendimiento"
                        valor={rendimientoPromedio}
                        descripcion="Rendimiento promedio del período" />
                </Grid>
                {/*Chart*/}
                <Grid item container spacing={2}>
                    <RendimientoChart
                        title="Rendimiento"
                        yLabel="Rendimiento"
                        xLabel="Dias"
                        data={rendimientoMultilineFormatted}
                        xDataKey="dia"
                        dataKey="Ultimos 5 dias"
                        dataKey1="5 dias anteriores"
                        dataKey2="10 dias anteriores"
                        domain={domain}
                        legend={true} />
                    <RendimientoChart
                        title="Rendimiento"
                        yLabel="Rendimiento"
                        xLabel="Fecha"
                        data={rendimientoFormatted}
                        xDataKey="fecha"
                        dataKey="rendimiento"
                        domain={domain} />
                </Grid>
                <Grid item container spacing={2}>
                    {listaByQuesoFormatted.map((rendimiento) => (
                        <Grid item key={rendimiento.queso.id} xs={12} sm={6} md={4} lg={3} xl={2}>
                            <RendimientoQuesoCard queso={rendimiento.queso}
                                rendimiento={rendimiento.rendimiento} />
                        </Grid>
                    ))}
                </Grid>
            </Grid>
    );
}

export default Rendimiento;