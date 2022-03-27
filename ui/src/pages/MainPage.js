import { Box, CssBaseline } from "@mui/material";
import MuiAppBar from "@mui/material/AppBar";
import { styled } from "@mui/material/styles";
import * as React from "react";
import { useState } from "react";
import { CustomToolbar } from "../components/CustomToolbar";
import { Drawer } from "../components/Drawer";
import { Routes } from "../components/Routes";
import { useAuth } from "../services/use-auth";

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

const MainContainer = styled('main', { shouldForwardProp: (prop) => prop !== 'open' })(
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


export const MainPage = ({ theme }) => {
    const auth = useAuth();

    const [drawerOpen, setDrawerOpen] = useState(false);

    // useEffect(() => {
    //     auth.permissions()
    // }, [])

    const handleDrawerOpen = () => setDrawerOpen(true)
    const handleDrawerClose = () => setDrawerOpen(false)

    return <Box display="flex" height="98vh">
        <CssBaseline/>
        <AppBar position="fixed" open={drawerOpen}>
            <CustomToolbar handleDrawerOpen={handleDrawerOpen} drawerOpen={drawerOpen}/>
        </AppBar>
        <Drawer
            drawerWidth={drawerWidth}
            open={drawerOpen}
            handleDrawerClose={handleDrawerClose}
            theme={theme}
            permisos={auth.authorizations}
        />
        <MainContainer open={drawerOpen}>
            <DrawerHeader/>
            <Routes permisos={auth.authorizations}/>
        </MainContainer>
    </Box>
}