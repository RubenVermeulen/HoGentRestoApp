angular.module('hogentResto').controller('MainController',
    function($state, restaurants, auth) {
        
        var vm = this;

        vm.isLoggedIn = auth.isLoggedIn;

        vm.restaurants = restaurants.restaurants;
        vm.addRestaurant = addRestaurant;
        // vm.newRestaurant = newRestaurant;

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

            vm.successresto = "success!";
            
            $state.go('home');

        }

        // function newRestaurant() {

        //     if (!vm.add || vm.add === '') {
        //         return;
        //     }

        //     restaurants.create({
        //         name: "test xd",
        //         address: " ",
        //         openingHours: " ",
        //         coordinates: {
        //             lat: 2.5,
        //             long: 3.4
        //         },
        //         urlImage: "vm.restaurant.urlImage"
        //     });
        //     vm.add=false;
        // }
    }
);