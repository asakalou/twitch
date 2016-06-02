/**
 * Created by asakalou on 2/14/16.
 */

config.$inject = ['$stateProvider', '$urlRouterProvider', '$locationProvider', 'growlProvider'];

export function config($stateProvider, $urlRouterProvider, $locationProvider, growlProvider) {
    "use strict";

    $locationProvider.html5Mode(true);
    $urlRouterProvider.otherwise('/');

    growlProvider.globalTimeToLive(5000);
    growlProvider.globalDisableCountDown(true);
    growlProvider.globalDisableIcons(true);


    $urlRouterProvider.otherwise(function ($injector, $location) {
        var $state = $injector.get('$state');
        $state.go('app.main.dashboard.currentViewers', {}, {reload: true});
    });

    $stateProvider
        .state('app', {
            url: '',
            abstract: true
        })
        .state('app.main', {
                url: '',
                abstract: true,
                views: {
                    'topNav@': {
                        template: require('./nav/top-nav.html')
                    }
                }
            }
        );
}


