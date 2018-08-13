(function() {
    angular.module("app.data")
        .factory("StatisticSvc", function($http, $q, serverUrl) {
            return {
                getMostCourseBuy: getMostCourseBuy,
                getMostCourseRate: getMostCourseRate,
                getReceiptDay: getReceiptDay,
                getReceiptMonth: getReceiptMonth,
                getReceiptYear: getReceiptYear,
                getInsMostBuy: getInsMostBuy,
                getUserMostBuy: getUserMostBuy,
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
                        alert(err.message);
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
                        alert(err.message);
                        deferred.reject(err);
                    });

                return deferred.promise;
            }

            function getReceiptDay(modify, username, password) {
                var auth = btoa(`${username}:${password}`);

                var url = serverUrl + "statistic/receipt/day/" + modify;

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
                        alert(err.message);
                        deferred.reject(err);
                    });

                return deferred.promise;
            }

            function getReceiptMonth(modify, username, password) {
                var auth = btoa(`${username}:${password}`);

                var url = serverUrl + "statistic/receipt/month/" + modify;

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
                        alert(err.message);
                        deferred.reject(err);
                    });

                return deferred.promise;
            }

            function getReceiptYear(modify, username, password) {
                var auth = btoa(`${username}:${password}`);

                var url = serverUrl + "statistic/receipt/year/" + modify;

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
                        alert(err.message);
                        deferred.reject(err);
                    });

                return deferred.promise;
            }

            function getInsMostBuy(username, password) {
                var auth = btoa(`${username}:${password}`);

                var url = serverUrl + "statistic/instructor/most/buy";

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
                        alert(err.message);
                        deferred.reject(err);
                    });

                return deferred.promise;
            }

            function getUserMostBuy(username, password) {
                var auth = btoa(`${username}:${password}`);

                var url = serverUrl + "statistic/student/most/buy";

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
                        alert(err.message);
                        deferred.reject(err);
                    });

                return deferred.promise;
            }
        })
})();