(function() {
    angular.module("app.data")
        .factory("CourseSvc", function($http, $q, $rootScope, serverUrl, UserSvc) {
            return {
                findAllCourse: findAllCourse,
                findByCourseId: findByCourseId,
                getIntroVideo: getIntroVideo,
                getImage: getImage,
                getAllRate: getAllRate,
                getRateFull: getRateFull,
                getAllReview: getAllReview,
                getAllResources: getAllResources,
                getResourceVideo: getResourceVideo,
                getResourceFile: getResourceFile,
                getMyCourses: getMyCourses,
                // getCurInstructor: getCurInstructor,
            }

            function getIntroVideo(id, username, password) {
                var auth = btoa(`${username}:${password}`);

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
                        deferred.resolve(response);
                    })
                    .error(function(err) {
                        alert(err.message);
                        deferred.reject(err);
                    });

                return deferred.promise;
            }

            function getImage(id) {
                var url = serverUrl + "course/" + id + "/media/image";

                var deferred = $q.defer();

                deferred.resolve(url);

                // $http.get(url)
                //     .success(function(response) {
                //         console.log(response);

                //         deferred.resolve(response);
                //     })
                //     .error(function(err) {
                //         deferred.reject(err);
                //     });

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
                        alert(err.message);
                        deferred.reject(err);
                    });

                return deferred.promise;
            }

            function findByCourseId(id) {
                var url = serverUrl + "course/info/" + id;

                var deferred = $q.defer();

                $http.get(url)
                    .success(function(response) {
                        deferred.resolve(response);
                    })
                    .error(function(err) {
                        alert(err.message);
                        deferred.reject(err);
                    });

                return deferred.promise;
            }

            function getAllRate(id) {
                var url = serverUrl + "course/" + id + "/rate";

                var deferred = $q.defer();

                $http.get(url)
                    .success(function(response) {
                        deferred.resolve(response);
                    })
                    .error(function(err) {
                        alert(err.message);
                        deferred.reject(err);
                    });

                return deferred.promise;
            }

            function getRateFull(id) {
                var url = serverUrl + "course/" + id + "/rate/full";

                var deferred = $q.defer();

                $http.get(url)
                    .success(function(response) {
                        deferred.resolve(response);
                    })
                    .error(function(err) {
                        alert(err.message);
                        deferred.reject(err);
                    });

                return deferred.promise;
            }

            function getAllReview(id) {
                var url = serverUrl + "course/" + id + "/review";

                var deferred = $q.defer();

                $http.get(url)
                    .success(function(response) {
                        deferred.resolve(response);
                    })
                    .error(function(err) {
                        alert(err.message);
                        deferred.reject(err);
                    });

                return deferred.promise;
            }

            function getAllResources(id, username, password) {
                var auth = btoa(`${username}:${password}`);

                var url = serverUrl + "course/" + id + "/resources";

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

            function getResourceVideo(id, username, password) {
                var auth = btoa(`${username}:${password}`);

                var url = serverUrl + "course/resource/" + id + "/media/video";

                var deferred = $q.defer();

                deferred.resolve(url);

                return deferred.promise;
            }

            function getResourceFile(id, username, password) {
                var auth = btoa(`${username}:${password}`);

                var url = serverUrl + "course/resource/" + id + "/media/file";

                var deferred = $q.defer();

                $http({
                        method: 'GET',
                        url: url,
                        headers: {
                            'Authorization': 'Basic ' + auth,
                            'Accept': 'text/html'
                        }
                    })
                    .success(function(response) {
                        console.log(response);
                        deferred.resolve(response);
                    })
                    .error(function(err) {
                        alert(err.message);
                        deferred.reject(err);
                    });

                return deferred.promise;
            }

            function getMyCourses(username, password) {
                var auth = btoa(`${username}:${password}`);

                var url = serverUrl + "user/info/courses";

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
        });
})();