import { Grid, Paper, Stack } from '@mui/material';
import Typography from '@mui/material/Typography';
import * as React from 'react';
import { SearchByMonths } from '../../components/SearchByMonths';

export const RendimientoSearch = ({ fechaInicial, meses, onSearch }) => {
    return (
        <Grid item xs={12} sm={12} md={4} spacing={2}>
            <Paper
                sx={{
                    p: 2,
                    display: 'flex',
                    flexDirection: 'column',
                    height: '100%',
                    width: '100%'
                }}            >
                <Stack sx={{ height: '100%' }} direction="column" justifyContent="space-evenly">
                    <Typography variant='h6'>Consultar produccion</Typography>
                    <SearchByMonths
                        fechaInicial={fechaInicial}
                        meses={meses}
                        onSearch={onSearch} />
                </Stack>
            </Paper>
        </Grid>
    );
}