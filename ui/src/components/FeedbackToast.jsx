import * as React from 'react';
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';
import Snackbar from '@mui/material/Snackbar';
import MuiAlert from '@mui/material/Alert';

const Alert = React.forwardRef(function Alert(props, ref) {
    return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
});

const FeedbackToast = ({ openSuccess, openError, openInfo, openWarning, msgSuccess, msgError, msgInfo, msgWarning, closeSuccess, closeWarning, closeError, closeInfo }) => {

    const handleClose = (event, reason) => {
        if (reason === 'clickaway') {
            return;
        }
        if (openSuccess) closeSuccess();
        if (openError) closeError();
        if (openInfo) closeInfo();
        if (openWarning) closeWarning();
    }

    const vertical = 'bottom';
    const horizontal = 'right';

    return (
        <>
            <Snackbar open={openSuccess} autoHideDuration={6000} onClose={handleClose} anchorOrigin={{ vertical, horizontal }} >
                <Alert onClose={handleClose} severity="success" sx={{ width: '100%' }}>
                    {msgSuccess}
                </Alert>
            </Snackbar>
            <Snackbar open={openError} autoHideDuration={6000} onClose={handleClose} anchorOrigin={{ vertical, horizontal }}>
                <Alert onClose={handleClose} severity="error" sx={{ width: '100%' }}>
                    {msgError}
                </Alert>
            </Snackbar>
            <Snackbar open={openWarning} autoHideDuration={6000} onClose={handleClose} anchorOrigin={{ vertical, horizontal }}>
                <Alert onClose={handleClose} severity="warning" sx={{ width: '100%' }}>
                    {msgWarning}
                </Alert>
            </Snackbar>
            <Snackbar open={openInfo} autoHideDuration={6000} onClose={handleClose} anchorOrigin={{ vertical, horizontal }} >
                <Alert onClose={handleClose} severity="info" sx={{ width: '100%' }}>
                    {msgInfo}
                </Alert>
            </Snackbar>
        </>
    );
}

export default FeedbackToast;