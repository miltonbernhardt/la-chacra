import { Grid, Paper } from "@mui/material";
import * as React from 'react';
import Chart from '../../components/Chart';

export const RendimientoChart = ({
                                     title,
                                     yLabel,
                                     xLabel,
                                     data,
                                     xDataKey,
                                     dataKey,
                                     dataKey1,
                                     dataKey2,
                                     domain,
                                     legend
                                 }) => {
    return (
        <Grid item xs={12} md={6}>
            <Paper
                sx={{
                    p: 2,
                    display: 'flex',
                    flexDirection: 'column',
                    height: 300,
                    width: '100%'
                }}
            >
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
                    legend={legend}
                />
            </Paper>
        </Grid>
    );
}