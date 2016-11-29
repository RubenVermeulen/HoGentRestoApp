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

        vm.menus = toggleMenus(new Date().getDay());
        vm.date;
        vm.toggleMenus = toggleMenus;
        vm.hasMenusThisDay = hasMenusThisDay;

        var alert = alertService.getAlert();
        if(alert.message != ''){
            vm.alertMessage = alert.message;
            vm.alertType = alert.type;
            alertService.resetAlert();
        }

        function editRestaurant() {
            if (!vm.restaurant.name || vm.restaurant.name === '' || !vm.restaurant.address || vm.restaurant.address === '' || !vm.restaurant.openingHours || vm.restaurant.openingHours === '' ||
            !vm.restaurant.coordinates.lat || vm.restaurant.coordinates.lat === '' || !vm.restaurant.coordinates.long || vm.restaurant.coordinates.long === '' ||
            !vm.restaurant.urlImage || vm.restaurant.urlImage === '') {
                 vm.alertMessage = 'Gelieve alle velden in te vullen.';
                 vm.alertType = 'danger';
                 return;
            }

            if (!$.isNumeric(vm.restaurant.coordinates.lat) || !$.isNumeric(vm.restaurant.coordinates.long)){
                vm.alertMessage = 'Coördinaten moeten een getal zijn.';
                vm.alertType = 'danger';
                return;
            }

            if(vm.delete){
                restaurants.deleteRestaurant(restaurant._id).error(function(error){
                    vm.alertMessage = 'Error. De server kon uw aanvraag niet verwerken.';
                    vm.alertType = 'danger';
                    return;
                });

                /* Delete Bootstrap modal */
                angular.element("#myModal").modal('hide');
                angular.element(".modal-backdrop.fade.in").remove();
                angular.element(".modal-open").removeClass("modal-open");

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
            }).error(function(error){
                vm.alertMessage = 'Error. De server kon uw aanvraag niet verwerken.';
                vm.alertType = 'danger';
                return;
            });

            alertService.setAlert('Resto ' + vm.restaurant.name + ' is aangepast.', 'success');
            $state.go($state.current, {}, {reload: true});


        }

        function addMenu() {

            if (!vm.title || vm.title === '' || !vm.price || vm.price === '' || !vm.product || vm.product === '' || !vm.availableAt || vm.availableAt ==='') {
                vm.alertMessage = 'Gelieve alle velden in te vullen.';
                vm.alertType = 'danger';
                return;
            }

            if (!$.isNumeric(vm.price)){
                vm.alertMessage = 'Prijs moeten een getal zijn.';
                vm.alertType = 'danger';
                return;
            }

            if (!angular.isDate(vm.availableAt)){
                vm.alertMessage = 'Gelieve een geldige datum in te geven, volgens het formaat dd/mm/jjjj';
                vm.alertType = 'danger';
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
            }).error(function(error){
                vm.alertMessage = 'Error. De server kon uw aanvraag niet verwerken.';
                vm.alertType = 'danger';
                return;
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

            }).error(function(error){
                vm.alertMessage = 'Error. De server kon uw aanvraag niet verwerken.';
                vm.alertType = 'danger';
                return;
            });

            /* Delete Bootstrap modal */
            angular.element("#myModal").modal('hide');
            angular.element(".modal-backdrop.fade.in").remove();
            angular.element(".modal-open").removeClass("modal-open");

            vm.alertMessage = 'Menu ' + vm.menuobj.title + ' is succesvol verwijderd.';
            vm.alertType = 'success';
        }

        function hasMenus() {
            return vm.restaurant.menus.length !== 0;
        }

        function hasMenusThisDay() {
            return vm.menus ? vm.menus.length !== 0 : false;
        }

        function toggleMenus(day){
            vm.menus = restaurants.menus.filter(function(menu){
                if((new Date(menu.availableAt)).getDay() === day){
                    vm.date = menu.availableAt;
                    return menu;
                }
            });

            var today = new Date();

            if (today.getDay() === 6){
                vm.date = today.setDate(today.getDate() + day + 1);
            }
            else{
                vm.date = today.setDate(today.getDate() + day - today.getDay());
            }

            angular.element("#menu-days a").removeClass('btn-primary');
            angular.element("#menu-day-" + day).addClass('btn-primary');
        }
    }
);
