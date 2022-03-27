import { Grid, Typography } from '@mui/material';
import * as React from 'react';

export const PageFormTable = ({ form, table, titleTable, children, titleForm, mdForm, lgForm }) => {
    return (
        <Grid container
              direction="row"
              spacing={2}
              paddingRight={2}
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
            <Grid item xs={12} sm={12} md={mdForm ? mdForm : 4} lg={lgForm ? lgForm : 3}>
                <Grid item xs={12}>
                    <Typography variant="h6" padding={0.5}>
                        {titleForm}
                    </Typography>
                </Grid>
                {form}
            </Grid>

            {/* TABLE */}
            <Grid item xs={12} sm={12} md={mdForm ? 12 - mdForm : 8} lg={lgForm ? 12 - lgForm : 9}>
                <Grid item xs={12}>
                    <Typography variant="h6" padding={0.5}>
                        {titleTable}
                    </Typography>
                </Grid>
                <Grid item xs={12}>
                    {table}
                </Grid>
            </Grid>

            {/* DIALOGS */}
            {children}
        </Grid>
    )
}