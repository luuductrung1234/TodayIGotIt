(function() {
    angular.module("app.course.details")
        .controller("CourseDetails", function($scope, $rootScope, $cookieStore, $routeParams, CourseSvc) {
            $scope.current = null;
            $scope.relatedcourses = [];
            $scope.isSubcribed = false;
            $scope.introVideo = null;
            $scope.introVideoLoaded = false;
            $scope.currentReview = [];

            if ($routeParams.id !== undefined) {
                $scope.introVideoLoaded = false;
                getByCourseId($routeParams.id);
            }

            $scope.$on('$viewContentLoaded', function() {
                var initInterval = setInterval(function() {
                    if ($rootScope.curLogin != null) {
                        checkSubcribed($routeParams.id);
                        clearInterval(initInterval);
                    }
                }, 100);
            });

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
                        loadReview($scope.current.courseId);
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
            }

            function loadReview(id) {
                CourseSvc.getAllReview(id)
                    .then(function(response) {
                        $scope.currentReview = response;
                    }, function(err) {
                        console.log("Error: " + err);
                    })
            }

            $scope.getUserFeedbackInfo = function(rate) {

            }

            $scope.getPersonalRate = function(rate) {
                var cnt = 0;
                // var htmlrate = '';

                // for (let i = 0; i < parseInt(rate); i++) {
                //     htmlrate += '<span class="fa fa-star checked"></span>';
                //     cnt++;
                // }

                // for (let i = 0; i < (5 - cnt); i++) {
                //     htmlrate += '<span class="fa fa-star"></span>';
                // }

                var personalRate = [];

                for (let i = 0; i < parseInt(rate); i++) {
                    personalRate.push('checked');
                    cnt++;
                }

                for (let i = 0; i < (5 - cnt); i++) {
                    personalRate.push('');
                }

                return personalRate;
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