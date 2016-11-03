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
                address: vm.address
            });

            vm.name = '';
            vm.address = '';
        };

    }
);