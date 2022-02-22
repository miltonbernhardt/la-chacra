import { Grid, Typography } from "@mui/material";

const TitleForm = ({value}) => {
    return      <Grid item xs={12}>
        <Typography variant="h6">
            {value}
        </Typography>
    </Grid>
}

export default TitleForm;