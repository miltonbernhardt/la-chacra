import { DataGrid } from '@mui/x-data-grid';

const GridTrazabilidad = () => {

    const columns = [{
        field: 'fechaExpedicion',
        headerName: 'Fecha de expedición',
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
        field: 'razonSocial',
        headerName: 'Cliente',
        flex: 0.75,
        minWidth: 50,
    },
    {
        field: 'cantidad',
        headerName: 'Cantidad',
        type: 'number',
        flex: 0.5,
        minWidth: 50,
    },]

    return (
        <>
            <DataGrid
                style={{ minHeight: "600px" }}
                autoHeight={true}
                rows={[]}
                columns={columns}
                rowHeight={42}
                pageSize={15}
                rowsPerPageOptions={[15]}
            // onCellClick={(params) => setSelection(params.id)}
            />
        </>
    )
}

export default GridTrazabilidad