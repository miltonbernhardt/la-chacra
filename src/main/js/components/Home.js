import * as React from 'react';
import { Box } from '@material-ui/core'
import { Container } from '@material-ui/core';

export const Home = () => {
    return (
        <>
            <Container maxWidth="sm">
                <Box
                    sx={{
                        marginTop: 8,
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                        mt: 3
                    }}
                >
                    {/* TODO: solucionar cuando se usa el mobile y se abre el drawer, el icono se queda chiquito */}
                    <img src="images/Logo.svg" height={"100%"} width={"100%"}/>
                </Box>
            </Container>
        </>
    );
}