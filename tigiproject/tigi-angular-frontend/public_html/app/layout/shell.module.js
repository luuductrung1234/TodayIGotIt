(function() {
    var name = "app.shell",
        requires = ["ngRoute", "ngAnimate", "ngSanitize"];

    angular.module(name, requires)
        .config(function($routeProvider) {
            $routeProvider
                .when("/home", {
                    templateUrl: "app/guest/home/home.html",
                    controller: "Home"
                })
                .when("/course", {
                    templateUrl: "app/guest/course/course.html",
                    controller: "Course"
                })
                .when("/course/:id", {
                    templateUrl: "app/guest/course/details/course.details.html",
                    controller: "CourseDetails"
                })
                .when("/about", {
                    templateUrl: "app/guest/about/about.html",
                    controller: "About"
                })
                .when("/profiles/:id", {
                    templateUrl: "app/guest/profiles/profiles.html",
                    controller: "Profiles"
                })
                .when("/mycourse/:id", {
                    templateUrl: "app/guest/course/user.course/course.user.html",
                    controller: "CourseUser"
                })
                .when("/mycart/:id", {
                    templateUrl: "app/guest/cart/cart.html",
                    controller: "Cart"
                })
                .when("/course/learn/:id", {
                    templateUrl: "app/guest/course/learn/course.learn.html",
                    controller: "CourseLearn"
                })
                .when("/admin/home", {
                    templateUrl: "app/admin/home/home.html",
                    controller: "AdminHome"
                })
                .when("/admin/course", {
                    templateUrl: "app/admin/course/course.html",
                    controller: "AdminCourse"
                })
                .when("/admin/course/:id", {
                    templateUrl: "app/admin/course/details/course.details.html",
                    controller: "AdminCourseDetails"
                })
                .when("/admin/instructor", {
                    templateUrl: "app/admin/instructor/instructor.html",
                    controller: "AdminInstructor"
                })
                .when("/admin/instructor/:id", {
                    templateUrl: "app/admin/instructor/details/instructor.details.html",
                    controller: "AdminInstructorDetails"
                })
                .when("/admin/user", {
                    templateUrl: "app/admin/user/user.html",
                    controller: "AdminUser"
                })
                .when("/admin/user/:id", {
                    templateUrl: "app/admin/user/details/user.details.html",
                    controller: "AdminUserDetails"
                })
                .when("/admin/logged", {
                    redirectTo: "/admin/home"
                })
                .otherwise({
                    redirectTo: "/home"
                });
        })
})();