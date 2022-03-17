import PageFormTable from '../../components/PageFormTable';
import GridRemito from './GridRemito';
import RemitoForm from "./RemitoForm";

const EmitirRemito = () => {
    return (
        <PageFormTable
            titleForm="Emitir Remito"
            titleTable="Detalle"
            form={<RemitoForm />}
            table={<GridRemito />}>
        </PageFormTable>
    );
}

export default EmitirRemito;