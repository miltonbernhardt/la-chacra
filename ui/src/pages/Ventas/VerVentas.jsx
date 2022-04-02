import { Grid } from "@mui/material";
import { useCallback, useEffect, useMemo, useState } from 'react';
import toast from 'react-hot-toast';
import Loading from "../../components/Loading";
import { getVentas, getAllQuesos } from "../../services/RestServices";
import ChartVentas from "./ChartVentas";
import GridVentas from "./GridVentas";
import SearchVentas from "./SearchVentas";
import VentasByQueso from "./VentasByQueso";
import { getValidDate } from "../../resources/utils";

const VerVentas = () => {

    const [listaVentas, setListaVentas] = useState([]);
    const [listaQuesos, setListaQuesos] = useState([]);

    const [isLoadingVentas, setLoadingVentas] = useState(true);
    const [isLoadingQuesos, setLoadingQuesos] = useState(true)

    const fetchQuesos = () => {
        getAllQuesos()
            .then(quesos => {
                const listaAux = quesos.data.map((q) => {
                    return {
                        id: q.id,
                        codigo: q.codigo,
                        nomenclatura: q.nomenclatura,
                        tipoQueso: q.tipoQueso,
                        color: q.color
                    }
                })
                setListaQuesos(listaAux)
            })
            .catch(() => toast.error("No se pudo cargar quesos"))
            .finally(() => setLoadingQuesos(false));
    }

    const fetchVentas = useCallback((fechaHasta, meses) => {
        const currentDate = new Date(fechaHasta);
        currentDate.setDate(currentDate.getDate() - Math.floor(30.5 * meses));
        const year = currentDate.getFullYear();
        const month = currentDate.getMonth();
        var date = currentDate.getDate();

        date = getValidDate(date, month, year);

        const fechaDesde = `${year}-${padTo2Digits(month + 1)}-${padTo2Digits(date)}`;
        getVentas(fechaDesde, fechaHasta)
            .then(({ data }) => {
                setListaVentas(data)
                if (data.length < 16) toast.error('No se poseen suficientes datos');
            })
            .catch(() => toast.error('No se pudo cargar ventas'))
            .finally(() => setLoadingVentas(false));
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
        fetchVentas(fechaInicial, 1);
        fetchQuesos();
    }, [fechaInicial, fetchVentas])

    const handleSearch = useCallback((fechaHasta, meses) => {
        fetchVentas(fechaHasta, meses);
    }, [fetchVentas])

    // --- Variables ---
    const ventasFormatted = useMemo(() => {
        if (isLoadingQuesos || isLoadingVentas) return [];
        return listaVentas.map((dia, index) => {
            const ventas = dia.ventas;
            const ventasAsList = ventas.map((v) => {
                const tipoQueso = listaQuesos.filter(q => q.codigo === v.codigoQueso).pop().tipoQueso;
                return [tipoQueso, v.cantidad]
            });
            const ventasAsProps = Object.fromEntries(ventasAsList);
            return {
                id: index,
                fecha: dia.fecha.at(8) + dia.fecha.at(9) + '-' +
                    dia.fecha.at(5) + dia.fecha.at(6),
                total: dia.total,
                ...ventasAsProps
            }
        })
    }, [isLoadingQuesos, isLoadingVentas, listaQuesos, listaVentas])

    const quesosFormatted = useMemo(() =>
        listaQuesos.map((q) => {
            return {
                id: q.id,
                label: q.codigo + ' - ' + q.tipoQueso + ' - ' + q.nomenclatura,
                value: q.tipoQueso
            }
        }), [listaQuesos]);

    const ventasMultilineFormatted = useMemo(() => {
        if (listaVentas.length < 16) return [];
        const semana1 = listaVentas.slice(-16, -10);
        const semana2 = listaVentas.slice(-11, -5)
        const ultimaSemana = listaVentas.slice(-6);
        return ultimaSemana.map((value, index) => {
            return {
                dia: index,
                "10 dias anteriores": semana1.at(index).total,
                "5 dias anteriores": semana2.at(index).total,
                "Ultimos 5 dias": value.total
            }
        })
    }, [listaVentas]);

    return (
        isLoadingVentas || isLoadingQuesos ? <Loading /> :
            <Grid container
                direction="row"
                spacing={1.5}
                paddingRight={2}
                style={{
                    minHeight: "92%",
                    maxWidth: "98%",
                    margin: "1%",
                    boxSizing: "border-box",
                }}>
                <Grid item container spacing={2}>
                    <SearchVentas
                        fechaInicial={fechaInicial}
                        meses={1}
                        onSearch={handleSearch} />
                    <ChartVentas
                        md={5}
                        title="Ventas totales"
                        data={ventasFormatted}
                        xDataKey="fecha"
                        dataKey="total" />
                    <ChartVentas
                        md={5}
                        title="Ventas totales"
                        data={ventasMultilineFormatted}
                        xDataKey="fecha"
                        dataKey="Ultimos 5 dias"
                        dataKey1="5 dias anteriores"
                        dataKey2="10 dias anteriores"
                        legend={true} />
                </Grid>
                <Grid item container spacing={2}>
                    <VentasByQueso
                        listaVentas={ventasFormatted}
                        quesosSelect={quesosFormatted} />
                </Grid>
                <GridVentas
                    quesos={listaQuesos}
                    data={ventasFormatted} />
            </Grid>
    );
}

export default VerVentas;