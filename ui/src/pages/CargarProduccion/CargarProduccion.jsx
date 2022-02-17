import { Paper } from "@mui/material";
import { useCallback, useEffect, useState } from "react";
import toast from 'react-hot-toast';
import { deleteLote, getAllQuesos, postLote, putLote } from "../../services/RestServices";
import EliminarLoteDialog from "./EliminarLoteDialog";
import LoteForm from "./LoteForm";
import ProduccionGrid from "./ProduccionGrid";

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

const CargarProduccion = () => {

    const [lote, setLote] = useState(loteInicial);
    const [listaLotes, setListaLotes] = useState([]);
    const [listaQuesos, setListaQuesos] = useState([]);

    // Dialogs
    // const [dialogOpen, setDialogOpen] = useState(false);
    const [isEditingLote, setEditingLote] = useState(false);
    const [eliminarDialog, setEliminarDialog] = useState(false);

    const fetchQuesos = useCallback(() => {
        getAllQuesos().then(data => {
            console.log({ quesos: data.data })
            /* quesos: {codigo, tipoQueso, nomenclatura, stock}*/
            setListaQuesos(data.data)
        }).catch(e => toast.error(e.response ? e : e.message));//todo
    }, []);

    useEffect(() => {
        fetchQuesos()
    }, []);

    const updateStateLote = useCallback((attribute, value) => {
        const newLote = { ...lote, [attribute]: value };
        setLote(newLote);
    }, [lote]);

    const successfulUpdate = 'Actualización exitosa'
    const successfulLoad = 'Carga exitosa'

    const handleSubmit = (newLote) => {
        const codigoQueso = newLote.codigoQueso.label ? newLote.codigoQueso.label : newLote.codigoQueso;
        const loteSubmit = { ...newLote, ['codigoQueso']: codigoQueso };
        //-- if is editing
        if (isEditingLote) {
            putLote(loteSubmit).then(res => {
                //-- update list 
                const newList = listaLotes.filter((item) => item.id !== lote.id);
                setListaLotes([...newList, res.data.data]);
                toast.success(successfulUpdate);
                setLote(loteInicial);
            }).catch(e => toast.error(e));
            setEditingLote(false);
        }
        //-- if is new lote
        else {
            postLote(loteSubmit).then(res => {
                addToListaLotes(res.data.data);
                toast.success(successfulLoad);
                setLote(loteInicial);
            }).catch(e => toast.error((e)));
        }
    }

    const addToListaLotes = (newLote) => {
        setListaLotes([...listaLotes, newLote])
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
            toast.success('Lote Borrado');//todo
            const newList = listaLotes.filter((item) => item.id !== lote.id);
            setListaLotes(newList);
        }).catch(e => toast.error(e));//todos
        setEliminarDialog(false);
        setLote(loteInicial);
        setEditingLote(false);
    }, [lote.id]);

    // --- VARIABLES 
    const quesosAutocomplete = listaQuesos.map((q) => {
        return {
            id: q.id,
            string: q.codigo + ' - ' + q.tipoQueso + ' - ' + q.nomenclatura,
            label: q.codigo
        }
    });

    return (
        <>
            <Paper style={{ width: '100%', height: '100%', padding: 2 }}>
                {/* Formulario */}
                <LoteForm
                    quesos={quesosAutocomplete}
                    lote={lote}
                    updateStateLote={updateStateLote}
                    setLote={setLote}
                    isEditingLote={isEditingLote}
                    cancelEditing={cancelEditing}
                    deleteLote={eliminarLote}
                    handleSubmit={handleSubmit} />
                <EliminarLoteDialog
                    open={eliminarDialog}
                    lote={lote}
                    onClose={() => setEliminarDialog(false)}
                    onSubmit={handleEliminar} />
                {/* Tabla */}
                <ProduccionGrid
                    quesos={listaQuesos}
                    produccion={listaLotes}
                    setSelection={setSelection}
                />
                {/*<FeedbackToast*/}
                {/*    msgError={errorMsg}*/}
                {/*    openError={errorToastOpen}*/}
                {/*    closeError={closeToast}*/}
                {/*    msgSuccess={successMsg}*/}
                {/*    openSuccess={successToastOpen}*/}
                {/*    closeSuccess={closeToast}*/}
                {/*    msgWarning={warningMsg}*/}
                {/*    openWarning={warningToastOpen}*/}
                {/*    closeWarning={closeToast} />*/}
            </Paper>
        </>
    );
}

export default CargarProduccion;