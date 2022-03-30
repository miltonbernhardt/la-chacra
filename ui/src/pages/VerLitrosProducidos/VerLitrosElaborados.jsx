import { Grid } from "@mui/material";
import { useCallback, useEffect, useMemo, useState } from 'react';
import toast from 'react-hot-toast';
import Loading from "../../components/Loading";
import { quesos } from "../../data/data";
import { getAllQuesos, getLitros } from "../../services/RestServices";
import ChartLitros from "./ChartLitros";
import GridLitros from "./GridLitros";
import LitrosByQueso from "./LitrosByQueso";
import SearchLitros from "./SearchLitros";

const columns1 = quesos.map((queso, index) => {
    return {
        field: queso.nomenclatura,
        headerName: queso.nomenclatura,
        type: 'number',
        flex: 0.7,
        minWidth: 80
    }
})

const columns = [{
    field: "semana",
    headerName: "Semana",
    type: 'date',
    flex: 1,
    minWidth: 100
},
{
    field: "total",
    headerName: "Total",
    type: 'number',
    flex: 1,
    minWidth: 100
},
...columns1
];

const VerLitrosElaborados = () => {

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

    const fetchLitros = useCallback((fechaHasta, meses) => {
        const currentDate = new Date(fechaHasta);
        currentDate.setDate(currentDate.getDate() - (30 * meses));
        const year = currentDate.getFullYear();
        const month = currentDate.getMonth();
        const date = currentDate.getDate();
        const fechaDesde = `${year}-${padTo2Digits(month + 1)}-${padTo2Digits(date + 1)}`;
        getLitros(fechaDesde, fechaHasta)
            .then(({ data }) => { setListaLitros(data) })
            .catch(() => toast.error('No se pudo cargar litros'))
            .finally(() => setLoadingLitros(false));
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
                        title="Litros Totales"
                        data={litrosFormatted}
                        xDataKey="fecha"
                        dataKey="total" />
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

export default VerLitrosElaborados;