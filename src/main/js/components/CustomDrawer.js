import * as React from 'react';
import AddIcon from '@mui/icons-material/Add';
import AttachMoneyIcon from '@mui/icons-material/AttachMoney';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import HistoryIcon from '@mui/icons-material/History';
import HomeIcon from '@mui/icons-material/Home';
import LocalShippingIcon from '@mui/icons-material/LocalShipping';
import LogoutIcon from '@mui/icons-material/Logout';
import MenuBookIcon from '@mui/icons-material/MenuBook';
import PercentIcon from '@mui/icons-material/Percent';
import PersonIcon from '@mui/icons-material/Person';
import PointOfSaleIcon from '@mui/icons-material/PointOfSale';
import SellIcon from '@mui/icons-material/Sell';
import TimelineIcon from '@mui/icons-material/Timeline';
import { Divider, Drawer, IconButton, ListItemText, List, ListItem, ListItemIcon } from '@mui/material';
import { Link } from "react-router-dom";
import { styled } from '@mui/material/styles';

const DrawerHeader = styled('div')(({ theme }) => ({
    display: 'flex',
    alignItems: 'center',
    padding: theme.spacing(0, 1),
    ...theme.mixins.toolbar,
    justifyContent: 'flex-end',
    backgroundColor:  "rgba(220, 48, 48, 1)",

    contrastText: "#fff"
}));

export const CustomDrawer = ({ drawerWidth, open, handleDrawerClose, theme, logout }) => {
    return (
        <Drawer
            sx={{
                width: drawerWidth,
                flexShrink: 0,
                '& .MuiDrawer-paper': {
                    width: drawerWidth,
                    boxSizing: 'border-box',
                },
            }}
            variant="persistent"
            anchor="left"
            open={open}
        >
            <DrawerHeader>
                <IconButton onClick={handleDrawerClose} style={{color: "white"}}>
                    {theme.direction === 'ltr' ? <ChevronLeftIcon/> : <ChevronRightIcon/>}
                </IconButton>
            </DrawerHeader>
            <Divider/>
            <List>
                <ListItem button component={Link} to="/">
                    <ListItemIcon>
                        <HomeIcon/>
                    </ListItemIcon>
                    <ListItemText primary="Inicio"/>
                </ListItem>
            </List>
            <Divider/>
            <List>
                <ListItem button component={Link} to="/cargar/lotes" onClick={() => handleDrawerClose()}>
                    <ListItemIcon>
                        <AddIcon/>
                    </ListItemIcon>
                    <ListItemText primary="Cargar Producción"/>
                </ListItem>
                <ListItem button component={Link} to="/cargar/expedicion" onClick={() => handleDrawerClose()}>
                    <ListItemIcon>
                        <LocalShippingIcon/>
                    </ListItemIcon>
                    <ListItemText primary="Cargar Expediciones" onClick={() => handleDrawerClose()}/>
                </ListItem>
            </List>
            <Divider/>
            <List>
                <ListItem button component={Link} to="/cargar/quesos" onClick={() => handleDrawerClose()}>
                    <ListItemIcon>
                        <MenuBookIcon/>
                    </ListItemIcon>
                    <ListItemText primary="Productos"/>
                </ListItem>
                <ListItem button component={Link} to="/cargar/precios" onClick={() => handleDrawerClose()}>
                    <ListItemIcon>
                        <AttachMoneyIcon/>
                    </ListItemIcon>
                    <ListItemText primary="Precios"/>
                </ListItem>
                <ListItem button component={Link} to="/clientes" onClick={() => handleDrawerClose()}>
                    <ListItemIcon>
                        <PersonIcon/>
                    </ListItemIcon>
                    <ListItemText primary="Clientes"/>
                </ListItem>
            </List>
            <Divider/>
            <List>
                <ListItem button component={Link} to="/ver/produccion" onClick={() => handleDrawerClose()}>
                    <ListItemIcon>
                        <TimelineIcon/>
                    </ListItemIcon>
                    <ListItemText primary="Ver producción"/>
                </ListItem>
                <ListItem button component={Link} to="/ver/litros" onClick={() => handleDrawerClose()}>
                    <ListItemIcon>
                        <PercentIcon/>
                    </ListItemIcon>
                    <ListItemText primary="Litros elaborados"/>
                </ListItem>
                <ListItem button component={Link} to="/ver/ventas" onClick={() => handleDrawerClose()}>
                    <ListItemIcon>
                        <SellIcon/>
                    </ListItemIcon>
                    <ListItemText primary="Ventas"/>
                </ListItem>
                <ListItem button component={Link} to="/ver/trazabilidad" onClick={() => handleDrawerClose()}>
                    <ListItemIcon>
                        <HistoryIcon/>
                    </ListItemIcon>
                    <ListItemText primary="Trazabilidad"/>
                </ListItem>
            </List>
            <Divider/>
            <List>
                <ListItem button component={Link} to="/emitir/remito" onClick={() => handleDrawerClose()}>
                    <ListItemIcon>
                        <PointOfSaleIcon/>
                    </ListItemIcon>
                    <ListItemText primary="Emitir remito"/>
                </ListItem>
            </List>
            <Divider/>
            <List>
                <ListItem button onClick={logout}>
                    <ListItemIcon>
                        <LogoutIcon/>
                    </ListItemIcon>
                    <ListItemText primary="Cerrar Sesión"/>
                </ListItem>
            </List>
        </Drawer>
    );
}