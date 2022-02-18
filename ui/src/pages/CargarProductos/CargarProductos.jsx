import {Button, ButtonGroup, Grid, Typography} from '@mui/material';
import Paper from '@mui/material/Paper';
import {useEffect, useState} from "react";
import toast from 'react-hot-toast';
import {deleteQueso, getAllQuesos, postQueso, putQueso} from "../../services/RestServices";
import CargarProductoDialog from './CargarProductoDialog';
import EliminarProductoDialog from './EliminarProductoDialog';
import ProductosGrid from './ProductosGrid';
import * as message from "../../messages";

const quesoInicial = {
    id: '',
    codigo: '',
    tipoQueso: '',
    nomenclatura: ''
}

const CargarProductos = () => {

    const [queso, setQueso] = useState(quesoInicial);
    const [listaQuesos, setListaQuesos] = useState([]);

    // Dialogs
    const [isOpenCargarProducto, setOpenCargarProducto] = useState(false);
    const [isEditingQueso, setIsEditingQueso] = useState(false);
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
        }).catch(e => console.error(e.message));
    }

    const onSubmit = (quesoSubmit) => {
        if (quesoSubmit.id === '') {
            postQueso(quesoSubmit)
                .then(() => fetchQuesos())
                .catch(e => console.error(e.message));
        } else
            putQueso(quesoSubmit)
                .then(() => fetchQuesos())
                .catch(e => console.error(e.message));
        setOpenCargarProducto(false);
        setIsEditingQueso(false);
    }

    //--- EDIT QUESO METHODS ---
    const openCargarDialog = () => {
        setOpenCargarProducto(true);
        setQueso(quesoInicial);
    }

    const openEditarDialog = () => {
        if (queso.id === '')
            toast.error(message.errorProductNotSelected)
        else {
            setIsEditingQueso(true);
            setOpenCargarProducto(true);
        }
    }

    const closeCargarProducto = () => {
        setOpenCargarProducto(false);
        setIsEditingQueso(false);
    }

    const openEliminarDialog = () => {
        queso.id === '' ? toast.error(message.errorProductNotSelected) :
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
            .then(() => fetchQuesos())
            .catch(e => console.error(e.message));
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
                onClose={closeCargarProducto}
                onSubmit={onSubmit}
                isEditingQueso={isEditingQueso}
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