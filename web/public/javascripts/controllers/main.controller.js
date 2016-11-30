angular.module('hogentResto').controller('MainController',
    function($state, $filter, restaurants, auth, alertService) {

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
                 vm.alertMessage = 'Gelieve alle velden in te vullen.';
                 vm.alertType = 'danger';
                 return;
            }

            if (!$.isNumeric(vm.lat) || !$.isNumeric(vm.long)){
                vm.alertMessage = 'Coördinaten moeten een getal zijn.';
                vm.alertType = 'danger';
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
                vm.alertMessage = 'Error. De server kon uw aanvraag niet verwerken.';
                vm.alertType = 'danger';
                return;
            }).then(function(){
                alertService.setAlert('Resto ' + vm.name + ' is toegevoegd.', 'success');
                $state.go('admin-restaurants');
            });

        }

        function hasRestaurants() {
            return vm.restaurants.length !== 0;
        }

    }
);
