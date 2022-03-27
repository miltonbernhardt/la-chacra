import MenuIcon from '@mui/icons-material/Menu'
import { IconButton, Typography } from '@mui/material'
import Toolbar from '@mui/material/Toolbar'
import * as React from 'react';

export const Toolbar = ({ drawerOpen, handleDrawerOpen }) => {
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
