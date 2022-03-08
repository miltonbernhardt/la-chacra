import * as React from 'react';
import Paper from '@mui/material/Paper';
import Typography from '@mui/material/Typography';
import { ArgumentScale, Animation } from '@devexpress/dx-react-chart';
import { Chart, ArgumentAxis, ValueAxis, LineSeries, Title, Legend, } from '@devexpress/dx-react-chart-material-ui';
import { curveCatmullRom, line, } from 'd3-shape';
import { litrosElaborados as data } from '../data/data';
import { scalePoint } from 'd3-scale';
import { styled } from '@mui/material/styles';

const PREFIX = 'Demo';

const classes = {
    title: `${PREFIX}-title`,
    chart: `${PREFIX}-chart`,
};

const Line = props => (
    <LineSeries.Path
        {...props}
        path={line()
            .x(({ arg }) => arg)
            .y(({ val }) => val)
            .curve(curveCatmullRom)}
    />
);

const StyledDiv = styled('div')(() => ({
    [`&.${classes.title}`]: {
        textAlign: 'center',
        width: '100%',
        marginBottom: '10px',
    },
}));

const Text = ({ text }) => {
    const [mainText, subText] = text.split('\\n');
    return (
        <StyledDiv className={classes.title}>
            <Typography component="h3" variant="h5">
                {mainText}
            </Typography>
            <Typography variant="subtitle1">{subText}</Typography>
        </StyledDiv>
    );
};

const Root = props => (
    <Legend.Root {...props} sx={{ display: 'flex', margin: 'auto', flexDirection: 'row' }}/>
);
const Label = props => (
    <Legend.Label {...props} sx={{ mb: 1, whiteSpace: 'nowrap' }}/>
);
const Item = props => (
    <Legend.Item {...props} sx={{ flexDirection: 'column-reverse' }}/>
);

const StyledChart = styled(Chart)(() => ({
    [`&.${classes.chart}`]: {
        paddingRight: '30px',
    },
}));

export const DemoChart = () => {
    return (<Paper>
        <StyledChart
            data={data}
            className={classes.chart}
        >
            <ArgumentScale factory={scalePoint}/>
            <ArgumentAxis/>
            <ValueAxis/>

            <LineSeries
                name="Hydro-electric"
                valueField="C"
                argumentField="semana"
                seriesComponent={Line}
            />
            <LineSeries
                name="Oil"
                valueField="B"
                argumentField="semana"
                seriesComponent={Line}
            />
            <LineSeries
                name="Natural gas"
                valueField="S"
                argumentField="semana"
                seriesComponent={Line}
            />
            <LineSeries
                name="Coal"
                valueField="PS"
                argumentField="semana"
                seriesComponent={Line}
            />
            <LineSeries
                name="Nuclear"
                valueField="CH"
                argumentField="semana"
                seriesComponent={Line}
            />
            <Legend position="bottom" rootComponent={Root} itemComponent={Item} labelComponent={Label}/>
            <Title
                text="Energy Consumption in 2004\n(Millions of Tons, Oil Equivalent)"
                textComponent={Text}
            />
            <Animation/>
        </StyledChart>
    </Paper>);
}
