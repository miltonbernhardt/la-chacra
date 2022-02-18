import axios from 'axios';
import toast from 'react-hot-toast';
import {toastValidationErrors} from "../fields";

const PUERTO = '8000';
const RAIZ_URL = `http://localhost:${PUERTO}`;

const API_LOTE = '/api/v1/lotes/'
const API_QUESO = '/api/v1/quesos/'

const headers = {
    'Access-Control-Allow-Origin': "*",
    'Access-Control-Allow-Methods': "GET, POST, PATCH, PUT, DELETE, OPTIONS",
    'Access-Control-Allow-Headers': "Origin, X-Requested-With, Content-Type, Accept"
}

const getNewHeader = () => {
    // const token = localStorage.getItem('token');
    // return {...headers, Authorization: `Bearer ${token}`}
    return {...headers}
}

// --- LOTE METHODS ---
export const postLote = async (lote) => await POST(`${API_LOTE}`, lote);
export const putLote = async (lote) => await PUT(`${API_LOTE}`, lote);
export const deleteLote = async (id) => await DELETE(`${API_LOTE}${id}`);

// --- QUESO METHODS ---
export const getAllQuesos = async () => await GET(`${API_QUESO}`);
export const postQueso = async (queso) => await POST(`${API_QUESO}`, queso);
export const putQueso = async (queso) => await PUT(`${API_QUESO}`, queso);
export const deleteQueso = async (codigo) => await DELETE(`${API_QUESO}${codigo}`);


// --- GENERAL METHODS ---
export const POST = async (postfixUrl, data) => {
    const URL = `${RAIZ_URL}${postfixUrl}`;
    const method = `POST ${URL}`;
    console.log({request: method, data})

    return await axios.post(URL, data, {headers: getNewHeader()})
        .then(response => {
            const {data} = response
            console.info({response: data})
            toast.success(data.message);
            return {data: data.data}
        })
        .catch(err => {
            const {data, status} = err.response
            console.error({data, status})
            toastValidationErrors(new Map(Object.entries(data.errors)))
            throw new Error(data.message)
        })
}

export const PUT = async (postfixUrl, data) => {
    const URL = `${RAIZ_URL}${postfixUrl}`;
    const method = `PUT ${URL}`
    console.log({request: method, data})

    return await axios.put(URL, data, {headers: getNewHeader()})
        .then(response => {
            const {data} = response
            console.info({response: data})
            toast.success(data.message);
            return {data: data.data}
        })
        .catch(err => {
            const {data, status} = err.response
            console.error({data, status})
            toastValidationErrors(new Map(Object.entries(data.errors)))
            throw new Error(data.message)
        })
}

export const GET = async (postfixUrl) => {
    const URL = `${RAIZ_URL}${postfixUrl}`;
    const method = `GET ${URL}`
    console.log({request: method})

    return await axios.get(URL, {headers: getNewHeader()})
        .then(response => {
            const {data} = response
            console.info({response: data})
            return {data: data.data}
        })
        .catch(err => {
            const {data, status} = err.response
            console.error({data, status})
            throw new Error(data.message)
        })
}

export const DELETE = async (postfixUrl) => {
    const URL = `${RAIZ_URL}${postfixUrl}`;
    const method = `DELETE ${URL}`
    console.log({request: method})

    return await axios.delete(URL, {headers: getNewHeader()})
        .then(response => {
            const {data} = response
            console.info({response: data})
            toast.success(data.message);
            return {data: data.data}
        })
        .catch(err => {
            const {data, status} = err.response
            console.error({data, status})
            toastValidationErrors(new Map(Object.entries(data.errors)))
            throw new Error(data.message)
        })
}
