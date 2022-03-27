import * as React from 'react';
import { Redirect, Route } from 'react-router-dom';
import * as paths from "../resources/paths";
import { useAuth } from "../services/use-auth";

export const PrivateRoute = ({ children, ...rest }) => {
    const auth = useAuth();

    return (
        <Route
            {...rest}
            render={({ location }) =>
                auth.isLogged ? (
                    children
                ) : (
                    <Redirect
                        to={{
                            pathname: paths.login,
                            state: { from: location }
                        }}
                    />
                )
            }
        />
    );
}