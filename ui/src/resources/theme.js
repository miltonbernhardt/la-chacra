import { createTheme } from "@mui/material/styles";

const theme = {
    palette: {
        common: {
            black: "#000",
            white: "#fff"
        },
        background: {
            paper: "#fff",
            default: "#fafafa"
        },
        primary: {
            light: "rgba(247, 85, 82, 1)",
            main: "rgba(220, 48, 48, 1)",
            dark: "rgba(192, 28, 29, 1)",
            contrastText: "#fff"
        },
        secondary: {
            light: "rgba(176, 240, 237, 1)",
            main: "rgba(48, 220, 220, 1)",
            dark: "rgba(0, 186, 193, 1)",
            contrastText: "#fff"
        },
        error: {
            light: "#e57373",
            main: "#f44336",
            dark: "#d32f2f",
            contrastText: "#fff"
        },
        text: {
            primary: "rgba(0, 0, 0, 0.87)",
            secondary: "rgba(0, 0, 0, 0.54)",
            disabled: "rgba(0, 0, 0, 0.38)",
            hint: "rgba(0, 0, 0, 0.38)"
        }
    }
}

export const Theme = () => createTheme(theme);