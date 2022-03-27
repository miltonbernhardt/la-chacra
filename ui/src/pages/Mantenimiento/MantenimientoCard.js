import BuildIcon from '@mui/icons-material/Build';
import { CardActionArea, CardHeader } from '@mui/material';
import Avatar from '@mui/material/Avatar';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import * as React from 'react';

export const MantenimientoCard = ({ item }) => {
    return (
        <Card
            sx={{
                height: '100%',
                // display: 'flex',
                flexDirection: 'column'
            }}
        >
            <CardActionArea onClick={() => alert('queso')}>
                <CardHeader
                    avatar={
                        <Avatar> <BuildIcon/></Avatar>
                    }
                    title={`${item.fecha} ${item.equipo}`}/>
                <CardContent sx={{
                    flexGrow: 1, alignSelf: 'center',
                    justifyContent: 'center',
                    alignItems: 'center',
                    textAlign: 'center',
                }}>
                    <Typography gutterBottom variant="h7" component="h2" color="textSecondary" alignContent='center'>
                        {item.mantenimiento}
                    </Typography>
                </CardContent>
            </CardActionArea>
        </Card>
    );
}