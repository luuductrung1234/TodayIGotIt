(function() {
    angular.module("app.data")
        .factory("UserSvc", function($http, $q, serverUrl) {
            return {
                findAllUser: findAllUser,
                findByUsername: findByUsername,
                loginAction: loginAction,
                signinAction: signinAction,
                editAction: editAction,
            }

            function findAllUser(username, password) {
                var auth = btoa(`${username}:${password}`);

                var url = serverUrl + "users/full";

                var deferred = $q.defer();

                var students = [];

                $http({
                        method: 'GET',
                        url: url,
                        headers: {
                            'Authorization': 'Basic ' + auth
                        }
                    })
                    .success(function(response) {
                        response.forEach(item => {
                            if (item.userRoles[0].type == 'STUDENT') {
                                students.push(item);
                            }
                        });

                        deferred.resolve(students);
                    })
                    .error(function(err) {
                        alert(err.message);
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
                        alert(err.message);
                        deferred.reject(err);
                    });

                return deferred.promise;
            }

            function loginAction(username, password) {
                var auth = btoa(`${username}:${password}`);
                var url = serverUrl + "user/info";

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
                        alert(err.message);
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

                var url = serverUrl + "user/new";

                var deferred = $q.defer();

                $http.post(url, JSON.stringify(data))
                    .success(function(response) {
                        deferred.resolve(response);
                    })
                    .error(function(err) {
                        alert(err.message);
                        deferred.reject(err);
                    });

                return deferred.promise;
            }

            function editAction(id, data, username, password) {
                // var req = {
                //     method: 'PUT',
                //     headers: {
                //         'Content-Type': 'application/json'
                //     },
                //     body: JSON.stringify(data)
                // }

                var auth = btoa(`${username}:${password}`);

                var url = serverUrl + "user/update";

                var deferred = $q.defer();

                $http({
                        method: 'POST',
                        url: url,
                        headers: {
                            'Authorization': 'Basic ' + auth
                        },
                        data: JSON.stringify(data)
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