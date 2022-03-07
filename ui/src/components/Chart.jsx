import * as React from 'react';
import { useTheme } from '@mui/material/styles';
import { LineChart, Line, Tooltip, XAxis, YAxis, Label, ResponsiveContainer } from 'recharts';
import Title from './Title';

export default function Chart({ title, data, yLabel, xDataKey, dataKey, dataKey1, dataKey2 }) {
    const theme = useTheme();

    return (
        <React.Fragment>
            <Title>{title}</Title>
            <ResponsiveContainer>
                <LineChart
                    data={data}
                    margin={{
                        top: 16,
                        right: 16,
                        bottom: 0,
                        left: 24,
                    }}
                >
                    <XAxis
                        dataKey={xDataKey}
                        stroke={theme.palette.text.secondary}
                        style={theme.typography.body2}
                    />
                    <YAxis
                        stroke={theme.palette.text.secondary}
                        style={theme.typography.body2}
                    >
                        <Label
                            angle={270}
                            position="left"
                            style={{
                                textAnchor: 'middle',
                                fill: theme.palette.text.primary,
                                ...theme.typography.body1,
                            }}
                        >
                            {yLabel}
                        </Label>
                    </YAxis>
                    <Tooltip />
                    <Line
                        isAnimationActive={true}
                        type="monotone"
                        dataKey={dataKey}
                        stroke="#dc3030"
                        dot={true} />
                    <Line
                        isAnimationActive={true}
                        type="monotone"
                        dataKey={dataKey1}
                        stroke="#0288d1"
                        dot={true} />
                    <Line
                        isAnimationActive={true}
                        type="monotone"
                        dataKey={dataKey2}
                        stroke="#ed6c02"
                        dot={true} />
                </LineChart>
            </ResponsiveContainer>
        </React.Fragment>
    );
}