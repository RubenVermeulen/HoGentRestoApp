angular.module('hogentResto').controller('MenusController',
    function($state, restaurants, restaurant, menus, menu, auth, products, alertService) {
        var vm = this;

        vm.isLoggedIn = auth.isLoggedIn;
        vm.restaurant = restaurant;
        vm.menu = menu;
        vm.menu.availableAt = new Date(vm.menu.availableAt);
        vm.products = products.products;

        vm.editMenu = editMenu;
        vm.deleteMenu = deleteMenu;
        vm.selectedProduct = selectedProduct;

        var alert = alertService.getAlert();
        if(alert.message != ''){
            vm.alertMessage = alert.message;
            vm.alertType = alert.type;
            alertService.resetAlert();
        }

        function editMenu() {
            if (!vm.menu.title || vm.menu.title === '' || !vm.menu.price || vm.menu.price === '') {
                return;
            }

            menus.edit(vm.restaurant._id, vm.menu._id, {
                title: vm.menu.title,
                product: vm.menu.product,
                price: vm.menu.price,
                availableAt: vm.menu.availableAt
            });

            alertService.setAlert('Menu is aangepast.', 'success');
            $state.go($state.current, {}, {
                reload: true
            });

        }

        function selectedProduct() {
            for (var key in vm.products) {
                if (vm.products.hasOwnProperty(key)) {
                    if (vm.products[key] === vm.menu.product._id) {
                        console.log(key);
                        return key;
                    }
                }
            }
            console.log(0);
            return 0;
        }
    }
);
