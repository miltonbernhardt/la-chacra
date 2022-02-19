import { DataGrid } from '@mui/x-data-grid';

const GridTrazabilidad = () => {
    return (
        <>
            <DataGrid
                style={{height: "600px"}}
                autoHeight={true}
                rows={[]}
                columns={[]}
                rowHeight={42}
                pageSize={15}
                rowsPerPageOptions={[15]}
                // onCellClick={(params) => setSelection(params.id)}
            />
        </>
    )
}

export default GridTrazabilidad