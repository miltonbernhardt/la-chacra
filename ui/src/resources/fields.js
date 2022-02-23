import toast from 'react-hot-toast';

export const backID = "id"
export const backFechaElaboracion = "fechaElaboracion"
export const backCantHormas = "cantHormas"
export const backLitrosLeche = "litrosLeche"
export const backNumeroTina = "numeroTina"
export const backPeso = "peso"
export const backCodigoQueso = "codigoQueso"
export const backCodigo = "codigo"
export const backNomenclatura = "nomenclatura"
export const backTipoQueso = "tipoQueso"
export const backLoteCultivo = "loteCultivo"
export const backLoteColorante = "loteColorante"
export const backLoteCalcio = "loteCalcio"
export const backLoteCuajo = "loteCuajo"

export const ID = "ID"

// produccion
export const fechaElaboracion = "Fecha de producción"
export const cantHormas = "Cantidad de hormas"
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

// cliente
export const cuit = "CUIT"
export const razonSocial = "Razon Social"

const backendFields = () => {
    const fields = new Map()
    fields.set(backID, ID)
    fields.set(backFechaElaboracion, fechaElaboracion)
    fields.set(backCantHormas, cantHormas)
    fields.set(backLitrosLeche, litrosLeche)
    fields.set(backNumeroTina, numeroTina)
    fields.set(backPeso, peso)
    fields.set(backCodigoQueso, queso)
    fields.set(backCodigo, codigo)
    fields.set(backNomenclatura, nomenclatura)
    fields.set(backTipoQueso, tipoQueso)
    fields.set(backLoteCultivo, loteCultivo)
    fields.set(backLoteColorante, loteColorante)
    fields.set(backLoteCalcio, loteCalcio)
    fields.set(backLoteCuajo, loteCuajo)
    return fields
}

export const toastValidationErrors = (errors) => {
    if (errors == null && !errors instanceof Map)
        return
    toast.dismiss()
    let mapFields = backendFields()
    errors.forEach(function (msg, field) {
        let realField = mapFields.get(field)
        realField = realField ?? field
        toast.error(<>
            <div style={{ width: "100%" }}><b>{realField}</b>: {msg}</div>
        </>)
    })
}


