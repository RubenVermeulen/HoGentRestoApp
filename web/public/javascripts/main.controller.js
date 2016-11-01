angular.module('hogentResto').controller('MainController',
    function(auth) {
        var vm = this;

        vm.isLoggedIn = auth.isLoggedIn;
    }
);