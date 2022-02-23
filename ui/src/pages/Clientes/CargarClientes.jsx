import { Box, Button } from '@mui/material';
import CircularProgress from '@mui/material/CircularProgress';
import { useCallback, useEffect, useMemo, useState } from 'react';
import toast from 'react-hot-toast';
import PageTableButtonPane from "../../components/PageTableButtonPane";
import { clientes } from "../../data/data";
import { getAllClientes, getAllTipoClientes, postCliente } from '../../services/RestServices';
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
    const [listaClientes, setListaClientes] = useState(clientes);
    const [tiposClientes, setTiposClientes] = useState([]);

    const [isLoading, setLoading] = useState(true);

    const [isEditing, setEditing] = useState(false);

    const fetchClientes = () => {
        getAllClientes().then(({ data }) => {
            setListaClientes(data)
        }).catch();
    }

    const fetchTipoClientes = () => {
        getAllTipoClientes().then(({ data }) => {
            setTiposClientes(data);
            setLoading(false);
        }).catch();
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
        postCliente(clienteForm).then(() => fetchClientes());
        // toast.error('not yet implemented')
    }, [])

    // --- DIALOGS ---
    const [openDialogAlta, setOpenDialogAlta] = useState(false);
    const [openDialogBaja, setOpenDialogBaja] = useState(false);

    const onOpenAlta = () => {
        setCliente(clienteInicial);
        setOpenDialogAlta(true);
    }
    const onOpenBaja = () => {
        setOpenDialogBaja(true);
    }
    const onOpenActualizar = () => {
        if (!cliente.id) toast.error("No se seleccionó ningún cliente");
        else {
            setEditing(true);
            setOpenDialogAlta(true);
        }
    }
    const onCloseDialog = () => {
        setEditing(false);
        setOpenDialogBaja(false);
        setOpenDialogAlta(false);
    }

    const valoresTiposClientes = useMemo(() =>
        tiposClientes.map((c) => {
            return { id: c.id, value: c.id, label: c.tipo }
        }), [tiposClientes])

    if (isLoading) {
        return (
            <Box sx={{
                marginTop: 8,
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                mt: 3,
            }}>
                <CircularProgress />
            </Box>
        )
    };

    return (
        <PageTableButtonPane
            title="Clientes"
            buttons={<>
                <Button color="error" onClick={onOpenBaja}>Dar de baja</Button>
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
                tiposCliente={valoresTiposClientes} />
            <DialogBajaCliente
                cliente={cliente}
                onClose={onCloseDialog}
                open={openDialogBaja} />
        </PageTableButtonPane>
    );
}

export default CargarClientes;