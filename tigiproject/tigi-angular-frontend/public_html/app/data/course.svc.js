(function() {
    angular.module("app.data")
        .factory("CourseSvc", function($http, $q, $rootScope, serverUrl, UserSvc) {
            return {
                findAllCourse: findAllCourse,
                findByCourseId: findByCourseId,
                getIntroVideo: getIntroVideo,
                getImage: getImage,
                getAllReview: getAllReview,
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
                        console.log(response);

                        deferred.resolve(response);
                    })
                    .error(function(err) {
                        deferred.reject(err);
                    });

                return deferred.promise;
            }

            function getImage(id) {
                var url = serverUrl + "course/" + id + "/media/image";

                return url;
                // var deferred = $q.defer();

                // $http.get(url)
                //     .success(function(response) {
                //         console.log(response);

                //         deferred.resolve(response);
                //     })
                //     .error(function(err) {
                //         deferred.reject(err);
                //     });

                // return deferred.promise;
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

            function getCurInstructor(id) {
                var alluser = [];
                var ins = {};

                var deferred = $q.defer();

                UserSvc.findAllUser()
                    .then(function(response) {
                        alluser = response;
                        alluser.forEach(i => {
                            i.userCourseOwner.forEach(ii => {
                                if (ii.id == id) {
                                    if (ii.owerType == 'CREATE') {
                                        ins = ii;
                                        deferred.resolve(ins);
                                    }
                                }
                            });
                        });
                        // alluser.forEach(function(i) {
                        //     i.userCourseOwner.forEach(function(ii) {
                        //         if (ii.id == id) {
                        //             if (ii.owerType == 'CREATE') {
                        //                 ins = ii;
                        //                 deferred.resolve(ins);
                        //             }
                        //         }
                        //     })
                        // })
                    }, function(err) {
                        console.log("Error: " + err);
                    });

                return deferred.promise;
            }
        });
})();