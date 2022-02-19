import FormExpedicion from "./FormExpedicion";
import GridExpedicion from "./GridExpedicion";
import PageFormTable from "../../components/PageFormTable";

const CargarExpedicion = () => {
    return (
        <PageFormTable
            form={
                <FormExpedicion/>
            }
            table={
                <GridExpedicion/>
            }
            titleTable="Expediciones"
        />
    );
}

export default CargarExpedicion;