import { Box } from "@mui/material";
import { DataGrid } from '@mui/x-data-grid';

const ProduccionGrid = ({ produccion, quesos, handleEdit, setSelection }) => {

    const columns = [
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
            flex: 1,
            minWidth: 50,
        },
        {
            field: 'idQueso',
            headerName: 'Queso',
            flex: 0.5,
            minWidth: 50,
            valueFormatter: (params) => {
                return quesos
                    .filter(q => {
                        return q.id === params.value
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

    ];

    return (
        <Box height={600}
            sx={{
                padding: 1,
                flexDirection: 'column',
                alignItems: 'center',

            }}>
            <DataGrid
                rows={produccion}
                columns={columns}
                pageSize={20}
                rowsPerPageOptions={[20]}
                hideFooterPagination
                onCellDoubleClick={(params, event, details) => setSelection(params.id)}
            />
        </Box>
    );
}

export default ProduccionGrid;