import DeleteIcon from '@mui/icons-material/Delete';
import QrCodeScannerIcon from '@mui/icons-material/QrCodeScanner';
import {
    Box,
    Button,
    Container,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    Grid,
    Stack,
    Typography
} from '@mui/material';
import * as React from 'react';
import { createRef, useCallback, useState } from 'react';
import BarcodeReader from 'react-barcode-reader';
import toast from 'react-hot-toast';
import { Input } from '../../components/Input';
import { Select } from "../../components/Select";
import * as field from "../../resources/fields";
import * as message from "../../resources/messages";
import * as validation from "../../resources/validations";

export const ScanDialog = ({ open, onClose, onSubmit, clientes, cliente, fechaExpedicion }) => {

    const [listaLecturas, setListaLecturas] = useState([]);
    const [pesoExpedicion, setPesoExpedicion] = useState(0);

    const refCantidad = createRef()
    const refSelectCliente = createRef()
    const refFechaExpedicion = createRef()

    const handleScan = (scan) => {
        let lote;
        scan.charAt(12) === '0' ?
            lote = scan.substring(0, 12) : lote = scan.substring(0, 13);
        if (listaLecturas.length > 0 && lote !== listaLecturas.at(0).lote) {
            toast.error('Se leyeron etiquetas de lotes distintos');
            return
        }
        let result1 = scan.substring(14, 20);
        let result2 = scan.substring(20, 23);
        let result3 = `${result1}.${result2}`;
        let peso = parseFloat(result3);
        setListaLecturas([...listaLecturas,
            {
                id: listaLecturas.length,
                lote: lote,
                peso: peso
            }])
        setPesoExpedicion(Math.round((pesoExpedicion + peso) * 100) / 100);
    }

    const handleError = useCallback(() => {
        alert('Error en la lectura')
    }, []);

    const handleDeleteRow = (lote) => {
        setPesoExpedicion(Math.round((pesoExpedicion - lote.peso) * 100) / 100);
        const newList = listaLecturas.filter(item => item.id !== lote.id);
        setListaLecturas(newList);
    }

    const handleCargar = useCallback(() => {
        const errors = new Map();
        const values = {};

        refSelectCliente.current.validate(errors, values, [
            { func: validation.emptySelect, msg: message.valEmptyField }
        ])

        refCantidad.current.validate(errors, values, [
            { func: validation.minorToOne, msg: message.valZeroValue }])

        refFechaExpedicion.current.validate(errors, values, [
            { func: validation.empty, msg: message.valEmptyFecha },
            { func: validation.olderDate, msg: message.valOlderDate }
        ])

        if (listaLecturas.length === 0) {
            toast.error('No hay etiquetas para cargar');
            return;
        }

        if (errors.size > 0) {
            console.error(errors)
            field.toastValidationErrors(errors)
            return
        }

        values[field.backIdLote] = listaLecturas.at(0).lote;
        values[field.backPesoExpedicion] = pesoExpedicion;

        onSubmit(values)
        setListaLecturas([]);
    }, [listaLecturas, onSubmit, pesoExpedicion, refCantidad, refFechaExpedicion, refSelectCliente]);

    const handleCancelar = useCallback(() => {
        setListaLecturas([]);
        onClose();
    }, [onClose])

    return (
        <>
            <BarcodeReader
                onScan={handleScan}
                onError={handleError}/>
            <Dialog open={open} onClose={onClose} scroll="body">
                <DialogTitle>
                    <Stack direction="row" spacing={2}>
                        <QrCodeScannerIcon variant="outlined" fontSize='large'/>
                        <Typography variant="h6">
                            Escanear etiquetas
                        </Typography>
                    </Stack>
                </DialogTitle>
                <DialogContent>
                    <Container maxWidth="sm">
                        <Box
                            sx={{
                                marginTop: 8,
                                display: 'flex',
                                flexDirection: 'column',
                                alignItems: 'center',
                                mt: 3
                            }}
                        >
                            <Grid container spacing={2}>
                                <Input ref={refFechaExpedicion}
                                       id={field.backFechaExpedicion}
                                       label={field.fechaExpedicion}
                                       value={fechaExpedicion}
                                       type="date"
                                       required/>
                                <Select ref={refSelectCliente}
                                        id={field.backIdCliente}
                                        label={field.cliente}
                                        value={cliente}
                                        options={clientes}
                                        required/>
                                <Typography variant="h6" paddingLeft={2} mt={2}>
                                    Códigos escaneados
                                </Typography>
                                <Grid item xs={12}>
                                    <Stack direction="column">
                                        <Stack direction="row" justifyContent="space-between">
                                            <Typography variant="h6">
                                                Lote
                                            </Typography>
                                            <Typography variant="h6">
                                                Peso
                                            </Typography>
                                            <Typography>
                                            </Typography>
                                        </Stack>
                                        {listaLecturas.map(lote => {
                                            return (
                                                <Stack direction="row" justifyContent="space-between">
                                                    <Typography>
                                                        {lote.lote}
                                                    </Typography>
                                                    <Typography>
                                                        {lote.peso}
                                                    </Typography>
                                                    <Button onClick={() => handleDeleteRow(lote)}>
                                                        <DeleteIcon/>
                                                        quitar
                                                    </Button>
                                                </Stack>)
                                        })}
                                    </Stack>
                                </Grid>
                                <Input ref={refCantidad}
                                       id={field.backCantidad}
                                       label={field.cantidad}
                                       sm={6}
                                       required/>
                                <Input
                                    id={field.backPesoExpedicion}
                                    label={field.pesoExpedicion}
                                    value={pesoExpedicion}
                                    sm={6}
                                    contentEditable={false}/>
                            </Grid>
                        </Box>
                    </Container>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCancelar}>Cancelar</Button>
                    <Button onClick={handleCargar}>Cargar Expedición</Button>
                </DialogActions>
            </Dialog>
        </>
    );
}