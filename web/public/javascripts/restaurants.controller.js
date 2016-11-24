angular.module('hogentResto').controller('RestaurantsController',
    function($state, restaurants, restaurant, auth, alertService) {
        var vm = this;

        vm.isLoggedIn = auth.isLoggedIn;
        vm.restaurant = restaurant;

        vm.editRestaurant = editRestaurant;
        vm.addMenu = addMenu;
        vm.deleteMenu = deleteMenu;

        var alert = alertService.getAlert();
        if(alert.message != ''){
            vm.alertmessage = alert.message;
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

            if (!vm.title || vm.title === '') {
                return;
            }

            restaurants.createMenu(restaurant._id, {
                title: vm.title,
                description: vm.description,
                price: vm.price,
                availableAt: vm.availableAt
            }).then(function(menu) {
                vm.restaurant.menus.push(menu);
                alertService.setAlert('Resto ' + vm.name + ' is toegevoegd.', 'success');
                $state.go('menus', {id: restaurant._id});
            });


        }

        function deleteMenu(){
            restaurants.deleteMenu(vm.restaurant._id, vm.menuobj.id);
            angular.element("#myModal").modal('hide');
            angular.element(".modal-backdrop.fade.in").remove();
            $state.go($state.current, {}, {reload: true});
            console.log(value);
        }


        // function incrementUpvotes(comment) {
        //     posts.upvoteComment(post, comment);
        // };
    }
);
