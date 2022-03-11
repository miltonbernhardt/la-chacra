import { DataGrid } from '@mui/x-data-grid';
import PageFormTable from '../../components/PageFormTable';
import { produccion } from "../../data/data";
import RemitoForm from "./RemitoForm";


const columns = [
    {
        field: "cantidad",
        headerName: "Cantidad",
        type: 'number',
        flex: 1,
        minWidth: 100
    }, {
        field: "producto",
        headerName: "Producto",
        type: 'text',
        flex: 1,
        minWidth: 100
    }, {
        field: "peso",
        headerName: "Peso",
        type: 'number',
        flex: 1,
        minWidth: 100
    }, {
        field: "precio",
        headerName: "Precio Unitario",
        type: 'number',
        flex: 1,
        minWidth: 100
    }, {
        field: "importe",
        headerName: "Importe",
        type: 'number',
        flex: 1,
        minWidth: 100
    },
]

const EmitirRemito = () => {
    return (
        <PageFormTable
            titleForm="Emitir Remito"
            form={<RemitoForm />}
            titleTable="Lista de expediciones"
            table={
                <DataGrid
                    rows={produccion}
                    columns={columns}
                    pageSize={20}
                    rowsPerPageOptions={[20]}
                    pagination={false}
                    hideFooterPagination
                />

            }>
        </PageFormTable>
    );
}

export default EmitirRemito;