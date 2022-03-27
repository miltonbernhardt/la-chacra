import { Chip, Typography } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import { useMemo } from "react";
import * as field from "../../resources/fields";

const GridProduccion = ({ data, setSelection }) => {

    const columns = useMemo(() => {
        return [
            {
                field: field.backID,
                headerName: 'Lote',
                flex: 0.75,
                minWidth: 50
            },
            {
                field: field.backFechaElaboracion,
                headerName: 'Fecha',
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
                minWidth: 50
            },
            {
                field: field.backCodigoQueso,
                headerName: 'Queso',
                flex: 0.75,
                minWidth: 50,
                renderCell: ({ value }) => {
                    return <Chip
                        label={value.tipoQueso}
                        style={{ backgroundColor: value.color }} />
                }
            },
            {
                field: field.backNumeroTina,
                headerName: 'Tina',
                flex: 0.25,
                minWidth: 50
            },
            {
                field: field.backLitrosLeche,
                headerName: 'Litros',
                type: 'number',
                flex: 0.5,
                minWidth: 50
            },
            {
                field: field.backCantHormas,
                headerName: 'Hormas',
                type: 'number',
                flex: 0.5,
                minWidth: 50
            },
            {
                field: field.backStockLote,
                headerName: 'Saldo',
                type: 'number',
                flex: 0.5,
                minWidth: 50,
                renderCell: ({ value }) => {
                    return (
                        <Chip
                            label={value.stockLote}
                            color={value.color} />
                    );
                },
                sortComparator: (v1, v2) => { return v1.stockLote - v2.stockLote },

            },
            {
                field: field.backPeso,
                headerName: 'Peso',
                type: 'number',
                flex: 0.5,
                minWidth: 50
            },
            {
                field: field.backRendimientoLote,
                headerName: 'Rendimiento',
                type: 'number',
                flex: 0.5,
                minWidth: 50
            },
        ]
    }, []);

    return (
        < >
            <DataGrid
                style={{ minHeight: "600px" }}
                autoHeight={true}
                rows={data}
                columns={columns}
                rowHeight={42}
                pageSize={15}
                rowsPerPageOptions={[15]}
                onCellDoubleClick={(params) => setSelection(params.id)} />
            <Typography variant="h7" color="GrayText">
                Doble click sobre una fila de la tabla para editar el lote de producción
            </Typography>
        </ >
    )
}

export default GridProduccion