import { Box, Button, Container, Grid, Paper, TextField, Typography } from "@mui/material";
import { DataGrid } from '@mui/x-data-grid';
import Chart from '../../components/Chart';
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

const VerVentas = () => {
    return (
        <>
            <Container maxWidth="sm">
                <Box
                    sx={{
                        marginTop: 8,
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                        mt: 1
                    }}
                >
                    <Paper sx={{
                        p: 2,
                        display: 'flex',
                        flexDirection: 'column',
                    }}>
                        <Grid container spacing={2}>
                            <Typography variant="h6" paddingLeft={2} paddingTop={1}>
                                Ventas
                            </Typography>
                            <Grid item xs={12}>
                                <Typography variant="h7" color="GrayText">
                                    Rango de fechas
                                </Typography>
                            </Grid>
                            <Grid item xs={12} sm={4}>
                                <TextField
                                    id="fechaDesde"
                                    name="fechaDesde"
                                    label="Desde"
                                    fullWidth
                                    type="date"
                                    variant="outlined"
                                    InputLabelProps={{
                                        shrink: true,
                                    }}/>
                            </Grid>
                            <Grid item xs={12} sm={4}>
                                <TextField
                                    id="fechaHasta"
                                    name="fechaHasta"
                                    label="Hasta"
                                    type="date"
                                    fullWidth
                                    variant="outlined"
                                    InputLabelProps={{
                                        shrink: true,
                                    }}/>
                            </Grid>
                            <Grid item xs={12} sm={4} alignSelf="center" mb={0.5}>
                                <Button variant="contained" fullWidth>Buscar</Button>
                            </Grid>
                        </Grid>
                    </Paper>
                </Box>
            </Container>
            <Box
                sx={{
                    marginTop: 4,
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                    mt: 1,
                    padding: 1
                }}
            >
                <Grid container spacing={2}>
                    {/* Chart */}
                    <Grid item xs={12}>
                        <Paper
                            sx={{
                                p: 2,
                                display: 'flex',
                                flexDirection: 'column',
                                height: 280,
                            }}
                        >
                            <Chart
                                title="Ventas"
                                yLabel="Cantidad"
                                data={ventas}
                                xDataKey="semana"
                                yDataKey="C"
                            />
                        </Paper>
                    </Grid>
                </Grid>
            </Box>
            <Box height={600}
                 sx={{
                     padding: 1,
                     flexDirection: 'column',
                     alignItems: 'center',

                 }}>
                <Grid item xs={12}>
                    <Paper
                        sx={{
                            p: 2,
                            display: 'flex',
                            flexDirection: 'column',
                            height: 600,
                        }}
                    >
                        <DataGrid
                            rows={ventas}
                            columns={columns}
                            pageSize={20}
                            rowsPerPageOptions={[20]}
                            pagination={false}
                            hideFooterPagination
                        />
                    </Paper>
                </Grid>
            </Box>
        </>
    );
}

export default VerVentas;