angular.module('hogentResto').controller('NavController',
    function($state, auth) {
        var vm = this;

        vm.isLoggedIn = auth.isLoggedIn;
        vm.currentUser = auth.currentUser;
        vm.logOut = logOut;

        function logOut() {
            auth.logOut();
            $state.go('home');
        }

        angular.element(function(){
            angular.element('.nav a').on('click', function(){
                if(angular.element('.navbar-toggle').css('display') !='none'){
                    angular.element(".navbar-toggle").trigger( "click" );
                }
            });
        });

    }
);
