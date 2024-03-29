import * as React from 'react';
import { useCallback, useEffect, useMemo, useState } from 'react';
import toast from "react-hot-toast";
import { Loading } from "../../components/Loading";
import { PageFormTable } from "../../components/PageFormTable";
import * as field from "../../resources/fields";
import { todayDateISO } from '../../resources/utils';
import {
    deleteExpedicion,
    getAllClientes,
    postExpedicion,
    postExpedicionLoteCompleto,
    putExpedicion,
} from '../../services/RestServices';
import { DialogEliminarExpedicion } from './DialogEliminarExpedicion'
import { FormExpedicion } from "./FormExpedicion";
import { GridExpedicion } from "./GridExpedicion";


export const CargarExpedicion = () => {

    const fechaInicial = useMemo(() => { return todayDateISO() }, [])

    const expedicionInicial = useMemo(() => {
        return {
            id: '',
            idLote: '',
            idCliente: '',
            fechaExpedicion: fechaInicial,
            cantidad: '',
            peso: '',
            importe: ''
        }
    }, [fechaInicial]);

    const [expedicion, setExpedicion] = useState(expedicionInicial);
    const [listaClientes, setListaClientes] = useState([]);
    const [listaExpediciones, setListaExpediciones] = useState([]);

    const [isEditing, setEditing] = useState(false);
    const [isLoadingClientes, setLoadingClientes] = useState(true);

    const [openDialogEliminar, setOpenDialogEliminar] = useState(false);

    useEffect(() => {
        fetchClientes();
    }, []);

    const fetchClientes = () => {
        getAllClientes()
            .then(({ data }) => {
                setListaClientes(data)
            })
            .catch(() => toast.error("No se pudo cargar clientes"))
            .finally(() => setLoadingClientes(false));
    }

    const handleSubmit = useCallback((expedicionForm, isLoteCompleto) => {
        setExpedicion(expedicionForm);
        if (isEditing)
            putExpedicion(expedicionForm)
                .then(({ data }) => {
                    setEditing(false);
                    const cliente = expedicionForm.idCliente;
                    setExpedicion({ ...expedicionInicial, idCliente: cliente });
                    const newList = listaExpediciones.filter((item) => item.id !== expedicion.id);
                    setListaExpediciones([...newList, data]);
                })
                .catch(() => toast.error('No se pudo actualizar la expedicion'))
                .finally()
        else {
            if (isLoteCompleto)
                postExpedicionLoteCompleto(expedicionForm)
                    .then(({ data }) => {
                        const cliente = expedicionForm.idCliente;
                        setExpedicion({ ...expedicionInicial, idCliente: cliente });
                        setListaExpediciones([...listaExpediciones, data]);
                    })
                    .catch(() => {
                        toast.error('No se pudo cargar la expedicion')

                        const cliente = expedicionForm.idCliente;
                        setExpedicion({ ...expedicionInicial, idCliente: cliente });
                    })
                    .finally()
            else postExpedicion(expedicionForm)
                .then(({ data }) => {

                    const cliente = expedicionForm.idCliente;
                    setExpedicion({ ...expedicionInicial, idCliente: cliente });
                    setListaExpediciones([...listaExpediciones, data]);
                })
                .catch(() => toast.error('No se pudo cargar la expedicion'))
                .finally()
        }
    }, [expedicion.id, expedicionInicial, isEditing, listaExpediciones])

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
    }, [expedicion.id, expedicionInicial, listaExpediciones]);

    const handleDelete = useCallback(() => setOpenDialogEliminar(true), [])

    const handleSelect = useCallback((id) => {
        setExpedicion(listaExpediciones.filter((o) => o.id === id).pop())
        setEditing(true);
    }, [listaExpediciones]);

    const handleCancelar = useCallback(() => {
        setEditing(false);
        const cliente = expedicion.idCliente;
        setExpedicion({ ...expedicionInicial, idCliente: cliente });
    }, [expedicion.idCliente, expedicionInicial]);

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
        return <Loading />

    return <PageFormTable
        form={
            <FormExpedicion
                expedicion={expedicion}
                isEditing={isEditing}
                clientes={clientesFormatted}
                handleSubmit={handleSubmit}
                handleCancelar={handleCancelar}
                handleDelete={handleDelete} />
        }
        table={
            <GridExpedicion
                expediciones={expedicionesFormatted}
                setSelection={handleSelect} />
        }
        titleTable="Expediciones"
        titleForm="Ingreso de expediciones">
        <DialogEliminarExpedicion
            open={openDialogEliminar}
            onClose={cancelDelete}
            onSubmit={submitDelete} />
    </PageFormTable>
}