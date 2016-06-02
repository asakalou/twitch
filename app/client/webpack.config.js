/**
 * Created by asakalou on 2/14/16.
 */

var templateFunc = require('./webpack.config.template.js');

module.exports = templateFunc({
    outputDir: __dirname + '/build/dev',
    clean: false
});