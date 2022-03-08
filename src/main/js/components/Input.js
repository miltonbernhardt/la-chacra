import * as React from 'react';
import { Grid, TextField } from "@mui/material";

// todo: buscar crear la funcion de que  si un input es el ultimo input de un form, que envie el form
export const Input = React.forwardRef(({id, label, value, type = "number", required = false, sm}, ref) => {
    const [val, setVal] = React.useState(value);
    const [err, serErr] = React.useState(false);

    const setFieldError = () => serErr(true);
    const setValAndUpdate = (value) => {
        setVal(value)
        serErr(false)
    }

    React.useEffect(() => setValAndUpdate(value), [value]);

    React.useImperativeHandle(ref, () => ({
        value: () => val,
        setValue: (body) => {
            if (body != null && body instanceof Object)
                body[id] = val
        },
        /* parameters;
            - map de errores: se debe inicializar previamente
            - object: valores a hacer submit
            - array de funciones de validación y los mensajes asociados a la incorrecta validación
         */
        validate: (errors, body, functionsMsg) => {
            let isValid = true
            functionsMsg.some(({func, msg}) => {
                if (func(val)) {
                    errors.set(id, msg)
                    isValid = false
                    setFieldError()
                    return false
                }
            });
            if (isValid && body != null && body instanceof Object)
                body[id] = val
        }
    }));

    return <Grid item xs={12} sm={sm ? sm : null}>
        <TextField
            id={id}
            name={id}
            label={label}
            fullWidth
            type={type}
            variant="outlined"
            value={val}
            InputLabelProps={
                type === "date" ? {shrink: true} : {}
            }
            error={err}
            onChange={e => setValAndUpdate(e.target.value)}
            required={required}
            ref={ref}
        />
    </Grid>;
})