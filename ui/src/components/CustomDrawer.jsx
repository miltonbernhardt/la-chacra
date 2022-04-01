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
import AnalyticsIcon from '@mui/icons-material/Analytics';
import BalanceIcon from '@mui/icons-material/Balance';
import DashboardIcon from '@mui/icons-material/Dashboard';
import Inventory2Icon from '@mui/icons-material/Inventory2';

const DrawerHeader = styled('div')(({ theme }) => ({
    display: 'flex',
    alignItems: 'center',
    padding: theme.spacing(0, 1),
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
                <ListItem button component={Link} to="/cargar/lotes">
                    <ListItemIcon>
                        <AddIcon />
                    </ListItemIcon>
                    <ListItemText primary="Cargar Producción" />
                </ListItem>
                <ListItem button component={Link} to="/cargar/expedicion">
                    <ListItemIcon>
                        <LocalShippingIcon />
                    </ListItemIcon>
                    <ListItemText primary="Cargar Expediciones" />
                </ListItem>
                <ListItem button component={Link} to="/emitir/remito">
                    <ListItemIcon>
                        <PointOfSaleIcon />
                    </ListItemIcon>
                    <ListItemText primary="Emitir remito" />
                </ListItem>
            </List>
            <Divider />
            <List>
                <ListItem button component={Link} to="/cargar/quesos">
                    <ListItemIcon>
                        <MenuBookIcon />
                    </ListItemIcon>
                    <ListItemText primary="Productos" />
                </ListItem>
                <ListItem button component={Link} to="/cargar/precios">
                    <ListItemIcon>
                        <AttachMoneyIcon />
                    </ListItemIcon>
                    <ListItemText primary="Precios" />
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
                    <ListItemText primary="Producción" />
                </ListItem>
                <ListItem button component={Link} to="/ver/litros">
                    <ListItemIcon>
                        <PercentIcon />
                    </ListItemIcon>
                    <ListItemText primary="Litros Elaborados" />
                </ListItem>
                <ListItem button component={Link} to="/stock/productos">
                    <ListItemIcon>
                        <DashboardIcon />
                    </ListItemIcon>
                    <ListItemText primary="Stock Productos" />
                </ListItem>
                <ListItem button component={Link} to="/rendimiento">
                    <ListItemIcon>
                        <AnalyticsIcon />
                    </ListItemIcon>
                    <ListItemText primary="Rendimiento" />
                </ListItem>
                <ListItem button component={Link} to="/ver/ventas">
                    <ListItemIcon>
                        <BalanceIcon />
                    </ListItemIcon>
                    <ListItemText primary="Ventas" />
                </ListItem>
            </List>
            <Divider />
            <List>
                <ListItem button component={Link} to="/stock/embalaje">
                    <ListItemIcon>
                        <Inventory2Icon />
                    </ListItemIcon>
                    <ListItemText primary="Stock Embalaje" />
                </ListItem>
                <ListItem button component={Link} to="/mantenimiento">
                    <ListItemIcon>
                        <SellIcon />
                    </ListItemIcon>
                    <ListItemText primary="Mantenimiento" />
                </ListItem>
                <ListItem button component={Link} to="/ver/trazabilidad">
                    <ListItemIcon>
                        <HistoryIcon />
                    </ListItemIcon>
                    <ListItemText primary="Trazabilidad" />
                </ListItem>
            </List>
        </Drawer>
    );
}

export default CustomDrawer;