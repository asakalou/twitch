/**
 * Created by asakalou on 2/16/16.
 */

var templateFunc = require('./webpack.config.template.js');

module.exports = templateFunc({
    min: true,
    hash: true,
    clean: true,
    outputDir: __dirname + '/build/prod/'
});
