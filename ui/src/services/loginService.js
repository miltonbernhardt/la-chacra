//todo esto cambia al uso de token

export const existsLoginCookie = () => {
    return !(localStorage.getItem("username") === null || localStorage.getItem("username") === undefined || localStorage.getItem("username") === '')
}

export const getAuthHeader = () => {
    // const token = localStorage.getItem('token');
    // return {...headers, Authorization: `Bearer ${token}`}
    // return { ...headers }
    return { username: localStorage.getItem("username"), password: localStorage.getItem("password") }
}