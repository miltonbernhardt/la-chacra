import * as React from "react";
import { Route, Switch } from "react-router-dom";
import { CargarClientes } from "../pages/Clientes/Clientes";
import { CargarExpedicion } from "../pages/Expedicion/CargarExpedicion";
import { HomePage } from "../pages/HomePage";
import { CargarProduccion } from "../pages/Lotes/CargarLote";
import { Mantenimiento } from "../pages/Mantenimiento/Mantenimiento";
import { CargarPrecios } from "../pages/Precios/CargarPrecios";
import { CargarQuesos } from "../pages/Quesos/CargarQuesos";
import { EmitirRemito } from "../pages/Remito/EmitirRemito";
import { Rendimiento } from "../pages/Rendimiento/Rendimiento";
import { StockEmbalaje } from "../pages/StockEmbalaje/StockEmbalaje";
import { StockProductos } from "../pages/StockProductos/StockProductos";
import { VerVentas } from "../pages/Ventas/VerVentas";
import { VerExpediciones } from "../pages/VerExpediciones/VerExpediciones";
import { VerLitrosElaborados } from "../pages/VerLitrosProducidos/VerLitrosElaborados";
import { VerProduccion } from "../pages/VerProduccion/VerProduccion";
import { VerRemitos } from "../pages/VerRemitos/VerRemitos";
import { VerTrazabilidad } from "../pages/VerTrazabilidad/VerTrazabilidad";
import * as paths from "../resources/paths";

const ToNotFoundPage = () => {
    React.useEffect(() => {
        window.location.pathname = paths.notFound
    }, [])
    return <></>
}

const Ruta = ({ permisos, path, component }) => {
    return Array.isArray(permisos) && permisos.includes(path)
        ? <Route exact path={path} component={component} />
        : <></>
}

export const Routes = ({ permisos }) => {
    return <Switch>
        <Ruta permisos={permisos} path={paths.clientes} component={CargarClientes} />
        <Ruta permisos={permisos} path={paths.cargarLotes} component={CargarProduccion} />
        <Ruta permisos={permisos} path={paths.cargarExpedicion} component={CargarExpedicion} />
        <Ruta permisos={permisos} path={paths.cargarQuesos} component={CargarQuesos} />
        <Ruta permisos={permisos} path={paths.cargarPrecios} component={CargarPrecios} />
        <Ruta permisos={permisos} path={paths.verLitros} component={VerLitrosElaborados} />
        <Ruta permisos={permisos} path={paths.verProduccion} component={VerProduccion} />
        <Ruta permisos={permisos} path={paths.verVentas} component={VerVentas} />
        <Ruta permisos={permisos} path={paths.verRemitos} component={VerRemitos} />
        <Ruta permisos={permisos} path={paths.verTrazabilidad} component={VerTrazabilidad} />
        <Ruta permisos={permisos} path={paths.emitirRemito} component={EmitirRemito} />
        <Ruta permisos={permisos} path={paths.stockProductos} component={StockProductos} />
        <Ruta permisos={permisos} path={paths.stockEmbalaje} component={StockEmbalaje} />
        <Ruta permisos={permisos} path={paths.mantenimiento} component={Mantenimiento} />
        <Ruta permisos={permisos} path={paths.rendimiento} component={Rendimiento} />
        <Ruta permisos={permisos} path={paths.verExpediciones} component={VerExpediciones} />
        <Route exact path={paths.home} component={HomePage} />
        <Route path={paths.home} component={ToNotFoundPage} />
    </Switch>
}