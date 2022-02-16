
import { Box } from '@mui/system';
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
        headerName: 'Nombre',
        flex: 1,
        minWidth: 50
    },
]

const ProductosGrid = ({ listaQuesos, setSelection }) => {
    return (
        <Box height={600}
            sx={{
                padding: 1,
                flexDirection: 'column',
                alignItems: 'center',

            }}
        >
            <DataGrid
                rows={listaQuesos}
                columns={columns}
                pagination={false}
                hideFooterPagination
                onCellClick={(params, event, details) => setSelection(params.id)}
            />
        </Box>
    );
}

export default ProductosGrid;