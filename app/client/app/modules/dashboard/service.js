
var rootUrl = "http://localhost:8080/api/";
var gamesUrl = rootUrl + "games";
var channelsUrl = rootUrl + "channels";
var channelsHistoryUrl = rootUrl + "channels/history";

DashboardService.$inject = ['$http'];
function DashboardService($http) {
    'use strict';

    return {
        gamesList: function() {
            return $http.get(gamesUrl).then(function(response) {
                return response.data;
            });
        },

        channels: function(params) {
            return $http.get(channelsUrl, {params: params}).then(function(response) {
                return response.data;
            });
        },

        channelsHistory: function(params) {
            return $http.get(channelsHistoryUrl, {params: params}).then(function(response) {
                return response.data;
            });
        }
    };
}

module.exports = DashboardService;