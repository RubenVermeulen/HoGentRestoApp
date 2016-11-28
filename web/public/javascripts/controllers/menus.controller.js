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

            if (!vm.menu.title || vm.menu.title === '' || !vm.menu.price || vm.menu.price === '' || !vm.menu.product || vm.menu.product === '' || !vm.menu.availableAt || vm.menu.availableAt ==='') {
                vm.alertMessage = 'Gelieve alle velden in te vullen.';
                vm.alertType = 'danger';
                return;
            }

            if (!$.isNumeric(vm.menu.price)){
                vm.alertMessage = 'Prijs moeten een getal zijn.';
                vm.alertType = 'danger';
                return;
            }

            if (!angular.isDate(vm.menu.availableAt)){
                vm.alertMessage = 'Gelieve een geldige datum in te geven, volgens het formaat dd/mm/jjjj';
                vm.alertType = 'danger';
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
