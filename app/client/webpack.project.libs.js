
module.exports = {
    vendor: [
        'lodash',
        'jquery',
        'bootstrap/dist/js/bootstrap.js',
        'angular',
        'angular',
        'angular-resource',
        'angular-sanitize',
        'angular-ui-router',
        'oclazyload',
        'angular-block-ui',
        'angular-growl-v2/build/angular-growl.js',

        './app/styles/bootstrap.less',
        'angular-block-ui/dist/angular-block-ui.css',
        'angular-growl-v2/build/angular-growl.css',
        'font-awesome/css/font-awesome.css'
    ],
    provide: {
        '_': 'lodash',
        '$': 'jquery',
        'jQuery': 'jquery',
        'window.jQuery': 'jquery',
        'uirouter': 'angular-ui-router',
        'Highcharts': 'Highcharts'
    }
};