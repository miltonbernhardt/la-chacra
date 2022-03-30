import { Grid, Paper, Typography } from "@mui/material";
import { useRef, useState, useCallback, useMemo } from "react";
import Select from '../../components/Select';
import * as field from '../../resources/fields';
import ChartVentas from "./ChartVentas";
import SelectVentasByQueso from "./SelectVentasByQueso";

const VentasByQueso = ({ listaVentas, quesosSelect }) => {

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
            <ChartVentas
                title="Ventas por queso"
                data={listaVentas}
                xDataKey="fecha"
                dataKey={dataKeys.dataKey.value}
                dataKey1={dataKeys.dataKey1.value}
                dataKey2={dataKeys.dataKey2.value} />
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
                    <SelectVentasByQueso
                        data={dataKeys}
                        quesos={quesosSelect}
                        onChange={handleChange} />
                </Paper>
            </Grid>
        </Grid>
    );
}

export default VentasByQueso;