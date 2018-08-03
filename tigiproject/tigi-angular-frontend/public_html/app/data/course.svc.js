(function() {
    angular.module("app.data")
        .factory("CourseSvc", function($http, $q) {
            // var serverUrl = "http://localhost:3000/";
            var serverUrl = "http://localhost:8080/TigiProject/api/";

            return {
                findAllCourse: findAllCourse,
                findByCourseId: findByCourseId,
            }

            function findAllCourse() {
                var url = serverUrl + "courses";
                // if (coll === undefined || coll === "") {
                //     url += "courses";
                // } else {
                //     url += "courses?keys=" + coll;
                // }

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

            function findByCourseId(id) {
                var url = serverUrl + "course/show/" + id;

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
        });
})();