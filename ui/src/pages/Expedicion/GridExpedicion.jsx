import { DataGrid } from '@mui/x-data-grid';

import { createRef, useCallback, useEffect, useMemo, useState } from "react";
import Input from "../../components/Input";
import Select from "../../components/Select";
import * as field from "../../resources/fields";
import * as message from "../../resources/messages";
import * as validation from "../../resources/validations";

const GridExpedicion = ({ expediciones, setSelection }) => {

    const columns = useMemo(() => {
        return [
            {
                field: field.backFechaExpedicion,
                headerName: field.fechaExpedicion,
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
                headerName: field.razonSocial,
                type: 'text',
                flex: 0.5,
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
                style={{ minHeight: "600px" }}
                autoHeight={true}
                rows={expediciones}
                columns={columns}
                rowHeight={42}
                pageSize={15}
                rowsPerPageOptions={[15]}
                onCellClick={(params) => setSelection(params.id)}
            />
        </>
    )
}

export default GridExpedicion