(function() {
    angular.module("app.data")
        .factory("InstructorSvc", function($http, $q) {
            // var serverUrl = "http://localhost:3000/";
            var serverUrl = "http://localhost:8080/TigiProject/api/";

            return {
                findAllInstructor: findAllInstructor,
                findByUsername: findByUsername,
            }

            function findAllInstructor() {
                var url = serverUrl + "users";

                var deferred = $q.defer();

                $http.get(url)
                    .success(function(response) {
                        deferred.resolve(response);
                    })
                    .error(function(err) {
                        deferred.reject(err);
                    });

                return deferred.promise;
            }

            function findByUsername(id) {
                var url = serverUrl + "users" + id;

                var deferred = $q.defer();

                $http.get(url)
                    .success(function(response) {
                        deferred.resolve(response);
                    })
                    .error(function(err) {
                        deferred.reject(err);
                    });

                return deferred.promise;
            }

            function editAction(id, data) {
                var req = {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(data)
                }

                var url = serverUrl + "instructors/" + id;

                var deferred = $q.defer();

                $http.put(url, JSON.stringify(data))
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