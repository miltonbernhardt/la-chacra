import * as React from 'react';
import { useCallback, useEffect, useMemo, useState } from 'react';
import toast from "react-hot-toast";
import { Loading } from '../../components/Loading';
import { PageFormTable } from "../../components/PageFormTable";
import * as field from "../../resources/fields";
import { deleteLote, getAllQuesos, getLotesBetweenDates, putLote } from "../../services/RestServices";
import { DialogEliminarLote } from "../Lotes/DialogEliminarLote";
import { EditLoteDialog } from './EditLoteDialog';
import { FormProduccion } from "./FormProduccion";
import { GridProduccion } from "./GridProduccion";

export const VerProduccion = () => {

    const [listaLotes, setListaLotes] = useState([]);
    const [listaQuesos, setListaQuesos] = useState([]);
    const [lote, setLote] = useState({});

    const [isEditing, setEditing] = useState(false);
    const [isLoading, setLoading] = useState(true);
    const [eliminarDialog, setEliminarDialog] = useState(false);

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

    // --- Functions ---

    const handleBuscar = useCallback((fechaDesde, fechaHasta) => {
        fetchLotes(fechaDesde, fechaHasta);
    }, []);

    const setSelection = useCallback((id) => {
        setLote(listaLotes.filter(l => l.id === id).pop());
        setEditing(true);
    }, [listaLotes])

    const closeDialog = useCallback(() => setEditing(false), []);

    const handleSubmit = useCallback((loteSubmit) => {
        putLote(loteSubmit)
            .then(({ data }) => {
                const newList = listaLotes.filter((item) => item.id !== lote.id);
                setListaLotes([...newList, data]);
            })
            .catch(() => null);
        setEditing(false);
    }, [listaLotes, lote.id]);

    const eliminarLote = useCallback(() => {
        setEditing(false);
        setEliminarDialog(true)
    }, [])

    const handleEliminar = useCallback(() => {
        deleteLote(lote.id)
            .then(() => {
                const newList = listaLotes.filter((item) => item.id !== lote.id);
                setListaLotes(newList);
            })
            .catch(() => null);

        setEliminarDialog(false);
        setEditing(false);
    }, [lote.id, listaLotes]);

    const cancelEliminar = useCallback(() => setEliminarDialog(false), []);
    // --- Variables ---

    const today = useMemo(() => {
        const currentDate = new Date();
        return `${currentDate.getFullYear()}-${currentDate.getMonth() + 1}-${currentDate.getDate()}`
    }, []);

    const listaLotesFormatted = listaLotes.map((lote) => {
        let colorStock = lote.cantHormas === lote.stockLote ? 'info' : 'default';
        if (lote.cantHormas > lote.stockLote) colorStock = 'success';
        if (0 > lote.stockLote) colorStock = 'error';
        if (0 === lote.stockLote) colorStock = 'default';
        if (0 === lote.stockLote) colorStock = 'default';
        return {
            ...lote, [field.backStockLote]: {
                stockLote: lote.stockLote, color: colorStock
            }, [field.backCodigoQueso]: listaQuesos
                .filter(q => {
                    return q.codigo === lote.codigoQueso
                })
                .pop()

        }
    });


    if (isLoading) return (<Loading/>)

    return <PageFormTable
        mdForm={2}
        lgForm={2}
        titleForm={"Producción"}
        form={<FormProduccion
            onBuscar={handleBuscar}
            initialDate={today}/>}
        sizeForm={3}
        table={<GridProduccion
            quesos={listaQuesos}
            data={listaLotesFormatted}
            setSelection={setSelection}/>}>
        <EditLoteDialog
            lote={lote}
            open={isEditing}
            onClose={closeDialog}
            onSubmit={handleSubmit}
            onDelete={eliminarLote}/>
        <DialogEliminarLote
            open={eliminarDialog}
            lote={lote}
            onClose={cancelEliminar}
            onSubmit={handleEliminar}/>
    </PageFormTable>
}