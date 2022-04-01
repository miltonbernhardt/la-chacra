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

const EmbalajeDialog = ({ embalaje, quesos, open, onClose, onSubmit, onDelete }) => {

    const [embalajeForm, setEmbalajeForm] = useState(embalaje)

    const [deleteEnabled, setDeleteEnabled] = useState(false)

    const refStock = createRef(null)
    const refSelectTipoEmbalaje = createRef(null)
    const refSelectQueso = createRef(null)

    useEffect(() => {
        setEmbalajeForm(embalaje);
    }, [embalaje]);

    const handleChangeSwitch = (evt) => {
        setDeleteEnabled(evt.target.checked);
    }

    const submitEmbalaje = () => {
        const errors = new Map();
        const values = {};
        values["id"] = embalajeForm.id

        refStock.current.validate(errors, values, [
            { func: validation.minorToOne, msg: message.valZeroValue }
        ])
        refSelectTipoEmbalaje.current.validate(errors, values, [
            { func: validation.emptySelect, msg: message.valEmptyField }
        ])
        if (errors.size > 0) {
            console.error(errors)
            toastValidationErrors(errors)
            return
        }
        //TODO
        // const embalajeSubmit = {
        //     ...embalaje,
        //     [field.backLitrosLeche]: values.litrosLeche,
        //     [field.backCantHormas]: values.cantHormas,
        //     [field.backCantCajas]: values.cantCajas,
        //     [field.backPeso]: values.peso,
        //     [field.backEmbalajeCuajo]: values.embalajeCuajo,
        //     [field.backEmbalajeColorante]: values.embalajeColorante,
        //     [field.backEmbalajeCalcio]: values.embalajeCalcio,
        //     [field.backEmbalajeCultivo]: values.embalajeCultivo,
        // }

        // onSubmit(embalajeSubmit)
    }

    const setListaQuesos = useCallback((lista) => {
        const embalaje = { ...embalajeForm, listaQuesos: lista }
        setEmbalajeForm(embalaje);
    }, [embalajeForm])

    const handleDeleteRow = (queso) => {
        const newList = embalajeForm.listaQuesos.filter(item => item.id !== queso.id);
        setListaQuesos(newList);
    }

    // --- VARIABLES
    const tiposEmbalaje = useMemo(() => {
        return [
            { id: 1, label: 'CAJA', value: 'CAJA' },
            { id: 2, label: 'BOLSA', value: 'BOLSA' }
        ]
    }, [])

    return (
        <>
            <Dialog
                open={open}
                onClose={onClose}
                scroll="body">
                <DialogTitle>Editar Producción</DialogTitle>
                <DialogContent
                    style={{ backgroundColor: deleteEnabled ? "#F0BEBE" : '' }}>
                    <Container maxWidth="sm">
                        <Grid container spacing={2}>
                            <Grid item xs={8}>
                                <Typography variant="h6" paddingLeft={2}>
                                    Embalaje: {embalaje.id}
                                </Typography>
                            </Grid>
                            <Grid item xs={3}>
                                <Stack direction="row" justifyContent="right">
                                    Habilitar Borrado
                                    <Switch
                                        checked={deleteEnabled}
                                        onChange={handleChangeSwitch} />
                                </Stack>
                            </Grid>
                            <Grid item container spacing={1.5} xs={12} sm={6}>
                                <Select ref={refSelectTipoEmbalaje}
                                    value={embalajeForm.tipoEmbalaje}
                                    id={field.tipoEmbalaje}
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
                                    options={quesos}
                                    required />
                                <Grid item xs={12}>
                                    <Stack direction="column">
                                        <Stack direction="row" justifyContent="space-between">
                                            <Typography variant="h6">
                                                Queso
                                            </Typography>
                                            <Typography>
                                            </Typography>
                                        </Stack>
                                        {embalaje.listaQuesos.map(queso => {
                                            return (
                                                <Stack direction="row" justifyContent="space-between">
                                                    <Typography>
                                                        {queso.tipoQueso}
                                                    </Typography>
                                                    <Button onClick={() => handleDeleteRow(queso)}>
                                                        <DeleteIcon />
                                                        quitar
                                                    </Button>
                                                </Stack>
                                            )
                                        }
                                        )}
                                    </Stack>
                                </Grid>
                            </Grid>
                        </Grid>
                    </Container>
                </DialogContent>
                <DialogActions>
                    <Button onClick={onDelete} disabled={!deleteEnabled}>Borrar Embalaje</Button>
                    <Button onClick={onClose}>Cancelar</Button>
                    <Button onClick={submitEmbalaje}>Actualizar Embalaje</Button>
                </DialogActions>
            </Dialog>
        </>
    );
}

export default EmbalajeDialog;