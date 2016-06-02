
var templateFunc = require('./webpack.config.template.js');

module.exports = templateFunc({
    min: true,
    hash: true,
    clean: true,
    outputDir: __dirname + '/build/prod/'
});
