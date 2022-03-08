import * as React from 'react';
import { DataGrid } from '@mui/x-data-grid';

const columns = [
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
        field: 'tipoQueso',
        headerName: 'Tipo de queso',
        flex: 1,
        minWidth: 50
    },
]

export const GridQuesos = ({ listaQuesos, setSelection }) => {
    return (
        <DataGrid
            style={{ minHeight: "600px" }}
            autoHeight={true}
            rows={listaQuesos}
            rowHeight={42}
            pageSize={15}
            rowsPerPageOptions={[15]}
            columns={columns}
            onCellClick={(params) => setSelection(params.id)}
        />
    );
}