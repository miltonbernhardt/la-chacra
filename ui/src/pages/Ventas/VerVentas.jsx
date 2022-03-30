import { Grid } from "@mui/material";
import { useCallback, useEffect, useMemo, useState } from 'react';
import toast from 'react-hot-toast';
import Loading from "../../components/Loading";
import { getVentas, getAllQuesos } from "../../services/RestServices";
import ChartVentas from "./ChartVentas";
import GridVentas from "./GridVentas";
import SearchVentas from "./SearchVentas";
import VentasByQueso from "./VentasByQueso";

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
        currentDate.setDate(currentDate.getDate() - (30 * meses));
        const year = currentDate.getFullYear();
        const month = currentDate.getMonth();
        const date = currentDate.getDate();
        const fechaDesde = `${year}-${padTo2Digits(month + 1)}-${padTo2Digits(date + 1)}`;
        getVentas(fechaDesde, fechaHasta)
            .then(({ data }) => { setListaVentas(data) })
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
    }, [listaQuesos, listaVentas])

    const quesosFormatted = useMemo(() =>
        listaQuesos.map((q) => {
            return {
                id: q.id,
                label: q.codigo + ' - ' + q.tipoQueso + ' - ' + q.nomenclatura,
                value: q.tipoQueso
            }
        }), [listaQuesos]);

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
                        title="Ventas Totales"
                        data={ventasFormatted}
                        xDataKey="fecha"
                        dataKey="total" />
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