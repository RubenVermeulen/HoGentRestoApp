angular.module('hogentResto').controller('MainController',
    function(restaurants, auth) {
        var vm = this;

        vm.isLoggedIn = auth.isLoggedIn;

        vm.restaurants = restaurants.restaurants;
        vm.addRestaurant = addRestaurant;

        function addRestaurant() {
            if (!vm.name || vm.name === '') {
                return;
            }

            restaurants.create({
                name: vm.name,
                address: vm.address,
                openingHours: vm.openingHours,
                coordinates: {
                    lat: vm.coordinates.lat,
                    long: vm.coordinates.long
                },
                urlImage: vm.restaurant.urlImage
            });

            vm.name = '';
            vm.address = '';
            vm.openingHours = '';
            vm.coordinates.lat = '';
            vm.coordinates.long = '';
        }

    }
);