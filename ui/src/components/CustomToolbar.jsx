import * as React from 'react';
import { Typography, IconButton } from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import Toolbar from '@mui/material/Toolbar';

const CustomToolbar = ({ drawerOpen, handleDrawerOpen }) => {
    return <Toolbar>
        <IconButton
            color="inherit"
            aria-label="open drawer"
            onClick={handleDrawerOpen}
            edge="start"
            sx={{ mr: 2, ...(drawerOpen && { display: 'none' }) }}
        >
            <MenuIcon/>
        </IconButton>
        <Typography variant="h6" noWrap component="div" sx={{ flexGrow: 1 }}>
            La Chacra
        </Typography>
    </Toolbar>
}

export default CustomToolbar