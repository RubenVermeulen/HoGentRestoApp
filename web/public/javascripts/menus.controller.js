angular.module('hogentResto').controller('MenusController',
    function($state, menu, restaurant, auth, alertService) {
        var vm = this;

        vm.isLoggedIn = auth.isLoggedIn;
        vm.restaurant = restaurant;
        vm.menu = menu;

        vm.editMenu = editMenu;

        var message = alertService.getMessage();
        if (message != '') {
            vm.successmessage = alertService.getMessage();
            alertService.resetMessage();
        }

        function editMenu() {
            if (!vm.menu.title || vm.menu.title === '') {
                return;
            }



        }
    }
);
