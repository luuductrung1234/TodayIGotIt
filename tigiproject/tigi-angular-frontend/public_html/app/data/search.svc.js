(function() {
    angular.module("app.data")
        .factory("SearchSvc", function($http, $q, serverUrl) {
            return {
                searchSth: searchSth,
            }

            function searchSth(filter) {
                var url = serverUrl + "search";

                var deferred = $q.defer();

                $http.post(url, filter)
                    .success(function(response) {
                        deferred.resolve(response);
                    })
                    .error(function(err) {
                        deferred.reject(err);
                    });

                return deferred.promise;
            }
        });
})();