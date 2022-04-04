import { Autocomplete, Box, Grid, TextField, Typography } from "@mui/material";
import * as React from 'react';

export const SelectVentasByQueso = ({ data, quesos, onChange }) => {

    return (
        <Grid container spacing={2}>
            <Grid item>
                <Typography variant='h6'>Ventas por queso</Typography>
            </Grid>
            <Grid item xs={12}>
                <Autocomplete
                    id="dataKey"
                    name="dataKey"
                    options={quesos}
                    autoHighlight
                    getOptionLabel={(option) => option.label || ''}
                    renderOption={(props, option) => {
                        return <Box component="li"  {...props}>
                            {option.label}
                        </Box>
                    }}
                    renderInput={(params) => (
                        <TextField
                            error={false}
                            required={false}
                            {...params}
                            label="Queso" />
                    )}
                    value={data.dataKey}
                    onChange={(e, value) => {
                        onChange("dataKey", value);
                    }}
                    isOptionEqualToValue={(option, value) => {
                        return value.value ? option.value === value.value : option.value === value
                    }}
                />
            </Grid>
            <Grid item xs={12}>
                <Autocomplete
                    id="dataKey1"
                    name="dataKey1"
                    options={quesos}
                    autoHighlight
                    getOptionLabel={(option) => option.label || ''}
                    renderOption={(props, option) => {
                        return <Box component="li"  {...props}>
                            {option.label}
                        </Box>
                    }}
                    renderInput={(params) => (
                        <TextField
                            error={false}
                            required={false}
                            {...params}
                            label="Queso" />
                    )}
                    value={data.dataKey1}
                    onChange={(e, value) => {
                        onChange("dataKey1", value);
                    }}
                    isOptionEqualToValue={(option, value) => {
                        return value.value ? option.value === value.value : option.value === value
                    }}
                />
            </Grid>
            <Grid item xs={12}>
                <Autocomplete
                    id="dataKey2"
                    name="dataKey2"
                    options={quesos}
                    autoHighlight
                    getOptionLabel={(option) => option.label || ''}
                    renderOption={(props, option) => {
                        return <Box component="li"  {...props}>
                            {option.label}
                        </Box>
                    }}
                    renderInput={(params) => (
                        <TextField
                            error={false}
                            required={false}
                            {...params}
                            label="Queso" />
                    )}
                    value={data.dataKey2}
                    onChange={(e, value) => {
                        onChange("dataKey2", value);
                    }}
                    isOptionEqualToValue={(option, value) => {
                        return value.value ? option.value === value.value : option.value === value
                    }}
                />
            </Grid>
        </Grid >
    );
}