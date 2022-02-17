export const fechaElaboracion = "Fecha de producción"
export const cantidadHormas = "Cantidad de hormas"
export const litrosLeche = "Litros procesados"
export const numeroTina = "Número de tina"
export const peso = "Peso del lote"
export const queso = "Tipo de queso"

export const fieldsFromBackend = () => {
    const fields = new Map()
    fields.set("fechaElaboracion", fechaElaboracion)
    fields.set("cantHormas", cantidadHormas)
    fields.set("litrosLeche", litrosLeche)
    fields.set("numeroTina", numeroTina)
    fields.set("peso", peso)
    fields.set("codigoQueso", queso)
    return fields
}