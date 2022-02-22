import { Button } from '@mui/material';
import { useState } from 'react';
import DialogAltaCliente from './DialogAltaCliente';
import DialogActualizarCliente from './DialogActualizarCliente';
import DialogBajaCliente from './DialogBajaCliente';
import GridClientes from "./GridClientes";
import PageTableButtonPane from "../../components/PageTableButtonPane";


const CargarClientes = () => {
    const [openDialogAlta, setOpenDialogAlta] = useState(false);
    const [openDialogActualizar, setOpenDialogActualizar] = useState(false);
    const [openDialogBaja, setOpenDialogBaja] = useState(false);

    const onOpenAlta = () => {
        setOpenDialogAlta(true);
    }
    const onOpenBaja = () => {
        setOpenDialogBaja(true);
    }
    const onOpenActualizar = () => {
        setOpenDialogActualizar(true);
    }
    const onCloseDialog = () => {
        setOpenDialogActualizar(false);
        setOpenDialogBaja(false);
        setOpenDialogAlta(false);
    }

    return (
        <PageTableButtonPane
            title="Clientes"
            buttons={<>
                <Button color="error" onClick={onOpenBaja}>Dar de baja</Button>
                <Button color="warning" onClick={onOpenActualizar}>Actualizar</Button>
                <Button onClick={onOpenAlta}>Dar de alta</Button>
            </>}
            grid={<GridClientes/>}
        >
            <DialogAltaCliente onClose={onCloseDialog} open={openDialogAlta}/>
            <DialogActualizarCliente onClose={onCloseDialog} open={openDialogActualizar}/>
            <DialogBajaCliente onClose={onCloseDialog} open={openDialogBaja}/>
        </PageTableButtonPane>
    );
}

export default CargarClientes;