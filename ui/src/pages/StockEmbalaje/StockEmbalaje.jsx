import { Box, Container, Grid, Typography } from "@material-ui/core";
import { Button, ButtonGroup } from "@mui/material";
import { useCallback, useEffect, useMemo, useState } from "react";
import toast from 'react-hot-toast';
import Loading from '../../components/Loading';
import { deleteEmbalaje, getAllEmbalajes, getAllQuesos, postEmbalaje, putEmbalaje, updateStockEmbalaje } from "../../services/RestServices";
import DeleteDialog from "./DeleteDialog";
import EmbalajeDialog from "./EmbalajeDialog";
import StockEmbalajeCard from "./StockEmbalajeCard";

const embalajeInicial = {
    id: '',
    tipoEmbalaje: 'CAJA',
    stock: 0,
    listaQuesos: []
}

const StockEmbalaje = () => {

    const [embalaje, setEmbalaje] = useState(embalajeInicial)
    const [listaEmbalajes, setListaEmbalajes] = useState([]);
    const [listaQuesos, setListaQuesos] = useState([]);

    const [isNewEmbalaje, setNewEmbalaje] = useState(true);
    const [isLoadingEmbalajes, setLoadingEmbalajes] = useState(true);
    const [isLoadingQuesos, setLoadingQuesos] = useState(true);
    const [isDialogOpen, setDialogOpen] = useState(false);
    const [openDelete, setOpenDelete] = useState(false);

    const fetchEmbalajes = useCallback(() => {
        getAllEmbalajes()
            .then(({ data }) => setListaEmbalajes(data))
            .catch(() => toast.error("No se pudo cargar embalajes"))
            .finally(() => setLoadingEmbalajes(false))
    }, []);

    const fetchQuesos = useCallback(() => {
        getAllQuesos()
            .then(({ data }) => {

                setListaQuesos(data)
                setLoadingQuesos(false);
            })
            .catch(() => toast.error("No se pudo cargar productos"))
            .finally(() => setLoadingQuesos(false));
    }, []);

    useEffect(() => {
        fetchEmbalajes();
        fetchQuesos();
    }, [fetchEmbalajes, fetchQuesos]);

    const handleNewEmbalaje = useCallback(() => { setDialogOpen(true) }, []);

    const handleCardClick = useCallback((id) => {
        setNewEmbalaje(false);
        const selected = listaEmbalajes.filter(e => e.id === id).pop();
        setEmbalaje(selected);
        setDialogOpen(true);
    }, [listaEmbalajes]);

    const closeDialog = useCallback(() => {
        setEmbalaje(embalajeInicial);
        setNewEmbalaje(true);
        setDialogOpen(false);
    }, [])

    const handleSubmit = useCallback((embalajeSubmmit) => {
        if (isNewEmbalaje) postEmbalaje(embalajeSubmmit)
            .then(() => {
                fetchEmbalajes();
                setDialogOpen(false);
                setEmbalaje(embalajeInicial);
            })
            .catch(() => toast.error('No se pudo crear embalaje'))
        else putEmbalaje(embalajeSubmmit)
            .then(() => {
                fetchEmbalajes();
                setDialogOpen(false);
                setEmbalaje(embalajeInicial);
            })
            .catch(() => toast.error('No se pudo actualizar embalaje'))
    }, [fetchEmbalajes, isNewEmbalaje])

    const handleAgregarStock = useCallback((nuevoStock) => {
        updateStockEmbalaje(embalaje.id, nuevoStock)
            .then(() => {
                fetchEmbalajes();
                setDialogOpen(false);
                setEmbalaje(embalajeInicial);
            })
            .catch(() => toast.error('No se pudo actualizar embalaje'))
    }, [embalaje.id, fetchEmbalajes])

    const openDeleteEmbalaje = useCallback(() => setOpenDelete(true), []);

    const closeDelete = useCallback(() => setOpenDelete(false), [])

    const handleDelete = useCallback(() => {
        deleteEmbalaje(embalaje.id).then(() => {
            closeDelete();
            setDialogOpen(false);
            fetchEmbalajes();
            setEmbalaje(embalajeInicial);
            setNewEmbalaje(true);
        })
    }, [closeDelete, embalaje.id, fetchEmbalajes]);

    // --- VARIABLES ---

    const listaEmbalajesFormatted = useMemo(() => {
        if (isLoadingEmbalajes) return [];
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
    }, [isLoadingEmbalajes, listaEmbalajes])

    return (
        isLoadingEmbalajes || isLoadingQuesos ? <Loading /> :
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
                            </Grid>
                            <Grid item xs={12} sm={4} mb={0.5}>
                                <ButtonGroup variant="contained" fullWidth>
                                    <Button variant="contained" onClick={handleNewEmbalaje} fullWidth>Nuevo Item</Button>
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
                                                    onClick={handleCardClick}
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
                    embalaje={embalaje}
                    open={isDialogOpen}
                    quesos={listaQuesos}
                    onClose={closeDialog}
                    onSubmit={handleSubmit}
                    onDelete={openDeleteEmbalaje}
                    onAgregarStock={handleAgregarStock}
                    isNewEmbalaje={isNewEmbalaje} />
                <DeleteDialog
                    open={openDelete}
                    onClose={closeDelete}
                    onDelete={handleDelete} />
            </>
    );
}

export default StockEmbalaje;