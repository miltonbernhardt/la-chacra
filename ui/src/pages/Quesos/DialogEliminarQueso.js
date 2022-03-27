import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle } from '@mui/material';
import * as React from 'react';

export const DialogEliminarQueso = ({ open, onClose, queso, onBorrar }) => {
    return (
        <>
            <Dialog open={open} onClose={onClose} scroll="body">
                <DialogTitle>Borrar Producto</DialogTitle>
                <DialogContent>
                    <DialogContentText paddingBottom={2}>
                        Esta acción es irreversible ¿Desea borrar el producto {queso.tipoQueso} del sistema?
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={onClose}>Cancelar</Button>
                    <Button onClick={onBorrar}>Borrar Producto</Button>
                </DialogActions>
            </Dialog>
        </>
    );
}