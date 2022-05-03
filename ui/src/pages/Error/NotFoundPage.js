import * as React from 'react';
import { ErrorPage } from "../../components/ErrorPage";

export const NotFoundPage = () => {
    return <ErrorPage
        error={404}
        title='PÁGINA NO ENCONTRADA'
        description='Tal vez la página que está buscando se eliminó o ingresó la URL incorrecta'
    />
}

