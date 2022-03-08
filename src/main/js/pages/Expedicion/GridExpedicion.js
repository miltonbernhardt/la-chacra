import { DataGrid } from '@mui/x-data-grid';
import { useMemo } from "react";
import * as field from "../../resources/fields";

const GridExpedicion = ({ expediciones, setSelection }) => {

    const columns = useMemo(() => {
        return [
            {
                field: field.backFechaExpedicion,
                headerName: field.fechaExpedicion,
                type: 'date',
                valueFormatter: (params) => {
                    if (params.value === undefined)
                        return '';
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
                headerName: field.razonSocial,
                type: 'text',
                flex: 1,
                minWidth: 50,
            },
            {
                field: field.backIdLote,
                headerName: 'Lote',
                flex: 0.75,
                minWidth: 50
            },
            {
                field: field.backCantidad,
                headerName: field.cantidad,
                type: 'number',
                flex: 0.5,
                minWidth: 50,
            },
            {
                field: field.backPesoExpedicion,
                headerName: field.pesoExpedicion,
                type: 'number',
                flex: 0.5,
                minWidth: 50,
            },
            {
                field: field.backImporte,
                headerName: field.importe,
                type: 'number',
                flex: 0.5,
                minWidth: 50,
            },
        ]
    }, []);

    return (
        <>
            <DataGrid
                rows={expediciones}
                columns={columns}
                autoHeight={true}
                rowHeight={42}
                pageSize={15}
                rowsPerPageOptions={[15]}
                onCellClick={(params) => setSelection(params.id)}
            />
        </>
    )
}

export default GridExpedicion