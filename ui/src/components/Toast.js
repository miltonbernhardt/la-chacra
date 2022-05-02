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
                    // border: '1px solid #713200',
                    width: '100%',
                    fontSize: '0.95em',
                    color: 'white',
                    boxShadow: '2px 2px 5px #b2b2b2'
                },
                success: {
                    duration: 5000,
                    style: {
                        background: '#2e7d32',
                        fontWeight: 'bold'
                    },
                },
                error: {
                    duration: 5000,
                    style: {
                        background: '#d32f2f',
                        color: 'white',
                        fontWeight: 'bold'
                    },
                },
            }}
        />
    )
}