
var libs = require('./webpack.project.libs.js');

var webpack = require('webpack');
var WebpackMd5Hash = require('webpack-md5-hash');
var HtmlWebpackPlugin = require('html-webpack-plugin');
var ExtractTextPlugin = require("extract-text-webpack-plugin");
var CopyWebpackPlugin = require('copy-webpack-plugin');
var CleanWebpackPlugin = require('clean-webpack-plugin');

var ProgressBarPlugin = require('progress-bar-webpack-plugin');


module.exports = function (opts) {
    var _outputDir = opts.outputDir || (__dirname + '/dist');
    var _jsFileNameTemplate = opts.hash ? '[name].[chunkhash:8].js' : '[name].js';
    var _cssFileNameTemplate = opts.hash ? '[name].[contenthash:8].css' : '[name].css';

    var plugins = [
        new ProgressBarPlugin(),

        new webpack.NoErrorsPlugin(),
        new webpack.ProvidePlugin(libs.provide),

        new webpack.optimize.CommonsChunkPlugin({
            name: 'vendor', filename: 'js/' + _jsFileNameTemplate
        }),

        new HtmlWebpackPlugin({
            template: 'app/index.html'
        }),

        new ExtractTextPlugin('css/' + _cssFileNameTemplate),

        new webpack.DefinePlugin({
            "require.specified": "require.resolve"
        }),

        new CopyWebpackPlugin([
            {from: 'app/thirdparty', to: _outputDir + '/thirdparty'}
        ]),

        new webpack.optimize.DedupePlugin()
    ];

    if (opts.min) {
        plugins.push(new webpack.optimize.UglifyJsPlugin({
            compress: {warnings: false},
            mangle: {except: ['$q', '$ocLazyLoad']}
        }));
    }
    if (opts.clean) {
        plugins.push(new CleanWebpackPlugin([_outputDir], {
            root: process.cwd(),
            verbose: true, dry: false
        }));
    }
    if (opts.hash) {
        plugins.push(new WebpackMd5Hash());
    }

    return {
        entry: {
            'app': ['./app/app.js'],
            'vendor': libs.vendor
        },

        output: {
            path: _outputDir,
            publicPath: '/',
            filename: 'js/' + _jsFileNameTemplate
        },

        module: {
            loaders: [
                {test: /\.css$/, loader: ExtractTextPlugin.extract("style", "css")},
                {test: /\.less$/, loader: ExtractTextPlugin.extract("style", "css!less")},
                {test: /\.html$/, loader: 'html'},
                {test: /\.(woff|woff2|eot|ttf)(\?.*$|$)/, loader: 'url?limit=10000&name=fonts/[hash].[ext]'},
                {test: /\.(png|svg)(\?.*$|$)/, loader: 'url?limit=10000&name=images/[hash].[ext]'},
                {
                    test: /\.js$/, exclude: /node_modules|thirdparty/, loader: 'babel',
                    query: {
                        cacheDirectory: true,
                        plugins: ['transform-runtime']
                    }
                },

                {test: /jquery\.js$/, include: /node_modules\/jquery/, loader: 'expose?$'},
                {test: /jquery\.js$/, include: /node_modules\/jquery/, loader: 'expose?jQuery'}
            ]
        },

        plugins: plugins,

        resolve: {
            alias: {}
        },

        devtool: "#source-map",

        devServer: {
            hot: true,
            port: 8084,
            historyApiFallback: true,
            outputPath: './build/dev'
        }
    };
};
