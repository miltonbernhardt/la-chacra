import {CardActionArea, CardHeader} from '@mui/material';
import Avatar from '@mui/material/Avatar';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import {red} from '@mui/material/colors';
import Typography from '@mui/material/Typography';
import * as React from 'react';

const RendimientoQuesoCard = ({queso, stockQueso}) => {
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
                        <Avatar sx={{bgcolor: red[500]}}>
                            {queso.nomenclatura}
                        </Avatar>
                    }
                    title={queso.tipoQueso}/>
                <CardContent sx={{
                    flexGrow: 1,
                    justifyContent: 'center',
                    alignItems: 'center',
                    textAlign: 'center',
                }}>
                    <Typography gutterBottom variant="h3" component="h2">
                        {stockQueso}
                    </Typography>
                </CardContent>
            </CardActionArea>
        </Card>
    );
}

export default RendimientoQuesoCard;