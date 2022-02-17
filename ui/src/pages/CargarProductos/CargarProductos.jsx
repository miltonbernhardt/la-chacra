import {Button, ButtonGroup, Grid, Typography} from '@mui/material';
import Paper from '@mui/material/Paper';
import {useEffect, useState} from "react";
import {deleteQueso, getAllQuesos, postQueso, putQueso} from "../../services/RestServices";
import CargarProductoDialog from './CargarProductoDialog';
import ProductosGrid from './ProductosGrid';
import EliminarProductoDialog from './EliminarProductoDialog';
import toast from 'react-hot-toast';
import * as message from "../../messages";
import {toastValidationErrors} from "../../fields";

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
    const fieldCodigo = "Código"
    const fieldNomenclatura = "Nomenclatura"
    const fieldTipoQueso = "Tipo queso"

    const [queso, setQueso] = useState(quesoInicial);
    const [listaQuesos, setListaQuesos] = useState([]);

    // Dialogs
    const [isOpenCargarProducto, setOpenCargarProducto] = useState(false);
    const [isOpenEliminarProducto, setOpenEliminarProducto] = useState(false);

    useEffect(() => {
        fetchQuesos()
    }, []);

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

    const updateStateQueso = (attribute, value) => {
        const newQueso = {...queso, [attribute]: value};
        setQueso(newQueso);
    }

    const onSubmit = () => {
        if (validarQueso()) {
            if (queso.id === '') {
                postQueso(queso).then((response) => {
                    toast.success(response.data.message);
                    fetchQuesos();
                }).catch(e => feedbackErrors(e));
            } else {
                putQueso(queso).then((response) => {
                    toast.success(response.data.message);
                    fetchQuesos();
                }).catch(e => feedbackErrors(e));
            }
        }
        setOpenCargarProducto(false);
    }

    const validarQueso = () => {
        const errors = new Map();

        if (queso.codigo === '')
            errors.set(fieldCodigo, message.valEmptyField)

        if (queso.nomenclatura === '')
            errors.set(fieldNomenclatura,  message.valEmptyField)

        if (queso.tipoQueso === '')
            errors.set(fieldTipoQueso,  message.valEmptyField)

        if (errors.size > 0) {
            console.error({errors})
            toastValidationErrors(errors)
            return false;
        }
        return true;
    }

    //--- EDIT QUESO METHODS ---
    const openCargarDialog = () => {
        setOpenCargarProducto(true);
        setQueso(quesoInicial);
    }

    const openEditarDialog = () => {
        queso.id === '' ? toast.error("No se ha seleccionado un producto") :
            setOpenCargarProducto(true);
    }

    const openEliminarDialog = () => {
        queso.id === '' ? toast.error("No se ha seleccionado un producto") :
            setOpenEliminarProducto(true);
    }

    const setSelection = (id) => {
        setQueso(listaQuesos.filter((o) => {
            return o.id === id
        }).pop());
    }

    const onDelete = () => {
        setOpenEliminarProducto(false);
        deleteQueso(queso.codigo)
            .then((response) => {
                toast.success(response.data.message);
                fetchQuesos()
            })
            .catch(e => feedbackErrors(e));
    }

    const feedbackErrors = (error) => { //todo
        try {
            toast.error(errors[error.response.status]);
            if (error.response.status === 422) {
                console.log(error.message);
            }
        } catch {
            toast.error(error.message)
        }
    }

    return (
        <Paper style={{width: '100%', height: '100%', padding: 2}}>
            <Grid container columns={2} justifyContent="space-between" padding={2}>
                <Grid item>
                    <Typography variant="h6">Productos</Typography>
                </Grid>
                <Grid item>
                    <ButtonGroup variant="contained">
                        <Button onClick={openEliminarDialog} color="warning">Borrar Producto</Button>
                        <Button onClick={openEditarDialog} color="info">Editar Producto</Button>
                        <Button onClick={openCargarDialog}>Cargar Producto</Button>
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
                queso={queso}
                onBorrar={onDelete}/>
            <ProductosGrid
                listaQuesos={listaQuesos}
                setSelection={setSelection}/>
        </Paper>
    );
}

export default CargarProductos;