import * as React from 'react';
import * as message from "../../resources/messages";
import toast from 'react-hot-toast';
import { Button } from '@mui/material';
import { DialogCargarQueso } from './DialogCargarQueso';
import { DialogEliminarQueso } from './DialogEliminarQueso';
import { GridQuesos } from './GridQuesos';
import { Loading } from '../../components/Loading';
import { PageTableButtonPane } from "../../components/PageTableButtonPane";
import { deleteQueso, getAllQuesos, postQueso, putQueso } from "../../services/RestServices";
import { useEffect, useState } from "react";

const quesoInicial = {
    id: '',
    codigo: '',
    tipoQueso: '',
    nomenclatura: ''
}

export const CargarQuesos = () => {

    const [queso, setQueso] = useState(quesoInicial);
    const [listaQuesos, setListaQuesos] = useState([]);

    const [isLoading, setLoading] = useState(true);

    const [isCargarQueso, setIsCargarQueso] = useState(false);
    const [isEditarQueso, setIsEditarQueso] = useState(false);
    const [isOpenEliminarProducto, setOpenEliminarProducto] = useState(false);

    useEffect(() => fetchQuesos(), []);

    const fetchQuesos = () => {
        getAllQuesos()
            .then(quesos => {
                const listaAux = quesos.data.map((q) => {
                    return {
                        id: q.id,
                        codigo: q.codigo,
                        nomenclatura: q.nomenclatura,
                        tipoQueso: q.tipoQueso
                    }
                })
                setListaQuesos(listaAux)
                setLoading(false);
            })
            .catch(() => toast.error("No se pudo cargar productos"))
            .finally(() => setLoading(false));

        setQueso(quesoInicial)
    }

    const onSubmit = (quesoSubmit) => {
        if (quesoSubmit.id === '') {
            postQueso(quesoSubmit)
                .then(() => {
                    fetchQuesos()
                    setIsCargarQueso(false);
                })
                .catch(() => null);
        } else {
            putQueso(quesoSubmit)
                .then(() => {
                    fetchQuesos()
                    setIsEditarQueso(false);
                })
                .catch(() => null);
        }
    }

    const onDelete = () => {
        deleteQueso(queso.id)
            .then(() => {
                fetchQuesos()
            })
            .catch(() => null);

        closeEliminarDialog()
    }

    //--- EDIT QUESO METHODS ---
    const openCargarDialog = () => setIsCargarQueso(true);

    const openEditarDialog = () => {
        if (queso.id === '') {
            toast.dismiss()
            toast.error(message.errorProductNotSelected)
        } else
            setIsEditarQueso(true)
    }

    const closeGestorProducto = () => {
        setIsCargarQueso(false);
        setIsEditarQueso(false);
    }

    const openEliminarDialog = () => {
        if (queso.id === '') {
            toast.dismiss()
            toast.error(message.errorProductNotSelected)
        } else
            setOpenEliminarProducto(true);
    }
    const closeEliminarDialog = () => setOpenEliminarProducto(false);

    const setSelection = (id) => {
        setQueso(listaQuesos.filter(o => o.id === id).pop())
    }

    if (isLoading)
        return (<Loading />)

    return (
        <PageTableButtonPane
            title="Productos"
            buttons={<>
                <Button onClick={openEliminarDialog} color="warning">Borrar Producto</Button>
                <Button onClick={openEditarDialog} color="info">Editar Producto</Button>
                <Button onClick={openCargarDialog}>Cargar Producto</Button>
            </>}
            grid={
                <GridQuesos
                    listaQuesos={listaQuesos}
                    setSelection={setSelection} />
            }
        >
            <DialogCargarQueso
                isEditarQueso={isEditarQueso}
                isCargarQueso={isCargarQueso}
                queso={isCargarQueso ? quesoInicial : queso}
                onClose={closeGestorProducto}
                onSubmit={onSubmit}
            />
            <DialogEliminarQueso
                open={isOpenEliminarProducto}
                onClose={() => setOpenEliminarProducto(false)}
                queso={queso}
                onBorrar={onDelete} />
        </PageTableButtonPane>
    );
}