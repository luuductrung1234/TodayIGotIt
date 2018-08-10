(function() {
    angular.module("app.data")
        .factory("CourseSvc", function($http, $q, $rootScope, serverUrl, UserSvc) {
            return {
                findAllCourse: findAllCourse,
                findByCourseId: findByCourseId,
                getIntroVideo: getIntroVideo,
                getImage: getImage,
                getAllReview: getAllReview,
                getAllResources: getAllResources,
                getResourceVideo: getResourceVideo,
                getResourceFile: getResourceFile,
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
                        // console.log(response);

                        deferred.resolve(response);
                    })
                    .error(function(err) {
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
                        // console.log(response);
                        deferred.resolve(response);
                    })
                    .error(function(err) {
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
                        // console.log(response);
                        deferred.resolve(response);
                    })
                    .error(function(err) {
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
                    .error(function(error) {
                        deferred.reject(error);
                    });

                return deferred.promise;
            }
        });
})();