import * as React from 'react';
import { useCallback, useEffect, useMemo, useState } from 'react';
import toast from "react-hot-toast";
import { Loading } from "../../components/Loading";
import { PageFormTable } from '../../components/PageFormTable';
import { getAllClientes, getRemito, postRemito, PDF_REMITO } from '../../services/RestServices';
import { GridRemito } from './GridRemito';
import { RemitoForm } from "./RemitoForm";
import * as field from "../../resources/fields";

const remitoInicial = {
    [field.backImporteTotal]: 0.0,
    [field.backCantCajas]: '',
    [field.backCantPallets]: ''
}

export const EmitirRemito = () => {

    const [remito, setRemito] = useState(remitoInicial);
    const [listaItems, setListaItems] = useState([]);
    const [listaClientes, setListaClientes] = useState([]);
    const [emitible, setEmitible] = useState(false);

    const [isLoading, setLoading] = useState(true);

    useEffect(() => {
        fetchClientes();
    }, []);

    const fetchClientes = () => {
        getAllClientes()
            .then(({ data }) => {
                setListaClientes(data)
            })
            .catch(() => toast.error("No se pudo cargar clientes"))
            .finally(() => setLoading(false));
    }

    const handleCargar = useCallback((cliente, fecha) => {
        getRemito(cliente, fecha)
            .then(({ data }) => {
                // setImporteTotal(data.importeTotal);
                // setRemito({ ...remito, [field.backImporteTotal]: data.backImporteTotal })
                setRemito(data);
                setListaItems(data.itemsRemito);
                if (data.itemsRemito.length === 0) {
                    toast.success('El cliente no posee expediciones para remito');
                    setEmitible(false);
                } else setEmitible(true);
            })
            .catch(() => toast.error('No se pudieron cargar los datos'))
    }, []);

    const handleEmitir = useCallback((idCliente, remito) => {
        postRemito(idCliente, remito)
            .then(({ data }) => {
                window.open(`${PDF_REMITO}${data.id}`, '_blank').focus();
            })
            .catch(() => {
                toast.error('No se pudo generar el remito')
            })
            .finally(() => {
                // setImporteTotal(0.0);
                setRemito(remitoInicial);
                setListaItems([]);
                setEmitible(false);
            })
    }, [])

    //--- Variables ---

    const clientesFormatted = useMemo(() => listaClientes.map((c) => {
        return { id: c.id, value: c.id, label: c.razonSocial }
    }), [listaClientes])

    const itemsFormatted = useMemo(() =>
        listaItems.map(i => {
            return { ...i, id: i.tipoQueso }
        })
        , [listaItems]);

    if (isLoading)
        return <Loading />

    return <PageFormTable
        titleForm="Emitir Remito"
        titleTable="Detalle"
        form={
            <RemitoForm
                onCargar={handleCargar}
                onEmitir={handleEmitir}
                clientes={clientesFormatted}
                // importe={importeTotal}
                remitoInicial={remito}
                emitible={emitible} />}
        table={
            <GridRemito
                data={itemsFormatted} />}>
    </PageFormTable>
}