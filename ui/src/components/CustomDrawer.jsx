import AddIcon from '@mui/icons-material/Add';
import AttachMoneyIcon from '@mui/icons-material/AttachMoney';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import LocalShippingIcon from '@mui/icons-material/LocalShipping';
import MenuBookIcon from '@mui/icons-material/MenuBook';
import PercentIcon from '@mui/icons-material/Percent';
import PersonIcon from '@mui/icons-material/Person';
import PointOfSaleIcon from '@mui/icons-material/PointOfSale';
import SellIcon from '@mui/icons-material/Sell';
import TimelineIcon from '@mui/icons-material/Timeline';
import Divider from '@mui/material/Divider';
import Drawer from '@mui/material/Drawer';
import IconButton from '@mui/material/IconButton';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import { styled } from '@mui/material/styles';
import * as React from 'react';
import { Link } from "react-router-dom";
import HistoryIcon from '@mui/icons-material/History';
import HomeIcon from '@mui/icons-material/Home';

const DrawerHeader = styled('div')(({ theme }) => ({
    display: 'flex',
    alignItems: 'center',
    padding: theme.spacing(0, 1),
    // necessary for content to be below app bar
    ...theme.mixins.toolbar,
    justifyContent: 'flex-end',
}));


const CustomDrawer = ({ drawerWidth, open, handleDrawerClose, theme }) => {
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
                <IconButton onClick={handleDrawerClose}>
                    {theme.direction === 'ltr' ? <ChevronLeftIcon /> : <ChevronRightIcon />}
                </IconButton>
            </DrawerHeader>
            <Divider />
            <List>
                <ListItem button component={Link} to="/">
                    <ListItemIcon>
                        <HomeIcon />
                    </ListItemIcon>
                    <ListItemText primary="Inicio" />
                </ListItem>
            </List>
            <Divider />
            <List>
                <ListItem button component={Link} to="/cargar/produccion">
                    <ListItemIcon>
                        <AddIcon />
                    </ListItemIcon>
                    <ListItemText primary="Cargar Producción" />
                </ListItem>
                <ListItem button component={Link} to="/cargar/expedicion">
                    <ListItemIcon>
                        <LocalShippingIcon />
                    </ListItemIcon>
                    <ListItemText primary="Cargar Expedición" />
                </ListItem>
                <ListItem button component={Link} to="/cargar/productos">
                    <ListItemIcon>
                        <MenuBookIcon />
                    </ListItemIcon>
                    <ListItemText primary="Cargar Productos" />
                </ListItem>
                <ListItem button component={Link} to="/cargar/precios">
                    <ListItemIcon>
                        <AttachMoneyIcon />
                    </ListItemIcon>
                    <ListItemText primary="Cargar Precios" />
                </ListItem>
                <ListItem button component={Link} to="/clientes">
                    <ListItemIcon>
                        <PersonIcon />
                    </ListItemIcon>
                    <ListItemText primary="Clientes" />
                </ListItem>

            </List>
            <Divider />
            <List>
                <ListItem button component={Link} to="/ver/produccion">
                    <ListItemIcon>
                        <TimelineIcon />
                    </ListItemIcon>
                    <ListItemText primary="Ver Producción" />
                </ListItem>
                <ListItem button component={Link} to="/ver/litros">
                    <ListItemIcon>
                        <PercentIcon />
                    </ListItemIcon>
                    <ListItemText primary="Litros Elaborados" />
                </ListItem>
                <ListItem button component={Link} to="/ver/ventas">
                    <ListItemIcon>
                        <SellIcon />
                    </ListItemIcon>
                    <ListItemText primary="Ventas" />
                </ListItem>
                <ListItem button component={Link} to="/ver/trazabilidad">
                    <ListItemIcon>
                        <HistoryIcon />
                    </ListItemIcon>
                    <ListItemText primary="Trazabilidad" />
                </ListItem>
            </List>
            <Divider />
            <List>
                <ListItem button component={Link} to="/emitir/remito">
                    <ListItemIcon>
                        <PointOfSaleIcon />
                    </ListItemIcon>
                    <ListItemText primary="Emitir Remito" />
                </ListItem>
            </List >
        </Drawer>
    );
}

export default CustomDrawer;