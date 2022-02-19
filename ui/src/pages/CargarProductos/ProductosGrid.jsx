import { Box } from '@mui/system';
import { Typography, Grid } from "@mui/material";
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
        headerName: 'Tipo de queso',
        flex: 1,
        minWidth: 50
    },
]

const ProductosGrid = ({listaQuesos, setSelection}) => {
    return (
        <Grid item xs={12} style={{maxHeight: "1100px"}}>
            <DataGrid
                style={{minHeight: "500px"}}
                rows={listaQuesos}
                rowsPerPageOptions={[]}
                columns={columns}
                onCellClick={(params) => setSelection(params.id)}
            />
        </Grid>
    );
}

export default ProductosGrid;