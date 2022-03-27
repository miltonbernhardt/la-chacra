import { Box, Container } from '@material-ui/core'
import * as React from 'react';
import { HomeIcon } from "../Images";

export const HomePage = () => {
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
                    <HomeIcon/>
                </Box>
            </Container>
        </>
    );
}