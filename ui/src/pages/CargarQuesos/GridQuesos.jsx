import { Grid } from "@mui/material";
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

const GridQuesos = ({listaQuesos, setSelection}) => {
    return (
        <Grid item xs={12}>
            <DataGrid
                style={{height: "600px"}}
                autoHeight={true}
                rows={listaQuesos}
                rowHeight={42}
                pageSize={15}
                rowsPerPageOptions={[15]}
                columns={columns}
                onCellClick={(params) => setSelection(params.id)}
            />
        </Grid>
    );
}

export default GridQuesos;