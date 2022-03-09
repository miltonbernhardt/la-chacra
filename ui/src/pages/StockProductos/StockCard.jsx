import { CardActionArea, CardHeader } from '@mui/material';
import Avatar from '@mui/material/Avatar';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import * as React from 'react';

export default function StockCard({ queso }) {
    return (
        <Card
            sx={{
                height: '100%',
                // display: 'flex',
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
                    }
                    title={''} />
                <CardContent sx={{
                    flexGrow: 1,
                    justifyContent: 'center',
                    alignItems: 'center',
                    textAlign: 'center',
                }}>
                    <Typography gutterBottom variant="h3" component="h2" >
                        {queso.stock}
                    </Typography>
                    <Typography gutterBottom variant="h7" component="h2" color="textSecondary" alignContent='center'>
                        {queso.tipoQueso}
                    </Typography>
                </CardContent>
            </CardActionArea>
        </Card>
    );
}
