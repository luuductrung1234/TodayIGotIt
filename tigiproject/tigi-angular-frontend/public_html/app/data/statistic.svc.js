(function() {
    angular.module("app.data")
        .factory("StatisticSvc", function($http, $q, serverUrl) {
            return {
                getMostCourseBuy: getMostCourseBuy,
                getMostCourseRate: getMostCourseRate,
            }

            function getMostCourseBuy(username, password) {
                var auth = btoa(`${username}:${password}`);

                var url = serverUrl + "statistic/course/most/buy";

                var deferred = $q.defer();

                $http({
                        method: 'GET',
                        url: url,
                        headers: {
                            'Authorization': 'Basic ' + auth
                        }
                    })
                    .success(function(response) {
                        deferred.resolve(response);
                    })
                    .error(function(err) {
                        deferred.reject(err);
                    });

                return deferred.promise;
            }

            function getMostCourseRate(username, password) {
                var auth = btoa(`${username}:${password}`);

                var url = serverUrl + "statistic/course/most/rate";

                var deferred = $q.defer();

                $http({
                        method: 'GET',
                        url: url,
                        headers: {
                            'Authorization': 'Basic ' + auth
                        }
                    })
                    .success(function(response) {
                        deferred.resolve(response);
                    })
                    .error(function(err) {
                        deferred.reject(err);
                    });

                return deferred.promise;
            }
        })
})();