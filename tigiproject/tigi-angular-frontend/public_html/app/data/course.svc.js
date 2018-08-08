(function() {
    angular.module("app.data")
        .factory("CourseSvc", function($http, $q, $rootScope, serverUrl) {
            return {
                findAllCourse: findAllCourse,
                findByCourseId: findByCourseId,
                getIntroVideo: getIntroVideo,
            }

            function getIntroVideo(id, username, password) {
                var auth = btoa(`${username}:${password}`);
                console.log(username);
                console.log(password);

                var url = serverUrl + "course/" + id + "/media/video";

                var deferred = $q.defer();

                $http({
                        method: 'GET',
                        url: url,
                        headers: {
                            'Authorization': 'Basic ' + auth
                        }
                    })
                    .success(function(response) {
                        console.log(response);

                        deferred.resolve(response);
                    })
                    .error(function(err) {
                        deferred.reject(err);
                    });

                return deferred.promise;
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
                        console.log(response);
                        deferred.resolve(response);
                    })
                    .error(function(err) {
                        deferred.reject(err);
                    });

                return deferred.promise;
            }
        });
})();