import axios from 'axios';
import toast from 'react-hot-toast';
import { toastValidationErrors } from "../resources/fields";

const PUERTO = '8000';
const RAIZ_URL = `http://localhost:${PUERTO}`;

const API_LOTE = '/api/v1/lotes/'
const API_QUESO = '/api/v1/quesos/'
const API_CLIENTE = '/api/v1/clientes/'
const API_TIPO_CLIENTE = '/api/v1/tipos_cliente/'
const API_PRECIO = '/api/v1/precios/'
const API_EXPEDICION = '/api/v1/expediciones/'
const API_REMITO = '/api/v1/remitos/'
const API_VENTAS = '/api/v1/ventas/'

const headers = {
    'Access-Control-Allow-Origin': "*",
    'Access-Control-Allow-Methods': "GET, POST, PATCH, PUT, DELETE, OPTIONS",
    'Access-Control-Allow-Headers': "Origin, X-Requested-With, Content-Type, Accept"
}

const getNewHeader = () => {
    // const token = localStorage.getItem('token');
    // return {...headers, Authorization: `Bearer ${token}`}
    return { ...headers }
}

// --- LOTE METHODS ---
export const postLote = async (lote) => await POST(`${API_LOTE}`, lote);
export const putLote = async (lote) => await PUT(`${API_LOTE}`, lote);
export const deleteLote = async (id) => await DELETE(`${API_LOTE}${id}`);
export const getLote = async (id) => await GET(`${API_LOTE}${id}`);
export const getLotesBetweenDates = async (fechaDesde, fechaHasta) => await GET(`${API_LOTE}/produccion?fecha_desde=${fechaDesde}&fecha_hasta=${fechaHasta}`);
export const getLotesByQuesoWithStock = async (codigoQueso) => await GET(`${API_LOTE}/queso?codigoQueso=${codigoQueso}`);
export const getRendimientoByDia = async (fechaDesde, fechaHasta) => await GET(`${API_LOTE}/rendimiento/dia?fecha_desde=${fechaDesde}&fecha_hasta=${fechaHasta}`);
export const getRendimientoByQueso = async (fechaDesde, fechaHasta) => await GET(`${API_LOTE}/rendimiento/queso?fecha_desde=${fechaDesde}&fecha_hasta=${fechaHasta}`);

// --- QUESO METHODS ---
export const getAllQuesos = async () => await GET(`${API_QUESO}`);
export const postQueso = async (queso) => await POST(`${API_QUESO}`, queso);
export const putQueso = async (queso) => await PUT(`${API_QUESO}`, queso);
export const deleteQueso = async (codigo) => await DELETE(`${API_QUESO}${codigo}`);

// --- TIPO CLIENTE METHODS ---
export const getAllTipoClientes = async () => await GET(`${API_TIPO_CLIENTE}`);

// --- CLIENTE METHODS ---
export const getAllClientes = async () => await GET(`${API_CLIENTE}`);
export const postCliente = async (cliente) => await POST(`${API_CLIENTE}`, cliente);
export const putCliente = async (cliente) => await PUT(`${API_CLIENTE}`, cliente);
export const deleteCliente = async (id) => await DELETE(`${API_CLIENTE}${id}`);

// --- PRECIO METHODS ---
export const getAllPrecios = async () => await GET(`${API_PRECIO}`);
export const postPrecio = async (precio) => await POST(`${API_PRECIO}`, precio);
export const putPrecio = async (precio) => await PUT(`${API_PRECIO}`, precio);

// --- EXPEDICION METHODS ---
export const getAllExpediciones = async () => await GET(`${API_EXPEDICION}`);
export const postExpedicion = async (expedicion) => await POST(`${API_EXPEDICION}`, expedicion);
export const putExpedicion = async (expedicion) => await PUT(`${API_EXPEDICION}`, expedicion);
export const deleteExpedicion = async (id) => await DELETE(`${API_EXPEDICION}${id}`);
export const getExpedicionesByLote = async (idLote) => await GET(`${API_EXPEDICION}lote?idLote=${idLote}`);

// --- REMITO METHODS ---
export const getRemito = async (idCliente, fecha) => await GET(`${API_REMITO}/generate?id_cliente=${idCliente}&fecha=${fecha}`);
export const postRemito = async (idCliente, fecha) => await POST(`${API_REMITO}?id_cliente=${idCliente}&fecha=${fecha}`);

// --- VENTAS METHODS ---

export const getVentas = async (fechaDesde, fechaHasta) => await GET(`${API_VENTAS}?fecha_desde=${fechaDesde}&fecha_hasta=${fechaHasta}`);

// --- GENERAL METHODS ---
export const GET = async (postfixUrl) => {
    const URL = `${RAIZ_URL}${postfixUrl}`;
    const method = `GET ${URL}`
    console.log({ request: method })

    return await axios.get(URL, { headers: getNewHeader() })
        .then(response => {
            const { data } = response
            console.info({ response: data })
            return { data: data.data }
        })
        .catch(err => processResponseError(err))
}

export const POST = async (postfixUrl, data) => {
    const URL = `${RAIZ_URL}${postfixUrl}`;
    const method = `POST ${URL}`;
    console.log({ request: method, data })

    return await axios.post(URL, data, { headers: getNewHeader() })
        .then(response => processSuccessResponseWithMessage(response))
        .catch(err => processResponseError(err))
}

export const PUT = async (postfixUrl, data) => {
    const URL = `${RAIZ_URL}${postfixUrl}`;
    const method = `PUT ${URL}`
    console.log({ request: method, data })

    return await axios.put(URL, data, { headers: getNewHeader() })
        .then(response => processSuccessResponseWithMessage(response))
        .catch(err => processResponseError(err))
}

export const DELETE = async (postfixUrl) => {
    const URL = `${RAIZ_URL}${postfixUrl}`;
    const method = `DELETE ${URL}`
    console.log({ request: method })

    return await axios.delete(URL, { headers: getNewHeader() })
        .then(response => processSuccessResponseWithMessage(response))
        .catch(err => processResponseError(err))
}

const processSuccessResponseWithMessage = (response) => {
    const { data } = response
    console.info({ response: data })
    toast.dismiss()
    toast.success(data.message);
    return { data: data.data }
}

const processResponseError = (err) => {
    if (err.response) {
        const { data, status } = err.response
        console.error({ data, status })
        toast.dismiss()
        if (data["errors"])
            toastValidationErrors(new Map(Object.entries(data.errors)))
        else
            toast.error(data.message)
    } else {
        toast.error(err.message)
        console.error(err.message)
    }
    throw new Error(err)
}