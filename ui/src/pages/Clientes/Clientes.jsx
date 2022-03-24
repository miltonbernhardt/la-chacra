import {Button} from '@mui/material';
import {useCallback, useEffect, useMemo, useState} from 'react';
import toast from 'react-hot-toast';
import Loading from '../../components/Loading';
import PageTableButtonPane from "../../components/PageTableButtonPane";
import {deleteCliente, getAllClientes, getAllTipoClientes, postCliente, putCliente} from '../../services/RestServices';
import DialogAltaCliente from './DialogAltaCliente';
import DialogBajaCliente from './DialogBajaCliente';
import GridClientes from "./GridClientes";

const clienteInicial = {
    id: '',
    razonSocial: '',
    cuit: '',
    domicilio: '',
    codPostal: '',
    localidad: '',
    provincia: '',
    pais: '',
    transporte: '',
    senasaUta: '',
    email: '',
    telefono: '',
    fax: '',
    celular: '',
    idTipoCliente: ''
}

const CargarClientes = () => {

    const [cliente, setCliente] = useState(clienteInicial);
    const [listaClientes, setListaClientes] = useState([]);
    const [tiposClientes, setTiposClientes] = useState([]);

    const [isLoadingClientes, setLoadingClientes] = useState(true);
    const [isLoadingTipoClientes, setLoadingTipoClientes] = useState(true);

    const [isEditing, setEditing] = useState(false);

    const fetchClientes = () => {
        getAllClientes()
            .then(({data}) => {
                setListaClientes(data)
            })
            .catch(() => toast.error("No se pudo cargar clientes"))
            .finally(() => setLoadingClientes(false))
    }

    const fetchTipoClientes = () => {
        getAllTipoClientes()
            .then(({data}) => {
                setTiposClientes(data);
            })
            .catch(() => toast.error("No se pudo cargar tipos de cliente"))
            .finally(() => setLoadingTipoClientes(false));
    }

    useEffect(() => {
        fetchClientes();
        fetchTipoClientes();
    }, [])

    const setSelection = useCallback((id) => {
        setCliente(listaClientes.filter(c => c.id === id).pop())
    }, [listaClientes])

    const onSubmit = useCallback((clienteForm) => {
        setOpenDialogAlta(false);
        setCliente(clienteInicial);

        if (isEditing) {
            setEditing(false);
            putCliente(clienteForm).then(() => fetchClientes());
        } else
            postCliente(clienteForm).then(() => fetchClientes());
    }, [isEditing])

    const onDelete = useCallback(() => {
        deleteCliente(cliente.id)
            .then(() => {
                fetchClientes();
                setCliente(clienteInicial);
                onCloseDialog();
            })
            .catch(() => null)
    }, [cliente.id])

    // --- DIALOGS ---
    const [openDialogAlta, setOpenDialogAlta] = useState(false);
    const [openDialogBaja, setOpenDialogBaja] = useState(false);

    const onOpenAlta = () => {
        setEditing(false);
        setCliente(clienteInicial);
        setOpenDialogAlta(true);
    }

    const onOpenBaja = () => {
        if (!cliente.id)
            toast.error("No se seleccionó ningún cliente");
        else
            setOpenDialogBaja(true);
    }

    const onOpenActualizar = () => {
        if (!cliente.id)
            toast.error("No se seleccionó ningún cliente");
        else {
            setEditing(true);
            setOpenDialogAlta(true);
        }
    }

    const onCloseDialog = () => {
        setOpenDialogBaja(false);
        setOpenDialogAlta(false);
    }

    const valoresTiposClientes = useMemo(() =>
        tiposClientes.map((c) => {
            return {id: c.id, value: c.id, label: c.tipo}
        }), [tiposClientes])

    if (isLoadingClientes || isLoadingTipoClientes)
        return (<Loading/>)

    return (
        <PageTableButtonPane
            title="Clientes"
            buttons={<>
                <Button color="warning" onClick={onOpenBaja}>Dar de baja</Button>
                <Button color="info" onClick={onOpenActualizar}>Actualizar</Button>
                <Button onClick={onOpenAlta}>Dar de alta</Button>
            </>}
            grid={<GridClientes
                clientes={listaClientes}
                setSelection={setSelection}
            />}
        >
            <DialogAltaCliente
                cliente={cliente}
                onClose={onCloseDialog}
                onSubmit={onSubmit}
                open={openDialogAlta}
                isEditing={isEditing}
                tiposCliente={valoresTiposClientes}/>
            <DialogBajaCliente
                cliente={cliente}
                onClose={onCloseDialog}
                open={openDialogBaja}
                onSubmit={onDelete}/>
        </PageTableButtonPane>
    );
}

export default CargarClientes;