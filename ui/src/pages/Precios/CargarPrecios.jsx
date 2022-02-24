import { precios } from "../../data/data";
import GridPrecios from "./GridPrecios";
import FormPrecios from "./FormPrecios";
import PageFormTable from "../../components/PageFormTable";
import { useState, useMemo, useEffect, useCallback } from "react";
import { getAllPrecios, postPrecio, putPrecio, getAllQuesos, getAllTipoClientes } from "../../services/RestServices";

const precioInicial = {
    id: '',
    precio: '',
    idTipoCliente: '',
    idQueso: ''
}

const CargarPrecios = () => {

    const [precio, setPrecio] = useState(precioInicial);
    const [listaPrecios, setListaPrecios] = useState([]);
    const [listaQuesos, setListaQuesos] = useState([]);
    const [listaTipoClientes, setListaTipoClientes] = useState([]);

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
            postPrecio(precioForm).then(() => fetchPrecios()).catch(e => { }).finally(setPrecio(precioInicial))
        } else {
            putPrecio(precioForm).then(() => fetchPrecios()).catch(e => { }).finally(setPrecio(precioInicial))
        }
    }, [])

    const quesosAutocomplete = useMemo(() => listaQuesos.map((q) => {
        return {
            id: q.id,
            label: q.codigo + ' - ' + q.tipoQueso + ' - ' + q.nomenclatura,
            value: q.codigo
        }
    }), [listaQuesos]);

    const tiposClienteAutocomplete = useMemo(() =>
        listaTipoClientes.map((c) => {
            return { id: c.id, value: c.id, label: c.tipo }
        }), [listaTipoClientes])

    if (isLoadingPrecios || isLoadingClientes || isLoadingQuesos) { return <></> } //TODO

    return (
        <>
            <PageFormTable
                form={
                    <FormPrecios
                        clientes={tiposClienteAutocomplete}
                        quesos={quesosAutocomplete}
                        precio={precio}
                        handleSubmit={handleSubmit} />
                }
                table={
                    <GridPrecios precios={listaPrecios} />
                }
                titleTable="Precios"
            />
        </>
    );
}

export default CargarPrecios;