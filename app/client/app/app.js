
import './styles/bootstrap.less';
import 'font-awesome/css/font-awesome.css';
import 'angular-block-ui/dist/angular-block-ui.css';
import 'angular-growl-v2/build/angular-growl.css';
import './styles/style.less';

import 'bootstrap/dist/js/bootstrap.js';

import angular from 'angular';
import angularResource from 'angular-resource';
import angularSanitize from 'angular-sanitize';
import uirouter from 'angular-ui-router';
import blockUI from 'angular-block-ui';
import oclazyload from 'oclazyload';

import highcharts from 'highcharts';

import 'angular-growl-v2/build/angular-growl';


import {config} from './app.config';

import topnav from './nav';

import dashboard from './modules/dashboard';


angular.module('app', [
        angularResource,
        angularSanitize,
        uirouter,
        blockUI,
        oclazyload,
        'angular-growl',

        topnav,

        dashboard
    ])
    .config(config);