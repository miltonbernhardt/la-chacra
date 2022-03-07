'use strict';

// tag::vars[]
const React = require('react')
const ReactDOM = require('react-dom')
const client = require('./client')
const App = require("./App");

ReactDOM.render(
    <React.StrictMode>
        <App />
    </React.StrictMode>,
    document.getElementById('root')
);