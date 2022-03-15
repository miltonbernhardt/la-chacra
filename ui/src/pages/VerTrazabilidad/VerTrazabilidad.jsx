import GridTrazabilidad from "./GridTrazabilidad";
import FormTrazabilidad from "./FormTrazabilidad";
import PageFormTable from "../../components/PageFormTable";
import { produccion as data } from "../../data/data";
import { useCallback, useEffect, useMemo, useState } from "react";
import { getLote, getExpedicionesByLote, getAllClientes } from "../../services/RestServices";
import toast from "react-hot-toast";
import * as field from "../../resources/fields";

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

const VerTrazabilidad = () => {

    const [lote, setLote] = useState(loteInicial);
    const [listaExpediciones, setListaExpediciones] = useState([])
    const [listaClientes, setListaClientes] = useState([])

    useEffect(() => fetchClientes(), []);

    const fetchClientes = () => {
        getAllClientes()
            .then(({ data }) => {
                setListaClientes(data)
            })
            .catch(() => toast.error("No se pudo cargar clientes"))
    }

    const handleBuscar = useCallback((idLote) => {
        getLote(idLote)
            .then(({ data }) => setLote(data))
            .catch(() => toast.error('No se pudo cargar el lote'))
        getExpedicionesByLote(idLote)
            .then(({ data }) => setListaExpediciones(data))
            .catch(() => toast.error('No se pudieron cargar las expediciones'))
    }, [])

    // --- Variables ---
    const expedicionesFormatted = useMemo(() =>
        listaExpediciones.map(expedicion => {
            return {
                id: expedicion.id,
                [field.backFechaExpedicion]: expedicion.fechaExpedicion,
                [field.backRazonSocial]: listaClientes.filter(c => c.id === expedicion.idCliente).pop().razonSocial,
                [field.backCantidad]: expedicion.cantidad
            }
        }), [listaClientes, listaExpediciones]);

    return (
        <>
            <PageFormTable
                titleForm={"Trazabilidad"}
                form={
                    <FormTrazabilidad
                        lote={lote}
                        onBuscar={handleBuscar} />
                }
                sizeForm={5}
                titleTable={"Expediciones"}
                table={
                    <GridTrazabilidad data={expedicionesFormatted} />
                }
            />
        </>
    );
}

export default VerTrazabilidad;