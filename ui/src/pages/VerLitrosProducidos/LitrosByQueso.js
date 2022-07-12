import { Grid, Paper } from "@mui/material";
import { useCallback, useMemo, useState } from "react";
import { ChartLitros } from "./ChartLitros";
import { SelectLitrosByQueso } from "./SelectLitrosByQueso";
import * as React from 'react';

export const LitrosByQueso = ({ listaLitros, quesosSelect }) => {

    const dataKeysInit = useMemo(() => {
        return {
            dataKey: quesosSelect.at(0),
            dataKey1: "",
            dataKey2: ""
        }
    }, [quesosSelect])

    const [dataKeys, setDataKeys] = useState(dataKeysInit);

    const handleChange = useCallback((field, value) => {
        const val = value ? value : ''
        const newKeys = { ...dataKeys, [field]: val };
        setDataKeys(newKeys)
    }, [dataKeys])

    return (
        <Grid item container spacing={2}>
            <ChartLitros
                title="Litros por queso"
                data={listaLitros}
                xDataKey="fecha"
                dataKey={dataKeys.dataKey ? dataKeys.dataKey.value : ''}
                dataKey1={dataKeys.dataKey1 ? dataKeys.dataKey1.value : ''}
                dataKey2={dataKeys.dataKey2 ? dataKeys.dataKey2.value : ''}
                dot={true}/>
            <Grid item xs={12} md={4}>
                <Paper
                    sx={{
                        p: 2,
                        display: 'flex',
                        flexDirection: 'column',
                        height: '100%',
                        width: '100%',
                        minHeight: 300
                    }}>
                    <SelectLitrosByQueso
                        data={dataKeys}
                        quesos={quesosSelect}
                        onChange={handleChange}/>
                </Paper>
            </Grid>
        </Grid>
    );
}