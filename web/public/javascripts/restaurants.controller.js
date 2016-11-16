angular.module('hogentResto').controller('RestaurantsController',
    function($state, restaurants, restaurant, auth) {
        var vm = this;

        vm.isLoggedIn = auth.isLoggedIn;
        vm.restaurant = restaurant;
        
        vm.editRestaurant = editRestaurant;
        vm.addMenu = addMenu;

        function editRestaurant() {
            if (!vm.restaurant.name || vm.restaurant.name === '') {
                return;
            }

            if(vm.delete){
                restaurants.deleteRestaurant(restaurant._id);
                angular.element("#myModal").modal('hide');
                angular.element(".modal-backdrop.fade.in").remove();
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

            
        }

        function addMenu() {

            if (!vm.title || vm.title === '') {
                return;
            }

            restaurants.addMenu(restaurant._id, {
                title: vm.title,
                description: vm.description,
                price: vm.price,
                availableAt: vm.availableAt
            }).success(function(menu) {
                vm.restaurant.menus.push(menu);
            });

            vm.title = '';
            vm.description = '';
            vm.price = '';
            vm.availableAt = '';

        }

        function deleteMenu(){
            
        }


        // function incrementUpvotes(comment) {
        //     posts.upvoteComment(post, comment);
        // };
    }
);