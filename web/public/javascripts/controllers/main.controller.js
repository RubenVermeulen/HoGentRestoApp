angular.module('hogentResto').controller('MainController',
    function($state, $filter, restaurants, auth, alertService) {

        var vm = this;

        vm.isLoggedIn = auth.isLoggedIn;
        vm.restaurants = restaurants.restaurants;

        vm.addRestaurant = addRestaurant;
        vm.hasRestaurants = hasRestaurants;
        vm.getTrafficClass = getTrafficClass;
        vm.getTrafficText = getTrafficText;

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

        function getTrafficClass(occupancy) {
            switch (getTrafficGrade(occupancy)) {
                case 0: return 'circle-traffic-green';
                case 1: return 'circle-traffic-orange';
                case 2: return 'circle-traffic-red';
            }
        }

        function getTrafficText(occupancy) {
            switch (getTrafficGrade(occupancy)) {
                case 0: return 'Rustig';
                case 1: return 'Druk';
                case 2: return 'Heel druk';
            }
        }

        function getTrafficGrade(occupancy) {
            if (occupancy < 0.30) {
                return 0
            }
            else if (occupancy < 0.70) {
                return 1
            }
            else {
                return 2;
            }
        }
    }
);
