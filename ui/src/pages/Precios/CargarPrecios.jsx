import { useCallback, useEffect, useMemo, useState } from "react";
import PageFormTable from "../../components/PageFormTable";
import { getAllPrecios, getAllQuesos, getAllTipoClientes, postPrecio, putPrecio } from "../../services/RestServices";
import FormPrecios from "./FormPrecios";
import GridPrecios from "./GridPrecios";

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
            postPrecio(precioForm).then(() => fetchPrecios()).catch(e => { }).finally(setPrecio(precioInicial))
        } else {
            putPrecio(precioForm).then(() => fetchPrecios()).catch(e => { }).finally(setPrecio(precioInicial))
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

    if (isLoadingPrecios || isLoadingClientes || isLoadingQuesos) { return <></> } //TODO

    return (
        <>
            {console.log(quesosAutocomplete)}
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
                        precios={listaPrecios}
                        quesos={listaQuesos}
                        tiposCliente={listaTipoClientes}
                        setSelection={setSelection} />
                }
                titleTable="Precios"
            />
        </>
    );
}

export default CargarPrecios;