import { Autocomplete, Box, Grid, TextField } from "@mui/material";
import { useState } from "react";

const Select = ({id, label, initialValue, required = false, options = {}}) => {
    const [value, setValue] = useState(initialValue);
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
                    required={required}
                    {...params}
                    label={label}/>
            )}
            value={value}
            onChange={(e, value) => {
                setValue(value)
            }}
            isOptionEqualToValue={(option, value) =>
                value.value ? option.value === value.value : option.value === value
            }
        />
    </Grid>
}

export default Select