import { Button, Container, CssBaseline, Grid, Typography } from '@mui/material';
import * as React from 'react';
import { createRef } from 'react';
import { Redirect, useHistory, useLocation } from "react-router-dom";
import { Input } from "../components/Input";
import { LoginIcon } from "../Images";
import * as field from "../resources/fields";
import { toastValidationErrors } from "../resources/fields";
import * as message from "../resources/messages";
import * as paths from "../resources/paths";
import * as validation from "../resources/validations";
import { useAuth } from "../services/use-auth";

const loginForm = {
    username: '',
    password: ''
}

export const LoginPage = () => {
    const refUsername = createRef()
    const refPassword = createRef()

    const location = useLocation();
    const auth = useAuth();

    const [isLogged, setIsLogged] = React.useState(false)

    const { from } = location.state || { from: { pathname: paths.home } };

    React.useEffect(() => {
        setIsLogged(auth.isLogged)
    }, [auth.isLogged]);


    const handleSubmit = (event) => {
        event.preventDefault();
        const errors = new Map();
        const user = {};

        refUsername.current.validate(errors, user, [
            { func: validation.empty, msg: message.valEmptyField }
        ])

        refPassword.current.validate(errors, user, [
            { func: validation.empty, msg: message.valEmptyField }
        ])

        if (errors.size > 0) {
            console.error(errors)
            toastValidationErrors(errors)
            return
        }

        auth.signin(user)
    };

    if (isLogged) {
        return <Redirect push to={from}/>
    }


    return (
        <Container component="main" maxWidth="xs">
            <CssBaseline/>
            <Grid
                sx={{
                    marginTop: 8,
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                }}
            >
                <LoginIcon/>
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
            </Grid>
        </Container>
    )
}