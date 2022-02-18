import { Box, Typography } from "@mui/material";
import { DataGrid } from '@mui/x-data-grid';
import { useMemo } from "react";

const ProduccionGrid = ({ produccion, quesos, setSelection }) => {

    const columns = useMemo(() => {
        return [
            {
                field: 'fechaElaboracion',
                headerName: 'Fecha de produccion',
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
                field: 'codigoQueso',
                headerName: 'Queso',
                flex: 0.75,
                minWidth: 50,
                valueFormatter: (params) => {
                    return quesos
                        .filter(q => {
                            return q.codigo === params.value
                        })
                        .pop().tipoQueso
                }
            },
            {
                field: 'numeroTina',
                headerName: 'Tina',
                type: 'number',
                flex: 0.5,
                minWidth: 50,
            },
            {
                field: 'litrosLeche',
                headerName: 'Litros Procesados',
                type: 'number',
                flex: 0.5,
                minWidth: 50,
            },
            {
                field: 'cantHormas',
                headerName: 'Hormas',
                type: 'number',
                flex: 0.5,
                minWidth: 50,
            },
            {
                field: 'peso',
                headerName: 'Peso',
                type: 'number',
                flex: 0.5,
                minWidth: 50,
            },

        ]
    }, [produccion]);

    return (
        <Box height={600}
            sx={{
                padding: 1,
                flexDirection: 'column',
                alignItems: 'center',

            }}>
            <Typography variant="h6" color="GrayText" padding={1}>
                Producción ingresada
            </Typography>
            <DataGrid
                rows={produccion}
                columns={columns}
                pageSize={20}
                rowsPerPageOptions={[20]}
                hideFooterPagination
                onCellDoubleClick={(params) => setSelection(params.id)}
            />
        </Box>
    );
}

export default ProduccionGrid;