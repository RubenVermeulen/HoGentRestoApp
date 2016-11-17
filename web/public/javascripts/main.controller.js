angular.module('hogentResto').controller('MainController',
    function($state, restaurants, auth, alertService) {
        
        var vm = this;

        vm.isLoggedIn = auth.isLoggedIn;

        vm.restaurants = restaurants.restaurants;
        vm.addRestaurant = addRestaurant;
        
        var message = alertService.getMessage();
        if(message != ''){
            vm.successmessage = alertService.getMessage();
            alertService.resetMessage();
        }

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
            }).then(function(){
                alertService.setMessage('Resto ' + vm.name + ' is toegevoegd.');
                $state.go('home');
            });

        }

    }
);