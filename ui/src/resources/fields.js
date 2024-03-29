import toast from 'react-hot-toast';
import * as React from 'react';

export const backID = "id"
export const backFechaElaboracion = "fechaElaboracion"
export const backCantHormas = "cantHormas"
export const backCantCajas = "cantCajas"
export const backLitrosLeche = "litrosLeche"
export const backNumeroTina = "numeroTina"
export const backPeso = "peso"
export const backCodigoQueso = "codigoQueso"
export const backIdQueso = "idQueso"
export const backCodigo = "codigo"
export const backNomenclatura = "nomenclatura"
export const backTipoQueso = "tipoQueso"
export const backLoteCultivo = "loteCultivo"
export const backLoteColorante = "loteColorante"
export const backLoteCalcio = "loteCalcio"
export const backLoteCuajo = "loteCuajo"
export const backCuit = "cuit"
export const backRazonSocial = "razonSocial"
export const backDomicilio = "domicilio"
export const backCodPostal = "codPostal"
export const backLocalidad = "localidad"
export const backProvincia = "provincia"
export const backPais = "pais"
export const backTransporte = "transporte"
export const backSenasaUta = "senasaUta"
export const backEmail = "email"
export const backTelefono = "telefono"
export const backFax = "fax"
export const backCelular = "celular"
export const backIdTipoCliente = "idTipoCliente"
export const backIdCliente = "idCliente"
export const backIdLote = "idLote"
export const backPesoExpedicion = "peso"
export const backCantidad = "cantidad"
export const backImporte = "importe"
export const backFechaExpedicion = "fechaExpedicion"
export const backPrecio = "valor"
export const backColor = "color"
export const backFechaDesde = "fromDate"
export const backFechaHasta = "toDate"
export const backStockLote = "stockLote"
export const backRendimientoLote = "rendimiento"
export const backFechaRemito = "fecha"
export const backImporteTotal = "importeTotal"
export const backStockEmbalaje = "stock"
export const backTipoEmbalaje = "tipoEmbalaje"
export const backUsername = "username"
export const backPassword = "password"
export const backCantPallets = "cantPallets"
export const backPesoNoConfiable = "pesoNoConfiable"
export const backOnRemito = "onRemito"
export const ID = "ID"

// produccion
export const fechaElaboracion = "Fecha de producción"
export const cantHormas = "Cantidad de hormas"
export const cantCajas = "Cantidad de cajas"
export const litrosLeche = "Litros procesados"
export const numeroTina = "Tina"
export const peso = "Peso del lote"
export const queso = "Tipo de queso"
export const loteCultivo = "Lote cultivo"
export const loteColorante = "Lote colorante"
export const loteCalcio = "Lote calcio"
export const loteCuajo = "Lote cuajo"

// producto
export const codigo = "Código"
export const nomenclatura = "Nomenclatura"
export const tipoQueso = "Tipo de queso"
export const color = "Color"

// cliente
export const cuit = "CUIT"
export const razonSocial = "Razon Social"
export const domicilio = "Domicilio"
export const codPostal = "Código Postal"
export const localidad = "Localidad"
export const provincia = "Provincia"
export const pais = "Pais"
export const transporte = "Transporte"
export const senasaUta = "Senasa/UTA"
export const email = "E-mail"
export const telefono = "Teléfono"
export const fax = "Fax"
export const celular = "Celular"
export const idTipoCliente = "Tipo de cliente"

// precio
export const precio = "Precio"

// expedicion
export const cliente = "Cliente"
export const numeroLote = "Número de lote"
export const cantidad = "Cantidad"
export const pesoExpedicion = "Peso"
export const importe = "Importe"
export const fechaExpedicion = "Fecha de expedición"
export const onRemito = "Remito"

// consulta produccion
export const fechaDesde = "Desde"
export const fechaHasta = "Hasta"

// remito
export const fechaRemito = "Fecha de remito"
export const importeTotal = "Importe total"
export const cantPallets = "Cantidad de pallets"

// busqueda por semanas
export const cantidadSemanas = "Cantidad de semanas"
export const backCantidadSemanas = "cantidadSemanas"

// embalaje
export const tipoEmbalaje = "Tipo de embalaje"
export const stockEmbalaje = "Stock"

//login
export const username = "Nombre de usuario"
export const password = "Contraseña"

const backendFields = () => {
    const fields = new Map()
    fields.set(backID, ID)
    fields.set(backFechaElaboracion, fechaElaboracion)
    fields.set(backCantHormas, cantHormas)
    fields.set(backCantCajas, cantCajas)
    fields.set(backLitrosLeche, litrosLeche)
    fields.set(backNumeroTina, numeroTina)
    fields.set(backPeso, peso)
    fields.set(backCodigoQueso, queso)
    fields.set(backIdQueso, queso)
    fields.set(backCodigo, codigo)
    fields.set(backNomenclatura, nomenclatura)
    fields.set(backTipoQueso, tipoQueso)
    fields.set(backLoteCultivo, loteCultivo)
    fields.set(backLoteColorante, loteColorante)
    fields.set(backLoteCalcio, loteCalcio)
    fields.set(backLoteCuajo, loteCuajo)
    fields.set(backCuit, cuit)
    fields.set(backRazonSocial, razonSocial)
    fields.set(backDomicilio, domicilio)
    fields.set(backCodPostal, codPostal)
    fields.set(backLocalidad, localidad)
    fields.set(backProvincia, provincia)
    fields.set(backPais, pais)
    fields.set(backTransporte, transporte)
    fields.set(backSenasaUta, senasaUta)
    fields.set(backEmail, email)
    fields.set(backTelefono, telefono)
    fields.set(backFax, fax)
    fields.set(backCelular, celular)
    fields.set(backIdTipoCliente, idTipoCliente)
    fields.set(backPrecio, precio)
    fields.set(backIdCliente, cliente)
    fields.set(backIdLote, numeroLote)
    fields.set(backPesoExpedicion, pesoExpedicion)
    fields.set(backCantidad, cantidad)
    fields.set(backImporte, importe)
    fields.set(backFechaExpedicion, fechaExpedicion)
    fields.set(backColor, color)
    fields.set(backFechaDesde, fechaDesde)
    fields.set(backFechaHasta, fechaHasta)
    fields.set(backFechaRemito, fechaRemito)
    fields.set(backImporteTotal, importeTotal)
    fields.set(backCantidadSemanas, cantidadSemanas)
    fields.set(backStockEmbalaje, stockEmbalaje)
    fields.set(backTipoEmbalaje, tipoEmbalaje)
    fields.set(backUsername, username)
    fields.set(backPassword, password)
    fields.set(backOnRemito, onRemito)
    return fields
}

export const toastValidationErrors = (errors) => {
    if (errors == null && !errors instanceof Map)
        return
    toast.dismiss()
    console.log({ errors })
    let mapFields = backendFields()
    errors.forEach(function (msg, field) {
        console.log({ msg })
        console.log({ field })
        let realField = mapFields.get(field)
        console.log({ realField })
        realField = realField ?? field
        toast.error(<>
            <div style={{ width: "100%" }}><b>{realField}</b>: {msg}</div>
        </>)
    })
}


