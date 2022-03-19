import {Button, Grid, Typography} from "@mui/material";
import {createRef, useCallback} from "react";
import Input from "../../components/Input";
import * as field from "../../resources/fields";
import {toastValidationErrors} from "../../resources/fields";
import * as message from "../../resources/messages";
import * as validation from "../../resources/validations";

const FormTrazabilidad = ({lote, onBuscar}) => {

    const refIdLote = createRef(null)

    const handleBuscar = useCallback(() => {
        const errors = new Map();
        const values = {};

        refIdLote.current.validate(errors, values, [
            {func: validation.empty, msg: message.valEmptyField}
        ])

        if (errors.size > 0) {
            console.error(errors)
            toastValidationErrors(errors)
            return
        }

        onBuscar(values.id)
    }, [onBuscar, refIdLote])

    return (
        <Grid container spacing={1.5}>
            <Grid item xs={12}>
                <Typography variant="h7" color="GrayText">
                    Buscar Lote
                </Typography>
            </Grid>
            <Grid container
                  style={{
                      justifyContent: "center",
                      alignItems: "center",
                      padding: 10
                  }}
                  spacing={2}>

                <Grid item xs={9}>
                    <Input ref={refIdLote}
                           id={field.backID}
                           label={"Número de lote"}
                           type="text"
                           required/>
                </Grid>
                <Grid item xs={3} alignSelf="flex-center">
                    <Button
                        fullWidth
                        variant="contained"
                        onClick={handleBuscar}
                        color="primary">Buscar</Button>
                </Grid>
            </Grid>
            <Grid item xs={12}>
                <Typography variant="h7" color="GrayText">
                    Información del Lote
                </Typography>
            </Grid>
            <Input
                id={field.backFechaElaboracion}
                label={field.fechaElaboracion}
                value={lote.fechaElaboracion}
                type="date"
                sm={6}/>
            <Input
                id={field.backCodigoQueso}
                label={field.queso}
                value={lote.codigoQueso}
                sm={6}/>
            <Input
                id={field.backNumeroTina}
                label={field.numeroTina}
                value={lote.numeroTina}
                sm={6}/>
            <Input
                id={field.backLitrosLeche}
                label={field.litrosLeche}
                value={lote.litrosLeche}
                sm={6}/>
            <Input
                id={field.backCantHormas}
                label={field.cantHormas}
                value={lote.cantHormas}
                sm={6}/>
            {/* <Input
                id={field.backCantCajas}
                label={field.cantCajas}
                value={lote.cantCajas}
                sm={6} /> */}
            <Input
                id={field.backPeso}
                label={field.peso}
                value={lote.peso}
                sm={6}/>
            <Input
                id={field.backLoteCultivo}
                label={field.loteCultivo}
                value={lote.loteCultivo}
                type="text"
                sm={6}/>
            <Input
                id={field.backLoteColorante}
                label={field.loteColorante}
                value={lote.loteColorante}
                type="text"
                sm={6}/>
            <Input
                id={field.backLoteCalcio}
                label={field.loteCalcio}
                value={lote.loteCalcio}
                type="text"
                sm={6}/>
            <Input
                id={field.backLoteCuajo}
                label={field.loteCuajo}
                value={lote.loteCuajo}
                type="text"
                sm={6}/>
        </Grid>
    )
}

export default FormTrazabilidad