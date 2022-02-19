import { Grid } from '@mui/material';

const WhitePage = ({form, table, children}) => {
    return (
        <Grid container spacing={2} padding={2} direction="row"
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

export default WhitePage