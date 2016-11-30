angular.module('hogentResto').controller('RestaurantsStudentsController',
    function($state, restaurants, restaurant) {
        var vm = this;

        vm.restaurant = restaurant;

        initialize();

        vm.toggleMenus = toggleMenus;
        vm.hasMenusThisDay = hasMenusThisDay;
        vm.isDayDisabled = isDayDisabled;

        function initialize() {
            var day = new Date().getDay();
            toggleMenus(day);

            if (day > 1 && day < 6) {
                for (var i = day - 1; i > 0; i--) {
                    angular.element("#menu-day-" + i).attr('disabled', 'disabled');
                }
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
