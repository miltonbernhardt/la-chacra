import { Grid } from "@mui/material";
import { DataGrid } from '@mui/x-data-grid';

const columns = [
    {
        field: 'num',
        headerName: '#',
        minWidth: 20,
        flex: 0.15
    },
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
        <Grid item container direction="column" xs={12} spacing={1}>
            <Grid item xs={12}>
                <DataGrid
                    style={{minHeight: "500px"}}
                    autoHeight={true}
                    rows={listaQuesos}
                    rowsPerPageOptions={[15]}
                    hideFooterPagination
                    columns={columns}
                    onCellClick={(params) => setSelection(params.id)}
                />
            </Grid>
        </Grid>
    );
}

export default GridQuesos;