import { DataGrid } from '@mui/x-data-grid';
import * as React from 'react';
import { useMemo } from 'react';
import * as fields from '../../resources/fields'

export const GridPrecios = ({ precios, setSelection }) => {

    const columns = useMemo(() => {
        return [
            {
                field: fields.backIdQueso,
                headerName: 'Queso',
                flex: 1,
                minWidth: 50,
            },
            {
                field: fields.backIdTipoCliente,
                headerName: 'Cliente',
                flex: 1,
                minWidth: 50
            },
            {
                field: fields.backPrecio,
                headerName: 'Precio',
                type: 'number',
                flex: 1,
                minWidth: 50,
                valueFormatter: (params) => {
                    return `$ ${params.value}`
                }

            }
        ]
    }, []);

    return <DataGrid
        autoHeight={true}
        rows={precios}
        columns={columns}
        onCellDoubleClick={(params) => setSelection(params.id)}
        rowHeight={42}
        pageSize={15}
        rowsPerPageOptions={[15]}
    />
}