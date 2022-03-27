import * as React from 'react';
import { Bar, BarChart, Legend, ResponsiveContainer, Tooltip, XAxis, YAxis } from 'recharts';

export const GraficoBarras = ({ data, xDataKey, yDataKeyArray }) => {
    console.log(data);

    return (
        <ResponsiveContainer width="100%" height="100%">
            <BarChart width={150} height={40} data={data}>
                <XAxis dataKey={xDataKey}/>
                <YAxis/>
                <Tooltip/>
                <Legend/>
                {yDataKeyArray.map((key) => {
                    return <Bar key={key} dataKey={key} fill="#8884d8"/>
                })}
            </BarChart>
        </ResponsiveContainer>
    );
}