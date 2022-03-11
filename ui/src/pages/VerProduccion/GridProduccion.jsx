import { Chip } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import * as field from "../../resources/fields";
import { useMemo } from "react";

const GridProduccion = ({ data, quesos }) => {

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
                    const queso = quesos
                        .filter(q => {
                            return q.codigo === value
                        })
                        .pop().tipoQueso;
                    return <Chip label={queso.tipoQueso}
                        color={queso.color} />
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
                    console.log(value)
                    return (
                        <Chip
                            label={value.stockLote}
                            color={value.color} />
                    );
                }
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
        <>
            <DataGrid
                style={{ minHeight: "600px" }}
                autoHeight={true}
                rows={data}
                columns={columns}
                rowHeight={42}
                pageSize={15}
                rowsPerPageOptions={[15]}
            // onCellClick={(params) => setSelection(params.id)}
            />
        </>
    )
}

export default GridProduccion