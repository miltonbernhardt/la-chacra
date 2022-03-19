import {Box, Container, Grid, Typography} from "@material-ui/core";
import {Button, ButtonGroup, TextField} from "@mui/material";
import StockEmbalajeCard from "./StockEmbalajeCard";

const StockEmbalaje = () => {
    const cards = [
        {id: 1, tipo: 'bolsa', productos: 'Sardo', stock: 10},
        {id: 4, tipo: 'bolsa', productos: 'Barra', stock: 10},
        {id: 6, tipo: 'caja', productos: 'Sardo, Barra', stock: 10},
        {id: 5, tipo: 'bolsa', productos: 'Azul', stock: 10},
        {id: 7, tipo: 'caja', productos: 'Azul', stock: 10},
        {id: 3, tipo: 'bolsa', productos: 'Muzzarella', stock: 10},
        {id: 8, tipo: 'caja', productos: 'Muzzarella', stock: 10},
        {id: 2, tipo: 'bolsa', productos: 'Cremoso', stock: 10},
    ];
    return (
        <Container maxWidth="xl">
            <Grid container
                  direction="row"
                  spacing={2}
                  paddingRight={2}
                  style={{
                      minHeight: "92%",
                      maxWidth: "98%",
                      backgroundColor: "white",
                      margin: "1%",
                      borderRadius: "8px",
                      boxShadow: 'rgba(0, 0, 0, 0.2) 0px 2px 1px -1px, rgba(0, 0, 0, 0.14) 0px 1px 1px 0px, rgba(0, 0, 0, 0.12) 0px 1px 3px 0px',
                      boxSizing: "border-box",
                  }}>

                <Grid container
                      style={{
                          justifyContent: "center",
                          alignItems: "center",
                          padding: 10
                      }}
                      spacing={2}>
                    <Grid item xs={12} sm={2}>
                        <Typography variant='h6' style={{paddingLeft: 2}}>Embalaje</Typography>
                    </Grid>
                    <Grid item xs={12} sm={6}>
                        <TextField fullWidth/>
                    </Grid>
                    <Grid item xs={12} sm={4} mb={0.5}>
                        <ButtonGroup variant="contained" fullWidth>
                            <Button variant="contained" color="info" fullWidth>Buscar</Button>
                            <Button variant="contained" fullWidth>Nuevo Item</Button>
                        </ButtonGroup>
                    </Grid>
                </Grid>
                <Grid item xs={12} alignSelf={"center"}>

                    <Box sx={{overflowY: 'auto', maxHeight: 800, padding: 2,}}>

                        <Container sx={{py: 8}} style={{
                            backgroundColor: "#fafafa",
                        }} maxWidth="xl">
                            {/* End hero unit */}
                            <Grid container spacing={2}>
                                {cards.map((card) => (
                                    <Grid item key={card.id} xs={12} sm={6} md={4} lg={3} xl={2}>
                                        <StockEmbalajeCard
                                            item={card}/>
                                    </Grid>
                                ))}
                            </Grid>
                        </Container>
                    </Box>
                </Grid>
            </Grid>
        </Container>
    );
}

export default StockEmbalaje;