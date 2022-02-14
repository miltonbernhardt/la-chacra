import { AppBar, Box, CssBaseline, Typography } from '@mui/material';
import PropTypes from 'prop-types';
import * as React from 'react';
import Clientes from './Clientes/Clientes'
import CargarPrecios from './Precios/CargarPrecios'
import EmitirRemito from './Remito/EmitirRemito'
import VerVentas from './Ventas/VerVentas'
import CargarProduccion from './CargarProduccion/CargarProduccion'
import CargarProductos from './CargarProductos/CargarProductos'
import VerLitrosElaborados from './VerLitrosProducidos/VerLitrosElaborados'
import VerProduccion from './VerProduccion/VerProduccion'
import CargarExpedicion from './CargarExpedicion/CargarExpedicion'
import { ThemeProvider, useTheme, createTheme } from '@mui/material/styles';

function TabPanel(props) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`full-width-tabpanel-${index}`}
      aria-labelledby={`full-width-tab-${index}`}
      {...other}
    >
      {value === index && (
        <Box sx={{ p: 3 }}>
          <Typography>{children}</Typography>
        </Box>
      )}
    </div>
  );
}

TabPanel.propTypes = {
  children: PropTypes.node,
  index: PropTypes.number.isRequired,
  value: PropTypes.number.isRequired,
};

const App = () => {

  const darkTheme = createTheme({
    palette: {
      mode: 'dark',
    },
  });

  return (
    <>
      {/* <ThemeProvider theme={darkTheme}> */}

      <CssBaseline />
      <AppBar position="static">
        <Typography textAlign="left" variant="h6" paddingLeft="10" >La Chacra</Typography>
      </AppBar>
      <CargarProduccion />
      <CargarExpedicion />
      <CargarProductos />
      <Clientes />
      <CargarPrecios />
      <EmitirRemito />
      <VerVentas />
      <VerLitrosElaborados />
      <VerProduccion />
      {/* </ThemeProvider> */}
    </>
  );
}

export default App;