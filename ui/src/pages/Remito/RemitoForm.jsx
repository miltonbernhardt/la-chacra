import { Autocomplete, Button, ButtonGroup, Grid, TextField, Typography } from "@mui/material";
import { tiposDeQueso } from "../../data/data";
import FileOpenIcon from '@mui/icons-material/FileOpen';
import ReceiptIcon from '@mui/icons-material/Receipt';
import { createRef } from "react";
import Input from "../../components/Input";
import Select from "../../components/Select";
import * as field from "../../resources/fields";
import * as message from "../../resources/messages";
import * as validation from "../../resources/validations";
import toast from "react-hot-toast";

const RemitoForm = ({ importe, clientes, onCargar, onEmitir }) => {

    const refFechaRemito = createRef(null)
    const refSelectCliente = createRef(null)

    const handleCargar = () => {
        const errors = new Map();
        const values = {};

        refSelectCliente.current.validate(errors, values, [
            { func: validation.emptySelect, msg: message.valEmptyField }
        ])
        refFechaRemito.current.validate(errors, values, [
            { func: validation.empty, msg: message.valEmptyFecha },
            { func: validation.olderDate, msg: message.valOlderDate }
        ])

        if (errors.size > 0) {
            console.error(errors)
            field.toastValidationErrors(errors)
            return
        }

        onCargar(values.idCliente, values.fecha)
    }

    return (
        <Grid container spacing={1.5}>
            <Grid item xs={12}>
                <Typography variant="h7" color="GrayText">
                    Datos del Remito
                </Typography>
            </Grid>
            <Input ref={refFechaRemito}
                id={field.backFechaRemito}
                label={field.fechaRemito}
                type="date"
                required />
            <Select
                ref={refSelectCliente}
                id={field.backIdCliente}
                label={field.cliente}
                options={clientes}
                required />
            <Input
                id={field.backImporteTotal}
                label={field.importeTotal}
                value={importe}
                contentEditable={false} />
            <Grid item xs={12} alignSelf="center" mb={0.5}>
                <ButtonGroup variant="contained" fullWidth>
                    <Button color="info" onClick={handleCargar} startIcon={<FileOpenIcon />}>
                        Cargar Datos
                    </Button>
                    <Button startIcon={<ReceiptIcon />}>
                        Emitir Remito
                    </Button>
                </ButtonGroup>
            </Grid>
        </Grid>
    );
}

export default RemitoForm;