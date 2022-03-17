import { DataGrid } from '@mui/x-data-grid';

const columns = [
    {
        field: "cantidad",
        headerName: "Cantidad",
        type: 'number',
        flex: 1,
        minWidth: 100
    }, {
        field: "producto",
        headerName: "Producto",
        type: 'text',
        flex: 1,
        minWidth: 100
    }, {
        field: "peso",
        headerName: "Peso",
        type: 'number',
        flex: 1,
        minWidth: 100
    }, {
        field: "precio",
        headerName: "Precio Unitario",
        type: 'number',
        flex: 1,
        minWidth: 100
    }, {
        field: "importe",
        headerName: "Importe",
        type: 'number',
        flex: 1,
        minWidth: 100
    },
]
const GridRemito = () => {
    return (
        <DataGrid
            style={{ minHeight: "600px" }}
            rows={[]}
            columns={columns}
            pageSize={20}
            rowsPerPageOptions={[20]}
            pagination={false}
            hideFooterPagination
        />);
}

export default GridRemito;