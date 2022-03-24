import {Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle} from '@mui/material';

const DialogEliminarLote = ({open, onClose, onSubmit, lote}) => {
    return (
        <>
            <Dialog open={open} onClose={onClose} scroll="body">
                <DialogTitle>Borrar Lote</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        Esta acción es irreversible ¿Desea borrar el lote {lote.id}?
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={onClose}>Cancelar</Button>
                    <Button onClick={onSubmit}>Borrar Lote</Button>
                </DialogActions>
            </Dialog>
        </>
    );
}

export default DialogEliminarLote;