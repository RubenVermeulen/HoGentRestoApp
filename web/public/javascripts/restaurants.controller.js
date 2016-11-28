angular.module('hogentResto').controller('RestaurantsController',
    function($state, restaurants, restaurant, products, auth, alertService) {
        var vm = this;

        vm.isLoggedIn = auth.isLoggedIn;
        vm.restaurant = restaurant;
        vm.products = products.products;

        vm.editRestaurant = editRestaurant;
        vm.addMenu = addMenu;
        vm.deleteMenu = deleteMenu;
        vm.hasMenus = hasMenus;

        var alert = alertService.getAlert();
        if(alert.message != ''){
            vm.alertMessage = alert.message;
            vm.alertType = alert.type;
            alertService.resetAlert();
        }

        function editRestaurant() {
            if (!vm.restaurant.name || vm.restaurant.name === '') {
                return;
            }

            if(vm.delete){
                restaurants.deleteRestaurant(restaurant._id);
                angular.element("#myModal").modal('hide');
                angular.element(".modal-backdrop.fade.in").remove();
                alertService.setAlert('Resto ' + vm.restaurant.name + ' is succesvol verwijderd.', 'success');
                $state.go('home');
                return;
            }

            restaurants.edit(restaurant._id, {
                name: vm.restaurant.name,
                address: vm.restaurant.address,
                openingHours: vm.restaurant.openingHours,
                coordinates: {
                    lat: vm.restaurant.coordinates.lat,
                    long: vm.restaurant.coordinates.long
                },
                urlImage: vm.restaurant.urlImage
            });

            alertService.setAlert('Resto ' + vm.restaurant.name + ' is aangepast.', 'success');
            $state.go($state.current, {}, {reload: true});


        }

        function addMenu() {

            if (!vm.title || vm.title === '' || !vm.price || vm.price === '') {
                return;
            }

            restaurants.createMenu(restaurant._id, {
                title: vm.title,
                product: vm.product,
                price: vm.price,
                availableAt: vm.availableAt
            }).then(function(menu) {
                vm.restaurant.menus.push(menu);
                alertService.setAlert('Menu ' + vm.title + ' is toegevoegd.', 'success');
                $state.go('menus', {id: restaurant._id});
            });
        }

        function deleteMenu(){
            restaurants.deleteMenu(vm.restaurant._id, vm.menuobj.id, vm.menuobj).success(function() {
                for (var key in vm.restaurant.menus) {
                    if (vm.restaurant.menus[key]._id === vm.menuobj.id) {
                        vm.restaurant.menus.splice(key, 1);
                        break;
                    }
                }

            });

            angular.element("#myModal").modal('hide');
            angular.element(".modal-backdrop.fade.in").remove();

            vm.alertMessage = 'Menu ' + vm.menuobj.title + ' is succesvol verwijderd.';
            vm.alertType = 'success';
        }

        function hasMenus() {
            return vm.restaurant.menus.length !== 0;
        }
    }
);
