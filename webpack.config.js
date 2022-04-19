const path = require('path');
const webpack = require("webpack");

module.exports = (env = { PORT: 8000, DOMAIN: "localhost" }, argv) => {
    console.log({env})
    return {
        entry: ['babel-polyfill', './ui/src/index.js'],
        devtool: 'source-map',
        cache: true,
        mode: argv.mode,
        output: {
            path: __dirname,
            filename: './ui/src/main/resources/static/built/bundle.js',
        },
        module: {
            rules: [
                {
                    test: path.join(__dirname, '.'),
                    exclude: /(node_modules)/,
                    use: [{
                        loader: 'babel-loader',
                        options: {
                            presets: ["@babel/preset-env", "@babel/preset-react"]
                        }
                    }]
                }
            ]
        },
        plugins: [
            new webpack.DefinePlugin({
                "process.env": JSON.stringify({
                    REACT_APP_PORT: env.PORT,
                    REACT_APP_DOMAIN: env.DOMAIN
                })
            }),
        ]
    };
}