import { Box, Grid, Container } from "@material-ui/core";
import { Button, TextField } from "@mui/material";
import { maxWidth } from "@mui/system";
import { quesos } from "../../data/data";
import StockCard from "./StockCard";
const StockProductos = () => {
    const cards = quesos;
    return (
        <>
            <Grid container spacing={2} style={{
                maxWidth: "98%",
                backgroundColor: "white",
                margin: "1%",
                borderRadius: "8px",
                boxShadow: 'rgba(0, 0, 0, 0.2) 0px 2px 1px -1px, rgba(0, 0, 0, 0.14) 0px 1px 1px 0px, rgba(0, 0, 0, 0.12) 0px 1px 3px 0px',
                boxSizing: "border-box",
            }}>
                <Grid item xs={12} sm={8}>
                    <TextField fullWidth />
                </Grid>
                <Grid item xs={12} sm={4}>
                    <Button color='primary' fullWidth variant="contained">Buscar</Button>
                </Grid>
                <Grid item xs={12} alignSelf={"center"}>

                    <Box sx={{ overflowY: 'auto', maxHeight: 800, padding: 2, }}>

                        <Container sx={{ py: 8 }} maxWidth="lg">
                            {/* End hero unit */}
                            <Grid container spacing={2}>
                                {cards.map((card) => (
                                    <Grid item key={card.codigo} xs={12} sm={6} md={4} lg={3}>
                                        <StockCard tipoQueso={card.nombreQueso}//nombre de queso
                                            stockQueso={100}
                                            nomenclatura={card.nomenclatura} />
                                    </Grid>
                                ))}
                            </Grid>
                        </Container>
                    </Box>
                </Grid>
            </Grid>

        </>

    );
}

export default StockProductos;