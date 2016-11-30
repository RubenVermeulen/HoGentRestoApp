angular.module('hogentResto').controller('RestaurantsStudentsController',
    function($state, restaurants, restaurant) {
        var vm = this;

        vm.restaurant = restaurant;

        initialize();

        vm.toggleMenus = toggleMenus;
        vm.hasMenusThisDay = hasMenusThisDay;
        vm.isDayDisabled = isDayDisabled;
        vm.getTrafficClass = getTrafficClass;
        vm.getTrafficText = getTrafficText;

        function initialize() {
            var day = new Date().getDay();
            toggleMenus(day);

            if (day > 1 && day < 6) {
                for (var i = day - 1; i > 0; i--) {
                    angular.element("#menu-day-" + i).attr('disabled', 'disabled');
                }
            }

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
            console.log(occupancy);

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

        function isDayDisabled(day) {
            var attr = angular.element("#menu-day-" + day).attr('disabled');

            console.log(attr);

            if (attr !== undefined && attr !== false) {
                return true;
            }

            return false;
        }

        function hasMenusThisDay() {
            return vm.menus ? vm.menus.length !== 0 : false;
        }

        function toggleMenus(day) {
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
