import { Grid, Typography, ButtonGroup } from '@mui/material';

export const PageFormTable = ({form, table, children}) => {
    return (
        <Grid container
              direction="row"
              spacing={2}
              padding={2}
              style={{
                  minHeight: "92%",
                  maxWidth: "98%",
                  backgroundColor: "white",
                  margin: "1%",
                  borderRadius: "8px",
                  boxShadow: 'rgba(0, 0, 0, 0.2) 0px 2px 1px -1px, rgba(0, 0, 0, 0.14) 0px 1px 1px 0px, rgba(0, 0, 0, 0.12) 0px 1px 3px 0px',
                  boxSizing: "border-box",
              }}>
            <Grid item container direction="column" xs={12} sm={12} md={4} lg={3}>
                {form}
            </Grid>
            <Grid item container direction="row" justifyContent="center" alignItems="flex-start" xs={12} sm={12} md={8}
                  lg={9}>
                {table}
            </Grid>
            {children}
        </Grid>
    )
}

export const WhitePageTable = ({children}) => {
    return (
        <Grid container
              direction="row"
              padding={2}
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
            {children}
        </Grid>
    )
}