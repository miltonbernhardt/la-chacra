import {Paper} from "@mui/material";
import {useState} from "react";
import {getAllQuesos, postLote, putLote, deleteLote} from "../../services/RestServices";
import CargarTrazabilidadDialog from "./CargarTrazabilidadDialog";
import LoteForm from "./LoteForm";
import ProduccionGrid from "./ProduccionGrid";
import validator from "validator";
import {useEffect} from "react";
import EliminarLoteDialog from "./EliminarLoteDialog";
import toast from 'react-hot-toast';
import * as message from "../../messages";
import * as field from "../../fields";

const loteInicial = {
    id: 1,
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

//todo mover estas constantes a un archivo aparte

const successfulDelete = 'Lote borrado'

const CargarProduccion = () => {

    const [lote, setLote] = useState(loteInicial);
    const [listaLotes, setListaLotes] = useState([]);
    const [listaQuesos, setListaQuesos] = useState([]);

    // Dialogs
    const [dialogOpen, setDialogOpen] = useState(false);
    const [isEditingLote, setEditingLote] = useState(false);
    const [eliminarDialog, setEliminarDialog] = useState(false);

    const fetchQuesos = () => {
        getAllQuesos().then(data => {
            console.log({quesos: data.data})
            /* quesos: {codigo, tipoQueso, nomenclatura, stock}*/
            setListaQuesos(data.data)
        }).catch(e => {
            console.error(e)
            toast.error(e.response ? e : e.message)//todo
        });
    }

    useEffect(() => {
        fetchQuesos()
    }, []);

    const updateStateLote = (attribute, value) => {
        const newLote = {...lote, [attribute]: value};
        setLote(newLote);
    }

    const onCargar = () => {
        if (validarLote()) setDialogOpen(true);
    }

    const handleSubmit = () => {
        const codigoQueso = lote.codigoQueso.label ? lote.codigoQueso.label : lote.codigoQueso;
        const newLote = {...lote, ['codigoQueso']: codigoQueso};

        if (validarLote()) {
            //-- if is editing
            if (isEditingLote) {
                putLote(newLote)
                    .then(({data}) => {
                        const newList = listaLotes.filter((item) => item.id !== lote.id);
                        setListaLotes([...newList, data]);
                        setLote(loteInicial);
                    })
                    .catch(e => console.error(e.message));
                setEditingLote(false);
            }
            //-- if is new lote
            else {
                postLote(newLote)
                    .then(({data}) => {
                        setListaLotes([...listaLotes, data])
                        setLote(loteInicial);
                    })
                    .catch(e => console.error(e.message));
            }
        }
        setDialogOpen(false);
    }

    const validarLote = () => {
        const current = new Date();
        const date = `${current.getFullYear()}-${current.getMonth() + 1}-${current.getDate()}`;

        const errors = new Map();

        if (lote.fechaElaboracion === '')
            errors.set(field.fechaElaboracion, message.valEmptyFecha)
        else if (validator.isBefore(date, lote.fechaElaboracion))
            errors.set(field.fechaElaboracion, message.valOlderDate)

        if (lote.cantHormas < 1)
            errors.set(field.cantidadHormas, message.valZeroValue)

        if (lote.litrosLeche < 1)
            errors.set(field.litrosLeche, message.valZeroValue)

        if (lote.numeroTina < 1)
            errors.set(field.numeroTina, message.valZeroValue)

        if (lote.peso < 1)
            errors.set(field.peso, message.valZeroValue)

        if (lote.codigoQueso === '')
            errors.set(field.queso, message.valEmptyField)

        if (errors.size > 0) {
            console.error({errors})
            errors.forEach(function (msg, field) {
                toast.error(`${field}: ${msg}`)
            })
            return false;
        }
        return true;
    }

    // --- EDIT LOTE METHODS ---
    const setSelection = (id) => {
        setLote(listaLotes.filter((o) => {
            return o.id === id
        }).pop());
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
            console.log({res})
            toast.success(successfulDelete);
            const newList = listaLotes.filter((item) => item.id !== lote.id);
            setListaLotes(newList);
        }).catch(e => {
            toast.error(e)
            console.error({e})
        });//todos
        setEliminarDialog(false);
        setLote(loteInicial);
        setEditingLote(false);
    }

    return (
        <>
            <Paper style={{width: '100%', height: '100%', padding: 2}}>
                {/* Formulario */}
                <LoteForm
                    quesos={listaQuesos}
                    lote={lote}
                    updateStateLote={updateStateLote}
                    onCargar={onCargar}
                    isEditingLote={isEditingLote}
                    cancelEditing={cancelEditing}
                    updateLote={onCargar}
                    deleteLote={eliminarLote}/>
                <CargarTrazabilidadDialog
                    open={dialogOpen}
                    onClose={() => {
                        setDialogOpen(false)
                    }}
                    lote={lote}
                    updateStateLote={updateStateLote}
                    onSubmit={handleSubmit}
                    isEditing={isEditingLote}/>
                <EliminarLoteDialog
                    open={eliminarDialog}
                    lote={lote}
                    onClose={() => setEliminarDialog(false)}
                    onSubmit={handleEliminar}/>
                {/* Tabla */}
                <ProduccionGrid
                    quesos={listaQuesos}
                    produccion={listaLotes}
                    setSelection={setSelection}/>
            </Paper>
        </>
    );
}

export default CargarProduccion;