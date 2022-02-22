import { Grid, TextField } from "@mui/material";
import { useState, forwardRef, useImperativeHandle } from "react";

const Input = forwardRef(({id, label, value, type = "number", required = false, sm}, ref) => {
    const [val, setVal] = useState(value);
    const [err, serErr] = useState(false);

    const setValidField = (value) => serErr(!value);

    useImperativeHandle(ref, () => ({
        value: () => val,
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
                    return false
                }
            });
            setValidField(isValid)
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
            onChange={e => setVal(e.target.value)}
            required={required}
            ref={ref}
        />
    </Grid>;
})

export default Input