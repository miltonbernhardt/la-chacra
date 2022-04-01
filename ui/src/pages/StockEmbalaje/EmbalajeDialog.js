import {
    Button,
    Container,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    Grid,
    Stack,
    Switch,
    Typography
} from "@mui/material";
import { createRef, useEffect, useState, useMemo, useCallback } from 'react';
import Input from "../../components/Input";
import * as field from "../../resources/fields";
import { toastValidationErrors } from "../../resources/fields";
import * as message from "../../resources/messages";
import * as validation from "../../resources/validations";
import Select from "../../components/Select";
import DeleteIcon from '@mui/icons-material/Delete';

const EmbalajeDialog = ({ embalaje, quesos, open, onClose, onSubmit, onDelete, isNewEmbalaje }) => {

    const [embalajeForm, setEmbalajeForm] = useState(embalaje)

    const [deleteEnabled, setDeleteEnabled] = useState(false)

    const refStock = createRef(null)
    const refSelectTipoEmbalaje = createRef(null)
    const refSelectQueso = createRef(null)

    useEffect(() => {
        setEmbalajeForm(embalaje);
        setDeleteEnabled(false);
    }, [embalaje]);

    const handleChangeSwitch = (evt) => {
        setDeleteEnabled(evt.target.checked);
    }

    const submitEmbalaje = useCallback(() => {
        const errors = new Map();
        const values = {};
        values["id"] = embalajeForm.id

        refStock.current.validate(errors, values, [
            { func: validation.minorToOne, msg: message.valZeroValue }
        ])

        refSelectTipoEmbalaje.current.validate(errors, values, [
            { func: validation.emptySelect, msg: message.valEmptyField }
        ])

        console.log(values);

        if (errors.size > 0) {
            console.error(errors)
            toastValidationErrors(errors)
            return
        }

        const embalajeSubmit = {
            ...embalajeForm,
            [field.backStockEmbalaje]: values.stock,
            [field.backTipoEmbalaje]: values.tipoEmbalaje
        }
        console.log(embalajeSubmit)

        onSubmit(embalajeSubmit)
    }, [embalajeForm, onSubmit, refSelectTipoEmbalaje, refStock])

    const setListaQuesos = useCallback((lista) => {
        const embalaje = { ...embalajeForm, listaQuesos: lista }
        setEmbalajeForm(embalaje);
    }, [embalajeForm])

    const handleDeleteRow = (queso) => {
        const newList = embalajeForm.listaQuesos.filter(item => item.id !== queso.id);
        setListaQuesos(newList);
    }

    const handleAgregarQueso = useCallback(() => {
        var values = {};
        const errors = new Map();
        refSelectQueso.current.validate(errors, values, [
            { func: validation.emptySelect, msg: message.valEmptyField }
        ])
        console.log(values);
        if (errors.size > 0) {
            console.error(errors)
            toastValidationErrors(errors)
            return
        }
        const queso = values.codigoQueso;
        const selectedQueso = quesos.filter(q => q.codigo === queso).pop();
        const newList = [...embalajeForm.listaQuesos, selectedQueso];
        setListaQuesos(newList)
    }, [embalajeForm.listaQuesos, quesos, refSelectQueso, setListaQuesos])


    // --- VARIABLES ---
    const quesosAutocomplete = quesos.map((q) => {
        return {
            id: q.id,
            label: q.codigo + ' - ' + q.tipoQueso + ' - ' + q.nomenclatura,
            value: q.codigo
        }
    });

    const tiposEmbalaje = useMemo(() => {
        return [
            { id: 1, label: 'CAJA', value: 'CAJA' },
            { id: 2, label: 'BOLSA', value: 'BOLSA' }
        ]
    }, [])

    const actions = useMemo(() => {
        return (
            <DialogActions>
                {!isNewEmbalaje ?
                    <>
                        <Grid item xs={12}>
                            <Stack direction="row" justifyContent="right">
                                Habilitar Borrado
                                <Switch
                                    checked={deleteEnabled}
                                    onChange={handleChangeSwitch} />
                            </Stack>
                        </Grid>
                        <Button
                            onClick={onDelete}
                            disabled={!deleteEnabled}>
                            Borrar Embalaje
                        </Button>
                    </>
                    : <></>}
                <Button onClick={onClose}>Cancelar</Button>
                <Button onClick={submitEmbalaje}>
                    {isNewEmbalaje ? "Crear Embalaje" : "Actualizar Embalaje"}
                </Button>
            </DialogActions>)
    }, [deleteEnabled, isNewEmbalaje, onClose, onDelete, submitEmbalaje])

    return (
        <>
            <Dialog
                open={open}
                onClose={onClose}
                scroll="body">
                <DialogTitle>Editar Embalaje</DialogTitle>
                <DialogContent
                    style={{ backgroundColor: deleteEnabled ? "#F0BEBE" : '' }}>
                    <Container maxWidth="sm">
                        <Grid container spacing={2}>

                            <Grid item container spacing={1.5} xs={12} sm={6}>
                                <Select ref={refSelectTipoEmbalaje}
                                    value={embalajeForm.tipoEmbalaje}
                                    id={field.backTipoEmbalaje}
                                    label={field.tipoEmbalaje}
                                    options={tiposEmbalaje}
                                    required />
                                <Input ref={refStock}
                                    id={field.backStockEmbalaje}
                                    label={field.stockEmbalaje}
                                    value={embalajeForm.stock}
                                    required />
                            </Grid>
                            <Grid item container spacing={1.5} xs={12} sm={6}>
                                <Select ref={refSelectQueso}
                                    id={field.backCodigoQueso}
                                    label={field.queso}
                                    options={quesosAutocomplete} />
                                <Grid item xs={12}>
                                    <Button
                                        variant="contained"
                                        fullWidth
                                        onClick={handleAgregarQueso}>Agregar Queso</Button>
                                </Grid>
                                <Grid item xs={12}>
                                    <Stack direction="column">
                                        <Stack direction="row" justifyContent="space-between">
                                            <Typography variant="h6">
                                                Queso
                                            </Typography>
                                            <Typography>
                                            </Typography>
                                        </Stack>
                                        {embalajeForm.listaQuesos.map(queso => {
                                            return (
                                                <Stack direction="row" justifyContent="space-between">
                                                    <Typography>
                                                        {queso.tipoQueso}
                                                    </Typography>
                                                    <Button onClick={() => handleDeleteRow(queso)}>
                                                        <DeleteIcon />
                                                        quitar
                                                    </Button>
                                                </Stack>)
                                        }
                                        )}
                                    </Stack>
                                </Grid>
                            </Grid>
                        </Grid>
                    </Container>
                </DialogContent>
                {actions}
            </Dialog>
        </>
    );
}

export default EmbalajeDialog;