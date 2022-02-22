import { Grid } from "@mui/material";
import { DataGrid } from '@mui/x-data-grid';
import { clientes } from "../../data/data";

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

const GridClientes = () => {
    return (
        <DataGrid
            rows={clientes}
            columns={columns}
            style={{minHeight: "600px"}}
            autoHeight={true}
            rowHeight={42}
            pageSize={15}
            rowsPerPageOptions={[15]}
            // onCellClick={(params) => setSelection(params.id)}
        />
    );
}

export default GridClientes;