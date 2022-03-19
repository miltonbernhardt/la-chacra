import {
    Box,
    Button,
    Container,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    Grid,
    TextField
} from "@mui/material";

const DialogBajaCliente = ({open, onClose, onSubmit, cliente}) => {

    return (
        <>
            <Dialog open={open} onClose={onClose} scroll="body">
                <DialogTitle>Baja de Cliente</DialogTitle>
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
                                <Grid item xs={12}>
                                    <TextField
                                        id="numeroCliente"
                                        name="numeroCliente"
                                        label="Número de Cliente"
                                        fullWidth
                                        type="text"
                                        variant="outlined"
                                        value={cliente.id}
                                        contentEditable={false}/>
                                </Grid>
                                <Grid item xs={12}>
                                    <TextField
                                        id="razonSocial"
                                        name="razonSocial"
                                        label="Razón Social"
                                        fullWidth
                                        type="text"
                                        variant="outlined"
                                        value={cliente.razonSocial}
                                        contentEditable={false}/>
                                </Grid>
                            </Grid>
                        </Box>
                    </Container>
                </DialogContent>
                <DialogActions>
                    <Button onClick={onClose}>Cancelar</Button>
                    <Button onClick={onSubmit} color="error">Dar de Baja</Button>
                </DialogActions>
            </Dialog>
        </>
    );
}

export default DialogBajaCliente;