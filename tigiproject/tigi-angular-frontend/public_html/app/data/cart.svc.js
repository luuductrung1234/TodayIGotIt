(function() {
    angular.module("app.data")
        .factory("CartSvc", function($http, $q, UserSvc) {
            var serverUrl = "http://localhost:8080/TigiProject/api/";

            return {
                addToCart: addToCart,
                removeFromCart: removeFromCart,
                checkoutOrder: checkoutOrder,
                payOrder: payOrder,
            }

            function addToCart(id, username, password) {
                var auth = btoa(`${username}:${password}`);
                var url = serverUrl + "user/cart/add/" + id;

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
                        console.log(err);
                        deferred.reject(err);
                    });

                return deferred.promise;
            }

            function removeFromCart(id, username, password) {
                var auth = btoa(`${username}:${password}`);
                var url = serverUrl + "user/cart/remove/" + id;

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

            function payOrder(order, username, password) {
                var auth = btoa(`${username}:${password}`);
                var url = serverUrl + "user/order/pay";

                var deferred = $q.defer();

                $http({
                        method: 'POST',
                        url: url,
                        headers: {
                            'Authorization': 'Basic ' + auth
                        },
                        data: JSON.stringify(order)
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