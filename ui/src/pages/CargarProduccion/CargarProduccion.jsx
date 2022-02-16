import { Paper } from "@mui/material";
import { useState } from "react";
import FeedbackToast from "../../components/FeedbackToast";
import { getAllQuesos, postLote, putLote, deleteLote } from "../../services/RestServices";
import CargarTrazabilidadDialog from "./CargarTrazabilidadDialog";
import LoteForm from "./LoteForm";
import ProduccionGrid from "./ProduccionGrid";
import validator from "validator";
import { useEffect } from "react";
import EliminarLoteDialog from "./EliminarLoteDialog";



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

const errors = {
    400: 'La solicitud es inválida',
    404: 'No se encontró el lote',
    422: 'Los datos enviados no son correctos',
    500: 'Error en el servidor'
}

const CargarProduccion = () => {

    const [lote, setLote] = useState(loteInicial);
    const [listaLotes, setListaLotes] = useState([]);
    const [listaQuesos, setListaQuesos] = useState([]);

    // Dialogs
    const [dialogOpen, setDialogOpen] = useState(false);
    const [isEditingLote, setEditingLote] = useState(false);
    const [eliminarDialog, setEliminarDialog] = useState(false);

    // States for feedback
    const [successMsg, setSuccessMsg] = useState('');
    const [errorMsg, setErrorMsg] = useState();
    const [warningMsg, setWarningMsg] = useState('');
    const [successToastOpen, setSuccessToast] = useState(false);
    const [errorToastOpen, setErrorToast] = useState(false);
    const [warningToastOpen, setWarningToast] = useState(false);

    const fetchQuesos = () => {
        getAllQuesos().then(data => {
            console.log({quesos: data.data})
            /* quesos: {codigo, tipoQueso, nomenclatura, stock}*/
            setListaQuesos(data.data)
        }).catch(e => feedbackErrors(e));
    }

    useEffect(() => { fetchQuesos() }, []);

    const updateStateLote = (attribute, value) => {
        const newLote = { ...lote, [attribute]: value };
        setLote(newLote);
    }

    const onCargar = () => { setDialogOpen(true) }

    const handleSubmit = () => {
        const codigoQueso = lote.codigoQueso.label;
        const newLote = { ...lote, ['codigoQueso']: codigoQueso };
        //-- validation
        if (validarLote()) {
            //-- if is editing
            if (isEditingLote) {
                putLote(newLote).then(res => {
                    //-- update list 
                    const newList = listaLotes.filter((item) => item.id !== lote.id);
                    setListaLotes([...newList, res.data.data]);
                    showSuccess('Actualización Exitosa');
                }).catch(e => feedbackErrors(e));
                setEditingLote(false);
            }
            //-- if is new lote
            else {
                postLote(newLote).then(res => {
                    addToListaLotes(res.data.data);
                    showSuccess('Carga Exitosa');
                }).catch(e => feedbackErrors(e));
            }
            setLote(loteInicial);
        }
        else { //TODO mostrar campos incorrectos o algo
        } setDialogOpen(false);
    }

    const validarLote = () => {
        const current = new Date();
        const date = `${current.getFullYear()}-${current.getMonth() + 1}-${current.getDate()}`;
        if (lote.cantHormas < 1 ||
            validator.isBefore(date, lote.fechaElaboracion) ||
            lote.fechaElaboracion === '' ||
            lote.codigoQueso === '' ||
            lote.litrosLeche < 1 ||
            lote.numeroTina < 1 ||
            lote.peso < 1) {
            showWarning('Los datos ingresados no son validos');
            return false;
        }
        return true;
    }

    const addToListaLotes = (newLote) => {
        setListaLotes([...listaLotes, newLote])
    }

    // --- EDIT LOTE METHODS ---
    const setSelection = (id) => {
        setLote(listaLotes.filter((o) => { return o.id === id }).pop());
        setEditingLote(true);
    }

    const cancelEditing = () => {
        setEditingLote(false);
        setLote(loteInicial);
    }

    const eliminarLote = () => {
        setEliminarDialog(true);
    }

    const handleEliminar = () => {
        deleteLote(lote.id).then(res => {
            showSuccess('Lote Borrado');
            const newList = listaLotes.filter((item) => item.id !== lote.id);
            setListaLotes(newList);
        }).catch(e => feedbackErrors(e));
        setEliminarDialog(false);
        setLote(loteInicial);
        setEditingLote(false);
    }

    // --- TOAST METHODS ---

    const showWarning = (msg) => {
        //todo show all warning
        setWarningMsg(msg);
        setWarningToast(true);
    }

    const showError = (msg) => {
        setErrorMsg(errors[msg]);
        setErrorToast(true);
    }

    const showSuccess = (msg) => {
        setSuccessMsg(msg);
        setSuccessToast(true);
    }

    const feedbackErrors = (error) => {
        try {
            console.log({error2: error})
            showError(errors[error.response.status]);
            if (error.response.status === 422) {
                console.log(error.message);
            }
        } catch {
            setErrorMsg(error.message);
            setErrorToast(true);
        }
    }

    const closeToast = () => {
        setSuccessToast(false);
        setErrorToast(false);
        setWarningToast(false);
    }

    return (
        <>
            <Paper style={{ width: '100%', height: '100%', padding: 2 }}>
                {/* Formulario */}
                <LoteForm
                    quesos={listaQuesos}
                    lote={lote}
                    updateStateLote={updateStateLote}
                    onCargar={onCargar}
                    isEditingLote={isEditingLote}
                    cancelEditing={cancelEditing}
                    updateLote={onCargar}
                    deleteLote={eliminarLote}
                />
                <CargarTrazabilidadDialog
                    open={dialogOpen}
                    onClose={() => { setDialogOpen(false) }}
                    lote={lote}
                    updateStateLote={updateStateLote}
                    onSubmit={handleSubmit} />
                <EliminarLoteDialog
                    open={eliminarDialog}
                    onClose={() => setEliminarDialog(false)}
                    onSubmit={handleEliminar}
                />
                {/* Tabla */}
                <ProduccionGrid
                    quesos={listaQuesos}
                    produccion={listaLotes}
                    setSelection={setSelection} />
                <FeedbackToast
                    msgError={errorMsg}
                    openError={errorToastOpen}
                    closeError={closeToast}
                    msgSuccess={successMsg}
                    openSuccess={successToastOpen}
                    closeSuccess={closeToast}
                    msgWarning={warningMsg}
                    openWarning={warningToastOpen}
                    closeWarning={closeToast}
                />
            </Paper>
        </>
    );
}

export default CargarProduccion;