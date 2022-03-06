import * as React from 'react';
import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { Typography, IconButton, Button } from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import LoginIcon from '@mui/icons-material/Login';
import LogoutIcon from '@mui/icons-material/Logout';
import Toolbar from '@mui/material/Toolbar';

const AppToolbar = ({ drawerOpen, handleDrawerOpen }) => {
    const [isLogged, setIsLogged] = useState(false);

    const logout = () => {
        //TODO mas bien seria una acción hacia el backend y que luego dirija a la página por default de alguien no logged

        localStorage.removeItem("user");
        setIsLogged(false);
    };

    useEffect(() => {
        checkStorage();
        return () => {
        };
    }, [isLogged]);

    const checkStorage = () => {
        if (localStorage.getItem("user"))
            setIsLogged(true);
        else
            setIsLogged(false);
    }

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
        {!isLogged ?
            <Button variant="inherit" endIcon={<LoginIcon/>} component={Link} to="/login">
                Iniciar Sesión
            </Button>
            :
            <Button variant="inherit" endIcon={<LogoutIcon/>} onClick={logout}>
                Cerrar Sesión
            </Button>
        }
    </Toolbar>
}

export default AppToolbar