import { Box, Grid, Container, Typography } from "@material-ui/core";
import { Button, TextField } from "@mui/material";
import { maxWidth } from "@mui/system";
import { quesos } from "../../data/data";
import StockCard from "./StockCard";
import { useCallback, useEffect, useState } from "react";
import toast from 'react-hot-toast';
import { deleteQueso, getAllQuesos, postQueso, putQueso } from "../../services/RestServices";

const StockProductos = () => {

    const [listaQuesos, setListaQuesos] = useState([]);

    const [isLoading, setLoading] = useState(true);

    useEffect(() => fetchQuesos(), []);

    const fetchQuesos = () => {
        getAllQuesos()
            .then(({ data }) => {
                setListaQuesos(data)
                setLoading(false);
            })
            .catch(() => toast.error("No se pudo cargar productos"))
            .finally(() => setLoading(false));
    }

    return (
        <Container maxWidth="xl">
            <Grid container spacing={2} style={{
                maxWidth: "98%",
                backgroundColor: "white",
                margin: "1%",
                borderRadius: "8px",
                boxShadow: 'rgba(0, 0, 0, 0.2) 0px 2px 1px -1px, rgba(0, 0, 0, 0.14) 0px 1px 1px 0px, rgba(0, 0, 0, 0.12) 0px 1px 3px 0px',
                boxSizing: "border-box",
            }} >
                <Grid container
                    style={{
                        justifyContent: "center",
                        alignItems: "center",
                        padding: 10
                    }}
                    spacing={2}>
                    <Grid item xs={12} sm={2} >
                        <Typography variant='h6' style={{ paddingLeft: 2 }}>Stock Productos</Typography>
                    </Grid>
                    <Grid item xs={12} sm={6}>
                        <TextField fullWidth />
                    </Grid>
                    <Grid item xs={12} sm={4} alignSelf="center" mb={0.5}>
                        <Button variant="contained" fullWidth>Buscar</Button>
                    </Grid>
                </Grid>
                <Grid item xs={12} alignSelf={"center"}>

                    <Box sx={{ overflowY: 'auto', maxHeight: 800, padding: 2, }}>

                        <Container sx={{ py: 8 }} style={{
                            backgroundColor: "#fafafa",
                        }} maxWidth="xl">
                            {/* End hero unit */}
                            <Grid container spacing={2}>
                                {listaQuesos.map((queso) => (
                                    <Grid item key={queso.id} xs={12} sm={6} md={4} lg={3} xl={2}>
                                        <StockCard queso={queso} />
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

export default StockProductos;