import { useCallback, useEffect, useMemo, useState } from "react";
import PageFormTable from "../../components/PageFormTable";
import Loading from "../../components/Loading"
import { getAllPrecios, getAllQuesos, getAllTipoClientes, postPrecio, putPrecio } from "../../services/RestServices";
import FormPrecios from "./FormPrecios";
import GridPrecios from "./GridPrecios";
import * as fields from '../../resources/fields'

const precioInicial = {
    id: '',
    valor: '',
    idTipoCliente: '',
    idQueso: ''
}

const CargarPrecios = () => {

    const [precio, setPrecio] = useState(precioInicial);
    const [listaPrecios, setListaPrecios] = useState([]);
    const [listaQuesos, setListaQuesos] = useState([]);
    const [listaTipoClientes, setListaTipoClientes] = useState([]);
    const [isEditing, setEditing] = useState(false);

    const [isLoadingPrecios, setLoadingPrecios] = useState(true);
    const [isLoadingQuesos, setLoadingQuesos] = useState(true);
    const [isLoadingClientes, setLoadingClientes] = useState(true);

    const fetchPrecios = () => {
        getAllPrecios().then(({ data }) => {
            setListaPrecios(data);
        }).catch(e => { }).finally(() =>
            setLoadingPrecios(false))
    }

    const fetchQuesos = () => {
        getAllQuesos().then(({ data }) => {
            setListaQuesos(data);
        }).catch(e => { }).finally(() =>
            setLoadingQuesos(false))

    }

    const fetchTipoClientes = () => {
        getAllTipoClientes().then(({ data }) => {
            setListaTipoClientes(data);
        }).catch(e => { }).finally(() =>
            setLoadingClientes(false))
    }

    useEffect(() => {
        fetchPrecios();
        fetchTipoClientes();
        fetchQuesos();
    }, []);

    const handleSubmit = useCallback((precioForm) => {
        if (precioForm.id === '') {
            postPrecio(precioForm)
                .then(() => fetchPrecios()).catch(e => { })
                .finally(() => { setPrecio(precioInicial) })
        } else {
            putPrecio(precioForm)
                .then(() => fetchPrecios()).catch(e => { })
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

    if (isLoadingPrecios || isLoadingClientes || isLoadingQuesos) { return <Loading /> } //TODO

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
                        handleCancelar={handleCancelar} />
                }
                table={
                    <GridPrecios
                        precios={preciosFormatted}
                        setSelection={setSelection} />
                }
                titleTable="Precios"
                titleForm="Ingreso de precios"
            />
        </>
    );
}

export default CargarPrecios;