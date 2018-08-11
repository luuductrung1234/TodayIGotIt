(function() {
    angular.module("app.course")
        .controller("Course", function($scope, CourseSvc) {
            $scope.courses = [];
            $scope.startUserCourseFrom = 0;

            $scope.$on('$viewContentLoaded', function() {
                CourseSvc.findAllCourse()
                    .then(function(response) {
                        $scope.courses = response;
                    }, function(err) {
                        console.log("Error: " + err);
                    });
            });

            $scope.filterPrice = function(field, minValue, maxValue) {
                if (minValue === undefined || minValue === null) minValue = Number.MIN_VALUE;
                if (maxValue === undefined || maxValue === null) maxValue = Number.MAX_VALUE;

                return function predicateFunc(item) {
                    return minValue <= item[field] && item[field] <= maxValue;
                };
            };
        })
        .directive("addToCart", ['$cookies', '$cookieStore', 'CourseSvc', 'CartSvc', function($cookies, $cookieStore, CourseSvc, CartSvc) {
            return {
                restrict: "A",
                link: function(scope, elem, attrs) {
                    $(elem).on('click', function() {
                        if (scope.$root.curLogin == null) {
                            alert();
                            return false;
                        }

                        CartSvc.addToCart(attrs.id, $cookieStore.get('curUser'), $cookieStore.get('curPass'))
                            .then(function(response) {
                                console.log(response);
                                scope.$root.$emit('GetUserInfo', $cookieStore.get('curUser'), $cookieStore.get('curPass'));
                            }, function(err) {
                                console.log("Error: " + err);
                            });

                        scope.$apply();

                        return false;
                    })
                }
            }
        }])
        .directive("allInstructor", ['InstructorSvc', function(InstructorSvc) {
            return {
                restrict: "A",
                link: function(scope, elem, attrs) {
                    InstructorSvc.findAllInstructor()
                        .then(function(response) {

                        }, function(err) {
                            console.log("Error: " + err);
                        })
                }
            }
        }])
})();