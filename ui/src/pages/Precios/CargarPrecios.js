import * as React from 'react';
import { useCallback, useEffect, useMemo, useState } from 'react';
import toast from "react-hot-toast";
import { Loading } from "../../components/Loading"
import { PageFormTable } from "../../components/PageFormTable";
import * as fields from '../../resources/fields'
import { getAllPrecios, getAllQuesos, getAllTipoClientes, postPrecio, putPrecio } from "../../services/RestServices";
import { FormPrecios } from "./FormPrecios";
import { GridPrecios } from "./GridPrecios";

const precioInicial = {
    id: '',
    valor: '',
    idTipoCliente: '',
    idQueso: ''
}

export const CargarPrecios = () => {

    const [precio, setPrecio] = useState(precioInicial);
    const [listaPrecios, setListaPrecios] = useState([]);
    const [listaQuesos, setListaQuesos] = useState([]);
    const [listaTipoClientes, setListaTipoClientes] = useState([]);
    const [isEditing, setEditing] = useState(false);

    const [isLoadingPrecios, setLoadingPrecios] = useState(true);
    const [isLoadingQuesos, setLoadingQuesos] = useState(true);
    const [isLoadingClientes, setLoadingClientes] = useState(true);

    //TODO: mover mensaje a constante
    const fetchPrecios = () => {
        getAllPrecios()
            .then(({ data }) => setListaPrecios(data))
            .catch(() => toast.error("No se pudo cargar precios"))
            .finally(() => setLoadingPrecios(false))
    }

    //TODO: mover mensaje a constante
    const fetchQuesos = () => {
        getAllQuesos()
            .then(({ data }) => setListaQuesos(data))
            .catch(() => toast.error("No se pudo cargar quesos"))
            .finally(() => setLoadingQuesos(false))
    }

    //TODO: mover mensaje a constante
    const fetchTipoClientes = () => {
        getAllTipoClientes()
            .then(({ data }) => setListaTipoClientes(data))
            .catch(() => toast.error("No se pudo cargar tipos de cliente"))
            .finally(() => setLoadingClientes(false))
    }

    useEffect(() => {
        fetchPrecios();
        fetchTipoClientes();
        fetchQuesos();
    }, []);

    const handleSubmit = useCallback((precioForm) => {
        setPrecio(precioForm);
        if (precioForm.id === '') {
            postPrecio(precioForm)
                .then(() => fetchPrecios())
                .catch(() => null)
                .finally(() => setPrecio(precioInicial))
        } else {
            putPrecio(precioForm)
                .then(() => fetchPrecios())
                .catch(() => null)
                .finally(() => {
                    setEditing(false);
                    setPrecio(precioInicial)
                })
        }
    }, [])

    const setSelection = useCallback((id) => {
        setEditing(true);
        setPrecio(listaPrecios.filter(p => p.id === id).pop());
    }, [listaPrecios])

    const handleCancelar = useCallback(() => {
        setEditing(false)
        setPrecio(precioInicial)
    }, []);

    // --- Variables
    const quesosAutocomplete = useMemo(() =>
        listaQuesos.map((q) => {
            return {
                id: q.id,
                label: q.codigo + ' - ' + q.tipoQueso + ' - ' + q.nomenclatura,
                value: q.id
            }
        }), [listaQuesos]);

    const tiposClienteAutocomplete = useMemo(() =>
        listaTipoClientes.map((c) => {
            return { id: c.id, value: c.id, label: c.tipo }
        }), [listaTipoClientes])

    if (isLoadingPrecios || isLoadingClientes || isLoadingQuesos)
        return <Loading/>

    // This needs to be called after all lists are loaded
    // or else lists are undefined
    const preciosFormatted = listaPrecios.map(p => {
        return {
            [fields.backIdQueso]: listaQuesos
                .filter(q => {
                    return q.id === p.idQueso
                })
                .pop().tipoQueso,
            [fields.backIdTipoCliente]: listaTipoClientes
                .filter(t => {
                    return t.id === p.idTipoCliente
                })
                .pop().tipo,
            [fields.backPrecio]: p.valor,
            id: p.id
        }
    })

    return (
        <>
            <PageFormTable
                form={
                    <FormPrecios
                        clientes={tiposClienteAutocomplete}
                        quesos={quesosAutocomplete}
                        precio={precio}
                        isEditing={isEditing}
                        handleSubmit={handleSubmit}
                        handleCancelar={handleCancelar}/>
                }
                table={
                    <GridPrecios
                        precios={preciosFormatted}
                        setSelection={setSelection}/>
                }
                titleTable="Precios"
                titleForm="Ingreso de precios"
            />
        </>
    );
}