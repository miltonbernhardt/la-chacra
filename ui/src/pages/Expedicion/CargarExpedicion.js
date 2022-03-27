import * as React from 'react';
import { useCallback, useEffect, useMemo, useState } from 'react';
import toast from "react-hot-toast";
import { Loading } from "../../components/Loading";
import { PageFormTable } from "../../components/PageFormTable";
import * as field from "../../resources/fields";
import { deleteExpedicion, getAllClientes, postExpedicion, putExpedicion } from '../../services/RestServices';
import { DialogEliminarExpedicion } from './DialogEliminarExpedicion'
import { FormExpedicion } from "./FormExpedicion";
import { GridExpedicion } from "./GridExpedicion";

const expedicionInicial = {
    id: '',
    idLote: '',
    idCliente: '',
    fechaExpedicion: '',
    cantidad: '',
    peso: '',
    importe: ''
}

export const CargarExpedicion = () => {

    const [expedicion, setExpedicion] = useState(expedicionInicial);
    const [listaClientes, setListaClientes] = useState([]);
    const [listaExpediciones, setListaExpediciones] = useState([]);

    const [isEditing, setEditing] = useState(false);
    const [isLoadingClientes, setLoadingClientes] = useState(true);

    const [openDialogEliminar, setOpenDialogEliminar] = useState(false);

    useEffect(() => {
        fetchClientes();
    }, []);

    //TODO: mover mensajes a constante
    const fetchClientes = () => {
        getAllClientes()
            .then(({ data }) => {
                setListaClientes(data)
            })
            .catch(() => toast.error("No se pudo cargar clientes"))
            .finally(() => setLoadingClientes(false));
    }

    //TODO: mover mensajes a constante
    const handleSubmit = useCallback((expedicionForm) => {
        setExpedicion(expedicionForm);
        if (isEditing)
            putExpedicion(expedicionForm)
                .then(({ data }) => {
                    setEditing(false);
                    setExpedicion(expedicionInicial);
                    const newList = listaExpediciones.filter((item) => item.id !== expedicion.id);
                    setListaExpediciones([...newList, data]);
                })
                .catch(() => toast.error('No se pudo actualizar la expedicion'))
                .finally()
        else
            postExpedicion(expedicionForm)
                .then(({ data }) => {
                    setExpedicion(expedicionInicial);
                    setListaExpediciones([...listaExpediciones, data]);
                })
                .catch(() => toast.error('No se pudo cargar la expedicion'))
                .finally()
    }, [expedicion, isEditing, listaExpediciones])

    const submitDelete = useCallback(() => {
        deleteExpedicion(expedicion.id)
            .then(() => {
                setEditing(false);
                setExpedicion(expedicionInicial);
                const newList = listaExpediciones.filter((item) => item.id !== expedicion.id);
                setListaExpediciones(newList);
            })
            .catch(() => null)
            .finally(() => setOpenDialogEliminar(false))
    }, [expedicion.id, listaExpediciones]);

    const handleDelete = useCallback(() => setOpenDialogEliminar(true), [])

    const handleSelect = useCallback((id) => {
        setExpedicion(listaExpediciones.filter((o) => o.id === id).pop())
        setEditing(true);
    }, [listaExpediciones]);

    const handleCancelar = useCallback(() => {
        setEditing(false);
        setExpedicion(expedicionInicial);
    }, []);

    const cancelDelete = useCallback(() => setOpenDialogEliminar(false), [])


    // --- Variables ---
    const clientesFormatted = useMemo(() => listaClientes.map((c) => {
        return { id: c.id, value: c.id, label: c.razonSocial }
    }), [listaClientes])

    const expedicionesFormatted = useMemo(() => listaExpediciones.map((e) => {
        return {
            'id': e.id,
            [field.backIdLote]: e.idLote,
            [field.backRazonSocial]: listaClientes
                .filter(c => c.id === e.idCliente).pop().razonSocial,
            [field.backFechaExpedicion]: e.fechaExpedicion,
            [field.backCantidad]: e.cantidad,
            [field.backPesoExpedicion]: e.peso,
            [field.backImporte]: e.importe

        }
    }), [listaClientes, listaExpediciones]);

    if (isLoadingClientes)
        return <Loading/>

    return <PageFormTable
        form={
            <FormExpedicion
                expedicion={expedicion}
                isEditing={isEditing}
                clientes={clientesFormatted}
                handleSubmit={handleSubmit}
                handleCancelar={handleCancelar}
                handleDelete={handleDelete}/>
        }
        table={
            <GridExpedicion
                expediciones={expedicionesFormatted}
                setSelection={handleSelect}/>
        }
        titleTable="Expediciones"
        titleForm="Ingreso de expediciones">
        <DialogEliminarExpedicion
            open={openDialogEliminar}
            onClose={cancelDelete}
            onSubmit={submitDelete}/>
    </PageFormTable>
}