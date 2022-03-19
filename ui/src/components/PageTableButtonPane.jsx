import {ButtonGroup, Grid, Typography} from '@mui/material';

const PageTableButtonPane = ({title, buttons, grid, children}) => {
    return (
        <Grid container
              direction="row"
              paddingRight={2}
              spacing={2}
              justifyContent="flex-start"
              alignItems="flex-start"
              style={{
                  minHeight: "92%",
                  maxWidth: "98%",
                  backgroundColor: "white",
                  margin: "1%",
                  borderRadius: "8px",
                  boxShadow: 'rgba(0, 0, 0, 0.2) 0px 2px 1px -1px, rgba(0, 0, 0, 0.14) 0px 1px 1px 0px, rgba(0, 0, 0, 0.12) 0px 1px 3px 0px',
                  boxSizing: "border-box",
              }}>
            <Grid item container direction="column" xs={12}>
                <Grid item container justifyContent="space-between" xs={12} style={{paddingBottom: "1%"}}>
                    <Typography variant="h6">{title}</Typography>
                    <ButtonGroup variant="contained">
                        {buttons}
                    </ButtonGroup>
                </Grid>
                {grid}
            </Grid>
            {children}
        </Grid>
    )
}

export default PageTableButtonPane