import { Grid, Paper } from "@mui/material";
import Chart from '../../components/Chart';

const ChartLitros = ({ title, yLabel, xLabel, data, xDataKey, dataKey, dataKey1, dataKey2, domain, legend }) => {
    return (
        <Grid item xs={12} md={8}>
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

export default ChartLitros;