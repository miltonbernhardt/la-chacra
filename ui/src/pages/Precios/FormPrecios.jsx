import { Button, ButtonGroup, Grid, Typography } from "@mui/material";
import { useCallback, useEffect, useRef, useState } from "react";
import Input from '../../components/Input';
import Select from '../../components/Select';
import * as field from '../../resources/fields';
import * as message from "../../resources/messages";
import * as validation from "../../resources/validations";

const precioInicial = {
    id: '',
    precio: '',
    idTipoCliente: '',
    idQueso: ''
}

const FormPrecios = ({ precio, quesos, clientes, handleSubmit }) => {

    const [precioForm, setPrecioForm] = useState(precioInicial);

    const refPrecio = useRef(null);
    const refSelectCliente = useRef(null);
    const refSelectQueso = useRef(null);

    useEffect(() => { setPrecioForm(precio) }, [precio])

    const handleCargar = useCallback(() => {
        const errors = new Map();
        const values = {};
        values["id"] = precioForm.id

        refSelectQueso.current.validate(errors, values, [
            { func: validation.emptySelect, msg: message.valEmptyField }
        ])

        refSelectCliente.current.validate(errors, values, [
            { func: validation.emptySelect, msg: message.valEmptyField }
        ])

        refPrecio.current.validate(errors, values, [
            { func: validation.minorToOne, msg: message.valZeroValue }])

        if (errors.size > 0) {
            console.error(errors)
            field.toastValidationErrors(errors)
            return
        }
        handleSubmit(values)
    }, [precioForm.id]);

    return (
        <>
            <Grid container spacing={2}>
                <Grid item xs={12}>
                    <Typography variant="h6">
                        Ingreso de precios
                    </Typography>
                </Grid>
                <Select ref={refSelectQueso}
                    value={precioForm.idQueso}
                    id={field.backIdQueso}
                    label={field.queso}
                    options={quesos}
                    required />
                <Select ref={refSelectCliente}
                    value={precioForm.idTipoCliente}
                    id={field.backIdTipoCliente}
                    label={field.idTipoCliente}
                    options={clientes}
                    required />
                <Input ref={refPrecio}
                    id={field.backPrecio}
                    label={field.precio}
                    value={precioForm.precio}
                    required />
                <Grid item xs={12} alignSelf="right" mb={0.5}>
                    <ButtonGroup fullWidth variant="contained">
                        <Button onClick={handleCargar} color="primary">Cargar Precio</Button>
                    </ButtonGroup>
                </Grid>
            </Grid>
        </>
    )
}

export default FormPrecios