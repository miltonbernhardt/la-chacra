import * as React from 'react';
import { ErrorPage } from "../../components/ErrorPage";

export const ForbiddenPage = () => {
    return <ErrorPage
        error={403}
        title='ACCESO DENEGADO'
        description='No posee acceso a la página deseada'
    />
}