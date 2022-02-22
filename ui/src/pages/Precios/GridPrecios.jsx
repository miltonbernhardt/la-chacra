import { Typography, Grid } from "@mui/material";
import { DataGrid } from '@mui/x-data-grid';
import { useMemo } from "react";

const GridPrecios = ({precios}) => {
    const columns = useMemo(() => {
        return [
            {
                field: 'tipoQueso',
                headerName: 'Queso',
                type: 'text',
                flex: 1,
                minWidth: 50
            },
            {
                field: 'tipoCliente',
                headerName: 'Cliente',
                type: 'text',
                flex: 1,
                minWidth: 50
            },
            {
                field: 'precio',
                headerName: 'Precio',
                type: 'number',
                flex: 1,
                minWidth: 50
            }
        ]
    }, [precios]);

    return (
        <>
            <DataGrid
                style={{minHeight: "600px"}}
                autoHeight={true}
                rows={precios}
                columns={columns}
                // onCellDoubleClick={(params) => setSelection(params.id)}
                rowHeight={42}
                pageSize={15}
                rowsPerPageOptions={[15]}
            />
        </>
    );
}

export default GridPrecios;