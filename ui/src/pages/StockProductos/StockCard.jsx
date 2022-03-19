import {CardActionArea, CardHeader} from '@mui/material';
import Avatar from '@mui/material/Avatar';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import * as React from 'react';
import {StockProductoDialog} from './StockProductoDialog';

export default function StockCard({queso}) {

    const [isStockDialogOpen, setStockDialogOpen] = React.useState(false);

    const handleCardAction = React.useCallback(() => {
        setStockDialogOpen(true)
    }, [])

    const closeDialog = React.useCallback(() => {
        setStockDialogOpen(false)
    }, [])

    return (
        <>
            <Card
                sx={{
                    height: '100%',
                    flexDirection: 'column',
                    justifyContent: 'center',
                    alignItems: 'center'
                }}
            >
                <CardActionArea onClick={handleCardAction}>
                    <CardHeader
                        avatar={
                            <Avatar sx={{bgcolor: queso.color}}>
                                {queso.nomenclatura}
                            </Avatar>
                        }
                        title={''}/>
                    <CardContent sx={{
                        flexGrow: 1,
                        justifyContent: 'center',
                        alignItems: 'center',
                        textAlign: 'center',
                    }}>
                        <Typography gutterBottom variant="h3" component="h2">
                            {queso.stock}
                        </Typography>
                        <Typography gutterBottom variant="h7" component="h2" color="textSecondary"
                                    alignContent='center'>
                            {queso.tipoQueso}
                        </Typography>
                    </CardContent>
                </CardActionArea>
            </Card>
            <StockProductoDialog
                queso={queso}
                open={isStockDialogOpen}
                onClose={closeDialog}
            />
        </>
    );
}
