
var templateFunc = require('./webpack.config.template.js');

module.exports = templateFunc({
    outputDir: __dirname + '/build/dev',
    clean: false
});