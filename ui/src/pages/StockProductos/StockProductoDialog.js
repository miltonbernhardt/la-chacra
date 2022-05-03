import {
    Box,
    Button,
    Chip,
    Container,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    Stack,
    Typography
} from '@mui/material';
import * as React from 'react';
import { useEffect, useState } from 'react';
import toast from 'react-hot-toast';
import { getLotesByQuesoWithStock } from '../../services/RestServices';

//TODO: creo que habria que mirar de hacer todas las llamdas de una sola vez
export const StockProductoDialog = ({ open, onClose, queso }) => {

    const [listaLotes, setListaLotes] = useState([]);

    const fetchLotes = () => {
        getLotesByQuesoWithStock(queso.codigo)
            .then(({ data }) => setListaLotes(data))
            .catch(() => toast.error('No se pudo consultar los lotes del producto'))
    }

    useEffect(() => fetchLotes(), []);

    return <>
        <Dialog open={open} onClose={onClose} scroll="body">
            <DialogTitle>
                <Chip
                    avatar={queso.nomenclatura}
                    label={queso.tipoQueso}
                    style={{ backgroundColor: queso.color }}
                />
            </DialogTitle>
            <DialogContent>
                <DialogContentText variant='h4' textAlign="center">
                    Stock: {queso.stock}
                </DialogContentText>
                <Container maxWidth="sm">
                    <Box
                        sx={{
                            width: 250,
                            marginTop: 8,
                            display: 'flex',
                            flexDirection: 'column',
                            alignItems: 'center',
                            mt: 3
                        }}
                    >
                        <Stack width={'100%'} direction="column">
                            <Stack direction="row" justifyContent="space-between">
                                <Typography variant="h6">Lote</Typography>
                                <Typography variant="h6">Stock</Typography>
                            </Stack>
                            {listaLotes.length === 0 ? <Typography>No hay lotes con stock</Typography> :
                                listaLotes.map((lote) => {
                                    return (
                                        <Stack direction="row" justifyContent="space-between">
                                            <Typography variant="h7">{lote.id}</Typography>
                                            <Typography variant="h7">{lote.stockLote}</Typography>
                                        </Stack>
                                    )
                                })}
                        </Stack>
                    </Box>
                </Container>
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose}>Salir</Button>
            </DialogActions>
        </Dialog>
    </>
}
