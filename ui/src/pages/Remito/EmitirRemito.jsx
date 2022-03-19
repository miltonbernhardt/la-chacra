import PageFormTable from '../../components/PageFormTable';
import GridRemito from './GridRemito';
import RemitoForm from "./RemitoForm";
import {useCallback, useEffect, useMemo, useState} from 'react';
import {getAllClientes, getRemito, postRemito} from '../../services/RestServices';
import toast from "react-hot-toast";
import Loading from "../../components/Loading";

const EmitirRemito = () => {

    const [importeTotal, setImporteTotal] = useState(0.0);
    const [listaItems, setListaItems] = useState([]);
    const [listaClientes, setListaClientes] = useState([]);

    const [isLoading, setLoading] = useState(true);

    useEffect(() => {
        fetchClientes();
    }, []);

    const fetchClientes = () => {
        getAllClientes()
            .then(({data}) => {
                setListaClientes(data)
            })
            .catch(() => toast.error("No se pudo cargar clientes"))
            .finally(() => setLoading(false));
    }

    const handleCargar = useCallback((cliente, fecha) => {
        getRemito(cliente, fecha)
            .then(({data}) => {
                setImporteTotal(data.importeTotal);
                setListaItems(data.itemsRemito);
            })
            .catch(() => toast.error('No se pudieron cargas los datos'))
    }, []);

    const handleEmitir = useCallback((cliente, fecha) => {
        postRemito(cliente, fecha)
            .then(({data}) => {
                window.open(`http://localhost:8000/api/v1/remitos/pdf/${data.id}`, '_blank').focus();
            })
            .catch(() => {
                toast.error('No se pudo generar el remito')
            })
            .finally(() => {
                setImporteTotal(0.0);
                setListaItems([]);
            })
    }, [])

    //--- Variables ---
    const clientesFormatted = useMemo(() => listaClientes.map((c) => {
        return {id: c.id, value: c.id, label: c.razonSocial}
    }), [listaClientes])

    const itemsFormatted = useMemo(() =>
            listaItems.map(i => {
                return {...i, id: i.tipoQueso}
            })
        , [listaItems]);

    if (isLoading)
        return <Loading/>

    return (
        <PageFormTable
            titleForm="Emitir Remito"
            titleTable="Detalle"
            form={
                <RemitoForm
                    onCargar={handleCargar}
                    onEmitir={handleEmitir}
                    clientes={clientesFormatted}
                    importe={importeTotal}/>}
            table={
                <GridRemito
                    data={itemsFormatted}/>}>
        </PageFormTable>
    );
}

export default EmitirRemito;