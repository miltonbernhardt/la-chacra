import { Typography, Grid } from "@mui/material";
import { DataGrid } from '@mui/x-data-grid';
import { useMemo } from "react";

const GridLotes = ({produccion, quesos, setSelection}) => {

    const columns = useMemo(() => {
        return [
            {
                field: 'fechaElaboracion',
                headerName: 'Fecha de produccion',
                type: 'date',
                valueFormatter: (params) => {
                    const valueFormatted =
                        params.value.at(8) + params.value.at(9) + '-' +
                        params.value.at(5) + params.value.at(6) + '-' +
                        params.value.at(0) + params.value.at(1) +
                        params.value.at(2) + params.value.at(3);
                    return `${valueFormatted}`;
                },
                flex: 0.75,
                minWidth: 50,
            },
            {
                field: 'codigoQueso',
                headerName: 'Queso',
                flex: 0.75,
                minWidth: 50,
                valueFormatter: (params) => {
                    return quesos
                        .filter(q => {
                            return q.codigo === params.value
                        })
                        .pop().tipoQueso
                }
            },
            {
                field: 'numeroTina',
                headerName: 'Tina',
                type: 'number',
                flex: 0.5,
                minWidth: 50,
            },
            {
                field: 'litrosLeche',
                headerName: 'Litros Procesados',
                type: 'number',
                flex: 0.5,
                minWidth: 50,
            },
            {
                field: 'cantHormas',
                headerName: 'Hormas',
                type: 'number',
                flex: 0.5,
                minWidth: 50,
            },
            {
                field: 'peso',
                headerName: 'Peso',
                type: 'number',
                flex: 0.5,
                minWidth: 50,
            },

        ]
    }, [quesos]);
    return (
        <>
            <Grid item container direction="column" xs={12} spacing={1}>
                <Grid item xs={12}>
                    <Typography variant="h6">
                        Producción ingresada
                    </Typography>
                </Grid>
                <Grid item xs={12} style={{maxHeight: "1100px"}}>
                    <DataGrid
                        style={{minHeight: "500px"}}
                        autoHeight={true}
                        rows={produccion}
                        columns={columns}
                        rowsPerPageOptions={[]}
                        onCellDoubleClick={(params) => setSelection(params.id)}
                    />
                </Grid>
            </Grid>
        </>
    );
}

export default GridLotes;