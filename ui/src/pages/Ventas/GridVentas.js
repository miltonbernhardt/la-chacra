import { Box, Grid, Paper } from "@mui/material";
import { DataGrid } from '@mui/x-data-grid';
import { quesos, ventas } from "../../data/data";

const columns1 = quesos.map((queso, index) => {
    return {
        field: queso.nomenclatura,
        headerName: queso.nomenclatura,
        type: 'number',
        flex: 0.7,
        minWidth: 80
    }
})

const columns = [{
    field: "semana",
    headerName: "Semana",
    type: 'date',
    flex: 1,
    minWidth: 100
},
{
    field: "total",
    headerName: "Total",
    type: 'number',
    flex: 1,
    minWidth: 100
},
...columns1
];

const GridVentas = () => {

    return (
        <Grid item xs={12}>
            <Paper
                sx={{
                    // p: 2,
                    display: 'flex',
                    flexDirection: 'column',
                    height: 600,
                }}>
                <DataGrid
                    rows={ventas}
                    columns={columns}
                    pageSize={20}
                    rowsPerPageOptions={[20]} />
            </Paper>
        </Grid>
    );
}

export default GridVentas;