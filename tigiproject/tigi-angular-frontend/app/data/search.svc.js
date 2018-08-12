(function() {
    angular.module("app.data")
        .factory("SearchSvc", function($http, $q, serverUrl) {
            return {
                searchSth: searchSth,
            }

            function searchSth(filter) {
                var url = serverUrl + "search?filter=" + filter;

                var deferred = $q.defer();

                $http.post(url)
                    .success(function(response) {
                        deferred.resolve(response);
                    })
                    .error(function(error) {
                        deferred.reject(error);
                    });

                return deferred.promise;
            }
        });
})();