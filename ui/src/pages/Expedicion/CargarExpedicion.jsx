import { useCallback, useEffect, useMemo, useState } from "react";
import Loading from "../../components/Loading";
import PageFormTable from "../../components/PageFormTable";
import { deleteExpedicion, getAllClientes, postExpedicion, putExpedicion } from '../../services/RestServices';
import FormExpedicion from "./FormExpedicion";
import GridExpedicion from "./GridExpedicion";
import DialogEliminarExpedicion from './DialogEliminarExpedicion'
import * as field from "../../resources/fields";

const expedicionInicial = {
    id: '',
    idLote: '',
    idCliente: '',
    fechaExpedicion: '',
    cantidad: '',
    peso: '',
    importe: ''
}

const CargarExpedicion = () => {

    const [expedicion, setExpedicion] = useState(expedicionInicial);
    const [listaClientes, setListaClientes] = useState([]);
    const [listaExpediciones, setListaExpediciones] = useState([]);

    const [isEditing, setEditing] = useState(false);
    const [isLoading, setLoading] = useState(true);

    const [openDialogEliminar, setOpenDialogEliminar] = useState(false);

    useEffect(() => {
        fetchClientes();
    }, []);

    const fetchClientes = () => {
        getAllClientes().then(({ data }) => {
            setListaClientes(data)
        }).catch(e => { }).finally(() => { setLoading(false) });
    }

    const handleSubmit = useCallback((expedicionForm) => {
        if (isEditing)
            putExpedicion(expedicionForm)
                .then(({ data }) => {
                    setEditing(false);
                    setExpedicion(expedicionInicial);
                    setListaExpediciones(...listaExpediciones, data);
                })
                .catch(e => { })
        else
            postExpedicion(expedicionForm)
                .then(({ data }) => {
                    setExpedicion(expedicionInicial);
                    setListaExpediciones(...listaExpediciones, data);
                })
                .catch(e => { })
    }, [isEditing, listaExpediciones])

    const submitDelete = useCallback(() => {
        deleteExpedicion(expedicion.id)
            .then(() => {
                setEditing(false);
                setExpedicion(expedicionInicial);
                const newList = listaExpediciones.filter((item) => item.id !== expedicion.id);
                setListaExpediciones(newList);
            })
            .catch(e => { })
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

    // --- Variables
    const clientesFormatted = useMemo(() => listaClientes.map((c) => {
        return { id: c.id, value: c.id, label: c.razonSocial }
    }), [listaClientes])

    const expedicionesFormatted = useMemo(() => listaExpediciones.map((e) => {
        return {
            id: e.id,
            [field.backIdLote]: e.idLote,
            [field.razonSocial]: listaClientes
                .filter(c => c.id === e.idCliente).pop().razonSocial,
            [field.backFechaExpedicion]: e.fechaExpedicion,
            [field.backCantidad]: e.cantidad,
            [field.backPesoExpedicion]: e.peso,
            [field.backImporte]: e.importe

        }
    }), [listaClientes, listaExpediciones]);

    if (isLoading) { return <Loading /> }

    return (
        <PageFormTable
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
                    setSelection={handleSelect} />
            }
            titleTable="Expediciones"
            titleForm="Ingreso de expediciones">
            <DialogEliminarExpedicion
                expediciones={expedicionesFormatted}
                open={openDialogEliminar}
                onClose={cancelDelete}
                onSubmit={submitDelete} />
        </PageFormTable>
    );
}

export default CargarExpedicion;