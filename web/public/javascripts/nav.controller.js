angular.module('hogentResto').controller('NavController',
    function(auth) {
        var vm = this;

        vm.isLoggedIn = auth.isLoggedIn;
        vm.currentUser = auth.currentUser;
        vm.logOut = logOut;

        function logOut() {
            auth.logOut();
            $state.go('home');
        }
    }
);