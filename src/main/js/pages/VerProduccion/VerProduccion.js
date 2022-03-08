import * as React from 'react';
import { FormProduccion } from "./FormProduccion";
import { GridProduccion } from "./GridProduccion";
import { PageFormTable } from "../../components/PageFormTable";
import { produccion as data } from '../../data/data';

export const VerProduccion = () => {

    return (
        <>
            <PageFormTable
                form={
                    <FormProduccion/>
                }
                sizeForm={3}
                table={
                    <GridProduccion data={data}/>
                }
            />
        </>
    );
}