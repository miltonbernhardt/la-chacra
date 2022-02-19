import { precios } from "../../data/data";
import GridPrecios from "./GridPrecios";
import FormPrecios from "./FormPrecios";
import PageFormTable from "../../components/PageFormTable";

const CargarPrecios = () => {
    return (
        <>
            <PageFormTable
                form={
                    <FormPrecios/>
                }
                table={
                    <GridPrecios precios={precios}/>
                }
                titleTable="Precios"
            />
        </>
    );
}

export default CargarPrecios;