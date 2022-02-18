import { Button, ButtonGroup, Grid, Typography } from '@mui/material';
import Paper from '@mui/material/Paper';
import { useEffect, useState } from "react";
import FeedbackToast from "../../components/FeedbackToast";
import { deleteQueso, getAllQuesos, postQueso, putQueso } from "../../services/RestServices";
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
            //--- agrego id porque necesita el datagrid y mi codigo
            const listaAux = quesos.data.map((q) => {
                return {
                    id: q.codigo,
                    codigo: q.codigo,
                    nomenclatura: q.nomenclatura,
                    tipoQueso: q.tipoQueso
                }
            })
            setListaQuesos(listaAux)
        }).catch(e => feedbackErrors(e));
    }

    const onSubmit = (quesoSubmit) => {
        if (quesoSubmit.id === '') {
            postQueso(quesoSubmit).then((response) => {
                showSuccess(response.data.message);
                fetchQuesos();
            }).catch(e => feedbackErrors(e));
        }
        else
            putQueso(quesoSubmit).then((response) => {
                showSuccess(response.data.message);
                fetchQuesos();
            }).catch(e => feedbackErrors(e));
        setOpenCargarProducto(false);
    }

    //--- EDIT QUESO METHODS ---
    const openCargarDialog = () => {
        setOpenCargarProducto(true);
        setQueso(quesoInicial);
    }

    const openEditarDialog = () => {
        queso.id === '' ? showWarning("No se ha seleccionado un producto") :
            setOpenCargarProducto(true);
    }

    const openEliminarDialog = () => {
        queso.id === '' ? showWarning("No se ha seleccionado un producto") :
            setOpenEliminarProducto(true);
    }

    const setSelection = (id) => {
        setQueso(listaQuesos.filter((o) => { return o.id === id }).pop());
    }

    const onDelete = () => {
        setOpenEliminarProducto(false);
        deleteQueso(queso.codigo)
            .then((response) => {
                showSuccess(response.data.message);
                fetchQuesos()
            })
            .catch(e => feedbackErrors(e));
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
                onSubmit={onSubmit}
            />
            <EliminarProductoDialog
                open={isOpenEliminarProducto}
                onClose={() => setOpenEliminarProducto(false)}
                queso={queso}
                onBorrar={onDelete} />
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