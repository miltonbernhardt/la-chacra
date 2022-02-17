import toast from 'react-hot-toast';

export const fechaElaboracion = "Fecha de producción"
export const cantidadHormas = "Cantidad de hormas"
export const litrosLeche = "Litros procesados"
export const numeroTina = "Número de tina"
export const peso = "Peso del lote"
export const queso = "Tipo de queso"

const backendFields = () => {
    const fields = new Map()
    fields.set("fechaElaboracion", fechaElaboracion)
    fields.set("cantHormas", cantidadHormas)
    fields.set("litrosLeche", litrosLeche)
    fields.set("numeroTina", numeroTina)
    fields.set("peso", peso)
    fields.set("codigoQueso", queso)
    fields.set("id", "ID")
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
        toast.error(<><b>{realField}</b>: {msg}</>)
    })
}


