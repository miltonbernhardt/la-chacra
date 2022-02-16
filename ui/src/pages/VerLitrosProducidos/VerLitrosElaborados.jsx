import { Box, Button, Container, Grid, Paper, TextField, Typography } from "@mui/material";
import { DataGrid } from '@mui/x-data-grid';
import Chart from '../../components/Chart';
import { litrosElaborados, quesos } from "../../data/data";

//TODO este chart anda feo, cambiar por https://devexpress.github.io/devextreme-reactive/react/chart/demos/line/spline/

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

const VerLitrosElaborados = () => {

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
                    <Paper
                        sx={{
                            p: 2,
                            display: 'flex',
                            flexDirection: 'column',

                        }}
                    >
                        <Grid container spacing={2}>
                            <Typography variant="h6" paddingLeft={2} paddingTop={1}>
                                Litros Elaborados
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
                                    }} />
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
                                    }} />
                            </Grid>
                            <Grid item xs={12} sm={4} alignSelf="center" mb={0.5}>
                                <Button variant="contained" fullWidth >Buscar</Button>
                            </Grid>
                        </Grid>
                    </Paper>
                </Box>
            </Container>
            <Box
                sx={{
                    marginTop: 8,
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                    mt: 1,
                    padding: 1
                }}
            >
                <Grid container spacing={2} >
                    {/* Chart */}
                    <Grid item xs={12} >
                        <Paper
                            sx={{
                                p: 2,
                                display: 'flex',
                                flexDirection: 'column',
                                height: 300,
                            }}
                        >
                            <Chart
                                title="Litros Elaborados"
                                yLabel="Litros"
                                data={litrosElaborados}
                                xDataKey="semana"
                                yDataKey="total"
                            />
                        </Paper>
                    </Grid>
                    {/* <Grid item xs={12} sm={6}>
                        <Paper
                            sx={{
                                p: 2,
                                display: 'flex',
                                flexDirection: 'column',
                                height: 600,
                            }}
                        >
                            <GraficoBarras
                                data={[litrosElaborados.at(3)]}
                                xDataKey="semana"
                                yDataKeyArray={quesos.map((q) => { return q.nomenclatura })}
                            />
                        </Paper>
                    </Grid>*/}
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
                            rows={litrosElaborados}
                            columns={columns}
                            pageSize={20}
                            rowsPerPageOptions={[20]}
                            pagination={false}
                            hideFooterPagination
                        />
                    </Paper>
                </Grid>
            </Box>
        </>);
}

export default VerLitrosElaborados;