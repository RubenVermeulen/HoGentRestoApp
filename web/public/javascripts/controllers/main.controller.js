angular.module('hogentResto').controller('MainController',
    function($state, restaurants, auth, alertService) {

        var vm = this;

        vm.isLoggedIn = auth.isLoggedIn;

        vm.restaurants = restaurants.restaurants;
        vm.addRestaurant = addRestaurant;
        vm.hasRestaurants = hasRestaurants;

        var alert = alertService.getAlert();
        if(alert.message != ''){
            vm.alertMessage = alert.message;
            vm.alertType = alert.type;
            alertService.resetAlert();
        }

        function addRestaurant() {
            if (!vm.name || vm.name === '' || !vm.address || vm.address === '' || !vm.openingHours || vm.openingHours === '' || !vm.lat || vm.lat === '' || !vm.long || vm.long === ''
             || !vm.urlImage || vm.urlImage === '') {
                 vm.alertmessage = 'Gelieve alle velden in te vullen.';
                 return;
            }

            if (false){
                vm.alertmessage = 'Coördinaten moeten een nummer zijn.';
                return;
            }

            restaurants.create({
                name: vm.name,
                address: vm.address,
                openingHours: vm.openingHours,
                coordinates: {
                    lat: vm.lat,
                    long: vm.long
                },
                urlImage: vm.urlImage
            }).error(function(error){
                alertService.setAlert(error.message, 'danger');
                $state.go($state.current, {}, {reload: true});
            }).then(function(){
                alertService.setAlert('Resto ' + vm.name + ' is toegevoegd.', 'success');
                $state.go('home');
            });

        }

        function hasRestaurants() {
            return vm.restaurants.length !== 0;
        }

    }
);
