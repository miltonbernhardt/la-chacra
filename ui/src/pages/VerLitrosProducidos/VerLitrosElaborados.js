import { Grid } from "@mui/material";
import * as React from 'react';
import { useCallback, useEffect, useMemo, useState } from 'react';
import toast from 'react-hot-toast';
import { Loading } from "../../components/Loading";
import { dateMinusWeeks, todayDateISO } from "../../resources/utils";
import { getAllQuesos, getLitros } from "../../services/RestServices";
import { ChartLitros } from "./ChartLitros";
import { GridLitros } from "./GridLitros";
import { LitrosByQueso } from "./LitrosByQueso";
import { SearchLitros } from "./SearchLitros";

export const VerLitrosElaborados = () => {

    const [listaLitros, setListaLitros] = useState([]);
    const [listaQuesos, setListaQuesos] = useState([]);

    const [isLoadingLitros, setLoadingLitros] = useState(true);
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

    const fetchLitros = useCallback((fechaHasta, semanas) => {

        const fechaDesde = dateMinusWeeks(fechaHasta, semanas);

        getLitros(fechaDesde, fechaHasta)
            .then(({ data }) => {
                setListaLitros(data)
                if (data.length < 15) toast.error('No se poseen suficientes datos');
            })
            .catch(() => toast.error('No se pudo cargar litros'))
            .finally(() => setLoadingLitros(false));
    }, [])

    const fechaInicial = useMemo(() => { return todayDateISO() }, [])

    useEffect(() => {
        fetchLitros(fechaInicial, 1);
        fetchQuesos();
    }, [fechaInicial, fetchLitros])

    const handleSearch = useCallback((fechaHasta, meses) => {
        fetchLitros(fechaHasta, meses);
    }, [fetchLitros])

    // --- Variables ---
    const litrosFormatted = useMemo(() => {
        if (isLoadingQuesos || isLoadingLitros) return [];
        return listaLitros.map((dia, index) => {
            const litros = dia.litrosElaborados;
            const litrosAsList = litros.map((v) => {
                const tipoQueso = listaQuesos.filter(q => q.codigo === v.codigoQueso).pop().tipoQueso;
                return [tipoQueso, v.cantidad]
            });
            const litrosAsProps = Object.fromEntries(litrosAsList);
            return {
                id: index,
                fecha: dia.fecha.at(8) + dia.fecha.at(9) + '/' +
                    dia.fecha.at(5) + dia.fecha.at(6),
                total: dia.total,
                ...litrosAsProps
            }
        })
    }, [isLoadingQuesos, isLoadingLitros, listaQuesos, listaLitros])

    const quesosFormatted = useMemo(() =>
        listaQuesos.map((q) => {
            return {
                id: q.id,
                label: q.codigo + ' - ' + q.tipoQueso + ' - ' + q.nomenclatura,
                value: q.tipoQueso
            }
        }), [listaQuesos]);

    const litrosMultilineFormatted = useMemo(() => {
        if (listaLitros.length < 16) return [];
        const semana1 = listaLitros.slice(-15, -10);
        const semana2 = listaLitros.slice(-10, -5)
        const ultimaSemana = listaLitros.slice(-5);
        return ultimaSemana.map((value, index) => {
            return {
                dia: index,
                "10 dias anteriores": semana1.at(index).total,
                "5 dias anteriores": semana2.at(index).total,
                "Ultimos 5 dias": value.total
            }
        })
    }, [listaLitros]);

    return (
        isLoadingLitros || isLoadingQuesos ? <Loading /> :
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
                    <SearchLitros
                        fechaInicial={fechaInicial}
                        meses={1}
                        onSearch={handleSearch} />
                    <ChartLitros
                        md={5}
                        title="Litros totales"
                        data={litrosFormatted}
                        xDataKey="fecha"
                        dataKey="total" />
                    <ChartLitros
                        md={5}
                        title="Litros totales"
                        data={litrosMultilineFormatted}
                        yLabel="Litros"
                        xLabel="Fecha"
                        xDataKey="fecha"
                        dataKey="Ultimos 5 dias"
                        dataKey1="5 dias anteriores"
                        dataKey2="10 dias anteriores"
                        legend={true} />
                </Grid>
                <Grid item container spacing={2}>
                    <LitrosByQueso
                        listaLitros={litrosFormatted}
                        quesosSelect={quesosFormatted} />
                </Grid>
                <GridLitros
                    quesos={listaQuesos}
                    data={litrosFormatted} />
            </Grid>
    );
}