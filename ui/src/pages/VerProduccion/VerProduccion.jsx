import GridProduccion from "./GridProduccion";
import FormProduccion from "./FormProduccion";
import PageFormTable from "../../components/PageFormTable";
import { useCallback, useEffect, useMemo, useState } from 'react'
import { getLotesBetweenDates } from "../../services/RestServices";
import * as field from "../../resources/fields";
import toast from "react-hot-toast";
import { deleteLote, getAllQuesos, postLote, putLote } from "../../services/RestServices";

import Loading from '../../components/Loading';

const VerProduccion = () => {

    const [listaLotes, setListaLotes] = useState([]);
    const [listaQuesos, setListaQuesos] = useState([]);

    const [isLoading, setLoading] = useState(true);

    const fetchLotes = (fechaDesde, fechaHasta) => {
        getLotesBetweenDates(fechaDesde, fechaHasta)
            .then(({ data }) => setListaLotes(data))
            .catch(() => toast.error('No se pudieron cargar los lotes'))
    }

    const fetchQuesos = useCallback(() => {
        getAllQuesos()
            .then(data => {
                setListaQuesos(data.data);
            })
            .catch(() => toast.error("No se pudo cargar quesos"))
            .finally(() => setLoading(false));
    }, []);

    useEffect(() => fetchQuesos(), [fetchQuesos])

    const handleBuscar = useCallback((fechaDesde, fechaHasta) => {
        fetchLotes(fechaDesde, fechaHasta);
    }, []);

    const today = useMemo(() => {
        const currentDate = new Date();
        return `${currentDate.getFullYear()}-${currentDate.getMonth() + 1}-${currentDate.getDate()}`
    }, []);

    const listaLotesFormatted =
        listaLotes.map((lote) => {
            let colorStock = lote.cantHormas === lote.stockLote ? 'info' : 'default';
            if (lote.cantHormas > lote.stockLote) colorStock = 'success';
            if (0 > lote.stockLote) colorStock = 'error';
            if (0 === lote.stockLote) colorStock = 'default';
            if (0 === lote.stockLote) colorStock = 'default';
            return {
                ...lote,
                [field.backStockLote]: {
                    stockLote: lote.stockLote,
                    color: colorStock
                },
                [field.backCodigoQueso]:
                    listaQuesos
                        .filter(q => {
                            return q.codigo === lote.codigoQueso
                        })
                        .pop()

            }
        });


    if (isLoading)
        return (<Loading />)

    return (
        <>
            <PageFormTable
                mdForm={2}
                lgForm={2}
                titleForm={"Producción"}
                form={
                    <FormProduccion
                        onBuscar={handleBuscar}
                        initialDate={today} />
                }
                sizeForm={3}
                table={
                    <GridProduccion
                        quesos={listaQuesos}
                        data={listaLotesFormatted} />
                }
            />
        </>
    );
}

export default VerProduccion;