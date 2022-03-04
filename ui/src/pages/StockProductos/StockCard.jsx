import { CardActionArea, CardHeader } from '@mui/material';
import Avatar from '@mui/material/Avatar';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import { red } from '@mui/material/colors';
import Typography from '@mui/material/Typography';
import * as React from 'react';

export default function StockCard({ tipoQueso, stockQueso, nomenclatura }) {
    return (
        <Card
            sx={{
                height: '100%',
                // display: 'flex',
                flexDirection: 'column'
            }}
        >
            <CardActionArea onClick={() => alert('queso')}>

                {/* <CardMedia
                component="img"
                sx={{
                    // 16:9
                    // pt: '56.25%',
                }}
                image="https://source.unsplash.com/random"
                alt="random"
            /> */}
                <CardHeader
                    avatar={
                        <Avatar sx={{ bgcolor: red[500] }} >
                            {nomenclatura}
                        </Avatar>
                    }
                    title={tipoQueso} />
                <CardContent sx={{ flexGrow: 1, alignSelf: 'center' }}>
                    <Typography gutterBottom variant="h3" component="h2" alignContent='center'>
                        {stockQueso}
                    </Typography>
                </CardContent>
            </CardActionArea>
        </Card>
    );
}
