import * as React from 'react';
import { useCallback, useEffect, useMemo, useState } from 'react';
import toast from "react-hot-toast";
import { PageFormTable } from "../../components/PageFormTable";
import * as field from "../../resources/fields";
import { getAllClientes, getExpedicionesByLote, getLote } from "../../services/RestServices";
import { FormTrazabilidad } from "./FormTrazabilidad";
import { GridTrazabilidad } from "./GridTrazabilidad";

const loteInicial = {
    id: '',
    fechaElaboracion: '',
    numeroTina: '',
    litrosLeche: '',
    cantHormas: '',
    peso: '',
    loteCultivo: '',
    loteColorante: '',
    loteCalcio: '',
    loteCuajo: '',
    codigoQueso: ''
}

export const VerTrazabilidad = () => {

    const [lote, setLote] = useState(loteInicial);
    const [listaExpediciones, setListaExpediciones] = useState([])
    const [listaClientes, setListaClientes] = useState([])

    useEffect(() => fetchClientes(), []);

    //TODO: mover mensajes a constantes
    const fetchClientes = () => {
        getAllClientes()
            .then(({ data }) => {
                setListaClientes(data)
            })
            .catch(() => toast.error("No se pudo cargar clientes"))
    }

    //TODO: mover mensajes a constantes
    const handleBuscar = useCallback((idLote) => {
        getLote(idLote)
            .then(({ data }) => setLote(data))
            .catch(() => toast.error('No se pudo cargar el lote'))

        getExpedicionesByLote(idLote)
            .then(({ data }) => setListaExpediciones(data))
            .catch(() => toast.error('No se pudieron cargar las expediciones'))
    }, [])

    // --- Variables ---
    const expedicionesFormatted = useMemo(() => listaExpediciones.map(expedicion => {
        return {
            id: expedicion.id,
            [field.backFechaExpedicion]: expedicion.fechaExpedicion,
            [field.backRazonSocial]: listaClientes.filter(c => c.id === expedicion.idCliente).pop().razonSocial,
            [field.backCantidad]: expedicion.cantidad
        }
    }), [listaClientes, listaExpediciones]);

    return <PageFormTable
        titleForm={"Trazabilidad"}
        form={<FormTrazabilidad
            lote={lote}
            onBuscar={handleBuscar}/>}
        mdForm={6}
        lgForm={5}
        titleTable={"Expediciones"}
        table={<GridTrazabilidad data={expedicionesFormatted}/>}
    />
}