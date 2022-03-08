import { useCallback, useEffect, useMemo, useState } from "react";
import Loading from "../../components/Loading";
import PageFormTable from "../../components/PageFormTable";
import { deleteExpedicion, getAllClientes, getAllPrecios, getLote, postExpedicion, putExpedicion } from '../../services/RestServices';
import FormExpedicion from "./FormExpedicion";
import GridExpedicion from "./GridExpedicion";
import DialogEliminarExpedicion from './DialogEliminarExpedicion'
import * as field from "../../resources/fields";
import toast from "react-hot-toast";
import ScanDialog from "./ScanDialog";

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
    const [isLoadingClientes, setLoadingClientes] = useState(true);

    const [openDialogEliminar, setOpenDialogEliminar] = useState(false);
    const [openScanDialog, setOpenScanDialog] = useState(false);

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
                .catch(e => { })
                .finally()
        else
            postExpedicion(expedicionForm)
                .then(({ data }) => {
                    setExpedicion(expedicionInicial);
                    setListaExpediciones([...listaExpediciones, data]);
                })
                .catch(e => { })
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
            .catch(e => { })
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

    const closeScanDialog = useCallback(() => setOpenScanDialog(false), []);

    // --- Variables
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
                    expediciones={expedicionesFormatted}
                    setSelection={handleSelect} />
            }
            titleTable="Expediciones"
            titleForm="Ingreso de expediciones">
            <DialogEliminarExpedicion
                open={openDialogEliminar}
                onClose={cancelDelete}
                onSubmit={submitDelete} />
            <ScanDialog
                open={openScanDialog}
                onClose={closeScanDialog} />
        </PageFormTable>
    );
}

export default CargarExpedicion;