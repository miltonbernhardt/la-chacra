import { produccion as data } from '../../data/data';
import GridProduccion from "./GridProduccion";
import FormProduccion from "./FormProduccion";
import PageFormTable from "../../components/PageFormTable";

const VerProduccion = () => {

    return (
        <>
            <PageFormTable
                titleForm={"Producción"}
                form={
                    <FormProduccion />
                }
                sizeForm={3}
                table={
                    <GridProduccion data={data} />
                }
            />
        </>
    );
}

export default VerProduccion;