var webpack = require('webpack');
var path = require('path');


module.exports = {
  //'./src' zoekt by default naar index.js als entry point dit is hetzelfde als './src/index.js'
    devtool: 'inline-source-map',
    entry: [
        'webpack-dev-server/client?http://127.0.0.1:8080',
        'webpack/hot/only-dev-server',

        'bootstrap-loader',
        './src',
    ],

    output: {
        path: path.join(__dirname, 'public'),
        filename: 'bundle.js'
    },

    resolve: {
        modulesDirectories: ['node_modules', 'src'],
        extension: ['', '.js', '.scss']
    },

    module: {
        loaders: [{
                test: /\.js$/,
                exclude: /node_modules/,
                loader: 'babel',
                query: {
                    presets: ['es2015']
                }
            },
            {
                test: /\.html$/,
                loader: "html"
            },

            {
                test: /\.scss$/,
                loaders: [
                    'style',
                    'css',
                    'autoprefixer?browsers=last 3 versions',
                    'sass?outputStyle=expanded'
                ]
            },

            {
                test: /\.(woff2?|ttf|eot|svg|png|jpg)$/,
                loader: 'url?limit=25000'
            },
            {
                test: /bootstrap-sass(\\|\/)assets(\\|\/)javascripts(\\|\/)/,
                loader: 'imports?jQuery=jquery'
            },
        ]
    },
    plugins: [
        new webpack.HotModuleReplacementPlugin(),
        new webpack.NoErrorsPlugin(),
    ],


    devServer: {
        hot: true,
        proxy: {
            '*': 'http://127.0.0.1:3000'
        }
    }
};
