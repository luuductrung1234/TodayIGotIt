(function() {
    angular.module("app.data")
        .factory("UserSvc", function($http, $q) {
            // var serverUrl = "http://localhost:3000/";
            var serverUrl = "http://localhost:8080/TigiProject/api/";

            return {
                findAllUser: findAllUser,
                findByUsername: findByUsername,
                loginAction: loginAction,
                signinAction: signinAction,
                editAction: editAction,
            }

            function findAllUser() {
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
                var url = serverUrl + "user/find/" + id;

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

            function loginAction(username, password) {
                var url = "http://localhost:3000/users?userName=" + username;

                // var url = "http://localhost:8080/TigiProject/login";
                // var data = {
                //     'username': username,
                //     'password': password
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

            function userShow() {
                var authConfig = {
                    headers: {
                        'Content-Type': 'application/json'
                    },
                },

                var url = serverUrl + "user/show";

                var deferred = $q.defer();

                $http.post(url, authConfig)
                    .success(function(response) {
                        deferred.resolve(response);
                    })
                    .error(function(err) {
                        deferred.reject(err);
                    });

                return deferred.promise;
            }

            function signinAction(data) {
                var req = {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(data)
                }

                var url = serverUrl + "users";

                var deferred = $q.defer();

                $http.post(url, JSON.stringify(data))
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

                var url = serverUrl + "users/" + id;

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
        });
})();