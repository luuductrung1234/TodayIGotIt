(function() {
    angular.module("app.admin.home")
        .controller("AdminHome", function($window, $scope, $rootScope, $cookies, $cookieStore, CourseSvc, UserSvc, InstructorSvc, StatisticSvc) {
            $scope.totalCourses = 0;
            $scope.totalUsers = 0;
            $scope.totalInstructors = 0;
            $scope.courseMostBuy = [];
            $scope.courseMostRate = [];
            $scope.modify = 0;
            $scope.receipt = [];
            $scope.receiptType = 'day';

            if ($cookieStore.get('curUser') == undefined ||
                $rootScope.curLogin == null) {
                $window.location.href = "#/home";
            } else {
                var cnt = 100;
                var init = setInterval(function() {
                    cnt += 100;
                    if ($rootScope.curLogin != null) {
                        if ($rootScope.curLogin.userRoles[0].type != 'ADMIN') {
                            $window.location.href = "#/home";
                            clearInterval(init);
                        }
                    }

                    if (cnt == (10 * 60 * 1000)) {
                        clearInterval(init);
                    }
                }, 100);


            }

            $scope.$on('$viewContentLoaded', function() {
                $scope.courseMostBuy = [];
                CourseSvc.findAllCourse()
                    .then(function(response) {
                        $scope.totalCourses = response.length;
                    }, function(err) {
                        console.log("Error: " + err);
                    });

                var cntInit = 0;
                var initInterval = setInterval(function() {
                    cntInit += 100;

                    if ($rootScope.curLogin != null) {
                        if ($rootScope.curLogin.userRoles[0].type == 'ADMIN') {
                            $scope.modify = 0;
                            UserSvc.findAllUser($cookieStore.get('curUser'), $cookieStore.get('curPass'))
                                .then(function(response) {
                                    $scope.totalUsers = response.length;
                                }, function(err) {
                                    console.log("Error: " + err);
                                });

                            InstructorSvc.findAllInstructor($cookieStore.get('curUser'), $cookieStore.get('curPass'))
                                .then(function(response) {
                                    $scope.totalInstructors = response.length;
                                }, function(err) {
                                    console.log("Error: " + err);
                                });

                            StatisticSvc.getMostCourseBuy($cookieStore.get('curUser'), $cookieStore.get('curPass'))
                                .then(function(response) {
                                    $scope.courseMostBuy = response;
                                }, function(err) {
                                    console.log("Error: " + err);
                                });

                            StatisticSvc.getMostCourseRate($cookieStore.get('curUser'), $cookieStore.get('curPass'))
                                .then(function(response) {
                                    $scope.courseMostRate = response;
                                }, function(err) {
                                    console.log("Error: " + err);
                                });

                            StatisticSvc.getReceiptDay($scope.modify, $cookieStore.get('curUser'), $cookieStore.get('curPass'))
                                .then(function(response) {
                                    $scope.receipt = response;
                                }, function(err) {
                                    console.log("Error: " + err);
                                });

                            // StatisticSvc.getReceiptMonth($scope.modify, $cookieStore.get('curUser'), $cookieStore.get('curPass'))
                            //     .then(function(response) {
                            //         $scope.receiptMonth = response;
                            //     }, function(err) {
                            //         
                            //         console.log("Error: " + err);
                            //     });

                            // StatisticSvc.getReceiptYear($scope.modify, $cookieStore.get('curUser'), $cookieStore.get('curPass'))
                            //     .then(function(response) {
                            //         $scope.receiptYear = response;
                            //     }, function(err) {
                            //         
                            //         console.log("Error: " + err);
                            //     });


                            clearInterval(initInterval);
                        }
                    }

                    if (cntInit >= (5 * 60 * 1000)) {
                        alert("Something wrong! Please try again!");
                        clearInterval(initInterval);
                    }
                }, 100);
            });

            $scope.changeReceipt = function() {
                if ($scope.receiptType === 'day') {
                    StatisticSvc.getReceiptDay(0, $cookieStore.get('curUser'), $cookieStore.get('curPass'))
                        .then(function(response) {
                            $scope.receipt = response;
                        }, function(err) {
                            alert("Something wrong! Please try again!");
                            console.log("Error: " + err);
                        });
                } else if ($scope.receiptType === 'month') {
                    StatisticSvc.getReceiptMonth(0, $cookieStore.get('curUser'), $cookieStore.get('curPass'))
                        .then(function(response) {
                            $scope.receipt = response;
                        }, function(err) {
                            alert("Something wrong! Please try again!");
                            console.log("Error: " + err);
                        });
                } else if ($scope.receiptType === 'year') {
                    StatisticSvc.getReceiptYear(0, $cookieStore.get('curUser'), $cookieStore.get('curPass'))
                        .then(function(response) {
                            $scope.receipt = response;
                        }, function(err) {
                            alert("Something wrong! Please try again!");
                            console.log("Error: " + err);
                        });
                }
            }

            $scope.receiptPrev = function(remodify) {
                $scope.modify += remodify;

                if ($scope.receiptType === 'day') {
                    StatisticSvc.getReceiptDay($scope.modify, $cookieStore.get('curUser'), $cookieStore.get('curPass'))
                        .then(function(response) {
                            $scope.receipt = response;
                        }, function(err) {
                            alert("Something wrong! Please try again!");
                            console.log("Error: " + err);
                        });
                } else if ($scope.receiptType === 'month') {
                    StatisticSvc.getReceiptMonth($scope.modify, $cookieStore.get('curUser'), $cookieStore.get('curPass'))
                        .then(function(response) {
                            $scope.receipt = response;
                        }, function(err) {
                            alert("Something wrong! Please try again!");
                            console.log("Error: " + err);
                        });
                } else if ($scope.receiptType === 'year') {
                    StatisticSvc.getReceiptYear($scope.modify, $cookieStore.get('curUser'), $cookieStore.get('curPass'))
                        .then(function(response) {
                            $scope.receipt = response;
                        }, function(err) {
                            alert("Something wrong! Please try again!");
                            console.log("Error: " + err);
                        });
                }
            }
        });
})();