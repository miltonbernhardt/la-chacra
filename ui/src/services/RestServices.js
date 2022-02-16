import axios from 'axios';

const RAIZ_URL = `http://localhost`;

const DIRECCION_LOTE = 'localhost';
const PUERTO = '8000';
const API_LOTE = 'v1/lotes/'
const API_QUESO = 'v1/quesos/'

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

export const postLote = async (lote) => await POST(PUERTO, `${API_LOTE}`, lote);
export const putLote = async (lote) => await PUT(PUERTO, `${API_LOTE}`, lote);
export const deleteLote = async (id) => await DELETE(PUERTO, `${API_LOTE}${id}`);

// --- QUESO METHODS ---

export const getAllQuesos = async () => await GET(PUERTO, `${API_QUESO}`);
export const postQueso = async (queso) => await POST(PUERTO, `${API_QUESO}`, queso);
export const putQueso = async (queso) => await PUT(PUERTO, `${API_QUESO}`, queso);
export const deleteQueso = async (codigo) => await DELETE(PUERTO, `${API_QUESO}${codigo}`);


// --- GENERAL METHODS ---

export const POST = async (port, postfixUrl, data) => {
    const URL = `${RAIZ_URL}:${port}/api/${postfixUrl}`;

    const method = `POST ${URL}`;

    console.log({ request: method, data })
    const response = await axios.post(URL, data, { headers: getNewHeader() })
    const { data: dataResponse, status } = response
    if (status !== 200) {
        if (status === 401) {
            localStorage.removeItem("token")
            localStorage.removeItem("username")
        }
        throw new Error(dataResponse)
    } else {
        console.log({ response: method, dataResponse })
        return { data: dataResponse }
    }
}

export const PUT = async (port, postfixUrl, data) => {
    console.log({ postfixUrl })
    const URL = `${RAIZ_URL}:${port}/api/${postfixUrl}`;

    const method = `PUT ${URL}`
    console.log({ request: method, data })

    const response = await axios.put(URL, data, { headers: getNewHeader() })
    const { data: dataResponse, status } = response
    if (status !== 200) {
        if (status === 401) {
            localStorage.removeItem("token")
            localStorage.removeItem("username")
        }
        throw new Error(dataResponse)
    } else {
        console.log({ response: method, dataResponse })
        return { data: dataResponse }
    }
}

export const GET = async (port, postfixUrl) => {
    const URL = `${RAIZ_URL}:${port}/api/${postfixUrl}`;

    const method = `GET ${URL}`

    console.log({ request: method })

    const response = await axios.get(URL, { headers: getNewHeader() })

    console.log({ response: response })

    const data = response.data.data
    const status = response.status

    if (status !== 200) {
        if (status === 401) {
            localStorage.removeItem("token")
            localStorage.removeItem("username")
        }
        throw new Error(data)
    } else {
        return { data }
    }
}

export const DELETE = async (port, postfixUrl) => {
    const URL = `${RAIZ_URL}:${port}/api/${postfixUrl}`;
    const method = `DELETE ${URL}`
    console.log({ request: method })

    const response = await axios.delete(URL, { headers: getNewHeader() })

    const { data: dataResponse, status } = response

    if (status !== 200) {
        if (status === 401) {
            localStorage.removeItem("token")
            localStorage.removeItem("username")
        }
        throw new Error(dataResponse)
    } else {
        console.log({ response: method, dataResponse })
        return { data: dataResponse }
    }
}