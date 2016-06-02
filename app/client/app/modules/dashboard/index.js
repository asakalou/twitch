import './style.less';

import angular from 'angular';
import DashboardService from './service';
import channelReport from './channel-report';

config.$inject = ['$stateProvider'];
function config($stateProvider) {
    $stateProvider
        .state('app.main.dashboard', {
            url: '/',
            abstract: true,
            views: {
                'main@': {
                    template: require('./index.html'),
                    controller: controller,
                    controllerAs: 'vm'
                }
            },
            resolve: {
                games: ['DashboardService', function (DashboardService) {
                    return DashboardService.gamesList();
                }]
            }
        })
        .state('app.main.dashboard.currentViewers', {
            url: '',
            template: require('./category.html'),
            category: 'CURRENT_VIEWERS',
            controller: categoryController,
            controllerAs: 'vm'
        })
        .state('app.main.dashboard.maxViewers', {
            url: 'max',
            template: require('./category.html'),
            category: 'MAX_VIEWERS',
            controller: categoryController,
            controllerAs: 'vm'
        })
        .state('app.main.dashboard.avgViewers', {
            url: 'avg',
            template: require('./category.html'),
            category: 'AVG_VIEWERS',
            controller: categoryController,
            controllerAs: 'vm'
        })

    ;
}

controller.$inject = ['$scope', 'blockUI', 'games', 'DashboardService'];
function controller($scope, blockUI, games, DashboardService) {
    var vm = this;
    this.games = games;
}

categoryController.$inject = ['$scope', 'blockUI', '$state', 'games', 'DashboardService'];
function categoryController($scope, blockUI, $state, games, DashboardService) {
    var vm = this;
    DashboardService.channels({categoryType: $state.current.category, from: new Date() - 60 * 60 * 1000, limit: 20}).then(function (data) {
        vm.channels = data;
    });
}




export default angular.module('app.dashboard', [channelReport])
    .factory('DashboardService', DashboardService)
    .config(config).name