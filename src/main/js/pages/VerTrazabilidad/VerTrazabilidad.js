import * as React from 'react';
import { FormTrazabilidad } from "./FormTrazabilidad";
import { GridTrazabilidad } from "./GridTrazabilidad";
import { PageFormTable } from "../../components/PageFormTable";
import { produccion as data } from "../../data/data";

export const VerTrazabilidad = () => {
    return (<>
            <>
                <PageFormTable
                    form={
                        <FormTrazabilidad/>
                    }
                    sizeForm={5}
                    table={
                        <GridTrazabilidad data={data}/>
                    }
                />
            </>
        </>
    );
}