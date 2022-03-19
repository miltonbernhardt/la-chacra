import {Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle} from '@mui/material';

const DialogEliminarExpedicion = ({open, onClose, onSubmit}) => {
    return (<>
        <Dialog open={open} onClose={onClose} scroll="body">
            <DialogTitle>Borrar Expedición</DialogTitle>
            <DialogContent>
                <DialogContentText>
                    Esta acción es irreversible ¿Desea borrar la expedición?
                </DialogContentText>
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose}>Cancelar</Button>
                <Button onClick={onSubmit}>Borrar Expedición</Button>
            </DialogActions>
        </Dialog>
    </>);
}

export default DialogEliminarExpedicion;