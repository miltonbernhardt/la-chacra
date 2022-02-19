import { ThemeProvider } from '@emotion/react';
import MenuIcon from '@mui/icons-material/Menu';
import { Box, CssBaseline, Typography } from '@mui/material';
import MuiAppBar from '@mui/material/AppBar';
import IconButton from '@mui/material/IconButton';
import { createTheme, styled } from '@mui/material/styles';
import Toolbar from '@mui/material/Toolbar';
import * as React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import CargarExpedicion from './pages/Expedicion/CargarExpedicion';
import CargarProduccion from './pages/Lotes/CargarLote';
import CargarQuesos from './pages/Quesos/CargarQuesos';
import CargarClientes from './pages/Clientes/CargarClientes';
import CustomDrawer from './components/CustomDrawer';
import Home from './components/Home';
import CargarPrecios from './pages/Precios/CargarPrecios';
import EmitirRemito from './pages/Remito/EmitirRemito';
import VerVentas from './pages/Ventas/VerVentas';
import VerLitrosElaborados from './pages/VerLitrosProducidos/VerLitrosElaborados';
import VerProduccion from './pages/VerProduccion/VerProduccion';
import VerTrazabilidad from './pages/VerTrazabilidad/VerTrazabilidad'
import { Toaster } from 'react-hot-toast';

export const themeOptions = {
    palette: {
        common: {
            black: "#000",
            white: "#fff"
        },
        background: {
            paper: "#fff",
            default: "#fafafa"
        },
        primary: {
            light: "rgba(247, 85, 82, 1)",
            main: "rgba(220, 48, 48, 1)",
            dark: "rgba(192, 28, 29, 1)",
            contrastText: "#fff"
        },
        secondary: {
            light: "rgba(176, 240, 237, 1)",
            main: "rgba(48, 220, 220, 1)",
            dark: "rgba(0, 186, 193, 1)",
            contrastText: "#fff"
        },
        error: {
            light: "#e57373",
            main: "#f44336",
            dark: "#d32f2f",
            contrastText: "#fff"
        },
        text: {
            primary: "rgba(0, 0, 0, 0.87)",
            secondary: "rgba(0, 0, 0, 0.54)",
            disabled: "rgba(0, 0, 0, 0.38)",
            hint: "rgba(0, 0, 0, 0.38)"
        }
    }
};

const drawerWidth = 240;

const Main = styled('main', {shouldForwardProp: (prop) => prop !== 'open'})(
    ({theme, open}) => ({
        flexGrow: 1,
        transition: theme.transitions.create('margin', {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.leavingScreen,
        }),
        marginLeft: `-${drawerWidth}px`,
        ...(open && {
            transition: theme.transitions.create('margin', {
                easing: theme.transitions.easing.easeOut,
                duration: theme.transitions.duration.enteringScreen,
            }),
            marginLeft: 0,
        }),
    }),
);

const AppBar = styled(MuiAppBar, {
    shouldForwardProp: (prop) => prop !== 'open',
})(({theme, open}) => ({
    transition: theme.transitions.create(['margin', 'width'], {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen,
    }),
    ...(open && {
        width: `calc(100% - ${drawerWidth}px)`,
        marginLeft: `${drawerWidth}px`,
        transition: theme.transitions.create(['margin', 'width'], {
            easing: theme.transitions.easing.easeOut,
            duration: theme.transitions.duration.enteringScreen,
        }),
    }),
}));

const DrawerHeader = styled('div')(({theme}) => ({
    display: 'flex',
    alignItems: 'center',
    padding: theme.spacing(0, 1),
    // necessary for content to be below app bar
    ...theme.mixins.toolbar,
    justifyContent: 'flex-end',
}));

const Toast = () => {
    return (
        <Toaster
            containerStyle={{
                bottom: 30,
                right: 10,
            }}
            position="bottom-right"
            toastOptions={{
                iconTheme: {
                    primary: 'black',
                },
                style: {
                    border: '1px solid #713200',
                    width: '100%',
                    fontSize: '0.85em',
                    color: 'black',
                },
                success: {
                    duration: 2000,
                    style: {
                        background: '#7ecc8e',
                    },
                },
                error: {
                    duration: 5000,
                    style: {
                        background: '#ff9191',
                    },
                },
            }}
        />
    )
}

const App = () => {
    const theme = createTheme(themeOptions);
    const [drawerOpen, setDrawerOpen] = React.useState(false);

    const handleDrawerOpen = () => {
        setDrawerOpen(true);
    };

    const handleDrawerClose = () => {
        setDrawerOpen(false);
    };

    return (
        <>
            <ThemeProvider theme={theme}>
                <Router>
                    <Box display="flex" height="98vh">
                        <CssBaseline/>
                        <AppBar position="fixed" open={drawerOpen}>
                            <Toolbar>
                                <IconButton
                                    color="inherit"
                                    aria-label="open drawer"
                                    onClick={handleDrawerOpen}
                                    edge="start"
                                    sx={{mr: 2, ...(drawerOpen && {display: 'none'})}}
                                >
                                    <MenuIcon/>
                                </IconButton>
                                <Typography variant="h6" noWrap component="div">
                                    La Chacra
                                </Typography>
                            </Toolbar>
                        </AppBar>
                        <CustomDrawer
                            drawerWidth={drawerWidth}
                            open={drawerOpen}
                            handleDrawerClose={handleDrawerClose}
                            theme={theme}
                        />
                        <Main open={drawerOpen}>
                            <DrawerHeader/>
                            <Switch>
                                <Route exact path="/" component={Home}/>
                                <Route path="/clientes" component={CargarClientes}/>
                                <Route path="/cargar/lotes" component={CargarProduccion}/>
                                <Route path="/cargar/expedicion" component={CargarExpedicion}/>
                                <Route path="/cargar/quesos" component={CargarQuesos}/>
                                <Route path="/cargar/precios" component={CargarPrecios}/>
                                <Route path="/ver/litros" component={VerLitrosElaborados}/>
                                <Route path="/ver/produccion" component={VerProduccion}/>
                                <Route path="/ver/ventas" component={VerVentas}/>
                                <Route path="/ver/trazabilidad" component={VerTrazabilidad}/>
                                <Route path="/emitir/remito" component={EmitirRemito}/>
                            </Switch>
                        </Main>
                    </Box>
                </Router>
            </ThemeProvider>
            <Toast/>
        </>
    );
}

export default App;