(function() {
    angular.module("app.data")
        .factory("InstructorSvc", function($http, $q, serverUrl) {
            return {
                findAllInstructor: findAllInstructor,
                findByUsername: findByUsername,
                findCourseOwner: findCourseOwner,
            }

            function findAllInstructor(username, password) {
                var auth = btoa(`${username}:${password}`);

                var url = serverUrl + "users/full";

                var deferred = $q.defer();

                var instructors = [];

                $http({
                        method: 'GET',
                        url: url,
                        headers: {
                            'Authorization': 'Basic ' + auth
                        }
                    })
                    .success(function(response) {
                        response.forEach(item => {
                            if (item.userRoles[0].type == 'TEACHER') {
                                instructors.push(item);
                            }
                        });

                        deferred.resolve(instructors);
                    })
                    .error(function(err) {
                        alert(err.message);
                        deferred.reject(err);
                    });

                return deferred.promise;
            }

            function findAllIns() {
                var url = serverUrl + "users";

                var deferred = $q.defer();

                var instructors = [];

                $http.get(url)
                    .success(function(response) {
                        response.forEach(item => {
                            if (item.userRoles[0].type == 'TEACHER') {
                                instructors.push(item);
                            }
                        });

                        deferred.resolve(instructors);
                    })
                    .error(function(err) {
                        alert(err.message);
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
                        alert(err.message);
                        deferred.reject(err);
                    });

                return deferred.promise;
            }

            function findCourseOwner(id) {
                var url = serverUrl + "course/" + id + "/instructor";

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
                        alert(err.message);
                        deferred.reject(err);
                    });

                return deferred.promise;
            }
        })
})();