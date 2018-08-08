(function() {
    angular.module("app.course.details")
        .controller("CourseDetails", function($scope, $rootScope, $cookieStore, $routeParams, CourseSvc) {
            var serverUrl = "http://localhost:3000/";

            $scope.current = null;
            $scope.relatedcourses = [];
            $scope.isSubcribed = false;
            $scope.introVideo = null;
            $scope.introVideoLoaded = false;

            if ($routeParams.id !== undefined) {
                $scope.introVideoLoaded = false;
                getByCourseId($routeParams.id);
            }

            $scope.$on('$viewContentLoaded', function() {
                checkSubcribed($routeParams.id);
            })

            $scope.getCurrentInstructor = function() {
                if ($routeParams.id !== undefined) {

                }
            }

            $scope.watchIntro = function() {
                $scope.introVideoLoaded = true;

                CourseSvc.getIntroVideo($scope.current.courseId, $cookieStore.get('curUser'), $cookieStore.get('curPass'))
                    .then(function(response) {
                        $scope.introVideo = response;
                    }, function(err) {
                        console.log("Error: " + err);
                    });
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

            function checkSubcribed(curId) {
                $scope.isSubcribed = false;

                if ($rootScope.curLogin != null) {
                    $rootScope.curLogin.userCourseOwners.forEach(function(i) {
                        if (i.id == curId) {
                            if (i.owerType == 'BUY') {
                                $scope.isSubcribed = true;
                            }
                        }
                    })
                }

                $scope.$apply();
            }
        })
        .directive("toggleChapter", function() {
            return {
                restrict: "A",
                link: function(scope, elem, attrs) {
                    $(elem).click(function() {
                        $(elem).siblings().slideToggle();
                    });
                }
            }
        })
        // .directive("btnWatchDemo", function() {
        //     return {
        //         restrict: "A",
        //         link: function(scope, elem, attrs) {
        //             $(elem).click(function() {
        //                 CourseSvc.getDemoVideo(1, $cookieStore.get('curUser'), $cookieStore.get('curPass'))
        //                     .then(function(response) {
        //                         scope.introVideo;
        //                     }, function(err) {
        //                         console.log("Error: " + err);
        //                     });
        //                 return false;
        //             });
        //         }
        //     }
        // });
})();