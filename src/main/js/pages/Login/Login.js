import * as React from 'react';
import SecurityIcon from '@mui/icons-material/Security';
import { Avatar, Button, Box, Container, CssBaseline, Grid, Typography } from '@mui/material';
import Input from "../../components/Input";
import { toastValidationErrors } from "../../resources/fields";
import * as field from "../../resources/fields";
import * as message from "../../resources/messages";
import * as validation from "../../resources/validations";

const loginForm = {
    username: '',
    password: ''
}

export const Login = ({ setLogged }) => {
    const refUsername = React.createRef()
    const refPassword = React.createRef()

    const handleSubmit = (event) => {
        event.preventDefault();
        const errors = new Map();
        const values = {};

        refUsername.current.validate(errors, values, [
            { func: validation.empty, msg: message.valEmptyField }
        ])

        refPassword.current.validate(errors, values, [
            { func: validation.empty, msg: message.valEmptyField }
        ])

        console.log({ errors })
        console.log({ values })

        if (errors.size > 0) {
            console.error(errors)
            toastValidationErrors(errors)
            return
        }

        localStorage.setItem("username", values.username)
        localStorage.setItem("password", values.password)
        setLogged(true)

        // todo enviar los datos al backend
        // todo si el envio es exitoso, setear la cookie y reenviar al home
        // setear el form en vacio
    };

    return (
        <Container component="main" maxWidth="xs">
            <CssBaseline/>
            <Box
                sx={{
                    marginTop: 8,
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                }}
            >
                <Avatar sx={{ m: 1, bgcolor: 'primary.main' }}>
                    {/* todo usar icon de la chacra */}
                    <SecurityIcon/>
                </Avatar>
                <Typography component="h1" variant="h5">
                    Iniciar Sesión
                </Typography>
                <Grid container spacing={1.5} sx={{ mt: 1 }}>
                    <Input ref={refUsername}
                           id={field.backUsername}
                           label={field.username}
                           value={loginForm.username}
                           type="text"
                           fullWidth
                           required/>
                    <Input ref={refPassword}
                           id={field.backPassword}
                           label={field.password}
                           value={loginForm.password}
                           type="password"
                           fullWidth
                           required/>
                    <Grid item xs={12}>
                        <Button
                            fullWidth
                            variant="contained"
                            sx={{ mt: 3, mb: 2 }}
                            onClick={handleSubmit}
                        >
                            Iniciar Sesión
                        </Button>
                    </Grid>
                </Grid>
            </Box>
        </Container>
    )
}