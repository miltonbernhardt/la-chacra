import * as React from "react";
import { Toaster } from "react-hot-toast";

export const Toast = () => {
    return (
        <Toaster
            containerStyle={{
                bottom: 30,
                right: 10,
            }}
            position="bottom-right"
            toastOptions={{
                iconTheme: {
                    primary: 'black',
                },
                style: {
                    width: '100%',
                    fontSize: '0.85em',
                    color: 'white',
                    boxShadow: '2px 2px 5px #b2b2b2'
                },
                success: {
                    duration: 5000,
                    style: {
                        background: '#2e7d32',
                    },
                },
                error: {
                    duration: 5000,
                    style: {
                        background: '#e57373',
                    },
                },
            }}
        />
    )
}