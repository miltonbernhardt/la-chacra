import { Paper } from "@mui/material";
import { useState } from "react";
import { getAllQuesos, postLote, putLote, deleteLote } from "../../services/RestServices";
import CargarTrazabilidadDialog from "./CargarTrazabilidadDialog";
import LoteForm from "./LoteForm";
import ProduccionGrid from "./ProduccionGrid";
import validator from "validator";
import { useEffect } from "react";
import EliminarLoteDialog from "./EliminarLoteDialog";
import toast from 'react-hot-toast';

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
    const [dialogOpen, setDialogOpen] = useState(false);
    const [isEditingLote, setEditingLote] = useState(false);
    const [eliminarDialog, setEliminarDialog] = useState(false);

    const fetchQuesos = () => {
        getAllQuesos().then(data => {
            console.log({ quesos: data.data })
            /* quesos: {codigo, tipoQueso, nomenclatura, stock}*/
            setListaQuesos(data.data)
        }).catch(e => toast.error(e.response ? e : e.message));//todo
    }

    useEffect(() => {
        fetchQuesos()
    }, []);

    const updateStateLote = (attribute, value) => {
        const newLote = { ...lote, [attribute]: value };
        setLote(newLote);
    }

    const onCargar = () => {
        if (validarLote()) setDialogOpen(true);
    }

    const successfulUpdate = 'Actualización exitosa'
    const successfulLoad = 'Carga exitosa'

    const handleSubmit = () => {
        const codigoQueso = lote.codigoQueso.label ? lote.codigoQueso.label : lote.codigoQueso;
        const newLote = { ...lote, ['codigoQueso']: codigoQueso };
        //-- validation
        if (validarLote()) {
            //-- if is editing
            if (isEditingLote) {
                putLote(newLote).then(res => {
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
                postLote(newLote).then(res => {
                    addToListaLotes(res.data.data);
                    toast.success(successfulLoad);
                    setLote(loteInicial);
                }).catch(e => toast.error((e)));
            }
        } else { //TODO mostrar campos incorrectos o algo
        }
        setDialogOpen(false);
    }

    const validarLote = () => {
        const current = new Date();
        const date = `${current.getFullYear()}-${current.getMonth() + 1}-${current.getDate()}`;

        const errors = new Map();

        //todo mover estas constantes a un archivo aparte
        const fieldFechaElaboracion = "Fecha de elaboración"
        const fieldHormas = "Cantidad de Hormas"
        const fieldLitrosLeche = "Litros procesados"
        const fieldNumeroTina = "Número de tina"
        const fieldPeso = "Peso del lote"
        const fieldQueso = "Tipo de queso"

        const valEmptyFecha = "Debe elegirse una fecha"
        const valOlderDate = "La fecha no debe ser posterior al día de hoy"
        const valZeroValue = "No puede ser menor a 1"
        const valEmptyCodigoQueso = "No puede estar vacío"

        if (lote.fechaElaboracion === '') {
            errors.set(fieldFechaElaboracion, valEmptyFecha)
        } else if (validator.isBefore(date, lote.fechaElaboracion)) {
            errors.set(fieldFechaElaboracion, valOlderDate)
        }

        if (lote.cantHormas < 1) {
            errors.set(fieldHormas, valZeroValue)
        }

        if (lote.litrosLeche < 1) {
            errors.set(fieldLitrosLeche, valZeroValue)
        }

        if (lote.numeroTina < 1) {
            errors.set(fieldNumeroTina, valZeroValue)
        }

        if (lote.peso < 1) {
            errors.set(fieldPeso, valZeroValue)
        }

        if (lote.codigoQueso === '') {
            errors.set(fieldQueso, valEmptyCodigoQueso)
        }


        if (errors.size > 0) {
            errors.forEach(function (msg, field) {
                console.log(`${field}: ${msg}`)
                toast.error(`${field}: ${msg}`)
            })
            return false;
        }
        return true;
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

    const cancelEditing = () => {
        setEditingLote(false);
        setLote(loteInicial);
    }

    const eliminarLote = () => {
        setEliminarDialog(true);
    }

    const handleEliminar = () => {
        deleteLote(lote.id).then(res => {
            toast.success('Lote Borrado');//todo
            const newList = listaLotes.filter((item) => item.id !== lote.id);
            setListaLotes(newList);
        }).catch(e => toast.error(e));//todos
        setEliminarDialog(false);
        setLote(loteInicial);
        setEditingLote(false);
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
                    deleteLote={eliminarLote} />
                <CargarTrazabilidadDialog
                    open={dialogOpen}
                    onClose={() => {
                        setDialogOpen(false)
                    }}
                    lote={lote}
                    updateStateLote={updateStateLote}
                    onSubmit={handleSubmit}
                    isEditing={isEditingLote} />
                <EliminarLoteDialog
                    open={eliminarDialog}
                    lote={lote}
                    onClose={() => setEliminarDialog(false)}
                    onSubmit={handleEliminar} />
                {/* Tabla */}
                <ProduccionGrid
                    quesos={listaQuesos}
                    produccion={listaLotes}
                    setSelection={setSelection} />
            </Paper>
        </>
    );
}

export default CargarProduccion;