import * as React from 'react';
import { useState, useEffect } from 'react';
import { ThemeProvider } from '@emotion/react';
import { Box, CssBaseline } from '@mui/material';
import { createTheme, styled } from '@mui/material/styles';
import MuiAppBar from '@mui/material/AppBar';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import CustomToolbar from "./components/CustomToolbar";
import CargarExpedicion from './pages/Expedicion/CargarExpedicion';
import Login from "./pages/Login/Login";
import CargarProduccion from './pages/Lotes/CargarLote';
import CargarQuesos from './pages/Quesos/CargarQuesos';
import CargarClientes from './pages/Clientes/Clientes';
import CustomDrawer from './components/CustomDrawer';
import Home from './components/Home';
import CargarPrecios from './pages/Precios/CargarPrecios';
import EmitirRemito from './pages/Remito/EmitirRemito';
import VerVentas from './pages/Ventas/VerVentas';
import VerLitrosElaborados from './pages/VerLitrosProducidos/VerLitrosElaborados';
import VerProduccion from './pages/VerProduccion/VerProduccion';
import VerTrazabilidad from './pages/VerTrazabilidad/VerTrazabilidad'
import { Toaster } from 'react-hot-toast';
import { existsLoginCookie } from "./services/loginService";

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

const AppBar = styled(MuiAppBar, {
    shouldForwardProp: (prop) => prop !== 'open',
})(({ theme, open }) => ({
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


const Main = styled('main', { shouldForwardProp: (prop) => prop !== 'open' })(
    ({ theme, open }) => ({
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

const DrawerHeader = styled('div')(({ theme }) => ({
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
                    width: '100%',
                    fontSize: '0.85em',
                    color: 'white',
                    boxShadow: '2px 2px 5px #b2b2b2'
                },
                success: {
                    duration: 5000,
                    style: {
                        background: '#2e7d32',
                    },
                },
                error: {
                    duration: 5000,
                    style: {
                        background: '#e57373',
                    },
                },
            }}
        />
    )
}

const App = () => {
    const theme = createTheme(themeOptions);
    const [drawerOpen, setDrawerOpen] = useState(false);
    const [isLogged, setIsLogged] = useState(false);

    const logout = () => {
        //TODO mas bien seria una acción hacia el backend y que luego dirija a la página por default de alguien no logged
        console.log("logout")
        localStorage.removeItem("username");
        //todo mostrar dialogo de confirmación de cierre de sesión
        setIsLogged(false);
    };

    useEffect(() => {
        console.log("use effect con is logged")
        setIsLogged(existsLoginCookie())
    }, [isLogged]);

    const handleDrawerOpen = () => {
        setDrawerOpen(true);
    };

    const handleDrawerClose = () => {
        setDrawerOpen(false);
    };

    //todo se podrían hacer componentes por roles y que muestren los diferentes menus
    const PageWhenLogged = () => {
        return <Router>
            <Box display="flex" height="98vh">
                <CssBaseline/>
                <AppBar position="fixed" open={drawerOpen}>
                    <CustomToolbar handleDrawerOpen={handleDrawerOpen} drawerOpen={drawerOpen}/>
                </AppBar>
                <CustomDrawer
                    drawerWidth={drawerWidth}
                    open={drawerOpen}
                    handleDrawerClose={handleDrawerClose}
                    theme={theme}
                    logout={logout}
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
    }

    return (
        <>
            <ThemeProvider theme={theme}>
                {isLogged ? <PageWhenLogged/> : <Login setLogged={setIsLogged}/>}
            </ThemeProvider>
            <Toast/>
        </>
    );
}

export default App;