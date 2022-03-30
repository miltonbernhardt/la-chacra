import { Grid, Paper } from "@mui/material";
import { DataGrid } from '@mui/x-data-grid';

const GridLitros = ({ data, quesos }) => {

    const columnsQueso = quesos.map((queso) => {
        return {
            field: queso.tipoQueso,
            headerName: queso.nomenclatura,
            type: 'number',
            flex: 0.7,
            minWidth: 80
        }
    })

    const columns = [{
        field: "fecha",
        headerName: "Fecha",
        type: 'date',
        flex: 0.7,
    },
    {
        field: "total",
        headerName: "Total",
        type: 'number',
        flex: 1,
    },
    ...columnsQueso
    ];

    return (
        <Grid item xs={12}>
            <Paper
                sx={{
                    display: 'flex',
                    flexDirection: 'column',
                    height: 600,
                }}>
                <DataGrid
                    rows={data}
                    columns={columns}
                    pageSize={20}
                    rowsPerPageOptions={[20]} />
            </Paper>
        </Grid>
    );
}

export default GridLitros;