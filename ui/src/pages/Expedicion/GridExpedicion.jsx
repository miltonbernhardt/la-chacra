import { DataGrid } from '@mui/x-data-grid';
import { produccion } from "../../data/data";

const GridExpedicion = () => {
    return (
        <>
            <DataGrid
                style={{height: "600px"}}
                autoHeight={true}
                rows={produccion}
                rowHeight={42}
                pageSize={15}
                rowsPerPageOptions={[15]}
                columns={[]}
                // onCellClick={(params) => setSelection(params.id)}
            />
        </>
    )
}

export default GridExpedicion