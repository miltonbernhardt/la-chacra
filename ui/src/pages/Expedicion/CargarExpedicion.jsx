import FormExpedicion from "./FormExpedicion";
import GridExpedicion from "./GridExpedicion";
import PageFormTable from "../../components/PageFormTable";
import { useState, useEffect, useCallback } from "react";
import { deleteCliente, getAllClientes, getAllTipoClientes, postCliente, putCliente } from '../../services/RestServices';
import { useMemo } from "react";
import Loading from "../../components/Loading";

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
    const [isEditing, setEditing] = useState(false);
    const [isLoading, setLoading] = useState(true);

    useEffect(() => {
        fetchClientes();
    }, []);

    const fetchClientes = () => {
        getAllClientes().then(({ data }) => {
            setListaClientes(data)
        }).catch(e => { }).finally(() => { setLoading(false) });
    }

    const handleSubmit = useCallback(() => alert('not yet implemented :)'), [])

    // --- Variables
    const clientesFormatted = useMemo(() => listaClientes.map((c) => {
        return { id: c.id, value: c.id, label: c.razonSocial }
    }), [listaClientes])

    if (isLoading) { return <Loading /> }

    return (
        <PageFormTable
            form={
                <FormExpedicion
                    expedicion={expedicion}
                    isEditing={isEditing}
                    clientes={clientesFormatted}
                    handleSubmit={handleSubmit} />
            }
            table={
                <GridExpedicion />
            }
            titleTable="Expediciones"
            titleForm="Ingreso de expediciones"
        />
    );
}

export default CargarExpedicion;