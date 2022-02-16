import { Box } from '@material-ui/core'
import Logo from '../resources/Logo.svg'
import { Container } from '@material-ui/core';
import { Image } from '@mui/icons-material';
const Home = () => {
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
                    {/* <img path={Logo} alt="logo" height={200} width={200} /> */}
                    <img src={Logo} height={"100%"} width={"100%"} />
                </Box>
            </Container>
        </>
    );
}

export default Home;