import { Box, Container, Grid, Typography } from "@material-ui/core";
import { Button, ButtonGroup, TextField } from "@mui/material";
import { useState, useCallback, useEffect, useMemo } from "react";
import StockEmbalajeCard from "./StockEmbalajeCard";
import { getAllEmbalajes } from "../../services/RestServices";
import toast from 'react-hot-toast';
import Loading from '../../components/Loading'
import EmbalajeDialog from "./EmbalajeDialog";

const embalajeInicial = {
    id: '',
    tipoEmbalaje: 'CAJA',
    stock: 0,
    listaQuesos: []
}

const StockEmbalaje = () => {

    const [listaEmbalajes, setListaEmbalajes] = useState([]);

    const [isLoading, setLoading] = useState(true);
    const [isDialogOpen, setDialogOpen] = useState(false);

    const fetchEmbalajes = useCallback(() => {
        getAllEmbalajes()
            .then(({ data }) => setListaEmbalajes(data))
            .catch(() => toast.error("No se pudo cargar embalajes"))
            .finally(() => setLoading(false))
    }, [])

    useEffect(() => fetchEmbalajes(), [fetchEmbalajes]);

    const listaEmbalajesFormatted = useMemo(() => {
        if (isLoading) return [];
        return listaEmbalajes.map((embalaje) => {
            var productos = "";
            embalaje.listaQuesos.forEach(element => {
                productos = productos + element.tipoQueso + " "
            });
            return {
                ...embalaje,
                productos: productos
            }
        })
    }, [isLoading, listaEmbalajes])

    return (
        isLoading ? <Loading /> :
            <>
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
                                <Typography variant='h6' style={{ paddingLeft: 2 }}>Embalaje</Typography>
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <TextField fullWidth />
                            </Grid>
                            <Grid item xs={12} sm={4} mb={0.5}>
                                <ButtonGroup variant="contained" fullWidth>
                                    <Button variant="contained" color="info" fullWidth>Buscar</Button>
                                    <Button variant="contained" fullWidth>Nuevo Item</Button>
                                </ButtonGroup>
                            </Grid>
                        </Grid>
                        <Grid item xs={12} alignSelf={"center"}>

                            <Box sx={{ overflowY: 'auto', maxHeight: 800, padding: 2, }}>

                                <Container sx={{ py: 8 }} style={{
                                    backgroundColor: "#fafafa",
                                }} maxWidth="xl">
                                    {/* End hero unit */}
                                    <Grid container spacing={2}>
                                        {listaEmbalajesFormatted.map((card) => (
                                            <Grid item key={card.id} xs={12} sm={6} md={4} lg={3} xl={2}>
                                                <StockEmbalajeCard
                                                    item={card} />
                                            </Grid>
                                        ))}
                                    </Grid>
                                </Container>
                            </Box>
                        </Grid>
                    </Grid>
                </Container>
                <EmbalajeDialog
                    embalaje={embalajeInicial}
                    open={isDialogOpen} />
            </>
    );
}

export default StockEmbalaje;