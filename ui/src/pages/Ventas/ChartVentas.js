import { Grid, Paper } from "@mui/material";
import Chart from '../../components/Chart';

const ChartVentas = ({ title, yLabel, xLabel, data, xDataKey, dataKey, dataKey1, dataKey2, domain, legend, md = 8 }) => {
    return (
        <Grid item xs={12} md={md}>
            <Paper
                sx={{
                    p: 2,
                    display: 'flex',
                    flexDirection: 'column',
                    height: '100%',
                    width: '100%'
                }}>
                <Chart
                    title={title}
                    yLabel={yLabel}
                    xLabel={xLabel}
                    data={data}
                    xDataKey={xDataKey}
                    dataKey={dataKey}
                    dataKey1={dataKey1}
                    dataKey2={dataKey2}
                    domain={domain}
                    legend={legend} />
            </Paper>
        </Grid>
    );
}

export default ChartVentas;