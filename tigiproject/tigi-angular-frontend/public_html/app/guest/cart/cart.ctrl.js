(function() {
    angular.module("app.cart")
        .controller("Cart", function($window, $scope, $rootScope, $cookies, $cookieStore, CartSvc) {
            $scope.cartcourses = [];
            $scope.order = [];
            $scope.totalPrice = 0.0;

            if ($cookieStore.get('curUser') == undefined ||
                $rootScope.curLogin == null) {
                $window.location.href = "#/home";
            } else {
                if ($rootScope.curLogin.userRoles[0].type != 'STUDENT') {
                    $window.location.href = "#/home";
                }
            }

            $scope.getCartDetails = function() {
                return $rootScope.curLogin.userCart.cartDetails;
            }

            $scope.buyAllCart = function() {
                CartSvc.checkoutOrder($cookieStore.get('curUser'), $cookieStore.get('curPass'))
                    .then(function(response) {
                        $scope.order = response;
                        $scope.order.orderDetails.forEach(function(or) {
                            $scope.totalPrice += or.course.price;
                        });
                        $rootScope.$emit('GetUserInfo', $cookieStore.get('curUser'), $cookieStore.get('curPass'));
                    }, function(err) {
                        console.log("Error: " + err);
                    });
            }

            $scope.payOrder = function() {
                CartSvc.payOrder($scope.order, $cookieStore.get('curUser'), $cookieStore.get('curPass'))
                    .then(function(response) {
                        console.log(response);
                        $rootScope.$emit('GetUserInfo', $cookieStore.get('curUser'), $cookieStore.get('curPass'));
                    }, function(err) {
                        console.log("Error: " + err);
                    });
            }
        })
        .directive("moreCourseInfo", function() {
            return {
                restrict: "A",
                link: function(scope, elem, attrs) {

                }
            }
        })
        .directive("deleteCourse", ['$cookies', '$cookieStore', 'CartSvc', function($cookies, $cookieStore, CartSvc) {
            return {
                restrict: "A",
                link: function(scope, elem, attrs) {
                    $(elem).on('click', function() {
                        CartSvc.removeFromCart(attrs.ngClass, $cookieStore.get('curUser'), $cookieStore.get('curPass'))
                            .then(function(response) {
                                console.log(response);
                                scope.$root.$emit('GetUserInfo', $cookieStore.get('curUser'), $cookieStore.get('curPass'));
                            }, function(err) {
                                console.log(err);
                            });
                        return false;
                    })
                }
            }
        }]);
})();