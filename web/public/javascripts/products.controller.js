angular.module('hogentResto').controller('ProductsController',
    function($state, products, auth, alertService) {
        var vm = this;

        vm.isLoggedIn = auth.isLoggedIn;
        vm.products = products.products;
        vm.product = {};

        vm.createProduct = createProduct;

        var alert = alertService.getAlert();
        if(alert.message != ''){
            vm.alertMessage = alert.message;
            vm.alertType = alert.type;
            alertService.resetAlert();
        }

        function createProduct() {
            if (!vm.product.description || vm.product.description === ''
                || !vm.product.allergens || vm.product.allergens === '') {
                vm.alertmessage = "Gelieve alle velden in te vullen.";
                vm.alerttype = "danger";
                return;
            }

            products.create({
                description: vm.product.description,
                allergens: vm.product.allergens.split(',')
            }).error(function(error) {
                vm.alertMessage = error.message;
                vm.alertType = "danger";
            }).then(function() {
                alertService.setAlert('Product ' + vm.product.description + ' is toegevoegd.', 'success');
                $state.go('products');
            });
        }

        // function editMenu() {
        //     if (!vm.menu.title || vm.menu.title === '') {
        //         return;
        //     }
        //
        //     menus.edit(vm.restaurant._id, vm.menu._id, {
        //         title: vm.menu.title,
        //         description: vm.menu.description,
        //         price: vm.menu.price,
        //         availableAt: vm.menu.availableAt
        //     });
        //
        //     alertService.setMessage('Resto ' + vm.restaurant.name + ' is aangepast.');
        //     $state.go($state.current, {}, {
        //         reload: true
        //     });
        //
        // }
        //
        // function deleteMenu() {
        //     restaurants.deleteMenu(vm.restaurant._id, vm.menu._id);
        //     angular.element("#myModal").modal('hide');
        //     angular.element(".modal-backdrop.fade.in").remove();
        //     $state.go('menus', {
        //         id: restaurant._id
        //     });
        //     console.log(value);
        // }
    }
);
