import * as React from 'react';
import { DataGrid } from '@mui/x-data-grid';

export const GridTrazabilidad = () => {
    return (
        <>
            <DataGrid
                style={{ minHeight: "600px" }}
                autoHeight={true}
                rows={[]}
                columns={[]}
                rowHeight={42}
                pageSize={15}
                rowsPerPageOptions={[15]}
                // onCellClick={(params) => setSelection(params.id)}
            />
        </>
    )
}