(function() {
    angular.module("app.data")
        .factory("CartSvc", function($http, $q) {
            var serverUrl = "http://localhost:8080/TigiProject/api/";

            return {
                checkoutOrder: checkoutOrder,
                payOrder: payOrder
            }

            function checkoutOrder(username, password) {
                var auth = btoa(`${username}:${password}`);
                var url = serverUrl + "user/order/checkout";

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

            function payOrder(username, password) {
                var auth = btoa(`${username}:${password}`);
                var url = serverUrl + "user/order/pay";

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