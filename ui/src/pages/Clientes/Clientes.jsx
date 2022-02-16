import { Button, ButtonGroup, Typography, Grid } from '@mui/material';
import Paper from '@mui/material/Paper';
import { Box } from '@mui/system';
import { DataGrid } from '@mui/x-data-grid';
import { clientes } from '../../data/data';
import { useState } from 'react';
import DialogAltaCliente from './DialogAltaCliente';
import DialogActualizarCliente from './DialogActualizarCliente';
import DialogBajaCliente from './DialogBajaCliente';

const columns = [
    {
        field: 'numeroCliente',
        headerName: 'Nro Cliente',
        flex: 1,
        minWidth: 50
    },
    {
        field: 'razonSocial',
        headerName: 'Razón Social',
        flex: 1,
        minWidth: 50
    },
    {
        field: 'cuit',
        headerName: 'CUIT',
        flex: 1,
        minWidth: 50
    }, {
        field: 'domicilio',
        headerName: 'Domicilio',
        flex: 1,
        minWidth: 50
    }, {
        field: 'localidad',
        headerName: 'Localidad',//TODO aca concatenar toda la localidad
        flex: 1,
        minWidth: 50
    }, {
        field: 'codigoPostal',
        headerName: 'CP',
        flex: 1,
        minWidth: 50
    }, {
        field: 'transporte',
        headerName: 'Transporte',
        flex: 1,
        minWidth: 50
    }, {
        field: 'senasaUta',
        headerName: 'Habilitación',
        flex: 1,
        minWidth: 50
    },
]

const Clientes = () => {

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
        <Paper style={{ width: '100%', height: '100%', padding: 2 }}>
            <Grid container columns={2} justifyContent="space-between" padding={2}>
                <Grid item >
                    <Typography variant="h6">Clientes</Typography>
                </Grid>
                <Grid item >
                    <ButtonGroup variant="contained">
                        <Button color="error" onClick={onOpenBaja}>Dar de baja</Button>
                        <Button color="warning" onClick={onOpenActualizar}>Actualizar</Button>
                        <Button onClick={onOpenAlta}>Dar de alta</Button>
                    </ButtonGroup>
                </Grid>
            </Grid>

            <Box height={600}
                sx={{
                    padding: 1,
                    flexDirection: 'column',
                    alignItems: 'center',

                }}
            >
                <DataGrid
                    rows={clientes}
                    columns={columns}
                    pageSize={20}
                    rowsPerPageOptions={[20]}
                    pagination={false}
                    hideFooterPagination

                />
            </Box>
            <DialogAltaCliente onClose={onCloseDialog} open={openDialogAlta} />
            <DialogActualizarCliente onClose={onCloseDialog} open={openDialogActualizar} />
            <DialogBajaCliente onClose={onCloseDialog} open={openDialogBaja} />
        </Paper >
    );
}

export default Clientes;