import AddIcon from '@mui/icons-material/Add';
import AnalyticsIcon from '@mui/icons-material/Analytics';
import AttachMoneyIcon from '@mui/icons-material/AttachMoney';
import BalanceIcon from '@mui/icons-material/Balance';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import DashboardIcon from '@mui/icons-material/Dashboard';
import HistoryIcon from '@mui/icons-material/History';
import HomeIcon from '@mui/icons-material/Home';
import Inventory2Icon from '@mui/icons-material/Inventory2';
import LocalShippingIcon from '@mui/icons-material/LocalShipping';
import LogoutIcon from '@mui/icons-material/Logout';
import MenuBookIcon from '@mui/icons-material/MenuBook';
import PercentIcon from '@mui/icons-material/Percent';
import PersonIcon from '@mui/icons-material/Person';
import PointOfSaleIcon from '@mui/icons-material/PointOfSale';
import SellIcon from '@mui/icons-material/Sell';
import TimelineIcon from '@mui/icons-material/Timeline';
import { Divider, Drawer as DrawerMU, IconButton, List, ListItem, ListItemIcon, ListItemText } from '@mui/material';
import { styled } from '@mui/material/styles';
import * as React from 'react';
import { Link, useHistory } from "react-router-dom";
import * as paths from "../resources/paths";
import { useAuth } from "../services/use-auth";

const DrawerHeader = styled('div')(({ theme }) => ({
    display: 'flex',
    alignItems: 'center',
    padding: theme.spacing(0, 1),
    ...theme.mixins.toolbar,
    justifyContent: 'flex-end',
    backgroundColor: "rgba(220, 48, 48, 1)",

    contrastText: "#fff"
}));

const DrawerButton = ({ label, path, action, icon }) => {
    return <ListItem button component={Link} to={path}>
        <ListItemIcon>
            {icon}
        </ListItemIcon>
        <ListItemText primary={label} onClick={() => action()}/>
    </ListItem>
}

const HomeSection = ({ closeDrawer }) => {
    return <>
        <Divider/>
        <List>
            <DrawerButton label="Inicio" path={paths.home} icon={<HomeIcon/>} action={closeDrawer}/>
        </List>
    </>
}

//todo naming
const LoadUpSection = ({ closeDrawer, permisos }) => {
    const enableCargarLotes = Array.isArray(permisos) && permisos.includes(paths.cargarLotes)
    const enableCargarExpedicion = Array.isArray(permisos) && permisos.includes(paths.cargarExpedicion)
    const enableEmitirRemitos = Array.isArray(permisos) && permisos.includes(paths.emitirRemito)

    if (!(enableCargarLotes || enableCargarExpedicion || enableEmitirRemitos))
        return <></>

    return <>
        <Divider/>
        <List>
            {enableCargarLotes
                ? <DrawerButton label="Cargar Producción" path={paths.cargarLotes} action={closeDrawer}
                                icon={<AddIcon/>}/>
                : undefined
            }
            {enableCargarExpedicion
                ? <DrawerButton label="Cargar Expediciones" path={paths.cargarExpedicion} action={closeDrawer}
                                icon={<LocalShippingIcon/>}/>
                : undefined
            }
            {enableEmitirRemitos
                ? <DrawerButton label="Emitir Remito" path={paths.emitirRemito} action={closeDrawer}
                                icon={<PointOfSaleIcon/>}/>
                : undefined
            }
        </List>
    </>
}

//todo naming
const BusinessSection = ({ closeDrawer, permisos }) => {
    const enableCargarQuesos = Array.isArray(permisos) && permisos.includes(paths.cargarQuesos)
    const enableCargarPrecios = Array.isArray(permisos) && permisos.includes(paths.cargarPrecios)
    const enableCargarClientes = Array.isArray(permisos) && permisos.includes(paths.clientes)

    if (!(enableCargarQuesos || enableCargarPrecios || enableCargarClientes))
        return <></>

    return <>
        <Divider/>
        <List>
            {enableCargarQuesos
                ? <DrawerButton label="Productos" path={paths.cargarQuesos} action={closeDrawer}
                                icon={<MenuBookIcon/>}/>
                : undefined
            }
            {enableCargarPrecios
                ? <DrawerButton label="Precios" path={paths.cargarPrecios} action={closeDrawer}
                                icon={<AttachMoneyIcon/>}/>
                : undefined
            }
            {enableCargarClientes
                ? <DrawerButton label="Clientes" path={paths.clientes} action={closeDrawer}
                                icon={<PersonIcon/>}/>
                : undefined
            }
        </List>
    </>
}

