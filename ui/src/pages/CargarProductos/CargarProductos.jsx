import { Button, ButtonGroup, Grid, Typography } from '@mui/material';
import Paper from '@mui/material/Paper';
import { useEffect, useState } from "react";
import FeedbackToast from "../../components/FeedbackToast";
import { getAllQuesos } from "../../services/RestServices";
import CargarProductoDialog from './CargarProductoDialog';
import ProductosGrid from './ProductosGrid';
import EliminarProductoDialog from './EliminarProductoDialog';

const quesoInicial = {
    id: '',
    codigo: '',
    tipoQueso: '',
    nomenclatura: ''
}

const errors = {
    400: 'La solicitud es inválida',
    404: 'No se encontró el lote',
    422: 'Los datos enviados no son correctos',
    500: 'Error en el servidor'
}

const CargarProductos = () => {

    const [queso, setQueso] = useState(quesoInicial);
    const [listaQuesos, setListaQuesos] = useState([]);

    // Dialogs
    const [isOpenCargarProducto, setOpenCargarProducto] = useState(false);
    const [isOpenEliminarProducto, setOpenEliminarProducto] = useState(false);
    // States for feedback
    const [successMsg, setSucsessMsg] = useState('');
    const [errorMsg, setErrorMsg] = useState('');
    const [warningMsg, setWarningMsg] = useState('');
    const [successToastOpen, setSuccessToast] = useState(false);
    const [errorToastOpen, setErrorToast] = useState(false);
    const [warningToastOpen, setWarningToast] = useState(false);


    useEffect(() => { fetchQuesos() }, []);

    const fetchQuesos = () => {
        getAllQuesos().then(quesos => {
            setListaQuesos(quesos.data)
        }).catch(e => feedbackErrors(e));
    }

    const updateStateQueso = (attribute, value) => {
        const newQueso = { ...queso, [attribute]: value };
        setQueso(newQueso);
    }

    const onSubmit = () => {
        if (validarQueso()) {
            if (queso.id === ''); //TODO post
            else;//TODO put
        } else {
        }
        setOpenCargarProducto(false);
    }

    const validarQueso = () => {
        if (queso.codigo === '' ||
            queso.nomenclatura === '' ||
            queso.tipoQueso === '') {
            showWarning('Los datos ingresados no son válidos');
            return false;
        }
        else return true;
    }

    //--- EDIT QUESO METHODS ---
    const openCargarDialog = () => {
        setOpenCargarProducto(true);
        setQueso(quesoInicial);
    }

    const openEditarDialog = () => {
        setOpenCargarProducto(true);
    }

    const openEliminarDialog = () => {
        setOpenEliminarProducto(true);
    }

    const setSelection = (id) => {
        setQueso(listaQuesos.filter((o) => { return o.id === id }).pop());
    }

    //--- FEEDBACK METHODS ---
    const showWarning = (msg) => {
        setWarningMsg(msg);
        setWarningToast(true);
    }

    const showError = (msg) => {
        setErrorMsg(errors[msg]);
        setErrorToast(true);
    }

    const showSuccess = (msg) => {
        setSucsessMsg(msg);
        setSuccessToast(true);
    }

    const feedbackErrors = (error) => {
        try {
            showError(errors[error.response.status]);
            if (error.response.status === 422) {
                console.log(error.message);
            }
        } catch {
            setErrorMsg(error.message);
            setErrorToast(true);
        }
    }

    const closeToast = () => {
        setSuccessToast(false);
        setErrorToast(false);
        setWarningToast(false);
    }

    return (
        <Paper style={{ width: '100%', height: '100%', padding: 2 }}>
            <Grid container columns={2} justifyContent="space-between" padding={2}>
                <Grid item >
                    <Typography variant="h6">Productos</Typography>
                </Grid>
                <Grid item >
                    <ButtonGroup variant="contained">
                        <Button onClick={openEliminarDialog} color="warning">Borrar Producto</Button>
                        <Button onClick={openEditarDialog} color="info">Editar Producto</Button>
                        <Button onClick={openCargarDialog} >Cargar Producto</Button>
                    </ButtonGroup>
                </Grid>
            </Grid>
            <CargarProductoDialog
                open={isOpenCargarProducto}
                queso={queso}
                onClose={() => setOpenCargarProducto(false)}
                updateStateQueso={updateStateQueso}
                onSubmit={onSubmit}
            />
            <EliminarProductoDialog
                open={isOpenEliminarProducto}
                onClose={() => setOpenEliminarProducto(false)}
                queso={queso} />
            <ProductosGrid
                listaQuesos={listaQuesos}
                setSelection={setSelection} />
            <FeedbackToast
                msgError={errorMsg}
                openError={errorToastOpen}
                closeError={closeToast}
                msgSuccess={successMsg}
                openSuccess={successToastOpen}
                closeSuccess={closeToast}
                msgWarning={warningMsg}
                openWarning={warningToastOpen}
                closeWarning={closeToast}
            />
        </Paper >
    );
}

export default CargarProductos;