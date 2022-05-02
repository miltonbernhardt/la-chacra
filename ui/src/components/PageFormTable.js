import { Grid, Typography } from '@mui/material';
import * as React from 'react';

export const PageFormTable = ({ form, table, titleTable, children, titleForm, mdForm = 6, lgForm = 4, xlForm = 3 }) => {
    return (
        <Grid container
            direction="row"
            spacing={2}
            paddingRight={2}
            style={{
                minHeight: "89vh",
                maxWidth: "98%",
                backgroundColor: "white",
                margin: "1%",
                borderRadius: "8px",
                boxShadow: 'rgba(0, 0, 0, 0.2) 0px 2px 1px -1px, rgba(0, 0, 0, 0.14) 0px 1px 1px 0px, rgba(0, 0, 0, 0.12) 0px 1px 3px 0px',
                boxSizing: "border-box",
            }}>

            {/* FORM */}
            <Grid item xs={12} sm={12} md={mdForm} lg={lgForm} xl={xlForm}>
                <Grid item xs={12}>
                    <Typography variant="h6" padding={0.5}>
                        {titleForm}
                    </Typography>
                </Grid>
                {form}
            </Grid>

            {/* TABLE */}
            <Grid item xs={12} sm={12} md={12 - mdForm} lg={12 - lgForm} xl={12 - xlForm}>
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