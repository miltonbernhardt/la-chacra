import * as React from 'react';
import { useCallback, useState, useMemo, useEffect } from 'react';
import toast from "react-hot-toast";
import { Loading } from "../../components/Loading";
import { PageFormTable } from "../../components/PageFormTable";
import { SearchByWeeks } from '../../components/SearchByWeeks';
import { deleteExpedicion, getAllClientes, getExpedicionesBetweenDates, putExpedicion } from '../../services/RestServices';
import { GridExpediciones } from './GridExpediciones';
import { dateMinusWeeks, todayDateISO } from '../../resources/utils';
import * as field from "../../resources/fields";
import { EditExpedicionDialog } from './EditExpedicionDialog';
import { DialogEliminarExpedicion } from '../Expedicion/DialogEliminarExpedicion';

export const VerExpediciones = () => {

    const today = useMemo(() => { return todayDateISO() }, []);

    const [listaExpediciones, setListaExpediciones] = useState([])
    const [listaClientes, setListaClientes] = useState([])
    const [expedicion, setExpedicion] = useState({})

    const [isEditing, setEditing] = useState(false);
    const [eliminarDialog, setEliminarDialog] = useState(false);

    const [isLoadingClientes, setLoadingClientes] = useState(true);
    const [isLoadingExpediciones, setLoadingExpediciones] = useState(true);

    const fetchExpediciones = useCallback((fechaHasta, semanas) => {
        const fechaDesde = dateMinusWeeks(fechaHasta, semanas);

        getExpedicionesBetweenDates(fechaDesde, fechaHasta)
            .then(({ data }) => setListaExpediciones(data))
            .catch(() => toast.error('No se pudieron cargar expediciones'))
            .finally(() => setLoadingExpediciones(false))
    }, [])

    const fetchClientes = () => {
        getAllClientes()
            .then(({ data }) => {
                setListaClientes(data)
            })
            .catch(() => toast.error("No se pudo cargar clientes"))
            .finally(() => setLoadingClientes(false));
    }

    useEffect(() => {
        fetchClientes();
        fetchExpediciones(today, 1);
    }, [fetchExpediciones, today])

    // --- Functions ---

    const handleSelect = useCallback((id) => {
        const selected = listaExpediciones.filter(l => l.id === id).pop();
        setExpedicion(selected);
        selected.onRemito ?
            toast.error('No se puede editar, pertenece a un remito') :
            setEditing(true);
    }, [listaExpediciones]);

    const closeDialog = useCallback(() => setEditing(false), []);

    const handleSubmit = useCallback((expedicionSubmit) => {
        putExpedicion(expedicionSubmit)
            .then(({ data }) => {
                const newList = listaExpediciones.filter((item) => item.id !== expedicion.id);
                setListaExpediciones([...newList, data]);
            })
            .catch(() => null);
        setEditing(false);
    }, [expedicion.id, listaExpediciones]);

    const eliminarExpedicion = useCallback(() => {
        setEditing(false);
        setEliminarDialog(true)
    }, [])

    const handleEliminar = useCallback(() => {
        deleteExpedicion(expedicion.id)
            .then(() => {
                const newList = listaExpediciones.filter((item) => item.id !== expedicion.id);
                setListaExpediciones(newList);
            })
            .catch(() => null);

        setEliminarDialog(false);
        setEditing(false);
    }, [expedicion.id, listaExpediciones]);

    const cancelEliminar = useCallback(() => setEliminarDialog(false), []);

    // --- Variables --- 

    const expedicionesFormatted = useMemo(() => listaExpediciones.map((e) => {
        return {
            'id': e.id,
            [field.backIdLote]: e.idLote,
            [field.backRazonSocial]: listaClientes
                .filter(c => c.id === e.idCliente).pop().razonSocial,
            [field.backFechaExpedicion]: e.fechaExpedicion,
            [field.backCantidad]: e.cantidad,
            [field.backPesoExpedicion]: e.peso,
            [field.backImporte]: e.importe,
            [field.backOnRemito]: e.onRemito
        }
    }), [listaClientes, listaExpediciones]);

    const clientesFormatted = useMemo(() => listaClientes.map((c) => {
        return { id: c.id, value: c.id, label: c.razonSocial }
    }), [listaClientes])

    return (
        isLoadingExpediciones || isLoadingClientes ? <Loading /> :
            <PageFormTable
                mdForm={2}
                lgForm={2}
                titleForm={"Expediciones"}
                form={<SearchByWeeks
                    fechaInicial={today}
                    semanas={1}
                    onSearch={fetchExpediciones}
                />}
                table={
                    <GridExpediciones
                        expediciones={expedicionesFormatted}
                        setSelection={handleSelect} />} >
                <EditExpedicionDialog
                    open={isEditing}
                    expedicion={expedicion}
                    clientes={clientesFormatted}
                    handleSubmit={handleSubmit}
                    onClose={closeDialog}
                    onDelete={eliminarExpedicion} />
                <DialogEliminarExpedicion
                    open={eliminarDialog}
                    onClose={cancelEliminar}
                    onSubmit={handleEliminar} />
            </PageFormTable>
    );
}
