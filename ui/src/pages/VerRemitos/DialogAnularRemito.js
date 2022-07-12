import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle } from '@mui/material';
import * as React from 'react';

export const DialogAnularRemito = ({ open, onClose, onSubmit }) => {
    return <Dialog open={open} onClose={onClose} scroll="body">
        <DialogTitle>Anular Remito</DialogTitle>
        <DialogContent>
            <DialogContentText>
                Esta acción es irreversible ¿Desea anular el remito?
            </DialogContentText>
        </DialogContent>
        <DialogActions>
            <Button onClick={onClose}>Cancelar</Button>
            <Button onClick={onSubmit}>Anular Remito</Button>
        </DialogActions>
    </Dialog>
}