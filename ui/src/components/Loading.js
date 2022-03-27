import { Box } from '@mui/material';
import CircularProgress from '@mui/material/CircularProgress';
import * as React from 'react';

export const Loading = () => {
    return (<Box sx={{
        marginTop: 8,
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        mt: 3,
    }}>
        <CircularProgress size={"50%"}/>
    </Box>);
}