angular.module('hogentResto').controller('MenusController',
    function($state, restaurants, restaurant, menus, menu, auth, alertService) {
        var vm = this;

        vm.isLoggedIn = auth.isLoggedIn;
        vm.restaurant = restaurant;
        vm.menu = menu;
        vm.menu.availableAt = new Date(vm.menu.availableAt);

        vm.editMenu = editMenu;
        vm.deleteMenu = deleteMenu;

        var alert = alertService.getAlert();
        if(alert.message != ''){
            vm.alertmessage = alert.message;
            alertService.resetAlert();
        }

        function editMenu() {
            if (!vm.menu.title || vm.menu.title === '') {
                return;
            }

            menus.edit(vm.restaurant._id, vm.menu._id, {
                title: vm.menu.title,
                description: vm.menu.description,
                price: vm.menu.price,
                availableAt: vm.menu.availableAt
            });

            alertService.setMessage('Resto ' + vm.restaurant.name + ' is aangepast.');
            $state.go($state.current, {}, {
                reload: true
            });

        }

        function deleteMenu() {
            restaurants.deleteMenu(vm.restaurant._id, vm.menu._id);
            angular.element("#myModal").modal('hide');
            angular.element(".modal-backdrop.fade.in").remove();
            $state.go('menus', {
                id: restaurant._id
            });
            console.log(value);
        }
    }
);
