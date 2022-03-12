import { Typography, Grid, Box, Container, Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, TextField, Stack } from '@mui/material';
import { useState } from 'react';
import BarcodeReader from 'react-barcode-reader'

const ScanDialog = ({ open, onClose, onSubmit }) => {

    const [listaLecturas, setListaLecturas] = useState([]);
    const [pesoExpedicion, setPesoExpedicion] = useState(0);

    const handleScan = (scan) => {
        let lote = scan.substring(0, 12);
        let result1 = scan.substring(13, 20);
        let result2 = scan.substring(20, 23);
        let result3 = `${result1}.${result2}`;
        let peso = parseFloat(result3);
        setListaLecturas([...listaLecturas, { id: scan, lote: lote, peso: peso }])
        setPesoExpedicion(Math.round((pesoExpedicion + peso) * 100) / 100);
    }

    const handleError = () => { alert('Error en la lectura') };

    return (
        <>
            <BarcodeReader
                onScan={handleScan}
                onError={handleError}
            />
            <Dialog open={open} onClose={onClose} scroll="body">
                <DialogTitle>Escanear códigos</DialogTitle>
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
                                <Typography variant="h6" paddingLeft={2}>
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
                                                    <Button>quitar</Button>
                                                </Stack>)
                                        })}
                                    </Stack>
                                </Grid>
                                <Grid item xs={12} sm={6}>
                                    <TextField
                                        fullWidth
                                        label="Cantidad de pizas" />
                                </Grid>
                                <Grid item xs={12} sm={6}>
                                    <TextField
                                        fullWidth
                                        label="Peso total"
                                        value={pesoExpedicion} />
                                </Grid>
                            </Grid>
                        </Box>
                    </Container>
                </DialogContent>
                <DialogActions>
                    <Button onClick={onClose}>Cancelar</Button>
                    <Button onClick={onSubmit}>Cargar Expedición</Button>
                </DialogActions>
            </Dialog>
        </>
    );
}

export default ScanDialog;