//todo naming
const StatsSection = ({ closeDrawer, permisos }) => {
    const enableVerProduccion = Array.isArray(permisos) && permisos.includes(paths.verProduccion)
    const enableVerLitros = Array.isArray(permisos) && permisos.includes(paths.verLitros)
    const enableStockProductos = Array.isArray(permisos) && permisos.includes(paths.stockProductos)
    const enableRendimiento = Array.isArray(permisos) && permisos.includes(paths.rendimiento)
    const enableVerVentas = Array.isArray(permisos) && permisos.includes(paths.verVentas)

    if (!(enableVerProduccion || enableVerLitros || enableStockProductos || enableRendimiento || enableVerVentas))
        return <></>

    return <>
        <Divider/>
        <List>
            {enableVerProduccion
                ? <DrawerButton label="Producción" path={paths.verProduccion} action={closeDrawer}
                                icon={<TimelineIcon/>}/>
                : undefined
            }
            {enableVerLitros
                ? <DrawerButton label="Litros Elaborados" path={paths.verLitros} action={closeDrawer}
                                icon={<PercentIcon/>}/>
                : undefined
            }
            {enableStockProductos
                ? <DrawerButton label="Stock Productos" path={paths.stockProductos} action={closeDrawer}
                                icon={<DashboardIcon/>}/>
                : undefined
            }
            {enableRendimiento
                ? <DrawerButton label="Rendimiento" path={paths.rendimiento} action={closeDrawer}
                                icon={<AnalyticsIcon/>}/>
                : undefined
            }
            {enableVerVentas
                ? <DrawerButton label="Ventas" path={paths.verVentas} action={closeDrawer}
                                icon={<BalanceIcon/>}/>
                : undefined
            }
        </List>
    </>
}

//todo naming
const BackgroundSection = ({ closeDrawer, permisos }) => {
    const enableStockEmbalaje = Array.isArray(permisos) && permisos.includes(paths.stockEmbalaje)
    const enableMantenimiento = Array.isArray(permisos) && permisos.includes(paths.mantenimiento)
    const enableTrazabilidad = Array.isArray(permisos) && permisos.includes(paths.verTrazabilidad)

    if (!(enableStockEmbalaje || enableMantenimiento || enableTrazabilidad))
        return <></>

    return <>
        <Divider/>
        <List>
            {enableStockEmbalaje
                ? <DrawerButton label="Stock Embalaje" path={paths.stockEmbalaje} action={closeDrawer}
                                icon={<Inventory2Icon/>}/>
                : undefined
            }
            {enableMantenimiento
                ? <DrawerButton label="Mantenimiento" path={paths.mantenimiento} action={closeDrawer}
                                icon={<SellIcon/>}/>
                : undefined
            }
            {enableTrazabilidad
                ? <DrawerButton label="Trazabilidad" path={paths.verTrazabilidad} action={closeDrawer}
                                icon={<HistoryIcon/>}/>
                : undefined
            }
        </List>
    </>
}

const LogoutSection = ({ logout }) => {
    return <>
        <Divider/>
        <List>
            <ListItem button>
                <ListItemIcon>
                    <LogoutIcon/>
                </ListItemIcon>
                <ListItemText primary="Cerrar sesión" onClick={() => logout()}/>
            </ListItem>
        </List>
    </>
}

export const Drawer = ({ drawerWidth, open, handleDrawerClose, theme, permisos }) => {
    const auth = useAuth();
    const history = useHistory();

    return (
        <DrawerMU
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
                <IconButton onClick={handleDrawerClose} style={{ color: "white" }}>
                    {theme.direction === 'ltr' ? <ChevronLeftIcon/> : <ChevronRightIcon/>}
                </IconButton>
            </DrawerHeader>
            <HomeSection closeDrawer={handleDrawerClose}/>
            <LoadUpSection closeDrawer={handleDrawerClose} permisos={permisos}/>
            <BusinessSection closeDrawer={handleDrawerClose} permisos={permisos}/>
            <StatsSection closeDrawer={handleDrawerClose} permisos={permisos}/>
            <BackgroundSection closeDrawer={handleDrawerClose} permisos={permisos}/>
            <LogoutSection logout={() => {
                //todo mover afuera
                auth.signout()
                history.push(paths.login)
            }}/>
        </DrawerMU>
    );
}