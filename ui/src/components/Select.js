import { Autocomplete, Box, Grid, TextField } from "@mui/material";
import * as React from 'react';
import { forwardRef, useEffect, useImperativeHandle, useState } from 'react';

export const Select = forwardRef((
    {
        id,
        label,
        value,
        required = false,
        options = { id: null, label: null, value: null },
        disabled = false,
    }, ref) => {

    const [val, setVal] = useState(value);
    const [err, serErr] = useState(false);

    const setSelectError = () => serErr(true);
    const setValAndUpdate = (value) => {
        setVal(value)
        serErr(false)
    }

    useEffect(() => {
        if (value && value !== {})
            setValAndUpdate(options.filter((o) => o.value === value).pop())
        else
            setValAndUpdate({ id: null, label: null, value: null });
    }, [options, value]);

    useImperativeHandle(ref, () => ({
        value: () => val.value,
        /* parameters;
            - map de errores: se debe inicializar previamente
            - object: valores a hacer submit
            - array de funciones de validación y los mensajes asociados a la incorrecta validación
         */
        validate: (errors, body, functionsMsg) => {
            let isValid = true
            functionsMsg.some(({ func, msg }) => {
                if (func(val)) {
                    errors.set(id, msg)
                    isValid = false
                    setSelectError()
                    return false
                }
            });
            if (isValid && body != null && body instanceof Object)
                body[id] = val.value
        }
    }));

    return <Grid item xs={12}>
        <Autocomplete
            id={id}
            name={id}
            options={options}
            autoHighlight
            getOptionLabel={(option) => option.label || ''}
            renderOption={(props, option) => {
                return <Box component="li"  {...props}>
                    {option.label}
                </Box>
            }}
            renderInput={(params) => (
                <TextField
                    error={err}
                    required={required}
                    {...params}
                    label={label} />
            )}
            value={val}
            error={err}
            onChange={(e, value) => {
                setValAndUpdate(value)
            }}
            isOptionEqualToValue={(option, value) => {
                return value.value ? option.value === value.value : option.value === value
            }}
            disabled={disabled} />
    </Grid>
})