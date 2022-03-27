import { withStyles } from '@material-ui/core/styles'
import { Box, Container, CssBaseline, Link, Typography } from '@mui/material';
import * as React from 'react';
import { ErrorImage } from "../Images";
import { home as pathHome } from "../resources/paths";

export const WhiteTypography = withStyles({
    root: {
        color: "#FFFFFF"
    }
})(Typography);

export const ErrorPage = ({ error = '', title, description }) => {
    const goToHome = () => window.location.href = pathHome

    React.useEffect(() => {
        document.body.style.backgroundColor = '#be2929'
    }, [])

    return (
        <Container>
            <CssBaseline/>
            <Box
                sx={{
                    display: 'flex',
                    flexDirection: 'column',
                    justifyContent: 'center',
                    alignItems: 'center',
                    height: '98vh'
                }}
            >
                <ErrorImage/>
                <WhiteTypography component="div" variant="h5">
                    ERROR {error}
                </WhiteTypography>
                <WhiteTypography component="div" variant="h4">
                    {title}
                </WhiteTypography>
                <WhiteTypography component="div" variant="subtitle1" gutterBottom>
                    {description}
                </WhiteTypography>
                <Link
                    fontStyle='italic'
                    variant="text"
                    color="#fff"
                    sx={{
                        marginTop: '15px',
                        color: '#fff'
                    }}
                    onClick={goToHome}
                >
                    Volver al inicio
                </Link>
            </Box>
        </Container>
    )
}