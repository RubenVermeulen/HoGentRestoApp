angular.module('hogentResto').controller('AuthController',
    function($state, auth) {
        var vm = this;

        vm.user = {};

        vm.register = function() {
            auth.register(vm.user).error(function(error) {
                vm.error = error;
            }).then(function() {
                $state.go('student-restaurants');
            });
        };

        vm.logIn = function() {
            auth.logIn(vm.user).error(function(error) {
                vm.error = error;
            }).then(function() {
                $state.go('student-restaurants');
            });
        };
    }
);