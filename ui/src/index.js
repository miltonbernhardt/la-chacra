"use strict";

import { ThemeProvider } from '@emotion/react';
import * as React from 'react';
import * as ReactDOM from "react-dom"
import { BrowserRouter as Router, Redirect, Route, Switch } from 'react-router-dom';
import { Toast } from "./components/Toast";
import { ForbiddenPage } from "./pages/Error/ForbiddenPage";
import { NotFoundPage } from "./pages/Error/NotFoundPage";
import { LoginPage } from "./pages/LoginPage";
import { MainPage } from "./pages/MainPage";
import { PrivateRoute } from "./pages/PrivateRoute";
import * as paths from "./resources/paths";
import { Theme } from "./resources/theme";
import { ProvideAuth } from "./services/use-auth";

export const App = () => {
    return (
        <>
            <ProvideAuth>
                <ThemeProvider theme={Theme}>
                    <Router>
                        <Switch>
                            <Route exact path={paths.login} component={LoginPage} />
                            <Route exact path={paths.forbidden} component={ForbiddenPage} />
                            <Route exact path={paths.notFound} component={NotFoundPage} />
                            <PrivateRoute path={paths.home}>
                                <MainPage theme={Theme} />
                            </PrivateRoute>
                            <Redirect from="*" to={paths.home} />
                        </Switch>
                    </Router>
                </ThemeProvider>
            </ProvideAuth>
            <Toast />
        </>
    );
}

ReactDOM.render(
    <App />,
    document.getElementById('react')
)
