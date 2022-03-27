import React, { createContext, useContext, useEffect, useState } from "react";
import { permisos, signinApi, signoutApi, signupApi } from "./RestServices";

const authContext = createContext();

export function ProvideAuth({ children }) {
    const auth = useProvideAuth(null);
    return <authContext.Provider value={auth}>{children}</authContext.Provider>;
}

export const useAuth = () => {
    return useContext(authContext);
};

function useProvideAuth() {
    const [authorizations, setAuthorizations] = useState([])
    const [isLogged, setIsLogged] = useState(localStorage.getItem("username") !== null)

    useEffect(() => {
        if (isLogged)
            permissions()
    }, [isLogged])

    const signin = ({ username, password }) => {
        const response = signinApi({ username, password })
            .then(response => {
                localStorage.setItem("username", "admin") //todo
                setIsLogged(true)
                return response
            })
            .catch(e => {
                console.error({ e })
                return e
            });
        return response.finally()
    }

    const signup = ({ username, password }) => {
        return signupApi({ username, password })
            .then((response) => {
                return response.user;
            })
            .catch(e => console.error({ e }));
    };

    const signout = () => {
        return signoutApi()
            .then(response => {
                localStorage.removeItem("username")
                setIsLogged(false)
                return response
            })
            .catch(e => console.error({ e }));
    };

    const permissions = () => {
        const response = permisos()
            .then((data) => {
                console.log({ data })
                setAuthorizations(data.data);
            })
            .catch(e => console.error({ e }))

        return response.finally()
    };

    return {
        authorizations,
        isLogged,
        permissions,
        signin,
        signup,
        signout
    };
}
