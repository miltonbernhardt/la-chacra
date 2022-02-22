import { DataGrid } from '@mui/x-data-grid';

const columns = [
    {
        field: 'identificadorLote',
        headerName: 'Lote',
        flex: 1,
        minWidth: 50
    },
    {
        field: 'fechaElaboracion',
        headerName: 'Fecha de elaboracion',
        type: 'date',
        flex: 1,
        minWidth: 50
    },
    {
        field: 'tipoDeQueso',
        headerName: 'Queso',
        flex: 0.5,
        minWidth: 50
    },
    {
        field: 'tina',
        headerName: 'Tina',
        flex: 0.5,
        minWidth: 50
    },
    {
        field: 'litrosProcesados',
        headerName: 'Litros Procesados',
        type: 'number',
        flex: 0.5,
        minWidth: 50
    },
    {
        field: 'cantidadDeHormas',
        headerName: 'Hormas',
        type: 'number',
        flex: 0.5,
        minWidth: 50
    },
    {
        field: 'saldoLote',
        headerName: 'Saldo',
        type: 'number',
        flex: 0.5,
        minWidth: 50,
        renderCell: (cellValues) => {
            return (
                <div
                    style={{
                        color: "blue",
                        // fontSize: 18,
                        width: "100%",
                        textAlign: "right"
                    }}
                >
                    {cellValues.value}
                </div>
            );
        }
    },
    {
        field: 'pesoLote',
        headerName: 'Peso',
        type: 'number',
        flex: 0.5,
        minWidth: 50
    },
    {
        field: 'rendimientoLote',
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

const GridProduccion = ({data}) => {
    return (
        <>
            <DataGrid
                style={{minHeight: "600px"}}
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