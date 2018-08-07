(function() {
    angular.module("app.course.details")
        .controller("CourseDetails", function($scope, $rootScope, $cookieStore, $routeParams, CourseSvc) {
            var serverUrl = "http://localhost:3000/";

            $scope.current = null;
            $scope.relatedcourses = [];
            $scope.isSubcribed = false;

            if ($routeParams.id !== undefined) {
                getByCourseId($routeParams.id);
            }

            $scope.getCurrentInstructor = function() {
                if ($routeParams.id !== undefined) {

                }
            }

            $scope.watchDemo = function(){
                CourseSvc.getDemoVideo(1, $cookieStore.get('curUser'), $cookieStore.get('curPass'))
                .then(function (response) {
                    console.log(response);
                }, function (err) {
                    console.log("Error: " + err);
                })
            }

            function getByCourseId(id) {
                CourseSvc.findByCourseId(id, $cookieStore.get('curUser'), $cookieStore.get('curPass'))
                    .then(function(response) {
                        $scope.current = response;
                        getAllRelatedCourse();
                    }, function(err) {
                        console.log("Error: " + err);
                    });
            }

            function getAllRelatedCourse() {
                CourseSvc.findAllCourse()
                    .then(function(response) {
                        $scope.relatedcourses = response;
                    }, function(err) {
                        console.log("Error: " + err);
                    });
            }
        })
        .directive("toggleChapter", function() {
            return {
                restrict: "A",
                link: function(scope, elem, attrs) {
                    $(elem).click(function() {
                        // if ($(elem).children().eq(1).hasClass('fa-angle-down')) {
                        //     $(elem).children().eq(1).removeClass('fa-angle-down');
                        //     $(elem).children().eq(1).addClass('fa-angle-up');
                        // } else if ($(elem).children().eq(1).hasClass('fa-angle-up')) {
                        //     $(elem).children().eq(1).removeClass('fa-angle-up');
                        //     $(elem).children().eq(1).addClass('fa-angle-down');
                        // }
                        $(elem).siblings().slideToggle();
                        // var target = $(elem).next(".panel-collapse");
                        // target.hasClass("collapse") ? target.collapse("show") : target.collapse("hide");
                    });
                }
            }
        })
        .directive("btnWatchDemo", function() {
            return {
                restrict: "A",
                link: function(scope, elem, attrs) {
                    $(elem).click(function() {
                        scope.watchDemo();
                        scope.$apply();

                        return false;
                    });
                }
            }
        });
})();