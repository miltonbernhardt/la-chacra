import { Chip } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import * as React from 'react';

const columns = [
    {
        field: 'tipoQueso',
        headerName: 'Tipo de queso',
        flex: 1,
        minWidth: 50
    },
    {
        field: 'codigo',
        headerName: 'Código',
        flex: 1,
        minWidth: 50
    },
    {
        field: 'nomenclatura',
        headerName: 'Nomenclatura',
        flex: 1,
        minWidth: 50
    },
    {
        field: 'color',
        headerName: 'Color',
        flex: 1,
        minWidth: 50,
        renderCell: ({ value }) => {
            return (<Chip
                style={{ backgroundColor: value }}/>)
        },
    },
]

export const GridQuesos = ({ listaQuesos, setSelection }) => {
    return <DataGrid
        style={{ minHeight: "600px" }}
        autoHeight={true}
        rows={listaQuesos}
        rowHeight={42}
        pageSize={15}
        rowsPerPageOptions={[15]}
        columns={columns}
        onCellClick={(params) => setSelection(params.id)}
    />
}