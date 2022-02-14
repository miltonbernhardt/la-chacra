
import * as React from 'react';
import { useTheme } from '@mui/material/styles';
import { LineChart, Line, Tooltip, XAxis, YAxis, Label, ResponsiveContainer } from 'recharts';
import Title from './Title';

export default function Chart({ title, data, yLabel, xDataKey, yDataKey }) {
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
                        dataKey={yDataKey}
                        stroke={theme.palette.primary.main}
                        dot={true}
                    />
                </LineChart>
            </ResponsiveContainer>
        </React.Fragment>
    );
}