import { Grid } from "@mui/material";
import { ventas } from "../../data/data";
import ChartVentas from "./ChartVentas";
import GridVentas from "./GridVentas";
import SearchVentas from "./SearchVentas";

const VerVentas = () => {

    const [listaVentas, setListaVentas] = useState([]);

    return (
        <Grid container
            direction="row"
            spacing={2}
            paddingRight={2}
            style={{
                minHeight: "92%",
                maxWidth: "98%",
                margin: "1%",
                boxSizing: "border-box",
            }}>
            <Grid item container spacing={2}>
                <SearchVentas />
                <ChartVentas
                    title="Ventas"
                    data={ventas}
                    xDataKey="semana"
                    dataKey="C" />.
            </Grid>
            <GridVentas />
        </Grid>
    );
}

export default VerVentas;