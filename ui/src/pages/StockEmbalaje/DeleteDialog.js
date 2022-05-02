import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle } from '@mui/material';
import * as React from 'react';

export const DeleteDialog = ({ onDelete, open, onClose }) => {
    return (
        <>
            <Dialog open={open} onClose={onClose} scroll="body">
                <DialogTitle>Borrar Embalaje</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        Esta acción es irreversible ¿Desea borrar el embalaje?
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={onClose}>Cancelar</Button>
                    <Button onClick={onDelete}>Borrar Embalaje</Button>
                </DialogActions>
            </Dialog>
        </>
    );
}