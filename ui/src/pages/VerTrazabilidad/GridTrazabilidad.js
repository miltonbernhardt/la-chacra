import { DataGrid } from '@mui/x-data-grid';
import * as React from 'react';
import { useMemo } from 'react';
import * as field from "../../resources/fields";

export const GridTrazabilidad = ({ data }) => {

    const columns = useMemo(() => {
        return [{
            field: field.backFechaExpedicion,
            headerName: 'Fecha de expedición',
            type: 'date',
            valueFormatter: (params) => {
                const valueFormatted =
                    params.value.at(8) + params.value.at(9) + '-' +
                    params.value.at(5) + params.value.at(6) + '-' +
                    params.value.at(0) + params.value.at(1) +
                    params.value.at(2) + params.value.at(3);
                return `${valueFormatted}`;
            },
            flex: 0.75,
            minWidth: 50,
        },
            {
                field: field.backRazonSocial,
                headerName: 'Cliente',
                flex: 0.75,
                minWidth: 50,
            },
            {
                field: field.backCantidad,
                headerName: 'Cantidad',
                type: 'number',
                flex: 0.5,
                minWidth: 50,
            },]
    }, []);

    return (
        <>
            <DataGrid
                style={{ minHeight: "600px" }}
                autoHeight={true}
                rows={data}
                columns={columns}
                rowHeight={42}
                pageSize={15}
                rowsPerPageOptions={[15]}
            />
        </>
    )
}