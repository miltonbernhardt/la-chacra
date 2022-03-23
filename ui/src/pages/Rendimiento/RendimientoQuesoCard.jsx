import { CardActionArea, CardHeader } from '@mui/material';
import Avatar from '@mui/material/Avatar';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import * as React from 'react';

const RendimientoQuesoCard = ({ queso, rendimiento }) => {
    return (
        <Card
            sx={{
                height: '100%',
                flexDirection: 'column',
                justifyContent: 'center',
                alignItems: 'center'
            }}
        >
            <CardActionArea onClick={() => alert('queso')}>
                <CardHeader
                    avatar={
                        <Avatar sx={{ bgcolor: queso.color }} >
                            {queso.nomenclatura}
                        </Avatar>
                    } />
                <CardContent sx={{
                    flexGrow: 1,
                    justifyContent: 'center',
                    alignItems: 'center',
                    textAlign: 'center',
                }}>
                    <Typography gutterBottom variant="h3" component="h2" >
                        {rendimiento}
                    </Typography>
                    <Typography gutterBottom variant="h7" color="textSecondary" component="h2" >
                        {queso.tipoQueso}
                    </Typography>
                </CardContent>
            </CardActionArea>
        </Card>
    );
}

export default RendimientoQuesoCard;