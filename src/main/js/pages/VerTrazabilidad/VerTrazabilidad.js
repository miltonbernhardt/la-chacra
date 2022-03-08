import GridTrazabilidad from "./GridTrazabilidad";
import FormTrazabilidad from "./FormTrazabilidad";
import PageFormTable from "../../components/PageFormTable";
import { produccion as data } from "../../data/data";


const VerTrazabilidad = () => {
    return (<>
            <>
                <PageFormTable
                    form={
                        <FormTrazabilidad/>
                    }
                    sizeForm={5}
                    table={
                        <GridTrazabilidad data={data}/>
                    }
                />
            </>
        </>
    );
}

export default VerTrazabilidad;