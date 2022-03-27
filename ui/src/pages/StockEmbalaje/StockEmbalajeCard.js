import Inventory2Icon from '@mui/icons-material/Inventory2';
import ShoppingBagIcon from '@mui/icons-material/ShoppingBag';
import { CardActionArea, CardHeader } from '@mui/material';
import Avatar from '@mui/material/Avatar';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import * as React from 'react';

export function StockEmbalajeCard({ item }) {
    return (
        <Card
            sx={{
                height: '100%',
                flexDirection: 'column'
            }}
        >
            <CardActionArea onClick={() => alert('queso')}>
                <CardHeader
                    avatar={
                        item.tipo === 'caja' ?
                            <Avatar sx={{ bgcolor: "#ed6c02" }}>
                                <Inventory2Icon/>
                            </Avatar>
                            :
                            <Avatar sx={{ bgcolor: "#0288d1" }}>
                                <ShoppingBagIcon/>
                            </Avatar>
                    }
                    title={item.tipo}/>
                <CardContent sx={{
                    flexGrow: 1, alignSelf: 'center',
                    justifyContent: 'center',
                    alignItems: 'center',
                    textAlign: 'center',
                }}>
                    <Typography variant="h3" component="h2" alignContent='center'>
                        {item.stock}
                    </Typography>
                    <Typography gutterBottom variant="h7" component="h2" color="textSecondary" alignContent='center'>
                        {item.productos}
                    </Typography>
                </CardContent>
            </CardActionArea>
        </Card>
    );
}
