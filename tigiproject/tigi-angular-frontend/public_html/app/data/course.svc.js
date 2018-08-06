(function() {
    angular.module("app.data")
        .factory("CourseSvc", function($http, $q, $rootScope) {
            // var serverUrl = "http://localhost:3000/";
            var serverUrl = "http://localhost:8080/TigiProject/api/";

            return {
                findAllCourse: findAllCourse,
                findByCourseId: findByCourseId,
            }

            function findAllCourse() {
                var url = serverUrl + "courses";

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

            function findByCourseId(id, username, password) {
                var auth = btoa(`${username}:${password}`);

                var url = serverUrl + "course/info/" + id;

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
        });
})();