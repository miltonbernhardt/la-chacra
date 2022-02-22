import toast from 'react-hot-toast';

export const fechaElaboracion = "Fecha de producción"
export const cantidadHormas = "Cantidad de hormas"
export const litrosLeche = "Litros procesados"
export const numeroTina = "Número de tina"
export const peso = "Peso del lote"
export const queso = "Tipo de queso"
export const codigo = "Código"
export const nomenclatura = "Nomenclatura"
export const tipoQueso = "Tipo de queso"
export const cuit = "CUIT"
export const razonSocial = "Razón Social"

const backendFields = () => {
    const fields = new Map()
    fields.set("id", "ID")
    fields.set("fechaElaboracion", fechaElaboracion)
    fields.set("cantHormas", cantidadHormas)
    fields.set("litrosLeche", litrosLeche)
    fields.set("numeroTina", numeroTina)
    fields.set("peso", peso)
    fields.set("codigoQueso", queso)
    fields.set("codigo", codigo)
    fields.set("nomenclatura", nomenclatura)
    fields.set("tipoQueso", tipoQueso)
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
        toast.error(<><div style={{ width: "100%" }}><b>{realField}</b>: {msg}</div></>)
    })
}


