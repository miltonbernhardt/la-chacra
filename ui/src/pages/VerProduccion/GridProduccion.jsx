import { Chip } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import * as field from "../../resources/fields";
const columns = [
    {
        field: field.backID,
        headerName: 'Lote',
        flex: 1,
        minWidth: 50
    },
    {
        field: field.backFechaElaboracion,
        headerName: 'Fecha de elaboracion',
        type: 'date',
        flex: 1,
        minWidth: 50
    },
    {
        field: field.backCodigoQueso,
        headerName: 'Queso',
        flex: 0.5,
        minWidth: 50
    },
    {
        field: field.backNumeroTina,
        headerName: 'Tina',
        flex: 0.5,
        minWidth: 50
    },
    {
        field: field.backLitrosLeche,
        headerName: 'Litros Procesados',
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
    // {
    //     field: 'fullName',
    //     headerName: 'Full name',
    //     description: 'This column has a value getter and is not sortable.',
    //     sortable: false,
    //     flex: 1,
    //     valueGetter: (params) =>
    //         `${params.getValue(params.id, 'firstName') || ''} ${params.getValue(params.id, 'lastName') || ''
    //         }`,
    // },
];

const GridProduccion = ({ data }) => {
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