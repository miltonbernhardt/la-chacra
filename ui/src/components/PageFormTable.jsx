import { Grid, Typography } from '@mui/material';

const PageFormTable = ({form, table, titleTable, children, sizeForm}) => {
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

            {/* FORM */}
            <Grid item container direction="column" xs={12} sm={12} md={4} lg={3}>
                <Grid item container direction="row" xs={sizeForm === undefined ? 4 : sizeForm} spacing={1.5}>
                    {form}
                </Grid>
            </Grid>

            {/* TABLE */}
            <Grid item container direction="row" justifyContent="center" alignItems="flex-start" xs={12} sm={12} md={8}
                  lg={9}>
                <Grid item container direction="column" xs={12} spacing={1}>
                    <Grid item xs={12}>
                        <Typography variant="h6">
                            {titleTable}
                        </Typography>
                    </Grid>
                    <Grid item xs={12}>
                        {table}
                    </Grid>
                </Grid>
            </Grid>

            {/* DIALOGS */}
            {children}

        </Grid>
    )
}

export default PageFormTable