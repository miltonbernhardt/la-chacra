import { DataGrid } from '@mui/x-data-grid';
import { useMemo } from "react";
import * as fields from '../../resources/fields'

const GridPrecios = ({ precios, tiposCliente, quesos, setSelection }) => {

    const columns = useMemo(() => {
        return [
            {
                field: fields.backIdQueso,
                headerName: 'Queso',
                type: 'text',
                flex: 1,
                minWidth: 50,
                valueFormatter: (params) => {
                    return quesos
                        .filter(q => {
                            return q.id === params.value
                        })
                        .pop().tipoQueso
                }
            },
            {
                field: fields.backIdTipoCliente,
                headerName: 'Cliente',
                type: 'text',
                flex: 1,
                minWidth: 50,
                valueFormatter: (params) => {
                    return tiposCliente
                        .filter(t => {
                            return t.id === params.value
                        })
                        .pop().tipo
                }
            },
            {
                field: 'precio',
                headerName: 'Precio',
                type: 'number',
                flex: 1,
                minWidth: 50,
                valueFormatter: (params) => {
                    return `$ ${params.value}`
                }

            }
        ]
    }, [quesos, tiposCliente]);

    return (
        <>
            <DataGrid
                // style={{minHeight: "600px"}}
                autoHeight={true}
                rows={precios}
                columns={columns}
                onCellDoubleClick={(params) => setSelection(params.id)}
                rowHeight={42}
                pageSize={15}
                rowsPerPageOptions={[15]}
            />
        </>
    );
}

export default GridPrecios;