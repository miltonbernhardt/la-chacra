import * as React from 'react';
import { PageFormTable } from '../../components/PageFormTable';
import { GridRemitos } from './GridRemitos';
import { useState, useCallback, useEffect, useMemo } from 'react';
import { dateMinusWeeks, todayDateISO } from '../../resources/utils';
import { getRemitosBetweenDates, getAllClientes, PDF_REMITO } from '../../services/RestServices';
import { Loading } from '../../components/Loading';
import toast from 'react-hot-toast';
import * as field from "../../resources/fields";
import { SearchByWeeks } from '../../components/SearchByWeeks'

export const VerRemitos = () => {

    const today = useMemo(() => { return todayDateISO() }, []);

    const [listaRemitos, setListaRemitos] = useState([]);
    const [listaClientes, setListaClientes] = useState([]);

    const [isLoadingRemitos, setLoadingRemitos] = useState(true);
    const [isLoadingClientes, setLoadingClientes] = useState(true);

    const fetchRemitos = useCallback((fechaHasta, semanas) => {
        const fechaDesde = dateMinusWeeks(fechaHasta, semanas);

        getRemitosBetweenDates(fechaDesde, fechaHasta)
            .then(({ data }) => setListaRemitos(data))
            .catch(() => toast.error('No se pudieron cargar los remitos'))
            .finally(() => setLoadingRemitos(false));
    }, []);

    const fetchClientes = useCallback(() => {
        getAllClientes()
            .then(({ data }) => {
                setListaClientes(data)
            })
            .catch(() => toast.error("No se pudo cargar clientes"))
            .finally(() => setLoadingClientes(false))
    }, []);

    useEffect(() => {
        fetchClientes();
        fetchRemitos(today, 1)
    }, [fetchClientes, fetchRemitos, today])

    const pdfRemito = useCallback((idRemito) => {
        window.open(`${PDF_REMITO}${idRemito}`, '_blank').focus();
    }, [])

    //--- Variables

    const listaRemitosFormatted = listaRemitos.map(remito => {
        return {
            ...remito,
            [field.backRazonSocial]: listaClientes.filter(c => { return c.id === remito.idCliente }).pop().razonSocial
        }
    })

    return (
        isLoadingClientes || isLoadingRemitos ? <Loading /> :
            <PageFormTable
                mdForm={2}
                lgForm={2}
                titleForm={"Remitos"}
                form={<SearchByWeeks
                    fechaInicial={today}
                    semanas={1}
                    onSearch={fetchRemitos}
                />}
                table={<GridRemitos
                    data={listaRemitosFormatted}
                    setSelection={pdfRemito}
                />} />
    );
}
