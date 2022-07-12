import { Button, Stack } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import * as React from 'react';
import { useMemo } from 'react';
import * as field from "../../resources/fields";

export const GridRemitos = ({ data, setSelection, anularRemito }) => {

    const columns = useMemo(() => {
        return [
            {
                field: field.backRazonSocial,
                headerName: 'Cliente',
                flex: 0.75,
                minWidth: 50
            },
            {
                field: field.backFechaRemito,
                headerName: 'Fecha',
                type: 'date',
                valueFormatter: (params) => {
                    const valueFormatted =
                        params.value.at(8) + params.value.at(9) + '/' +
                        params.value.at(5) + params.value.at(6) + '/' +
                        params.value.at(0) + params.value.at(1) +
                        params.value.at(2) + params.value.at(3);
                    return `${valueFormatted}`;
                },
                flex: 0.5,
                minWidth: 50
            },
            {
                field: field.backCantCajas,
                headerName: 'Cajas',
                flex: 0.25,
                minWidth: 50,
            },
            {
                field: field.backCantPallets,
                headerName: 'Pallets',
                flex: 0.25,
                minWidth: 50
            },
            {
                field: field.backImporteTotal,
                headerName: 'Importe',
                type: 'number',
                flex: 0.5,
                minWidth: 50
            },
            {
                field: 'id',
                headerName: '',
                flex: 0.5,
                minWidth: 50,
                renderCell: ({ value }) => {
                    return (
                        <Stack sx={{ width: '100%' }}
                            direction="row"
                            justifyContent="space-around">
                            <Button
                                variant="outlined"
                                size="small"
                                onClick={() => setSelection(value)}
                                color="info" >
                                Ver
                            </Button>
                            <Button
                                variant="outlined"
                                size="small"
                                onClick={() => anularRemito(value)}>
                                Anular
                            </Button>
                        </Stack>
                    );
                },
            },
        ]
    }, []);

    return <>
        <DataGrid
            style={{ minHeight: "600px" }}
            autoHeight={true}
            rows={data}
            columns={columns}
            rowHeight={42}
            pageSize={15}
            rowsPerPageOptions={[15]} />
    </>
}