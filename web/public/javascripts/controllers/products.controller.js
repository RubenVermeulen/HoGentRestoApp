angular.module('hogentResto').controller('ProductsController',
    function($state, products, product, auth, alertService) {
        var vm = this;

        vm.isLoggedIn = auth.isLoggedIn;
        vm.products = products.products;
        vm.product = product;

        vm.createProduct = createProduct;
        vm.editProduct = editProduct;
        vm.deleteProduct = deleteProduct;
        vm.hasProducts = hasProducts;

        var alert = alertService.getAlert();
        if(alert.message != ''){
            vm.alertMessage = alert.message;
            vm.alertType = alert.type;
            alertService.resetAlert();
        }

        function createProduct() {
            if (!vm.product.description || vm.product.description === ''
                || !vm.product.allergens || vm.product.allergens === '') {
                vm.alertMessage = "Gelieve alle velden in te vullen.";
                vm.alertType = "danger";
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

        function editProduct() {
            if (!vm.product.description || vm.product.description === ''
                || !vm.product.allergens || vm.product.allergens === '') {
                vm.alertMessage = "Gelieve alle velden in te vullen.";
                vm.alertType = "danger";
                return;
            }


            // Convert if allergens is string
            var allergens;

            if (vm.product.allergens instanceof Array) {
                allergens = vm.product.allergens;
            }
            else {
                allergens = vm.product.allergens.trim().split(',');
            }

            products.edit(vm.product._id, {
                description: vm.product.description,
                allergens: allergens
            }).error(function(error) {
                vm.alertMessage = error.message;
                vm.alertType = "danger";
            }).then(function() {
                alertService.setAlert('Product ' + vm.product.description + ' is aangepast.', 'success');
                $state.go($state.current, {}, {
                    reload: true
                });
            });



        }

        function deleteProduct() {
            products.deleteProduct(vm.productPendingDelete.id).error(function(error) {
                vm.alertMessage = error.message;
                vm.alertType = "danger";
            }).then(function() {
                vm.alertMessage = "Het product is succesvol verwijderd.";
                vm.alertType = "success";
            });

            angular.element("#myModal").modal('hide');
            angular.element(".modal-backdrop.fade.in").remove();

            $state.go('products');
        }

        function hasProducts() {
            return vm.products.length !== 0;
        }
    }
);
