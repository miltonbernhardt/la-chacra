import { BarChart, Bar, Cell, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';

const GraficoBarras = ({ data, xDataKey, yDataKeyArray }) => {

    console.log(data);


    return (
        <ResponsiveContainer width="100%" height="100%">
            <BarChart width={150} height={40} data={data}>
                <XAxis dataKey={xDataKey} />
                <YAxis />
                <Tooltip />
                <Legend />
                {yDataKeyArray.map((key) => {
                    return <Bar key={key} dataKey={key} fill="#8884d8" />
                })}
            </BarChart>
        </ResponsiveContainer>
    );
}

export default GraficoBarras;