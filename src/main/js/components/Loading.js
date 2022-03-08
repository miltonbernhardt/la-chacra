import * as React from 'react';
import CircularProgress from '@mui/material/CircularProgress';
import { Box } from '@mui/material';

export const Loading = () => {
    return (<Box sx={{
        marginTop: 8,
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        mt: 3,
    }}>
        <CircularProgress size={"50%"} />
    </Box>);
}