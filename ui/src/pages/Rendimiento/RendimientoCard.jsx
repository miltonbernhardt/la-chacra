import {CardHeader} from '@mui/material';
import Avatar from '@mui/material/Avatar';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import {red} from '@mui/material/colors';
import Typography from '@mui/material/Typography';
import * as React from 'react';
import PercentIcon from '@mui/icons-material/Percent';

const RendimientoCard = ({titulo, valor, descripcion}) => {
    return (
        <Card
            sx={{
                height: '100%',
                // display: 'flex',
                flexDirection: 'column',
                justifyContent: 'center',
                alignItems: 'center'
            }}>
            <CardHeader
                avatar={
                    <Avatar sx={{bgcolor: red[500]}}>
                        <PercentIcon/>
                    </Avatar>
                }/>
            <CardContent sx={{
                flexGrow: 1,
                justifyContent: 'center',
                alignItems: 'center',
                textAlign: 'center',
            }}>
                <Typography variant="h1" component="h2">
                    {valor}
                </Typography>
                <Typography gutterBottom variant="h7" color="textSecondary" component="h2">
                    {descripcion}
                </Typography>
            </CardContent>
        </Card>);
}

export default RendimientoCard;