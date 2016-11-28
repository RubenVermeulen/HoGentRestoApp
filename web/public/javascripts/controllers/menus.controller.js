angular.module('hogentResto').controller('MenusController',
    function($state, restaurants, restaurant, menus, menu, auth, products, alertService) {
        var vm = this;

        vm.isLoggedIn = auth.isLoggedIn;
        vm.restaurant = restaurant;
        vm.menu = menu;
        vm.menu.availableAt = new Date(vm.menu.availableAt);
        vm.products = products.products;

        vm.editMenu = editMenu;

        var alert = alertService.getAlert();
        if(alert.message != ''){
            vm.alertMessage = alert.message;
            vm.alertType = alert.type;
            alertService.resetAlert();
        }

        function editMenu() {

            if (vm.delete) {
                restaurants.deleteMenu(vm.restaurant._id, vm.menu._id, vm.menu);

                angular.element("#myModal").modal('hide');
                angular.element(".modal-backdrop.fade.in").remove();

                alertService.setAlert('Menu ' + vm.menu.title  + ' is verwijderd.', 'success');

                $state.go('menus', {id: restaurant._id});

                return;

            }

            if (!vm.menu.title || vm.menu.title === '' || !vm.menu.price || vm.menu.price === '') {
                return;
            }

            menus.edit(vm.restaurant._id, vm.menu._id, {
                title: vm.menu.title,
                product: vm.menu.product,
                price: vm.menu.price,
                availableAt: vm.menu.availableAt
            });

            vm.alertMessage = 'Menu is aangepast.';
            vm.alertType = 'success';
        }
    }
);
