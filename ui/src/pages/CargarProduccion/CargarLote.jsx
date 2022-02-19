import { Paper, Grid } from "@mui/material";
import { useCallback, useEffect, useState } from "react";
import toast from 'react-hot-toast';
import { deleteLote, getAllQuesos, postLote, putLote } from "../../services/RestServices";
import DialogEliminarLote from "./DialogEliminarLote";
import FormLote from "./FormLote";
import GridLotes from "./GridLotes";
import WhitePage from "../../components/WhitePage";

const loteInicial = {
    id: '',
    fechaElaboracion: '',
    numeroTina: '',//todo validar tina cantidad dig
    litrosLeche: '',
    cantHormas: '',
    peso: '',
    loteCultivo: '',
    loteColorante: '',
    loteCalcio: '',
    loteCuajo: '',
    codigoQueso: ''
}

const CargarProduccion = () => {

    const [lote, setLote] = useState(loteInicial);
    const [listaLotes, setListaLotes] = useState([]);
    const [listaQuesos, setListaQuesos] = useState([]);

    // Dialogs
    const [isEditingLote, setEditingLote] = useState(false);
    const [eliminarDialog, setEliminarDialog] = useState(false);

    const fetchQuesos = useCallback(() => {
        getAllQuesos().then(data => {
            console.log({quesos: data.data})
            /* quesos: {codigo, tipoQueso, nomenclatura, stock}*/
            setListaQuesos(data.data)
        }).catch(e => toast.error(e.response ? e : e.message));//todo
    }, []);

    useEffect(() => {
        fetchQuesos()
    }, []);

    const updateStateLote = useCallback((attribute, value) => {
        const newLote = {...lote, [attribute]: value};
        setLote(newLote);
    }, [lote]);

    const handleSubmit = (newLote) => {
        const codigoQueso = newLote.codigoQueso.label ? newLote.codigoQueso.label : newLote.codigoQueso;
        const loteSubmit = {...newLote, ['codigoQueso']: codigoQueso};
        //-- if is editing
        if (isEditingLote) {
            putLote(loteSubmit).then(({data}) => {
                //-- update list
                const newList = listaLotes.filter((item) => item.id !== lote.id);
                setListaLotes([...newList, data]);
                setLote(loteInicial);
            })
                .catch(e => console.error(e.message));
            setEditingLote(false);
        }
        //-- if is new lote
        else {
            postLote(loteSubmit).then(({data}) => {
                setListaLotes([...listaLotes, data]);
                setLote(loteInicial);
            })
                .catch(e => console.error(e.message));
        }
    }

    // --- EDIT LOTE METHODS ---
    const setSelection = (id) => {
        setLote(listaLotes.filter((o) => {
            return o.id === id
        }).pop());
        setEditingLote(true);
    }

    const cancelEditing = useCallback(() => {
        setEditingLote(false);
        setLote(loteInicial);
    }, []);

    const eliminarLote = useCallback(() => {
        setEliminarDialog(true);
    }, [])

    const handleEliminar = useCallback(() => {
        deleteLote(lote.id).then(res => {
            const newList = listaLotes.filter((item) => item.id !== lote.id);
            setListaLotes(newList);
        }).catch(e => console.error(e.message));
        setEliminarDialog(false);
        setLote(loteInicial);
        setEditingLote(false);
    }, [lote.id, listaLotes]);

    const cancelEliminar = useCallback(() => {
        setEliminarDialog(false);
    }, []);

    // --- VARIABLES
    const quesosAutocomplete = listaQuesos.map((q) => {
        return {
            id: q.id,
            string: q.codigo + ' - ' + q.tipoQueso + ' - ' + q.nomenclatura,
            label: q.codigo
        }
    });

    return (
        <WhitePage
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
        >
            <DialogEliminarLote
                open={eliminarDialog}
                lote={lote}
                onClose={cancelEliminar}
                onSubmit={handleEliminar}/>
        </WhitePage>
    )
}

export default CargarProduccion;