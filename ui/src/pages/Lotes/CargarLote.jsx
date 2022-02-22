import { useCallback, useEffect, useState } from "react";
import toast from 'react-hot-toast';
import { deleteLote, getAllQuesos, postLote, putLote } from "../../services/RestServices";
import DialogEliminarLote from "./DialogEliminarLote";
import FormLote from "./FormLote";
import GridLotes from "./GridLotes";
import PageFormTable from "../../components/PageFormTable";

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
    codigoQueso: ""
}

const CargarProduccion = () => {
    const [lote, setLote] = useState(loteInicial);
    const [listaLotes, setListaLotes] = useState([]);
    const [listaQuesos, setListaQuesos] = useState([]);

    const [isEditingLote, setEditingLote] = useState(false);
    const [eliminarDialog, setEliminarDialog] = useState(false);

    const fetchQuesos = useCallback(() => {
        getAllQuesos().then(data => {
            console.log({quesos: data.data})
            setListaQuesos(data.data)
        }).catch(e => toast.error(e.response ? e : e.message));
    }, []);

    useEffect(() => {
        fetchQuesos()
    }, []);

    const updateStateLote = useCallback((attribute, value) => {
        const newLote = {...lote, [attribute]: value};
        setLote(newLote);
    }, [lote]);

    const handleSubmit = (newLote) => {
        const loteSubmit = {...newLote, ['codigoQueso']: newLote.codigoQueso};
        if (isEditingLote) {
            putLote(loteSubmit)
                .then(({data}) => {
                    const newList = listaLotes.filter((item) => item.id !== lote.id);
                    setListaLotes([...newList, data]);
                })
                .catch(e => console.error(e.message));
            setEditingLote(false);
        } else {
            postLote(loteSubmit)
                .then(({data}) => {
                    setListaLotes([...listaLotes, data]);
                    setLote(loteInicial);
                })
                .catch(e => console.error(e.message));
        }
    }

    const setSelection = (id) => {
        setLote(listaLotes.filter((o) => o.id === id).pop())
        setEditingLote(true);
    }

    const cancelEditing = useCallback(() => {
        setEditingLote(false);
        setLote(loteInicial);
    }, []);

    const eliminarLote = useCallback(() => setEliminarDialog(true), [])

    const handleEliminar = useCallback(() => {
        deleteLote(lote.id).then(() => {
            const newList = listaLotes.filter((item) => item.id !== lote.id);
            setListaLotes(newList);
        }).catch(e => console.error(e.message));
        setEliminarDialog(false);
        setLote(loteInicial);
        setEditingLote(false);
    }, [lote.id, listaLotes]);

    const cancelEliminar = useCallback(() => setEliminarDialog(false), []);

    const quesosAutocomplete = listaQuesos.map((q) => {
        return {
            id: q.id,
            label: q.codigo + ' - ' + q.tipoQueso + ' - ' + q.nomenclatura,
            value: q.codigo
        }
    });

    return (
        <PageFormTable
            form={
                <FormLote
                    quesos={quesosAutocomplete}
                    lote={lote}
                    updateStateLote={updateStateLote}
                    setLote={setLote}
                    isEditingLote={isEditingLote}
                    cancelEditing={cancelEditing}
                    deleteLote={eliminarLote}
                    handleSubmit={handleSubmit}/>
            }
            table={
                <GridLotes
                    quesos={listaQuesos}
                    produccion={listaLotes}
                    setSelection={setSelection}/>
            }
            titleTable="Producción ingresada"
        >
            <DialogEliminarLote
                open={eliminarDialog}
                lote={lote}
                onClose={cancelEliminar}
                onSubmit={handleEliminar}/>
        </PageFormTable>
    )
}

export default CargarProduccion;